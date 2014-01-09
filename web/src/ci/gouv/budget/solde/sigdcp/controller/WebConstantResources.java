package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.controller.converter.ViewParamConverter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.service.GenericService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

/**
 * Les constantes web
 * @author christian
 *
 */
@Singleton @Named @Log
public class WebConstantResources implements Serializable{
	
	private static final long serialVersionUID = 1583754563831914427L;

	@Inject private GenericService genericService;
	//@Inject private TextService textService;
	@Inject protected NavigationManager navigationManager;
	
	@Getter private final String requestParamNatureDeplacement = "nd";
	@Produces @Named public ViewParamConverter getViewParamNatureDeplacementConverter(){
		return new ViewParamConverter(genericService, NatureDeplacement.class);
	}
	
	@Getter private final String requestParamDossier = "dossier";
	@Produces @Named public ViewParamConverter getViewParamDossierConverter(){
		return new ViewParamConverter(genericService, Dossier.class);
	}
	
	@Getter private final String requestParamStatut = "statut";
	@Produces @Named public ViewParamConverter getViewParamStatutConverter(){
		return new ViewParamConverter(genericService, Statut.class);
	}
	
	@Getter private final String requestParamCrudType = "ct";
	@Getter private final String requestParamCrudCreate = "create";
	@Getter private final String requestParamCrudRead = "read";
	@Getter private final String requestParamCrudUpdate = "update";
	
	@Getter private final String requestParamNextViewOutcome = "nvo";
	@Getter private final String requestParamPreviousURL = "purl";
	/*
	@Getter private final String requestParamAction = "action";
	@Getter private final String requestParamActionEditer = "editer";
	@Getter private final String requestParamActionConsulter = "consulter";
	*/
	@Getter private final String requestParamEntityId = "eid";
	
	@Getter private final String requestParamOutcomeAction = "outcomeaction";
	
	@Getter private final String inputMatriculePattern = "999999a";
	@Getter private final String inputDatePattern = "99/99/9999*";
	@Getter private final String inputPhoneNumberPattern = "99 99 99 99";
	//@Getter private final String inputMailBoxPattern = "99 99 99 99";
	
	//@Getter private final String outcomeValidationRecevabiliteDD = "validationrecevabilitedd";

	@Getter @Produces @Named private final String valueRequiredMessage = "Champ obligatoire";
	@Getter @Produces @Named private final String valuesRequiredMessage = "Tous les champs avec * sont obligatiore";
	@Getter @Produces @Named private final String noSelectionOptionMessage = "--Selectionnez--";
	
	@Getter private final String widgetVarWizard = "myWizard";
	
	@Produces @Named public Converter getViewParamEnumConverter(){
		return new Converter() {
			
			@Override
			public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) throws ConverterException {
				if(requestParamCrudCreate.equals(string))
					return CRUDType.CREATE;
				if(requestParamCrudRead.equals(string))
					return CRUDType.READ;
				if(requestParamCrudUpdate.equals(string))
					return CRUDType.UPDATE;
				log.severe("Cannot be mapped to an enum value : "+string);
				return null;
			}
			
			@Override
			public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) throws ConverterException {
				//nothing to do
				return null;
			}
		};
	}
	
	@Produces @Named public Converter getViewParamOutcomeConverter(){
		return new Converter() {
			
			@Override
			public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) throws ConverterException {
				if(string==null || string.isEmpty())
					return null;
				return navigationManager.url(string,Boolean.valueOf((String)uiComponent.getAttributes().get(requestParamOutcomeAction)));
			}
			
			@Override
			public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) throws ConverterException {
				//nothing to do
				return null;
			}
		};
	}
	
	
	
}
