package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ValidationPolicy;

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
			validation.validateMatricule(attribute(uiComponent, TypeAgentEtat.class, "typePersonne"), (String) value);
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
		try {
			validation.validateUsernameUnique((String) value);
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
			validation.validateDatePriseService( (AgentEtat)userSessionManager.getUser(), (Date)value);
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validateDateArrivee(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validateDateArrivee(attribute(uiComponent, Date.class, "dateDepart"),(Date) value);
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
			validation.validatePieceJustificativeNumero(false, (String) value, 
					attribute(uiComponent, Date.class, "dateEtablissement"), attribute(uiComponent, Fonction.class, "signataire"),
					null,(PieceJustificativeAFournir)uiComponent.getAttributes().get("model"));
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validatePieceJustificativeDateEtablissement(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validatePieceJustificativeDateEtablissement(false, attribute(uiComponent, String.class, "numero"), 
					(Date) value, attribute(uiComponent, Fonction.class, "signataire"),
					null,(PieceJustificativeAFournir)uiComponent.getAttributes().get("model"));
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validatePieceJustificativeFonctionSignataire(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validation.validatePieceJustificativeFonctionSignataire(false, attribute(uiComponent, String.class, "numero"), 
					attribute(uiComponent, Date.class, "dateEtablissement"), (Fonction)value,
					null,(PieceJustificativeAFournir)uiComponent.getAttributes().get("model"));
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}
	}
	
	public void validatePieceJustificativeFichier(FacesContext facesContext,UIComponent uiComponent,Object value){
		/*try {
			validation.validatePieceJustificativeFichier(false, attribute(uiComponent, String.class, "numero"), 
					attribute(uiComponent, Date.class, "dateEtablissement"), attribute(uiComponent, Fonction.class, "signataire"),
					null,(PieceJustificativeAFournir)uiComponent.getAttributes().get("model"));
		} catch (Exception e) {
			validationException(uiComponent, e.getMessage());
		}*/
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
	private <T> T attribute(UIComponent uiComponent,Class<T> type,String name){
		return (T) ((UIInput)uiComponent.getAttributes().get(name)).getValue();
	}

}
