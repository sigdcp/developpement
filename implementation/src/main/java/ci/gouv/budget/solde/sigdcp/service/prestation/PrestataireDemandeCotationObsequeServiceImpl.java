package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.prestation.DemandeCotationObsequeDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDemandeCotationObsequeDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.OperationValidationConfigDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObsequeId;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierObsequesService;
import ci.gouv.budget.solde.sigdcp.service.dossier.OperationService;

@Stateless
public class PrestataireDemandeCotationObsequeServiceImpl extends DefaultServiceImpl<PrestataireDemandeCotationObseque,PrestataireDemandeCotationObsequeId> implements PrestataireDemandeCotationObsequeService , Serializable {
	
	private static final long serialVersionUID = 8509906777706320841L;

	
	@Inject protected OperationService operationService;
	@Inject protected OperationValidationConfigDao operationValidationConfigDao;
	@Inject protected DossierObsequesService dossierObsequesService;
	@Inject protected PrestataireDemandeCotationObsequeDao prestataireDemandeCotationObsequeDao;
	@Inject protected DemandeCotationObsequeDao demandeCotationMissionDao;
	@Inject protected PrestataireDao prestataireDao;
	
	@Inject
	public PrestataireDemandeCotationObsequeServiceImpl(PrestataireDemandeCotationObsequeDao dao) {
		super(dao);
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public PrestataireDemandeCotationObseque nouveau(PrestataireDemandeCotationObseque demande) {
		//PrestataireDemandeCotationObseque prestataireDemandeCotationObseque = new PrestataireDemandeCotationObseque();
		init(demande);
		dossierObsequesService.init(demande.getDossier(), Code.NATURE_OPERATION_DEMANDE_COTATION);
		//demande.getClasseVoyages().clear();
		/*
		 
		Object[] trouve=null;
		
		for(DossierMission dossier :demande.getDossierObseques()){
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
				 
		//} 
		  
		 	
		return demande;
	}

	public void init(PrestataireDemandeCotationObseque demande){
		demande.setDossier(demandeCotationMissionDao.read(demande.getId().getDemandeCotationObsequeId()).getDossier());	
		demande.setPrestataire(prestataireDao.read(demande.getId().getPrestataireId()));
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<PrestataireDemandeCotationObseque> findATraiter(String natureOperationCode) {
		Collection<PrestataireDemandeCotationObseque> prestataireDemandeCotationObseques=new ArrayList<>();
		for(PrestataireDemandeCotationObseque demande :prestataireDemandeCotationObsequeDao.readATraiterByPrestataireId(authentificationInfos.getCompteUtilisateur().getUtilisateur().getId())){
			init(demande);
			prestataireDemandeCotationObseques.add(demande);
		}
		return prestataireDemandeCotationObseques;
	}

	@Override
	public void enregistrer(PrestataireDemandeCotationObseque demande) {
		demande.setDate(new Date());
		prestataireDemandeCotationObsequeDao.update(demande);
		
	}


}
