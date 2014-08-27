package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.prestation.DemandeCotationMissionDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDemandeCotationMissionDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.OperationValidationConfigDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMissionId;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.OperationService;

@Stateless
public class PrestataireDemandeCotationMissionServiceImpl extends DefaultServiceImpl<PrestataireDemandeCotationMission,PrestataireDemandeCotationMissionId> implements PrestataireDemandeCotationMissionService , Serializable {
	
	private static final long serialVersionUID = 8509906777706320841L;

	
	@Inject protected OperationService operationService;
	@Inject protected OperationValidationConfigDao operationValidationConfigDao;
	@Inject protected MissionExecuteeService missionExecuteeService;
	@Inject protected PrestataireDemandeCotationMissionDao prestataireDemandeCotationMissionDao;
	@Inject protected DemandeCotationMissionDao demandeCotationMissionDao;
	@Inject protected PrestataireDao prestataireDao;
	
	@Inject
	public PrestataireDemandeCotationMissionServiceImpl(PrestataireDemandeCotationMissionDao dao) {
		super(dao);
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public PrestataireDemandeCotationMission nouveau(PrestataireDemandeCotationMission demande) {
		//PrestataireDemandeCotationMission prestataireDemandeCotationMission = new PrestataireDemandeCotationMission();
		init(demande);
		missionExecuteeService.init(demande.getMissionExecutee(), Code.NATURE_OPERATION_DEMANDE_COTATION);
		demande.getClasseVoyages().clear();
		
		Object[] trouve=null;
		
		for(DossierMission dossier :demande.getMissionExecutee().getDossiers()){
			trouve=null;
			for(Object[] objet : demande.getClasseVoyages()){
				if(objet[0].equals(dossier.getClasseVoyage())){
					trouve=objet;
					break;				
				}				
			}
			
			if(trouve==null){
				demande.getClasseVoyages().add(new Object[]{dossier.getClasseVoyage(),1});
			}
			else {				
				trouve[1]=(int)trouve[1]+1;				
			}
			
			/*if(demande.getClasseVoyages().size()>0){			
			//Recherche de la classe de voyage dans notre liste
				
			for(Object[] objet : demande.getClasseVoyages()){
				if(objet[0].equals(dossier.getClasseVoyage())){objet[1]=(int)objet[1]+1;}
				else {demande.getClasseVoyages().add(new Object[]{dossier.getClasseVoyage(),1});}
			}
			
			}
			else demande.getClasseVoyages().add(new Object[]{dossier.getClasseVoyage(),1});*/
				 
		}	
		return demande;
	}

	public void init(PrestataireDemandeCotationMission demande){
		demande.setMissionExecutee(demandeCotationMissionDao.read(demande.getId().getDemandeCotationMissionId()).getMission());	
		demande.setPrestataire(prestataireDao.read(demande.getId().getPrestataireId()));
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<PrestataireDemandeCotationMission> findATraiter(String natureOperationCode) {
		Collection<PrestataireDemandeCotationMission> prestataireDemandeCotationMissions=new ArrayList<>();
		for(PrestataireDemandeCotationMission demande :prestataireDemandeCotationMissionDao.readATraiterByPrestataireId(authentificationInfos.getCompteUtilisateur().getUtilisateur().getId())){
			init(demande);
			prestataireDemandeCotationMissions.add(demande);
		}
		return prestataireDemandeCotationMissions;
	}

	@Override
	public void enregistrer(PrestataireDemandeCotationMission demande) {
		demande.setDate(new Date());
		prestataireDemandeCotationMissionDao.update(demande);
		
	}


}
