package ci.gouv.budget.solde.sigdcp.service.sotra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import ci.gouv.budget.solde.sigdcp.dao.traitement.OperationValidationConfigDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDao;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Operation;
import ci.gouv.budget.solde.sigdcp.model.traitement.OperationValidationConfig;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.dossier.OperationService;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitableService;

public abstract class TraitableServiceImpl<OBJET_TRAITE extends AbstractModel<TID>,TID,TRAITEMENT extends Traitement> extends DefaultServiceImpl<OBJET_TRAITE, TID> implements TraitableService<OBJET_TRAITE, TID> {
	
	private static final long serialVersionUID = 8509906777706320841L;

	@Inject private OperationService operationService;
	@Inject private OperationValidationConfigDao operationValidationConfigDao;
	
	@Inject protected EntityManager entityManager;
	
	public TraitableServiceImpl(TraitableDao<OBJET_TRAITE, TID> dao) {
		super(dao);
	} 
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public OBJET_TRAITE findDemande(Long id, String natureOperationCode) throws ServiceException {
		
		return null;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<OBJET_TRAITE> findATraiter(String natureOperationCode) {
		NatureOperation natureOperation = genericDao.readByClass(NatureOperation.class, natureOperationCode);
		Collection<OBJET_TRAITE> list;
		if(natureOperation.getPrecedent()==null){
			list = firstFlow(natureOperation);
		}else{
			list = middleFlow(natureOperation);
		}
		for(OBJET_TRAITE t : list)
			init(t, natureOperationCode);
		return list;
	}
	
	protected Collection<OBJET_TRAITE> firstFlow(NatureOperation natureOperation){return null;}
	
	protected Collection<OBJET_TRAITE> middleFlow(NatureOperation natureOperation){
		String codeStatutAcc = null;	
		codeStatutAcc = operationValidationConfigDao.readByNatureOperationIdByValidationType(natureOperation.getPrecedent().getCode(), ValidationType.ACCEPTER).getStatutResultat().getCode();
		return ((TraitableDao<OBJET_TRAITE, TID>)dao).readByNatureOperationIdByStatutId(natureOperation.getPrecedent().getCode(), codeStatutAcc);
	}
	
	protected Collection<OBJET_TRAITE> lastFlow(NatureOperation natureOperation){return null;}
	
	@SuppressWarnings("unchecked")
	protected Traitable<TRAITEMENT> traitable(OBJET_TRAITE objet){
		try {
			return (Traitable<TRAITEMENT>) FieldUtils.readField(objet, "traitable", true);
		} catch (IllegalAccessException e) {
			return null;
		}
	}
	
	@Override
	public void valider(Collection<OBJET_TRAITE> objetTraites) throws ServiceException {
		Traitable<TRAITEMENT> att = traitable(objetTraites.iterator().next());
		Operation operation = operationService.creer(att.getNatureOperation().getCode());
		
		OperationValidationConfig config = operationValidationConfigDao.readByNatureOperationIdByValidationType(att.getNatureOperation().getCode(), att.getTraitement().getValidationType());
		for(OBJET_TRAITE objetTraite : objetTraites){
			Traitable<TRAITEMENT> attribut = traitable(objetTraite);
			if(attribut.getTraitement().getValidationType()==null)
				serviceException("L'action est obligatoire");
			attribut.getTraitement().setOperation(operation);
			attribut.getTraitement().setStatut(config.getStatutResultat());
			associerTraitement(objetTraite);
			if(operation.getNature().getPrecedent()==null)
				firstAvantCreation(objetTraite);
			//System.out.println("TraitableServiceImpl.valider() : "+attribut.getTraitement().getValidationType());
			switch(attribut.getTraitement().getValidationType()){
			case ACCEPTER:validationAccepter(objetTraite);break;
			case DIFFERER:validationDifferer(objetTraite);break;
			case REJETER:validationRejeter(objetTraite);break;
			}
			afterValidation(objetTraite);
		}
	}
	
	protected void firstAvantCreation(OBJET_TRAITE objetTraite){}
	
	protected void validationAccepter(OBJET_TRAITE objetTraite){
		Traitable<TRAITEMENT> attribut = traitable(objetTraite);
		if(attribut.getNatureOperation().getPrecedent()==null){
			//System.out.println("TraitableServiceImpl.validationAccepter()");
			attribut.getTraitement().setObservation(null);
			creation(objetTraite);
			dao.create(objetTraite);
			afterCreation(objetTraite);
			entityManager.flush();
		}
	}
	
	protected void validationDifferer(OBJET_TRAITE objetTraite){
		serviceException("Aucune implementation du Differer");
	}
	
	protected void validationRejeter(OBJET_TRAITE objetTraite){
		serviceException("Aucune implementation du Rejeter");
	}
	
	protected void creation(OBJET_TRAITE objetTraite){}
	
	protected void afterCreation(OBJET_TRAITE objetTraite){}
	
	protected void afterValidation(OBJET_TRAITE objetTraite){
		try {
			Object id = FieldUtils.readField(objetTraite, "id", true);
			if(id==null)
				;
			else{
				Traitable<TRAITEMENT> attribut = traitable(objetTraite);
				entityManager.persist(attribut.getTraitement());//TODO A revoir
				attribut.setDernierTraitement(attribut.getTraitement());
				dao.update(objetTraite);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	protected abstract void associerTraitement(OBJET_TRAITE objetTraite);

	//@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings("unchecked")
	@Override
	public void init(OBJET_TRAITE objetTraite, String natureOperationCode) {
		Traitable<TRAITEMENT> attribut = traitable(objetTraite);
		
		TRAITEMENT dt = (TRAITEMENT) attribut.getDernierTraitement();
		if(StringUtils.isEmpty(natureOperationCode)){
			natureOperationIsNull(objetTraite);
			if(attribut.getNatureOperation()==null)
				if(dt==null)
					;
				else{
					if( dt.getOperation().getNature().getSuivant()!=null)
						natureOperationCode = dt.getOperation().getNature().getSuivant().getCode();
				}
			else
				natureOperationCode = attribut.getNatureOperation().getCode();
		}
		
		attribut.setTraitement(traitementInstance());
		if(StringUtils.isNotEmpty(natureOperationCode))
			natureOperationAExecuter(objetTraite,natureOperationCode);
		
		if(idValue(objetTraite)==null /*attribut.getNatureOperation()!=null && attribut.getNatureOperation().getPrecedent()==null*/){
			initFirstFlow(objetTraite);
		}else{
			
			attribut.setHistoriques(historiqueTraitements(objetTraite));
			if(attribut.getHistoriques()!=null)
				for(TRAITEMENT traitement : attribut.getHistoriques()){
					String libelle = formatTraitementUiMessage(traitement);
					//if()
					traitement.setUiLibelle(libelle);
					
				}
			attribut.getDernierTraitement().setUiLibelle(formatTraitementUiMessage((TRAITEMENT) attribut.getDernierTraitement()));
			initMiddleFlow(objetTraite);
		}
		
	}
	
	protected String formatTraitementUiMessage(TRAITEMENT traitement){
		return traitement.getOperation().getNature().getLibelle()+" - "+traitement.getStatut().getLibelle();
	}
	
	protected void natureOperationIsNull(OBJET_TRAITE objetTraite){
		return;
	}
	
	protected void natureOperationAExecuter(OBJET_TRAITE objetTraite,String natureOperationCode){
		Traitable<TRAITEMENT> attribut = traitable(objetTraite);
		attribut.setNatureOperation(genericDao.readByClass(NatureOperation.class,natureOperationCode));
		attribut.getTraitement().setValidationType(operationValidationConfigDao.countByNatureOperationId(natureOperationCode)==1?ValidationType.ACCEPTER:null);
		//actions possibles
		List<OperationValidationConfig> operationValidationConfigs = new ArrayList<>(operationValidationConfigDao.readByNatureOperationId(natureOperationCode));
		attribut.getValidationTypes().clear();
		for(OperationValidationConfig config : operationValidationConfigs){
			if(attribut.getDernierTraitement()!=null && Code.STATUT_DIFFERE.equals(config.getStatutResultat().getCode()) && Code.STATUT_DIFFERE.equals(attribut.getDernierTraitement().getStatut().getCode()))
				continue;
			attribut.getValidationTypes().add(config.getValidationType());
		}
		Collections.sort(attribut.getValidationTypes());
		return;
	}
	
	protected abstract Object idValue(OBJET_TRAITE objet);
	
	protected abstract Collection<TRAITEMENT> historiqueTraitements(OBJET_TRAITE objetTraite);
	
	protected abstract TRAITEMENT traitementInstance();
	
	protected void initFirstFlow(OBJET_TRAITE traitable){}
	
	protected void initMiddleFlow(OBJET_TRAITE traitable){
		
	}
	
	protected Boolean operationEffectuee(OBJET_TRAITE objetTraite,String operation,String statut){
		Traitable<TRAITEMENT> attribut = traitable(objetTraite);
		for(Traitement t : attribut.getHistoriques())
			if(t.getOperation().getNature().getCode().equals(operation) && t.getStatut().getCode().equals(statut) )
				return true;
		return false;
	}
	
	protected Boolean operationEffectuee(OBJET_TRAITE achat,String operation){
		return operationEffectuee(achat, operation, Code.STATUT_ACCEPTE);
	}



	



	


}
