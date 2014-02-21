package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.application.AbstractDemandeController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Named @ViewScoped @Getter @Setter
public class EnregistrerDemandeMHCIFormController extends AbstractDemandeController<MissionExecutee> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Services
	 */
	
	@Inject private AgentEtatService agentEtatService;
	@Inject private MissionExecuteeService missionService;
	@Inject private DossierMissionService dossierMissionService;
	
	/*
	 * Dtos
	 */
	@Getter private Collection<DossierMissionDto> dossierMissionDtos = new LinkedList<>();
	
	private String matricule;
	
	@Override
	protected void initialisation() {
		crudType=CRUDType.CREATE;
		super.initialisation();
		MissionExecutee missionExecuteeEnSaisie = missionService.findSaisieByPersonne(userSessionManager.getUser());
		enSaisie = missionExecuteeEnSaisie!=null;

		if(enSaisie && (entity==null || entity.getId()==null))
			entity = missionExecuteeEnSaisie;
		if(entity==null)
			entity = new MissionExecutee();
		
		if(entity.getId()==null){
			//entity=new MissionExecutee();
			entity.setDeplacement(new Deplacement());
			entity.getDeplacement().setNature(genericService.findByClass(NatureDeplacement.class, Code.NATURE_DEPLACEMENT_MISSION_HCI));
			entity.getDeplacement().setTypeDepense(genericService.findByClass(TypeDepense.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE));
		}else{
			//System.out.println(dossierMissionService.findByDeplacement(entity.getDeplacement()));
			for(DossierMission dm : dossierMissionService.findByDeplacement(entity.getDeplacement())){
				dossierMissionDtos.add(new DossierMissionDto(entity,dm,
						pieceJustificativeService.findByDossier(dm, null, null)
						, pieceJustificativeService, fichierService,isEditable()));
			}
		}
		requiredEnabled=Boolean.FALSE;
		
		title="Formulaire : MHCI";
		/*
		for(PieceJustificativeAFournir pieceCommune : pieceJustificativeAFournirService.findByNatureDeplacementIdByTypeDepenseId(entity.getDeplacement().getNature().getCode(), 
				entity.getDeplacement().getTypeDepense().getCode(),new PieceJustificativeAFournirConfig(Boolean.TRUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE)))
			pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceCommune));
		*/
	}
	
	private void enregistrer(ActionType actionType) throws Exception {
		Collection<Collection<PieceJustificative>> pieceJustificatives = new LinkedList<>();
		Collection<DossierMission> dossiers = new LinkedList<>();
		for(DossierMissionDto dto : dossierMissionDtos){
			dossiers.add(dto.getDossierMission());
			pieceJustificatives.add(dto.getPieceJustificativeUploader().process());
		}
		missionService.enregistrer(actionType,entity, dossiers,pieceJustificatives,userSessionManager.getUser());
		
	}

	@Override
	protected void enregistrer() throws Exception {
		enregistrer(ActionType.ENREGISTRER);
		
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		enregistrer(ActionType.SOUMETTRE);
	}

	public void ajouterParticipant(){
		if(StringUtils.isEmpty(matricule))
			return;
		boolean exist = false;
		for(DossierMissionDto dto : dossierMissionDtos)
			if(dto.getMatricule().equals(matricule)){
				exist=true;
				break;
			}
		if(!exist && dossierMissionDtos.add(new DossierMissionDto(entity,matricule,agentEtatService.findByMatricule(matricule),pieceJustificativeService,fichierService,isEditable())))
			matricule = null;
	}
	
	public void supprimerParticipant(DossierMissionDto dto){
		//System.out.println(dossierMissionDtos.size());
		//((List)dossierMissionDtos).remove(0);
		dossierMissionDtos.remove(dto);
	}

	
	@Override
	protected String onSoumettreSuccessOutcome() {
		return navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{webConstantResources.getRequestParamMessageId(),"notification.demande.soumise",
				webConstantResources.getRequestParamUrl(),navigationManager.url("missionexecuteeliste",null,false,false)},true);
	}
	
}
	