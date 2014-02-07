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

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceValidationUtils;

@Named
public class ValidationManager implements Serializable {

	private static final long serialVersionUID = -5187172212708031726L;
	
	@Inject private ServiceValidationUtils validationUtils; 
	
	public void validateMatricule(FacesContext facesContext,UIComponent uiComponent,Object value){
		String matricule = (String) value;
		TypeAgentEtat typeAgentEtat = (TypeAgentEtat) ((UIInput)uiComponent.getAttributes().get("typePersonne")).getValue();
		if(StringUtils.isEmpty(matricule) || typeAgentEtat==null)
			return;// Let required="true" do its job.
		try {
			validationUtils.validateMatricule(typeAgentEtat, matricule);
		} catch (Exception e) {
			validationException(uiComponent,e);
		}		
	}
	
	public void validateDateNaissance(FacesContext facesContext,UIComponent uiComponent,Object value){
		Date dateNaissance = (Date) value;
		if(dateNaissance==null)
			return;// Let required="true" do its job.
		try {
			validationUtils.validateDateNaissance(dateNaissance);
		} catch (Exception e) {
			validationException(uiComponent,e);
		}
	}
	
	public void validatePassword(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validationUtils.validatePassword((String) value);
		} catch (Exception e) {
			validationException((UIInput) uiComponent, e.getMessage());
		}
	}
	
	public void validateConfirmationMotPasse(FacesContext facesContext,UIComponent uiComponent,Object value){
		String p1 = (String) value,p2 = (String) ((UIInput)uiComponent.getAttributes().get("password")).getValue();
		if(StringUtils.isEmpty(p1) || StringUtils.isEmpty(p2))
			return;// Let required="true" do its job.
		if (!p1.equals(p2))
			validationException(uiComponent,"La confirmation doit correspondre au mot de passe.");
	}
	
	public void validateCodeDeverouillage(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validationUtils.validateTokenDeverouillage((String) value);
		} catch (Exception e) {
			validationException((UIInput) uiComponent, e.getMessage());
		}
	}
	
	public void validateAdresseElectroniqueCompte(FacesContext facesContext,UIComponent uiComponent,Object value){
		try {
			validationUtils.validateUsernameUnique((String) value);
		} catch (Exception e) {
			validationException((UIInput) uiComponent, e.getMessage());
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
		System.out.println(uiComponent+" NOT HANDLED");
	}
	
	private void validationException(UIComponent uiComponent,Exception exception){
		validationException(uiComponent, exception.getMessage());
	}

}
