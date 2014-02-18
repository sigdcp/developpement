package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import ci.gouv.budget.solde.sigdcp.dao.identification.CompteUtilisateurDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
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
		
	/*------------------------------------------  Contraintes  --------------------------------------------------------------*/

	
	public void validateMatricule(TypeAgentEtat typeAgentEtat,String matricule) throws Exception{
		if(Code.TYPE_AGENT_ETAT_GENDARME.equals(typeAgentEtat.getCode())){
			if(!matricule.matches(matriculeGendarmePattern))
				exception(ValidationExceptionType.MATRICULE_FORMAT);
		}else if(!matricule.matches(matriculeFonctionnairePattern))
			exception(ValidationExceptionType.MATRICULE_FORMAT);
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
	
	public void validateGrade(Grade grade) throws Exception{
		if(grade==null)
			exception();
	}
	
	public void validateDatePriseService(AgentEtat agentEtat,Date datePriseService) throws Exception{
		try {
			validateDateNaissance(agentEtat.getDateNaissance());
		} catch (Exception e) {
			exception(ValidationExceptionType.DATE_PRISE_SERVICE);
		}
		
		if(datePriseService==null || datePriseService.before(DateUtils.addYears(agentEtat.getDateNaissance(), getAgeMinimumAns())))
			exception(ValidationExceptionType.DATE_PRISE_SERVICE);
	}
	
	public void validateDateFinService(Date datePriseService,Date dateFinService) throws Exception{
		if(isOneNull(dateFinService) || dateFinService.before(datePriseService))
			exception();
	}
	
	public void validateDateMiseStage(AgentEtat agentEtat,Date dateMiseStage) throws Exception{
		try {
			validateDateNaissance(agentEtat.getDateNaissance());
		} catch (Exception e) {
			exception(ValidationExceptionType.DATE_PRISE_SERVICE);
		}
		
		if(dateMiseStage==null || dateMiseStage.before(DateUtils.addYears(agentEtat.getDateNaissance(), getAgeMinimumAns())))
			exception(ValidationExceptionType.DATE_PRISE_SERVICE);
	}
	
	public void validateDateFinStage(Date dateMiseStage,Date dateFinStage) throws Exception{
		if(isOneNull(dateMiseStage) || dateFinStage.before(dateMiseStage))
			exception();
	}
	
	public void validateDateDepart(AgentEtat agentEtat,Date dateDepart) throws Exception{
		if(isOneNull(dateDepart) || dateDepart.before(agentEtat.getDateNaissance()))
			exception();
	}
	
	public void validateDateArrivee(Date dateDepart,Date dateArrivee) throws Exception{
		if(isOneNull(dateDepart,dateArrivee) || dateArrivee.before(dateDepart))
			exception();
	}
	
	public void validateVilleDepart(AgentEtat agentEtat,Localite ville) throws Exception{
		if(isOneNull(ville))
			exception();
	}
	
	public void validateVilleArrivee(Localite depart,Localite arrivee) throws Exception{
		if(isOneNull(depart,arrivee) || arrivee.equals(depart))
			exception();
	}
	
	public void validateServiceAccueil(Section section) throws Exception{
		if(isOneNull(section))
			exception();
	}
	
	public void validateMontantFacture(Float montant) throws Exception{
		if(isOneNull(montant) || montant<=0)
			exception();
	}
	
	public void validateObjetMission(String objet) throws Exception{
		if(isOneNull(objet))
			exception();
	}
	
	public void validateDateDeces(Date dateNaissance,Date dateDeces) throws Exception{
		if(isOneNull(dateNaissance,dateDeces) || dateDeces.before(dateNaissance) || dateDeces.after(new Date()))
			exception();
	}
	
	public void validatePieceJustificativeNumero(Boolean soumission,String numero,Date date,Fonction signataire,byte[] fichier,PieceJustificativeAFournir model) throws Exception{
		if(soumission || Boolean.TRUE.equals(model.getPrincipale())){
			if(isNull(numero))
				exception();
		}else if(Boolean.TRUE.equals(model.getDerivee()))
			;
		else if(isNull(numero) && isOneNotNull(date,signataire/*,fichier*/))
				exception();
		
	}
	
	public void validatePieceJustificativeDateEtablissement(Boolean soumission,String numero,Date date,Fonction signataire,byte[] fichier,PieceJustificativeAFournir model) throws Exception{
		if(soumission || Boolean.TRUE.equals(model.getPrincipale())){
			if(isOneNull(date))
				exception();
		}else if(Boolean.TRUE.equals(model.getDerivee()))
			;
		else if(isNull(date) && isOneNotNull(numero,signataire/*,fichier*/))
			exception();
		
	}
	
	public void validatePieceJustificativeFonctionSignataire(Boolean soumission,String numero,Date date,Fonction signataire,byte[] fichier,PieceJustificativeAFournir model) throws Exception{
		if(soumission || Boolean.TRUE.equals(model.getPrincipale())){
			if(isOneNull(signataire))
				exception();
		}else if(Boolean.TRUE.equals(model.getDerivee()))
			;
		else if(isNull(signataire) && isOneNotNull(numero,date/*,fichier*/))
			exception();
		
	}
	
	public void validatePieceJustificativeFichier(Boolean soumission,String numero,Date date,Fonction signataire,byte[] fichier,PieceJustificativeAFournir model) throws Exception{
		
		if(soumission || Boolean.TRUE.equals(model.getPrincipale())){
			if(isOneNull(fichier))
				exception();
		}else if(Boolean.TRUE.equals(model.getDerivee()))
			;
		else if(!isNull(fichier) && isOneNotNull(numero,date,signataire))
			exception();
		
	}
	
	
	/* -------------------------------------------  Constantes  ----------------------------------------------------- */
	
	@Getter private final String matriculeFonctionnairePattern = "\\d\\d\\d\\d\\d\\d[a-zA-Z]";
	@Getter private final String matriculeGendarmePattern = "\\d\\d\\d\\d\\d\\d[a-zA-Z]";
	
	@Getter private Integer passwordLongueurMinimum = 8;
	@Getter private Integer passwordLongueurMaximum = 128;
	
	public String getPasswordPolicy(){
		return textService.find("policy.password", new Object[]{passwordLongueurMinimum,passwordLongueurMaximum});
	}
	
	@Getter private Integer ageMinimumAns = 19;
	
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
	
}
