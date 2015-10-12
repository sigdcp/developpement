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

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.application.RoleManager;
import ci.gouv.budget.solde.sigdcp.controller.converter.ViewParamConverter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Souscription;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou.Cause;
import ci.gouv.budget.solde.sigdcp.model.prestation.Commande;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotation;
import ci.gouv.budget.solde.sigdcp.model.prestation.Facture;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.CarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.service.GenericService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;
import ci.gouv.budget.solde.sigdcp.service.resources.ServiceConstantResources;

/**
 * Les constantes web
 * @author christian
 *
 */
@Singleton @Named @Log
public class WebConstantResources implements Serializable{
	
	private static final long serialVersionUID = 1583754563831914427L;

	private static final String TEMPLATE_COMMUN = "/template/commun.xhtml";
	//private static final String TEMPLATE_DIALOG = "/template/commundiag.xhtml";
	
	@Inject private GenericService genericService;
	//@Inject private TextService textService;
	@Inject protected NavigationManager navigationManager;
	@Inject protected ServiceConstantResources serviceConstantResources;
	@Inject protected RoleManager roleManager;
	@Getter private final String requestParamSectionId = "secId";
	@Getter private final String requestParamNatureDeplacement = "nd";
	@Getter private final String requestParamSolde = "op";
	@Produces @Named public ViewParamConverter getViewParamNatureDeplacementConverter(){
		return new ViewParamConverter(genericService, NatureDeplacement.class);
	}
	
	@Getter private final String requestParamBordereau = "bordereau";
	
	@Getter private final String requestParamDossier = "dossier";
	@Produces @Named public ViewParamConverter getViewParamDossierConverter(){
		return new ViewParamConverter(genericService, Dossier.class,Long.class);
	}
	
	@Getter private final String requestParamCompteUtilisateur = "cpteu";
	@Produces @Named public ViewParamConverter getViewParamCompteUtilisateurConverter(){
		return new ViewParamConverter(genericService, CompteUtilisateur.class,Long.class);
	}
	
	@Getter private final String requestParamMission = "mission";
	@Produces @Named public ViewParamConverter getViewParamMissionConverter(){
		return new ViewParamConverter(genericService,Long.class, Mission.class);
	}
	
	@Getter private final String requestParamStatut = "statut";
	@Produces @Named public ViewParamConverter getViewParamStatutConverter(){
		return new ViewParamConverter(genericService, Statut.class);
	}
	
	@Getter private final String requestParamCalendrierMission = "cmid";
	@Produces @Named public ViewParamConverter getViewParamCalendrierMissionConverter(){
		return new ViewParamConverter(genericService, CalendrierMission.class,Long.class);
	}
	
	@Getter private final String requestParamFacture = "factid";
	@Produces @Named public ViewParamConverter getViewParamFactureConverter(){
		return new ViewParamConverter(genericService, Facture.class);
	}
	
	@Getter private final String requestParamSouscription = "sousid";
	@Produces @Named public ViewParamConverter getViewParamSouscriptionConverter(){
		return new ViewParamConverter(genericService, Souscription.class);
	}
	
	@Getter private final String requestParamPieceJustificative = "pid";
	@Produces @Named public ViewParamConverter getViewParamPieceJustificativeConverter(){
		return new ViewParamConverter(genericService, PieceJustificative.class,Long.class);
	}
	
	@Getter private final String requestParamPieceProduite = "ppid";
	@Produces @Named public ViewParamConverter getViewParamPieceProduiteConverter(){
		return new ViewParamConverter(genericService, PieceProduite.class,Long.class);
	}
	
	@Getter private final String requestParamNatureOperation = "noid";
	@Produces @Named public ViewParamConverter getViewParamNatureOperationConverter(){
		return new ViewParamConverter(genericService, NatureOperation.class,String.class);
	}

	
	@Getter private final String requestParamDemandeCotation = "dcotid";
	@Produces @Named public ViewParamConverter getViewParamDemaneCotationConverter(){
		return new ViewParamConverter(genericService, DemandeCotation.class,String.class);
	}
	
	@Getter private final String requestParamPrestataire = "prestid";
	@Produces @Named public ViewParamConverter getViewParamPrestataireConverter(){
		return new ViewParamConverter(genericService, Prestataire.class,Long.class);
	}
	
	@Getter private final String requestParamCommande = "cmdid";
	@Produces @Named public ViewParamConverter getViewParamCommandeConverter(){
		return new ViewParamConverter(genericService, Commande.class,Long.class);
	}
	
	@Getter private final String requestParamAchatCarteSotra = "acarteid";
	@Produces @Named public ViewParamConverter getViewParamAchatCarteSotraConverter(){
		return new ViewParamConverter(genericService, AchatCarteSotra.class,Long.class);
	}
	
	@Getter private final String requestParamCarteSotra = "csid";
	@Produces @Named public ViewParamConverter getViewParamCarteSotraConverter(){
		return new ViewParamConverter(genericService, CarteSotra.class,Long.class);
	}
	
	@Getter private final String requestParamLiquidation = "liquidation";
	@Getter private final String requestParamLiquidationBulletin = "liquidationbul";
	@Getter private final String requestParamLiquidationBonTransport = "liquidationbt";
	@Getter private final String requestParamDialogCount = "diagcount";
	@Getter private final String requestParamTabMenuItemIndex = "tabmiidn";
	@Getter private final String requestParamPageTemplate = "pagetemplate";
	@Getter private final String requestParamPageTemplateCommon = "tempcom";
	@Getter private final String requestParamPageTemplateDialog = "tempdiag";
	@Getter private final String requestParamMessageId = "mid";
	@Getter private final String requestParamRuntimeMessageId = "rmid";
	@Getter private final String requestParamMessageTitle = "mtitle";
	@Getter private final String requestParamMessageParameters = "mpv";
	@Getter private final String requestParamUrl = "rurl";
	
	@Getter private final String requestParamCrudType = "ct";
	@Getter private final String requestParamCrudCreate = "create";
	@Getter private final String requestParamCrudRead = "read";
	@Getter private final String requestParamCrudUpdate = "update";
	@Getter private final String requestParamCrudDelete = "delete";
		
	@Getter private final String requestParamNextViewOutcome = "nvo";
	@Getter private final String requestParamPreviousURL = "purl";
	@Getter private final String requestParamViewType = "vt";
	@Getter private final String requestParamDialog = "dlg";
	@Getter private final String requestParamToken = "token";
	/*
	@Getter private final String requestParamAction = "action";
	@Getter private final String requestParamActionEditer = "editer";
	@Getter private final String requestParamActionConsulter = "consulter";
	*/
	@Getter private final String requestParamEntityId = "eid";
	
	@Getter private final String requestParamCategory = "catid";
	@Getter private final String requestParamCategorieRequerant = "catReq";
	
	@Getter private final String requestParamOutcomeAction = "outcomeaction";
	
	@Getter private final String inputMatriculePattern = "999999a";
	@Getter private final String inputDatePattern = "99/99/9999*";
	@Getter private final String inputPhoneNumberPattern = "99 99 99 99";
	//@Getter private final String inputMailBoxPattern = "99 99 99 99";
	
	//@Getter private final String outcomeValidationRecevabiliteDD = "validationrecevabilitedd";

	@Getter @Produces @Named private final String valueRequiredMessage = "Champ obligatoire";
	@Getter @Produces @Named private final String valuesRequiredMessage = "Tous les champs avec * sont obligatoire";
	@Getter @Produces @Named private final String noSelectionOptionMessage = "--Selectionnez--";
	@Getter @Produces @Named private final String clientValidationGroupClass = Client.class.getName();
	
	@Getter private final String widgetVarWizard = "myWizard";
	
	@Produces @Named public Converter getViewParamEnumConverter(){
		return new Converter() {
			
			@Override
			public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String string) throws ConverterException {
				//CRUD
				if(requestParamCrudCreate.equals(string))
					return CRUDType.CREATE;
				if(requestParamCrudRead.equals(string))
					return CRUDType.READ;
				if(requestParamCrudUpdate.equals(string))
					return CRUDType.UPDATE;
				if(requestParamCrudDelete.equals(string))
					return CRUDType.DELETE;
				//Cause Verrou
				if(serviceConstantResources.getWebRequestParamVerrouCauseAccessMultiple().equals(string))
					return Cause.ACCESS_MULTIPLE;
				if(serviceConstantResources.getWebRequestParamVerrouCauseReinitialiserPassword().equals(string))
					return Cause.REINITIALISATION_PASSWORD;
				
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
	
	public String getPageTemplate(){
		/*String pt = Faces.getRequestParameter(requestParamPageTemplate);
		if(StringUtils.isEmpty(pt) || requestParamPageTemplateCommon.equals(pt))
			return TEMPLATE_COMMUN;
		return TEMPLATE_DIALOG;*/
		return TEMPLATE_COMMUN;
	}
	
	public Integer getDialogCount(){
		String pt = Faces.getRequestParameter(requestParamDialogCount);
		try{
			return Integer.parseInt(pt);
		}catch(Exception exception){
			return 0;
		}
	}
	
	/*------------------------------------------------------- UI -----------------------------------------------------------------*/
	/*
	@Getter private final String uiMenuTabLiquidationEtablir = "etablir";
	@Getter private final String uiMenuTabLiquidationValider = "valider";
	@Getter private final String uiMenuTabLiquidationTransmettre = "transvisa";
	@Getter private final String uiMenuTabLiquidationViser = "viser";
	@Getter private final String uiMenuTabLiquidationProduireBT = "pbt";
	@Getter private final String uiMenuTabLiquidationValiderBT = "vbt";
	@Getter private final String uiMenuTabLiquidationTransmettreBT = "tbt";
	
	private final Integer uiMenuTabLiquidationLiquidateur = 1;
	private final Integer uiMenuTabLiquidationResponsable = 3;
	private final Integer uiMenuTabLiquidationDirecteur = 3;
	
	public Integer tabLiquidationIndex(String code){
		if(StringUtils.isEmpty(code))
			return 0;
		switch(code){
			case uiMenuTabLiquidationEtablir://L
				return 0;
			case uiMenuTabLiquidationValider://R
				if(roleManager.isLiquidateur())
					return 1;
				else
					return 0;
			case uiMenuTabLiquidationTransmettre://R
				if(roleManager.isLiquidateur())
					return 2;
				else
					return 1;
			case uiMenuTabLiquidationViser://D
				if(roleManager.isLiquidateur())
					if(roleManager.isResponsable())
						return 3;
					else
						return 1;
				else
					if(roleManager.isResponsable())
						return 2;
					else
						return 0;
			case uiMenuTabLiquidationProduireBT://R
				if(roleManager.isLiquidateur())
					if(roleManager.isDirecteur())
						return 4;
					else
						return 3;
				else
					if(roleManager.isDirecteur())
						return 3;
					else
						return 2;
			case uiMenuTabLiquidationValiderBT://D
				if(roleManager.isLiquidateur())
					if(roleManager.isResponsable())
						return 5;
					else
						return 1;
				else
					if(roleManager.isResponsable())
						return 2;
					else
						return 0;
			case uiMenuTabLiquidationTransmettreBT://D
				return 2;
		}
		return 0;
	}
	*/
}
