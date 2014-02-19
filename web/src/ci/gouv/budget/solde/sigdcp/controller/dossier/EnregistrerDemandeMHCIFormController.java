package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.application.AbstractDemandeController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournirConfig;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
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
	
	/*
	 * Dtos
	 */
	@Getter private Collection<DossierMissionDto> dossierMissionDtos = new LinkedList<>();
	
	private String matricule;
	
	@Override
	protected void initialisation() {
		crudType=CRUDType.CREATE;
		super.initialisation();
		entity = missionService.findSaisieByPersonne(userSessionManager.getUser());
		enSaisie = entity!=null;
		if(entity==null){
			entity=new MissionExecutee();
			entity.setDeplacement(new Deplacement());
			entity.getDeplacement().setNature(genericService.findByClass(NatureDeplacement.class, Code.NATURE_DEPLACEMENT_MISSION_HCI));
			entity.getDeplacement().setTypeDepense(genericService.findByClass(TypeDepense.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE));
		}
		requiredEnabled=Boolean.FALSE;
		
		title="Formulaire : MHCI";
		/*
		for(PieceJustificativeAFournir pieceCommune : pieceJustificativeAFournirService.findByNatureDeplacementIdByTypeDepenseId(entity.getDeplacement().getNature().getCode(), 
				entity.getDeplacement().getTypeDepense().getCode(),new PieceJustificativeAFournirConfig(Boolean.TRUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE)))
			pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceCommune));
		*/
	}

	@Override
	protected void onDefaultSubmitAction() throws Exception {
		Collection<Collection<PieceJustificative>> pieceJustificatives = new LinkedList<>();
		Collection<DossierMission> dossiers = new LinkedList<>();
		for(DossierMissionDto dto : dossierMissionDtos){
			dossiers.add(dto.getDossierMission());
			pieceJustificatives.add(dto.getPieceJustificativeUploader().process());
		}
		missionService.enregistrer(ActionType.SOUMETTRE,entity, dossiers,pieceJustificatives);
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
		if(!exist && dossierMissionDtos.add(new DossierMissionDto(entity,matricule,agentEtatService.findByMatricule(matricule),pieceJustificativeService,fichierService)))
			matricule = null;
	}
	
	public void supprimerParticipant(DossierMissionDto dto){
		//System.out.println(dossierMissionDtos.size());
		//((List)dossierMissionDtos).remove(0);
		dossierMissionDtos.remove(dto);
	}

	@Override
	protected void enregistrer() throws Exception {
		System.out
				.println("EnregistrerDemandeMHCIFormController.enregistrer()");
		/*
		Collection<Collection<PieceJustificative>> pieceJustificatives = new LinkedList<>();
		Collection<DossierMission> dossiers = new LinkedList<>();
		for(DossierMissionDto dto : dossierMissionDtos){
			dossiers.add(dto.getDossierMission());
			pieceJustificatives.add(dto.getPieceJustificativeUploader().process());
		}
		missionService.enregistrer(ActionType.SOUMETTRE,entity, dossiers,pieceJustificatives);
		*/
	}

	@Override
	protected String onEnregistrerSuccessOutcome() {
		return null;
	}
	
	@Override
	protected String onSoumettreSuccessOutcome() {
		return null;
	}
}
	