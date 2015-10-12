package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierObsequesDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.DemandeCotationObsequeDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDemandeCotationObsequeDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.OperationValidationConfigDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObsequeId;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.dossier.OperationService;
import ci.gouv.budget.solde.sigdcp.service.sotra.TraitableServiceImpl;

@Stateless
public class DemandeCotationObsequeServiceImpl extends TraitableServiceImpl<DemandeCotationObseque, Long,TraitementDemandeCotationObseque> implements DemandeCotationObsequeService {
	
	private static final long serialVersionUID = 8509906777706320841L;

	
	@Inject protected OperationService operationService;
	@Inject protected OperationValidationConfigDao operationValidationConfigDao;	
	@Inject protected PieceJustificativeDao pieceJustificativeDao;
	@Inject protected PrestataireDemandeCotationObsequeDao prestataireDemandeCotationObsequeDao;
	

	
	@Inject protected DossierObsequesDao dossierObsequesDao;
	
	
	
	@Inject
	public DemandeCotationObsequeServiceImpl(DemandeCotationObsequeDao dao) {
		super(dao); 
	}  


	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public DemandeCotationObseque findDemande(Long id,String natureOperationCode) throws ServiceException {
		return null;
	}
	
	@Override
	protected Collection<DemandeCotationObseque> firstFlow(NatureOperation natureOperation) {
		
		Collection<DemandeCotationObseque> list = new ArrayList<>();
		if(natureOperation.getCode().equals(Code.NATURE_OPERATION_DEMANDE_COTATION)){
		for(DossierObseques dossier : dossierObsequesDao.readACoter()){
			list.add(nouveau(dossier));
		}
		}
		else if(natureOperation.getCode().equals(Code.NATURE_OPERATION_GENERATION_BCFO)){
			list = ((DemandeCotationObsequeDao)dao).readACommander();
		}
		
		return list;
		
	} 
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public DemandeCotationObseque nouveau(DossierObseques dossierObseques){
		DemandeCotationObseque demandeCotationObseque = new DemandeCotationObseque(dossierObseques);
		
		init(demandeCotationObseque, Code.NATURE_OPERATION_DEMANDE_COTATION);
		return demandeCotationObseque;
		
	}
	
	@Override
	protected void associerTraitement(DemandeCotationObseque demandeCotationObseque) {
		demandeCotationObseque.getTraitable().getTraitement().setDemandeCotationObseque(demandeCotationObseque);
	}


	@Override
	protected void creation(DemandeCotationObseque demandeCotationObseque) {
		super.creation(demandeCotationObseque);
		
		
	}

	
	@Override
	protected void afterCreation(DemandeCotationObseque demandeCotationObseque) {
		super.afterCreation(demandeCotationObseque);
		
		for(Prestataire prestataire : demandeCotationObseque.getPrestataires()){
			PrestataireDemandeCotationObsequeId id = new PrestataireDemandeCotationObsequeId(demandeCotationObseque.getId(), prestataire.getId());			
			PrestataireDemandeCotationObseque prestataireDemandeCotationObseque=new PrestataireDemandeCotationObseque(id,demandeCotationObseque.getDossier(), prestataire);			
			prestataireDemandeCotationObsequeDao.create(prestataireDemandeCotationObseque);
			
			
		}
		
	}
	

	@Override
	public Collection<DemandeCotationObseque> findDemandes() {
		return null;//((CarteSotraDao)dao).readByAgent((AgentEtat)utilisateur());
	}

	@Override
	protected Object idValue(DemandeCotationObseque demandeCotationObseque) {
		return demandeCotationObseque.getId();
	}

	@Override
	protected TraitementDemandeCotationObseque traitementInstance() {
		return new TraitementDemandeCotationObseque();
	}

	@Override
	public DemandeCotationObseque find(Long id, String natureOperationCode)throws ServiceException {
		return null;
	}

	@Override
	protected Collection<TraitementDemandeCotationObseque> historiqueTraitements(DemandeCotationObseque objetTraite) {
		return null;
	}


	@Override
	public Collection<DemandeCotationObseque> findDemandesSolde() {
		// TODO Auto-generated method stub
		return null;
	}

	
	


}
