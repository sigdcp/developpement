package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;
import ci.gouv.budget.solde.sigdcp.service.indemnite.LocaliteGroupeMissionService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.MissionExecuteeValidator;

@Named @ViewScoped @Getter @Setter
public class OrganiserMissionController extends AbstractDemandeController<MissionExecutee> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Services
	 */
	
	@Inject private AgentEtatService agentEtatService;
	@Inject private MissionExecuteeService missionService;
	@Inject private DossierMissionService dossierMissionService;
	@Inject private LocaliteGroupeMissionService localiteGroupeMissionService;
	
	@Inject private MissionExecuteeValidator missionExecuteeValidator;
	/*
	 * Dtos
	 */
	@Getter private Collection<Participant> participants = new LinkedList<>();
	private String matricule;
	boolean courrierEditable = false;
	
	@Override
	protected void initDto() {
		super.initDto();
		Long id = null;
		try {
			id = Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamMission()));
		} catch (NumberFormatException e) {}
		entity = missionService.findSaisieByNumero(id);
		
	}
	
	@Override
	protected void afterDtoInit() {
		super.afterDtoInit();
		String action = Faces.getRequestParameter(webConstantResources.getRequestParamCrudType());
		title="Organiser une mission hors Côte d'Ivoire";
		if(StringUtils.isEmpty(action))
			action = webConstantResources.getRequestParamCrudRead();
		
		if(action.equals(webConstantResources.getRequestParamCrudRead())){
			crudType = CRUDType.READ;
		}else if(action.equals(webConstantResources.getRequestParamCrudCreate()) || action.equals(webConstantResources.getRequestParamCrudUpdate())){
			switch(entity.getNatureOperationCode()){
			case Code.NATURE_OPERATION_SAISIE:crudType = CRUDType.CREATE;pieceJustificativeUploader.setShowDescriptions(true); break;
			case Code.NATURE_OPERATION_SOUMISSION:crudType = CRUDType.UPDATE;pieceJustificativeUploader.setShowDescriptions(true);break;
			case Code.NATURE_OPERATION_DEPOT:
				crudType = CRUDType.READ;
				courrierEditable=true;
				break;
			default:crudType = CRUDType.READ;break;
			}
		}else if(action.equals(webConstantResources.getRequestParamCrudDelete())){
			crudType = CRUDType.DELETE;
		}
		
		for(DossierMission dossier : entity.getDossiers()){
			Participant participant = new Participant(dossierMissionService,entity,dossier,
					pieceJustificativeService, fichierService,validationPolicy,isEditable() && !Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE.equals(dossier.getTraitable().getDernierTraitement().getOperation().getNature().getCode()));
			participant.setCourrierDto(new CourrierDto(dossier.getCourrier()));
			if(courrierEditable){
				participant.getCourrierDto().setCourrierEditable(true); 
				participant.getCourrierDto().setShowCourrier(true);
			}else{
				participant.getCourrierDto().setShowCourrier(participant.getCourrierDto().getCourrier()!=null && StringUtils.isNotEmpty(participant.getCourrierDto().getCourrier().getNumero()));
				participant.getCourrierDto().setCourrierEditable(false);
				
				//dossierMissionDto.setEditable(!Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE.equals(dossier.getDernierTraitement().getOperation().getNature().getCode()));	
			}
			participants.add(participant);
		}
		
		warnOnClosing(!CRUDType.READ.equals(crudType) && !CRUDType.DELETE.equals(crudType) || courrierEditable);
		defaultSubmitCommand.setValue(text("bouton.soumettre"));
		pieceJustificativeUploader.setTitle("Actes administratifs");
		
		pieceJustificativeUploader.addPieceJustificative(entity.getCommunication(), isEditable(),false);
		pieceJustificativeUploader.update();
		requiredEnabled=true;
	}
		
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		switch(crudType){
			case CREATE:
				enregistrerCommand.setRendered(true);
				defaultSubmitCommand.setRendered(false);
				break;
			case READ:
				enregistrerCommand.setRendered(false);
				defaultSubmitCommand.setRendered(courrierEditable);
				break;
			case UPDATE:
				enregistrerCommand.setRendered(true);
				defaultSubmitCommand.setRendered(entity.getTousPresent());
				break;
			case DELETE:
				defaultSubmitCommand.setValue(text("bouton.annuler"));
				enregistrerCommand.setRendered(false);
				defaultSubmitCommand.setRendered(true);
				break;
		}
		
	}
	
	public Boolean estChezBeneficiaire(Participant tab){
		return tab.getDossier().getTraitable().getDernierTraitement()!=null && 
				Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE.equals(tab.getDossier().getTraitable().getDernierTraitement().getOperation().getNature().getCode());
	}
	
	private void enregistrer(ActionType actionType) throws Exception {
		pieceJustificativeUploader.process();
		entity.getCommunication().setFichier(pieceJustificativeUploader.getPieceJustificatives().iterator().next().getFichier());
		for(Participant participant : participants)
			participant.getDossier().setPieceJustificatives(participant.getPieceJustificativeUploader().process());
		
		entity.getDossiers().clear();
		for(Participant participant : participants)
			entity.getDossiers().add(participant.getDossier());
		
		missionService.enregistrer(actionType,entity);
		//debug(entity);
	}

	@Override
	protected void enregistrer() throws Exception {
		enregistrer(ActionType.ENREGISTRER);
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		if(Code.NATURE_OPERATION_DEPOT.equals(entity.getNatureOperationCode())){
			Collection<DossierMission> d = new ArrayList<>();
			for(Participant participant : participants){
				if(!StringUtils.isEmpty(participant.getDossier().getCourrier().getNumero()))
					d.add((DossierMission) participant.getDossier());
			}
			dossierMissionService.deposer(d);
		}else{
			//enregistrer(ActionType.SOUMETTRE);
		}
	}
	
	@Override
	protected String onSoumettreSuccessOutcome() {
		return navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{webConstantResources.getRequestParamMessageId(),"notification.demande.soumise",
				webConstantResources.getRequestParamUrl(),navigationManager.url("missionexecuteeliste",
						new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demande_mission_pointfocal"},false,false)},true);
	}	

	public void ajouterParticipant(){
		if(StringUtils.isEmpty(matricule))
			return;
		matricule = matricule.trim();
		boolean exist = false;
		for(Participant participant : participants)
			if(participant.getMatricule().equals(matricule)){
				exist=true;
				break;
			}
		/*
		DossierMission dossier = new DossierMission(entity.getDeplacement());
		dossierMissionService.init(dossier, Code.NATURE_OPERATION_SAISIE);
		*/
		DossierMission dossierMission = dossierMissionService.nouveau(matricule, entity);
		//debug(dossierMission.getBeneficiaire());
		Participant participant = new Participant(dossierMissionService,entity,dossierMission,pieceJustificativeService,fichierService,validationPolicy,isEditable());
		if(!exist && participants.add(participant)){
			matricule = null;
		}
	}
	
	public void supprimerParticipant(Participant participant){
		participants.remove(participant);
	}
	
	public void transmettre(Participant participant){
		missionService.transmettreDossier(Arrays.asList((DossierMission)participant.getDossier()));
	}
	
	
	
	/*
	@Override
	protected AbstractValidator<MissionExecutee> validator() {
		return missionExecuteeValidator;
	}*/
	
	@Override
	public void typeDepenseListener(ValueChangeEvent valueChangeEvent) {
		super.typeDepenseListener(valueChangeEvent);
		if(participants.isEmpty()){
			entity.getDeplacement().setTypeDepense((TypeDepense) valueChangeEvent.getNewValue());
			return;
		}
		
		for(Participant participant : participants)
			participant.getDossier().setTypeDepense((TypeDepense) valueChangeEvent.getNewValue());
		
		dossierMissionService.mettreAJourPiecesJustificatives(entity.getDossiers());
		for(Participant participant : participants)
			participant.updatePieceJustificatives();
		
	}
	
	public void destinationValueChange(ValueChangeEvent valueChangeEvent){
		
		
	}
	
	public void fonctionValueChange(ValueChangeEvent valueChangeEvent){
		/*
		Localite destination = attribute(valueChangeEvent, Localite.class);
		dossierMission.getIndemnite().setFraisMission(lgm.getIndemnite());
		LocaliteGroupeMission lgm = localiteGroupeMissionService.
				findByFonctionOrGradeByLocalite(attribute(valueChangeEvent, Fonction.class), attribute(valueChangeEvent, Grade.class), (Localite)valueChangeEvent.getNewValue());
		*/
		
	}
	
	public void professionValueChange(ValueChangeEvent valueChangeEvent){
		/*
		LocaliteGroupeMission lgm = localiteGroupeMissionService.
				findByFonctionOrGradeByLocalite(attribute(valueChangeEvent, Fonction.class), attribute(valueChangeEvent, Grade.class), (Localite)valueChangeEvent.getNewValue());
		DossierMission dossierMission = attribute(valueChangeEvent, DossierMission.class);
		dossierMission.getIndemnite().setFraisMission(lgm.getIndemnite());
		*/
	}
	
	
}
	