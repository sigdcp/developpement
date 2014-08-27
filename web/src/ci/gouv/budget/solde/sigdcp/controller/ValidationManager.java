package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.extern.java.Log;

import org.primefaces.model.UploadedFile;

import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ValidationPolicy;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ValidationPolicy.InfosFichierATelecharger;

/**
 * Ensemble de methode pour la validation des champs de saisie.<br/>
 * Une methode doit etre liee a un champ de saisie dans un formulaire en vue de capturer le message
 * dans son composant <b>message</b> associe. <b>f:attribute</b> devra etre utiliser si d'autres champs
 * de saisie sont necessaire lors de la validation du champ a valider
 * 
 *<br/><br/>
 *Aucune de ces methode ne doit implementer une logique de validation mais plutot solliciter l'une des methode du service dans la classe
 *<code>ValidationPolicy</code>
 * 
 * @author Komenan Y .Christian
 *
 */
@Named @Log
public class ValidationManager implements Serializable {

	private static final long serialVersionUID = -5187172212708031726L;
	
	@Inject private ValidationPolicy validation; 
	@Inject private UserSessionManager userSessionManager;
	
	/* Identification */
	
	public void validateMatricule(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateMatricule(attribute(uiComponent, TypeAgentEtat.class, "typePersonne",true), (String) value);
		} catch (Exception e) {
			validationException(uiComponent,e);
		}		
	}
	
	public void validateNationalite(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateNationalite((Boolean)uiComponent.getAttributes().get("expatrie"),(Localite) value);
		} catch (Exception e) {
			validationException(uiComponent,e);
		}
	}
	
	public void validateDateNaissance(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateNaissance((Date) value);
		} catch (Exception e) {
			validationException(uiComponent,e);
		}
	}
	
	public void validateDateMariage(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateMariage(userSessionManager.getUser().getSexe(),userSessionManager.getUser().getDateNaissance(),(Date) value);
		} catch (Exception e) {
			validationException(uiComponent,e);
		}
	}
	
	public void validateDateRetraite(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateRetraite(attribute(uiComponent, Date.class, "datePriseService",true),(Date) value);
		} catch (Exception e) {
			validationException(uiComponent,e);
		}
	}
	
	public void validateDateDeces(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateDeces(attribute(uiComponent, Date.class, "dateNaissance"),(Date) value);
		} catch (Exception e) {
			validationException(uiComponent,e);
		}
	}
	
	public void validatePassword(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validatePassword((String) value);
		} catch (Exception e) {
			validationException((UIInput) uiComponent, e.getMessage());
		}
	}
	
	public void validateConfirmationPassword(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateConfirmationPassword(attribute(uiComponent, String.class, "password"),(String) value);
		} catch (Exception e) {
			validationException((UIInput) uiComponent, e.getMessage());
		}
	}
	
	public void validateCodeDeverouillage(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateCodeDeverouillage((String) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateAdresseElectroniqueCompte(FacesContext facesContext,UIComponent uiComponent,Object value){
		String typeEmail = (String) uiComponent.getAttributes().get("typeEmail");
		
		try {
			if(typeEmail==null || "compte".equals(typeEmail))
				validation.validateUsernameUnique((String) value);
			else
				validation.validateEmailUnique((String) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateMontantFacture(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateMontantFacture(new BigDecimal((Double) value));
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validatePoidsBaggage(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validatePoidsBaggage(new BigDecimal((Double) value));
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateTelephone(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateTelephone((String) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	/* Demande */
	
	public void validateGrade(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateGrade((Grade) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateDatePriseService(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDatePriseService( (AgentEtat)userSessionManager.getUser(), (Date)value,attribute(uiComponent, Date.class, "dateFinService",true));
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateDateFinService(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateFinService(attribute(uiComponent, Date.class, "datePriseService"),(Date)value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	/*
	public void validateDateFinContrat(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateFinContrat( (AgentEtat)userSessionManager.getUser(), (Date)value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}*/
	
	public void validateDateDepart(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			//System.out.println(value);
			validation.validateDateDepart(attribute(uiComponent, TypeDepense.class, "typeDepense"),(AgentEtat) uiComponent.getAttributes().get("agentEtat"),
					(Date) value,attribute(uiComponent, Date.class, "datePriseService",true),attribute(uiComponent, Date.class, "dateFinService",true));
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateDateArrivee(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateArrivee(attribute(uiComponent, TypeDepense.class, "typeDepense"),attribute(uiComponent, Date.class, "dateFinService",true),
					attribute(uiComponent, Date.class, "dateDepart"),(Date) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateDateRetour(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateRetour(attribute(uiComponent, TypeDepense.class, "typeDepense"),attribute(uiComponent, Date.class, "dateDepart"),(Date) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateDateDistributionBCCSSotra(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateDistributionBCCSSotra((Date) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateDateDistributionBCCSDelegue(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateDistributionBCCSDelegue((Date)uiComponent.getAttributes().get("dateDistributionSotra"),(Date) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateDateDepotDossier(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateDepotDossier((Date) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateLocaliteDepart(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateVilleDepart((AgentEtat)userSessionManager.getUser(),(Localite) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateLocaliteArrivee(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateVilleArrivee(attribute(uiComponent, Localite.class, "localiteDepart"),(Localite) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateServiceAccueil(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateServiceAccueil((Section) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validatePieceJustificativeNumero(FacesContext facesContext,UIComponent uiComponent,Object value){
		
		try {
			//System.out.println( uiComponent.getAttributes().get("fichier") );
			//UploadedFile file = (UploadedFile) attribute(uiComponent, FileUpload.class, "fichier").getValue();
			InfosFichierATelecharger fichier = null;//new InfosFichierATelecharger(file.getFileName(),file.getSize());
			Date dateEtatblissement = null;//attribute(uiComponent, Date.class, "dateEtablissement");
			Fonction signataire = null; //attribute(uiComponent, Fonction.class, "signataire")
			PieceJustificativeAFournir model = null;//(PieceJustificativeAFournir)uiComponent.getAttributes().get("model")
			//validation.validatePieceJustificativeNumero(false, (String) value, dateEtatblissement, signataire,fichier,model);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
		
	}
	
	public void validatePieceJustificativeDateEtablissement(FacesContext facesContext,UIComponent uiComponent,Object value){
		//UploadedFile file = (UploadedFile) attribute(uiComponent, FileUpload.class, "fichier").getValue();
		InfosFichierATelecharger fichier = null;//new InfosFichierATelecharger(file.getFileName(),file.getSize());
		String numero = null;//attribute(uiComponent, String.class, "numero")
		Fonction signataire = null; //attribute(uiComponent, Fonction.class, "signataire")
		PieceJustificativeAFournir model = (PieceJustificativeAFournir)uiComponent.getAttributes().get("model");
		try {
			validation.validatePieceJustificativeDateEtablissement((Boolean)uiComponent.getAttributes().get("soumission"), numero, (Date) value, signataire,fichier,model);
		} catch (Exception e) {
			validationException(uiComponent, model.getTypePieceJustificative().getLibelle()+" : "+e.getMessage());
		}
		
	}
	
	public void validatePieceJustificativeFonctionSignataire(FacesContext facesContext,UIComponent uiComponent,Object value){
		
		try {
			//UploadedFile file = (UploadedFile) attribute(uiComponent, FileUpload.class, "fichier").getValue();
			InfosFichierATelecharger fichier = null;//new InfosFichierATelecharger(file.getFileName(),file.getSize());
			String numero = null;//attribute(uiComponent, String.class, "numero")
			Date dateEtatblissement = null;//attribute(uiComponent, Date.class, "dateEtablissement");
			PieceJustificativeAFournir model = null;//(PieceJustificativeAFournir)uiComponent.getAttributes().get("model")
			//validation.validatePieceJustificativeFonctionSignataire(false, numero, dateEtatblissement, (Fonction)value,fichier,model);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
		
	}
	
	public void validatePieceJustificativeFichier(FacesContext facesContext,UIComponent uiComponent,Object value){
		
		UploadedFile file = (UploadedFile) value;
		String numero = null;//attribute(uiComponent, String.class, "numero")
		Date dateEtatblissement = null;//attribute(uiComponent, Date.class, "dateEtablissement");
		Fonction signataire = null; //attribute(uiComponent, Fonction.class, "signataire")
		PieceJustificativeAFournir model = (PieceJustificativeAFournir)uiComponent.getAttributes().get("model");
		try {
			validation.validatePieceJustificativeFichier(false, numero, dateEtatblissement, signataire,new InfosFichierATelecharger(file.getFileName(),file.getSize()),model);
		} catch (Exception e) {
			validationException(uiComponent, model.getTypePieceJustificative().getLibelle()+" : "+e.getMessage());
		}
		
	}
	
	public void validatePieceFichier(FacesContext facesContext,UIComponent uiComponent,Object value){			
		UploadedFile file = (UploadedFile) value;
		try {
			validation.validatePieceJustificativeFichier(false, null, null, null,new InfosFichierATelecharger(file.getFileName(),file.getSize()),
					(PieceJustificativeAFournir)uiComponent.getAttributes().get("model"));
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
		
	}
		
	public void validateNumeroDossier(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateNumeroDossier((Long) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateProfessionMission(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateProfessionMission(attribute(uiComponent, Fonction.class, "fonction"), (Profession) value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	/*----------------------------------------------------------------------------------------------------------*/
	
	private void validationException(UIInput input,String message){
		input.setValid(Boolean.FALSE);
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message));
	}
	
	private void validationException(UIComponent uiComponent,String message){
		if(uiComponent instanceof UIInput)
			validationException((UIInput) uiComponent, message);
		log.severe("Validation of Component Type "+uiComponent.getClass()+" Not handled");
	}
	
	private void validationException(UIComponent uiComponent,Exception exception){
		validationException(uiComponent, exception.getMessage());
	}
	
	@SuppressWarnings("unchecked")
	private <T> T attribute(UIComponent uiComponent,Class<T> type,String name,boolean nullable){
		Object object = uiComponent.getAttributes().get(name);
		if(object==null){
			if(!nullable)
				log.severe("Attribute <"+name+"> of Component "+uiComponent.getClass()+" must not be null");
			return null;
		}
		if(object instanceof UIInput)
			return (T) ((UIInput)object).getValue();
		return (T) object;
	}
	
	private <T> T attribute(UIComponent uiComponent,Class<T> type,String name){
		return attribute(uiComponent, type, name, false);
	}

}
