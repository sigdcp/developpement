package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatReferenceDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentMissionReferenceDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.CompteUtilisateurDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.service.utils.TextService;
/**
 * Definit les contraintes de validation des donnees fournit par le client.<br/>
 * Ces contraintes sont exposees sous formes de methodes en vue d'etre utilisee par le service lui meme et le client<br/>
 * pour la validation des donnees. Une methode de validation prend les parametres necessaires pour effectuer la validation 
 * genere un exception en cas de non respect de la contrainte. Ainsi , cette exception pourrait etre capturee et presentee a l'utilisateur du syteme.<br/><br/>
 * 
 * @author Komenan Y .Christian
 *
 */
@Singleton @Named
public class ValidationPolicy {

	@Inject private DossierDao dossierDao;
	//@Inject private DossierDDDao dossierDDDao;
	@Inject private AgentEtatDao agentEtatDao;
	@Inject private AgentEtatReferenceDao agentEtatReferenceDao;
	@Inject private AgentMissionReferenceDao agentMissionReferenceDao;
	
	/*------------------------------------------  Contraintes  --------------------------------------------------------------*/

	public void validateDepartRetraite(String matricule) throws Exception{
		AgentEtat agentEtat = agentEtatDao.readByMatricule(matricule);
		//est t il connu
		if(agentEtat==null)
			return;
		//a t il un  dossier
		Collection<Dossier> dossiers = dossierDao.readByAgentEtat(agentEtat);
		for(Dossier dossier : dossiers)
			if(dossier instanceof DossierDD)
				if(Code.NATURE_DEPLACEMENT_RETRAITE.equals(dossier.getDeplacement().getNature().getCode()))
					if(Code.NATURE_OPERATION_REGLER_BL.equals(dossier.getTraitable().getDernierTraitement().getOperation().getNature().getCode())){
						exception(ValidationExceptionType.DEPART_RETRAITE_TRAITE);
					}else if(!Code.STATUT_REJETE.equals(dossier.getTraitable().getDernierTraitement().getStatut().getCode())){
						if(!Code.NATURE_OPERATION_SAISIE.equals(dossier.getTraitable().getDernierTraitement().getOperation().getNature().getCode())){
							exception(ValidationExceptionType.DEPART_RETRAITE_ENCOURS);
						}
					}
	}
	
	public void validateMatricule(TypeAgentEtat typeAgentEtat,String matricule) throws Exception{
		if(typeAgentEtat==null){
			
		}else if(Code.TYPE_AGENT_ETAT_FONCTIONNAIRE.equals(typeAgentEtat.getCode())){
			exception(!matricule.matches(matriculeFonctionnairePattern),ValidationExceptionType.MATRICULE_FORMAT);
			exception(agentEtatReferenceDao.read(matricule)==null,ValidationExceptionType.MATRICULE_NON_REFERENCE);	
		}else if(Code.TYPE_AGENT_ETAT_CONTRACTUEL.equals(typeAgentEtat.getCode())){
			exception(!matricule.matches(matriculeContractuelPattern),ValidationExceptionType.MATRICULE_FORMAT);
			exception(agentEtatReferenceDao.read(matricule)==null,ValidationExceptionType.MATRICULE_NON_REFERENCE);	
		}else if(Code.TYPE_AGENT_ETAT_GENDARME.equals(typeAgentEtat.getCode())){
			//if(!matricule.matches(matriculeGendarmePattern))
			//	exception(ValidationExceptionType.MATRICULE_FORMAT);
		}else if(Code.TYPE_AGENT_ETAT_MISSION.equals(typeAgentEtat.getCode())){
			
		}
	}
	
	public void validateNationalite(Boolean expatrie,Localite pays) throws Exception{
		//if(pays==null || (Boolean.TRUE.equals(expatrie) && Code.LOCALITE_COTE_DIVOIRE.equals(pays.getCode())) || (!Boolean.TRUE.equals(expatrie) && !Code.LOCALITE_COTE_DIVOIRE.equals(pays.getCode())))
		//	exception(ValidationExceptionType.NATIONALITE);
	}
	
	public void validateDateNaissance(Date dateNaissance) throws Exception{
		if(dateNaissance==null || !dateNaissance.before(getDateNaissanceMinimum()))
			exception(ValidationExceptionType.DATE_NAISSANCE_MAJEUR);
	}
	
	public void validateCodeDeverouillage(String code) throws Exception{
		
	}
	
	public void validatePassword(String password) throws Exception{
		if(password==null ||password.length()<passwordLongueurMinimum)
			exception(ValidationExceptionType.PASSWORD);
	}
	
	public void validateConfirmationPassword(String password1,String password2) throws Exception{
		try {
			validatePassword(password1);
		} catch (Exception e) {
			exception(ValidationExceptionType.PASSWORD_CONFIRMATION);
		}
		if(!password1.equals(password2))
			exception(ValidationExceptionType.PASSWORD_CONFIRMATION);
	}
	
	public void validateUsernameUnique(String username) throws Exception{
		if(compteUtilisateurDao.readByUsername(username)!=null)
			exception(ValidationExceptionType.EMAIL_OCCUPE);
	}
	
	public void validateEmailUnique(String email) throws Exception{
		validateUsernameUnique(email);
		if(agentMissionReferenceDao.readByEmail(email)!=null)
			exception(ValidationExceptionType.EMAIL_REFERENCE);
	}
	
	public void validateTelephone(String telephone) throws Exception{
		exception(!telephonePattern.matcher(telephone).matches(),ValidationExceptionType.TELEPHONE);
	}
	
	public void validateGrade(Grade grade) throws Exception{
		if(grade==null)
			exception();
	}
	
	public void validateDatePriseService(AgentEtat agentEtat,Date datePriseService,Date dateFinService) throws Exception{
		try {
			validateDateNaissance(agentEtat.getDateNaissance());
		} catch (Exception e) {
			exception(ValidationExceptionType.DATE_PRISE_SERVICE);
		}
		
		if(datePriseService==null || datePriseService.before(DateUtils.addYears(agentEtat.getDateNaissance(), getAgeMinimumAns())) || datePriseService.after(tempsCourant())
				|| (dateFinService!=null && datePriseService.after(dateFinService)))
			exception(ValidationExceptionType.DATE_PRISE_SERVICE);
	}
	
	/*
	public void validateDateFinContrat(AgentEtat agentEtat,Date datePriseService,Date dateFinService) throws Exception{
		
	}
	*/
	
	public void validateDateFinService(Date datePriseService,Date dateFinService) throws Exception{
		if(isOneNull(datePriseService,dateFinService) || dateFinService.before(datePriseService) || dateFinService.equals(datePriseService))
			exception(ValidationExceptionType.DATE_FIN_SERVICE);
	}
	
	public void validateDateMiseStage(AgentEtat agentEtat,Date dateMiseStage) throws Exception{
		try {
			validateDateNaissance(agentEtat.getDateNaissance());
		} catch (Exception e) {
			exception(ValidationExceptionType.DATE_MISE_STAGE);
		}
		
		if(dateMiseStage==null || dateMiseStage.before(DateUtils.addYears(agentEtat.getDateNaissance(), getAgeMinimumAns())))
			exception(ValidationExceptionType.DATE_MISE_STAGE);
	}
	
	public void validateDateFinStage(Date dateMiseStage,Date dateFinStage) throws Exception{
		if(isOneNull(dateMiseStage) || dateFinStage.before(dateMiseStage))
			exception();
	}
	
	public void validateDateDepart(TypeDepense typeDepense,AgentEtat agentEtat,Date dateDepart,Date datePriseService,Date dateFinService) throws Exception{
		
		if(isOneNull(dateDepart) || (agentEtat.getDateNaissance()!=null && dateDepart.before(agentEtat.getDateNaissance()))
				|| (datePriseService!=null && dateDepart.before(datePriseService))
				|| (dateFinService!=null && dateDepart.before(dateFinService)))
			exception(ValidationExceptionType.DATE_DEPART);
		
		Date tc = tempsCourant();
		switch(typeDepense.getCode()){
		case Code.TYPE_DEPENSE_PRISE_EN_CHARGE:
			//if(dateDepart.before(tc))
				//exception(ValidationExceptionType.DATE_DEPART);
			break;
		case Code.TYPE_DEPENSE_REMBOURSEMENT:
			if(dateDepart.after(tc))
				exception(ValidationExceptionType.DATE_DEPART);
			break;
		}
		
	}
	
	public void validateDateArrivee(TypeDepense typeDepense,Date dateFinService,Date dateDepart,Date dateArrivee) throws Exception{		
		if(isOneNull(dateDepart,dateArrivee) || dateArrivee.before(dateDepart)|| (dateFinService!=null && dateArrivee.before(dateFinService)) )
			exception(ValidationExceptionType.DATE_ARRIVEE);
		Date tc = tempsCourant();
		switch(typeDepense.getCode()){
			case Code.TYPE_DEPENSE_PRISE_EN_CHARGE:
				if(dateArrivee.before(dateDepart) || dateArrivee.before(tc))
					exception(ValidationExceptionType.DATE_ARRIVEE);
				break;
			case Code.TYPE_DEPENSE_REMBOURSEMENT:
				if(dateArrivee.before(dateDepart) || dateArrivee.after(tc))
					exception(ValidationExceptionType.DATE_ARRIVEE);
				break;
			}
		
	}
	
	public void validateDateRetour(TypeDepense typeDepense,Date dateDepart,Date dateRetour) throws Exception{
		if(isOneNull(dateDepart,dateRetour) || dateRetour.before(dateDepart) )
			exception(ValidationExceptionType.DATE_RETOUR);
		Date tc = tempsCourant();
		switch(typeDepense.getCode()){
			case Code.TYPE_DEPENSE_PRISE_EN_CHARGE:
				if(dateRetour.before(tc))
					exception(ValidationExceptionType.DATE_RETOUR);
				break;
			case Code.TYPE_DEPENSE_REMBOURSEMENT:
				if(dateRetour.after(tc))
					exception(ValidationExceptionType.DATE_RETOUR);
				break;
			}
		
	}
	
	public void validateDateDepotDossier(Date date) throws Exception{
		exception(isOneNull(date) || date.after(tempsCourant()),ValidationExceptionType.DATE_DEPOT_DOSSIER);
	}
	
	public void validateDateDistributionBCCSSotra(Date date) throws Exception{
		exception(isOneNull(date) || date.after(tempsCourant()),ValidationExceptionType.DATE_DISTRIBUTION);
	}
	
	public void validateDateDistributionBCCSDelegue(Date dateDistributionSotra,Date date) throws Exception{
		exception(isOneNull(date) || date.after(dateDistributionSotra),ValidationExceptionType.DATE_DISTRIBUTION);
	}
	
	public void validateVilleDepart(AgentEtat agentEtat,Localite ville) throws Exception{
		if(isOneNull(ville))
			exception(ValidationExceptionType.LOCALITE_DEPART);
	}
	
	public void validateVilleArrivee(Localite depart,Localite arrivee) throws Exception{
		if(isOneNull(depart,arrivee) || arrivee.equals(depart))
			exception(ValidationExceptionType.LOCALITE_ARRIVEE);
	}
	
	public void validateServiceAccueil(Section section) throws Exception{
		if(isOneNull(section))
			exception();
	}
	
	public void validateMontantFacture(BigDecimal montant) throws Exception{
		if(isOneNull(montant) || montant.intValue()<=0)
			exception();
	}
	
	public void validatePoidsBaggage(BigDecimal montant) throws Exception{
		if(isOneNull(montant) || montant.intValue()<=0)
			exception();
	}
	
	public void validateNumeroDossier(Long numero) throws Exception{
		//System.out.println("Dossier "+numero+" Existe : "+dossierDao.exist(numero));
		if(!dossierDao.exist(numero))
			exception();
	}
	
	public void validateDateMariage(Sexe sexe,Date dateNaissance,Date dateMariage) throws Exception{
		if(Sexe.MASCULIN.equals(sexe)){
			if(isOneNull(dateNaissance,dateMariage) || dateMariage.before(DateUtils.addYears(dateNaissance, ageMinimumAns)) || dateMariage.after(tempsCourant()))
				exception(ValidationExceptionType.DATE_MARIAGE);
		}
	}
	
	public void validateDateRetraite(Date datePriseService,Date dateRetraite) throws Exception{
		if(isOneNull(datePriseService,dateRetraite) || dateRetraite.before(datePriseService) || dateRetraite.after(tempsCourant()))
			exception(ValidationExceptionType.DATE_RETRAITE);
		
	}
	
	public void validateDateDeces(Date dateNaissance,Date dateDeces) throws Exception{
		if(isOneNull(dateNaissance,dateDeces) || dateDeces.before(DateUtils.addYears(dateNaissance, ageMinimumAns)) || dateDeces.after(tempsCourant()))
			exception(ValidationExceptionType.DATE_DECES);
	}
	
	public void validatePieceJustificativeNumero(Boolean soumission,String numero,Date date,Fonction signataire,InfosFichierATelecharger fichier,PieceJustificativeAFournir model) throws Exception{
		if(soumission || Boolean.TRUE.equals(model.getConfig().getAdministrative()) || Boolean.TRUE.equals(model.getConfig().getPrincipale())){
			if(isNull(numero))
				exception();
		}else if(Boolean.TRUE.equals(model.getConfig().getDerivee()))
			;
		/*
		else if(Boolean.TRUE.equals(model.getConfig().getAdministrative())){
			if(isNull(numero))
				exception();
		}else if(isNull(numero) /*&& isOneNotNull(date/*,signataire/*,fichier.getName()))
				exception();
		*/
		//validateFichier(fichier);
	}
	
	public void validatePieceJustificativeDateEtablissement(Boolean soumission,String numero,Date date,Fonction signataire,InfosFichierATelecharger fichier,PieceJustificativeAFournir model) throws Exception{
		if(soumission || Boolean.TRUE.equals(model.getConfig().getPrincipale()) || Boolean.TRUE.equals(model.getConfig().getAdministrative())){
			validateDateEtablissementPiece(model,date);
		}else if( Boolean.FALSE.equals(model.getConfig().getDerivee()) && date!=null){
			validateDateEtablissementPiece(model,date);
		}
		/*
		else if(Boolean.TRUE.equals(model.getConfig().getDerivee())){
			validateDateEtablissementPiece(date);
		}else /*if(isNull(date) /*&& isOneNotNull(numero/*,signataire/*,fichier.getName()))*{
			validateDateEtablissementPiece(date);
		}
		*/
		//validateFichier(fichier);
	}
	
	public void validatePieceJustificativeFonctionSignataire(Boolean soumission,String numero,Date date,Fonction signataire,InfosFichierATelecharger fichier,PieceJustificativeAFournir model) throws Exception{
		if(soumission || Boolean.TRUE.equals(model.getConfig().getPrincipale())){
			if(isOneNull(signataire))
				exception();
		}else if(Boolean.TRUE.equals(model.getConfig().getDerivee()))
			;
		else if(Boolean.TRUE.equals(model.getConfig().getAdministrative())){
			if(isNull(signataire))
				exception();
		}else if(isNull(signataire) /*&& isOneNotNull(numero,date/*,fichier.getName())*/)
			exception();
		
		//validateFichier(fichier);
	}
	
	public void validatePieceJustificativeFichier(Boolean soumission,String numero,Date date,Fonction signataire,InfosFichierATelecharger fichier,PieceJustificativeAFournir model) throws Exception{
		
		if(soumission || Boolean.TRUE.equals(model.getConfig().getPrincipale()) || Boolean.TRUE.equals(model.getConfig().getAdministrative())){
			if(isOneNull(fichier.getName()))
				exception(ValidationExceptionType.FICHIER_OBLIGATOIRE);
		}else if(fichier.getName()==null){
			return;
		}
		validateFichier(fichier);
	}
	
	public void validateFichier(InfosFichierATelecharger fichier) throws Exception{
		if(StringUtils.isEmpty(fichier.getName()))
			return;
		if(!ArrayUtils.contains(pieceExtensions, FilenameUtils.getExtension(fichier.getName())))
			exception(ValidationExceptionType.FICHIER_EXTENSION);
		if(fichier.getSize() > filePieceSizeMaximum)
			exception(ValidationExceptionType.FICHIER_TAILLE);
	}
	
	public void validateDateEtablissementPiece(PieceJustificativeAFournir model,Date date) throws Exception{
		Date tc = tempsCourant();
		exception(date==null || date.after(tc),ValidationExceptionType.DATE_ETABLISSEMENT_PIECE);
		int tcAnnee,dateAnnee;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tc);
		tcAnnee = calendar.get(Calendar.YEAR);
		calendar.setTime(date);
		dateAnnee = calendar.get(Calendar.YEAR);
		switch(model.getTypePieceJustificative().getCode()){
		case Code.TYPE_PIECE_DECISION_CONGE:
			exception(tcAnnee!=dateAnnee,ValidationExceptionType.DATE_ETABLISSEMENT_PIECE);break;
			
			
		}
	}
	
	public void validateProfessionMission(Fonction fonction,Profession profession) throws Exception{
		if(profession==null && fonction==null)
			exception();
	}
	
	
	/* -------------------------------------------  Constantes  ----------------------------------------------------- */
	
	@Getter private final String matriculeFonctionnairePattern = "\\d\\d\\d\\d\\d\\d[a-zA-Z]";
	@Getter private final String matriculeGendarmePattern = "\\d\\d\\d\\d\\d\\d[a-zA-Z]";
	@Getter private final String matriculeContractuelPattern = "5\\d\\d\\d\\d\\d[a-zA-Z]";
	@Getter private final Pattern telephonePattern = Pattern.compile("\\d{8,8}");
	
	@Getter private Integer passwordLongueurMinimum = 6;
	@Getter private Integer passwordLongueurMaximum = 15;
	
	@Getter private Integer filePieceSizeMaximum = 1024 * 1000 * 2;// 2 Mega
	
	@Getter private Integer ageMinimumAns = 19;
	
	@Getter private String[] pieceExtensions = {
			"jpg","jpeg","png","bmp","tiff","gif", //images
			"doc","docx", //microsoft office,
			"pdf"
			};
	
	public String getPasswordPolicy(){
		return textService.find("policy.password", new Object[]{passwordLongueurMinimum,passwordLongueurMaximum});
	}
	
	public String getPieceFilePolicy(){
		int f = filePieceSizeMaximum/(1024*1000);
		return textService.find("policy.file.piece", new Object[]{StringUtils.join(pieceExtensions," , "),f+" Mega"});
	}
	
	public Date getDateNaissanceMinimum(){
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.YEAR, -ageMinimumAns);
		return calendar.getTime();
	}
	
	public Date getDateNaissanceMaximum(){
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.YEAR, -100);
		return calendar.getTime();
	}
	
	/*------------------------------------------- Bean aidants  ------------------------------------------------------*/
	
	@Inject private TextService textService;
	@Inject private CompteUtilisateurDao compteUtilisateurDao;
	
	/*------------------------------- Fonctions racourcis ---------------------------------------------------*/
	
	private void exception(ValidationExceptionType type,Object[] parameters) throws Exception{
		throw new Exception(textService.find(type.getId(), parameters));
	}
	
	private void exception(ValidationExceptionType type) throws Exception{
		exception(type, null);
	}
	
	private void exception(Boolean condition,ValidationExceptionType type) throws Exception{
		if(Boolean.TRUE.equals(condition))
			exception(type, null);
	}
	
	private void exception() throws Exception{
		exception(ValidationExceptionType._DEFAULT);
	}
		
	public void validateNotNull(Object value) throws Exception{
		if(isNull(value))
			exception();
	}
	
	protected boolean isNull(Object value){
		if(value instanceof String)
			return StringUtils.isEmpty((CharSequence) value);
		return value == null;
	}
	
	protected boolean isOneNull(Object...objects){
		for(Object object : objects)
			if(isNull(object))
				return true;
		return false;
	}
	
	protected boolean isOneNotNull(Object...objects){
		for(Object object : objects)
			if(!isNull(object))
				return true;
		return false;
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor @NoArgsConstructor
	public static class InfosFichierATelecharger implements Serializable{
		 
		private static final long serialVersionUID = -822491877136922958L;
		
		private String name;
		private Long size;
		
		
	}
	
	protected Date tempsCourant(){
		//Calendar calendar = Calendar.getInstance();
		//return new DateTime().withZone(DateTimeZone.UTC).withTime(calendar.get(Calendar.HOUR), 0, 0, 0).toDate();
		return new DateTime().withZone(DateTimeZone.UTC).withTime(0, 0, 0, 0).toDate();
	}
	
}
