package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.calendrier.MissionExecuteeDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.DemandeCotationMissionDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDemandeCotationMissionDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.OperationValidationConfigDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMissionId;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.OperationService;
import ci.gouv.budget.solde.sigdcp.service.sotra.TraitableServiceImpl;

@Stateless
public class DemandeCotationMissionServiceImpl extends TraitableServiceImpl<DemandeCotationMission, Long,TraitementDemandeCotationMission> implements DemandeCotationMissionService {
	
	private static final long serialVersionUID = 8509906777706320841L;

	
	@Inject protected OperationService operationService;
	@Inject protected OperationValidationConfigDao operationValidationConfigDao;
	@Inject protected MissionExecuteeDao missionExecuteeDao;
	@Inject protected MissionExecuteeService missionExecuteeService;
	@Inject protected PieceJustificativeDao pieceJustificativeDao;
	@Inject protected PrestataireDemandeCotationMissionDao prestataireDemandeCotationMissionDao;
	//@Inject protected DemandeCotationMissionService demandeCotationMissionService;
	
	@Inject
	public DemandeCotationMissionServiceImpl(DemandeCotationMissionDao dao) {
		super(dao);
	} 


	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public DemandeCotationMission findDemande(Long id,String natureOperationCode) throws ServiceException {
		return null;
	}
	
	@Override
	protected Collection<DemandeCotationMission> firstFlow(NatureOperation natureOperation) {
		Collection<DemandeCotationMission> list = new ArrayList<>();
		if(natureOperation.getCode().equals(Code.NATURE_OPERATION_DEMANDE_COTATION)){
		for(MissionExecutee mission : missionExecuteeService.findACoter()){
			list.add(nouveau(mission));
		}
		}
		else if(natureOperation.getCode().equals(Code.NATURE_OPERATION_GENERATION_BCTT)){
			list = ((DemandeCotationMissionDao)dao).readACommander();
		}
		return list;
	} 
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public DemandeCotationMission nouveau(MissionExecutee mission){
		missionExecuteeService.init(mission, Code.NATURE_OPERATION_DEMANDE_COTATION);
		DemandeCotationMission demandeCotationMission = new DemandeCotationMission(mission);
		init(demandeCotationMission, Code.NATURE_OPERATION_DEMANDE_COTATION);
		return demandeCotationMission;
	}
	
	
	/*@Override
	protected void initFirstFlow(DemandeCotationMission demandeCotationMission) {
		super.initFirstFlow(demandeCotationMission);
	}*/
	
	@Override
	protected void associerTraitement(DemandeCotationMission demandeCotationMission) {
		demandeCotationMission.getTraitable().getTraitement().setDemandeCotationMission(demandeCotationMission);
	}


	@Override
	protected void creation(DemandeCotationMission demandeCotationMission) {
		super.creation(demandeCotationMission);
		
		
	}
	
	@Override
	protected void afterCreation(DemandeCotationMission demandeCotationMission) {
		super.afterCreation(demandeCotationMission);
		
		for(Prestataire prestataire : demandeCotationMission.getPrestataires()){
			PrestataireDemandeCotationMissionId id = new PrestataireDemandeCotationMissionId(demandeCotationMission.getId(), prestataire.getId());			
			PrestataireDemandeCotationMission prestataireDemandeCotationMission=new PrestataireDemandeCotationMission(id,demandeCotationMission.getMission(), prestataire);
			
			//prestataireDemandeCotationMission.getTraitable().setDernierTraitement(null);
			
			prestataireDemandeCotationMissionDao.create(prestataireDemandeCotationMission);
			
			
		}
		
	}

	@Override
	public Collection<DemandeCotationMission> findDemandes() {
		return null;//((CarteSotraDao)dao).readByAgent((AgentEtat)utilisateur());
	}

	@Override
	protected Object idValue(DemandeCotationMission demandeCotationMission) {
		return demandeCotationMission.getId();
	}

	@Override
	protected TraitementDemandeCotationMission traitementInstance() {
		return new TraitementDemandeCotationMission();
	}

	@Override
	public DemandeCotationMission find(Long id, String natureOperationCode)throws ServiceException {
		return null;
	}

	@Override
	protected Collection<TraitementDemandeCotationMission> historiqueTraitements(DemandeCotationMission objetTraite) {
		return null;
	}


	@Override
	public Collection<DemandeCotationMission> findDemandesSolde() {
		// TODO Auto-generated method stub
		return null;
	}

	
	


}
