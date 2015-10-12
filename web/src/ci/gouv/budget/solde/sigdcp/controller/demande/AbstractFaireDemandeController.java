package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;
import ci.gouv.budget.solde.sigdcp.service.geographie.LocaliteService;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceUtils;

@Getter @Setter
public abstract class AbstractFaireDemandeController<DOSSIER extends Dossier,DOSSIER_SERVICE extends AbstractDossierService<DOSSIER>> extends AbstractDemandeController<DOSSIER> implements Serializable {
	
	private static final long serialVersionUID = 6615049982603373278L;
	
	@Inject transient private ServiceUtils serviceUtils;
	
	@Inject private AgentEtatService agentEtatService;
	
	@Setter @Getter protected NatureDeplacement natureDeplacement;
	@Setter @Getter protected LocaliteService localiteService;
	@Setter @Getter private String solde=null;
	
	protected CourrierDto courrierDto;
	protected Boolean showHistoriqueTraitements=Boolean.TRUE,showBulletinLiquidation=Boolean.FALSE,showIndemnite=Boolean.FALSE;
	
	protected abstract DOSSIER_SERVICE getDossierService();
	
	@Override
	protected void initDto() {
		super.initDto();
		
		try {
			solde = Faces.getRequestParameter(webConstantResources.getRequestParamSolde());
			if(solde!=null)
				natureDeplacement.setSceSolde(Boolean.TRUE);		
		} catch (NumberFormatException e) {}
		entity = getDossierService().findDemande(natureDeplacement,requestParameterLong(webConstantResources.getRequestParamDossier()),requestParameter(webConstantResources.getRequestParamNatureOperation()));
		
	}
	
	@Override
	protected void afterDtoInit() {
		super.afterDtoInit();
			//System.out.println("AbstractFaireDemandeController.afterDtoInit() YYYY"+entity.getCourrier());	
		title = entity.getDeplacement().getNature().getLibelle(); /*(natureOperation==null?"":" - "+natureOperation.getLibelle())*/;
		courrierDto = new CourrierDto(entity.getCourrier());
		showIndemnite = !entity.getBulletinLiquidations().isEmpty();
		showHistoriqueTraitements = entity.getTraitable().getHistoriques()!=null && !entity.getTraitable().getHistoriques().isEmpty();
		String action = action();
		if(action.equals(webConstantResources.getRequestParamCrudRead())){
			crudType = CRUDType.READ;
			courrierDto.setShowCourrier(courrierDto.getCourrier()!=null && StringUtils.isNotEmpty(courrierDto.getCourrier().getNumero()));
			courrierDto.setCourrierEditable(false);
			
		}else if(action.equals(webConstantResources.getRequestParamCrudCreate()) || action.equals(webConstantResources.getRequestParamCrudUpdate())){
			switch(entity.getTraitable().getNatureOperation().getCode()){
			case Code.NATURE_OPERATION_SAISIE:crudType =operationSaisie();break;
			case Code.NATURE_OPERATION_SOUMISSION:
				if(entity instanceof DossierMission)
					crudType = ((DossierMission)entity).getDeplacement().getNature().getCode().equals(Code.NATURE_DEPLACEMENT_MISSION_HCI)?CRUDType.READ:CRUDType.UPDATE;
				else
					crudType = CRUDType.UPDATE;
				pieceJustificativeUploader.setSoumission(true);				
				
				break;
			case Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_ORGANISATEUR:crudType = CRUDType.UPDATE;break;
			case Code.NATURE_OPERATION_DEPOT:
				crudType = CRUDType.READ;
				courrierDto.setCourrier(entity.getCourrier());
				courrierDto.setCourrierEditable(true); 
				courrierDto.setShowCourrier(true);
				break;
			case Code.NATURE_OPERATION_RETOUR_FD:crudType = CRUDType.READ; break;
			default:crudType = CRUDType.READ;break;
			}
		}else if(action.equals(webConstantResources.getRequestParamCrudDelete())){
			crudType = CRUDType.DELETE;
			courrierDto.setShowCourrier(courrierDto.getCourrier()!=null && StringUtils.isNotEmpty(courrierDto.getCourrier().getNumero()));
			courrierDto.setCourrierEditable(false);
		}
		
		updatePieceJustificatives();
		warnOnClosing(!CRUDType.READ.equals(crudType) && !CRUDType.DELETE.equals(crudType) || courrierDto.getCourrierEditable());
		defaultSubmitCommand.setValue(text("bouton.soumettre"));
	}
		
	protected CRUDType operationSaisie(){
		return CRUDType.CREATE;
	}
	
	@Override
	protected void afterInitialisation() {
		if(entity==null) return;
		super.afterInitialisation();
		switch(crudType){
		case CREATE:
			enregistrerCommand.setRendered(true);
			defaultSubmitCommand.setRendered(false);
			pieceJustificativeUploader.setShowDescriptions(true);
			break;
		case READ:
			enregistrerCommand.setRendered(false);
			defaultSubmitCommand.setRendered(Code.NATURE_OPERATION_RETOUR_FD.equals(entity.getTraitable().getNatureOperation().getCode()) || courrierDto.getCourrierEditable());
			if(Code.NATURE_OPERATION_RETOUR_FD.equals(entity.getTraitable().getNatureOperation().getCode()))
				defaultSubmitCommand.setValue("Enregistrer");
			else if(Code.NATURE_OPERATION_DEPOT.equals(entity.getTraitable().getNatureOperation().getCode()))
				defaultSubmitCommand.setValue(text("bouton.deposer"));
			break;
		case UPDATE:
			enregistrerCommand.setRendered(true);
			defaultSubmitCommand.setRendered(!Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_ORGANISATEUR.equals(entity.getTraitable().getNatureOperation().getCode()));
			pieceJustificativeUploader.setShowDescriptions(true);
			
			
			
			//if(entity.getDeplacement().getAddUser().getId()==userSessionManager.getUser().getId()){
			for(int i = 0;i<pieceJustificativeUploader.getCollection().size();i++){
				pieceJustificativeUploader.getCollection().get(i).setNumeroEditable(true);
				pieceJustificativeUploader.getCollection().get(i).setDateEtablissementEditable(true);
			}
			//}
			
			break;
		case DELETE:
			defaultSubmitCommand.setValue(text("bouton.annuler"));
			enregistrerCommand.setRendered(false);
			defaultSubmitCommand.setRendered(true);
			break;
		}
		/*
		enregistrerCommand.set_beforeValide(new Action() {
			private static final long serialVersionUID = -1390906260844109140L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				((AbstractDossierValidator<DOSSIER>) validator()).setSoumission(false);
				return null;
			}
		});
		defaultSubmitCommand.set_beforeValide(new Action() {
			private static final long serialVersionUID = -1390906260844109140L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				((AbstractDossierValidator<DOSSIER>) validator()).setSoumission(true);
				return null;
			}
		});
		*/
	}
	
	protected String action(){
		String action = Faces.getRequestParameter(webConstantResources.getRequestParamCrudType());
		if(StringUtils.isEmpty(action))
			action = webConstantResources.getRequestParamCrudRead();
		return action;
	}
		
	@Override
	protected void enregistrer() throws Exception {
		entity.setPieceJustificatives( pieceJustificativeUploader.process());
		getDossierService().enregistrer(ActionType.ENREGISTRER,entity);
		
	}
		
	@Override
	protected Collection<PieceJustificativeAFournir> onEnregistrerSuccessPieceJustificativeAFournir() {
		return pieceJustificativeAFournirService.findDeriveeRestantByDossier(entity, pieceJustificativeUploader.getPieceJustificatives());
	}
	
	protected void updatePieceJustificatives(){
		getDossierService().mettreAJourPiecesJustificatives(Arrays.asList(entity));
		pieceJustificativeUploader.addPieces(entity.getPieceJustificatives(), isEditable());
		/*
		pieceJustificativeUploader.clear();
		for(PieceJustificative pieceJustificative : entity.getPieceJustificatives()){
			Boolean imprimable = Boolean.TRUE.equals(pieceJustificative.getModel().getConfig().getDerivee()) && isEditable();
			if(pieceJustificative.getModifiable()!=null)
				pieceJustificativeUploader.addPieceJustificative(pieceJustificative,Boolean.TRUE.equals(pieceJustificative.getModifiable()) || !Boolean.FALSE.equals(pieceJustificative.getModifiable()),
						imprimable);
			else
				pieceJustificativeUploader.addPieceJustificative(pieceJustificative, isEditable(),imprimable);
		}
		pieceJustificativeUploader.update();
		*/
	}
		
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		switch(entity.getTraitable().getNatureOperation().getCode()){
		case Code.NATURE_OPERATION_SOUMISSION:
			entity.setPieceJustificatives( pieceJustificativeUploader.process());
			getDossierService().enregistrer(ActionType.SOUMETTRE, entity);
			break;
		case Code.NATURE_OPERATION_DEPOT:
			getDossierService().deposer(Arrays.asList(entity));
			break;
		case Code.NATURE_OPERATION_ANNULATION_DEMANDE:
			getDossierService().annuler(Arrays.asList(entity));
			break;
		
		}		
	}
	
	@Override
	protected String onSoumettreSuccessOutcome() {
		return navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{webConstantResources.getRequestParamMessageId(),"notification.demande.soumise",
				webConstantResources.getRequestParamUrl(),navigationManager.url("demandeliste",null,false,false)},true);
	}
	
	@Override
	public void typeDepenseListener(ValueChangeEvent valueChangeEvent) {
		entity.setTypeDepense((TypeDepense) valueChangeEvent.getNewValue());
		updatePieceJustificatives();
	}
	
	public void matriculeListener(ValueChangeEvent valueChangeEvent){
		AgentEtat agentEtat = agentEtatService.findByMatriculeByReference((String) valueChangeEvent.getNewValue());
		//entity.getBeneficiaire().setMatricule((String) valueChangeEvent.getNewValue());
		entity.getBeneficiaire().setDateNaissance(agentEtat.getDateNaissance());
		//System.out.println("FaireDemandeDDController.matriculeListener()"+entity.getBeneficiaire().getDateNaissance());
		
	}
	
	
	
}
		
