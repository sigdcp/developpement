package ci.gouv.budget.solde.sigdcp.service.sotra;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.DelegueSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.CommandeCarteSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.sotra.AchatCarteSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.sotra.CarteSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitementAchatCarteSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitementPieceProduiteDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementAchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

@Stateless
public class AchatCarteSotraServiceImpl extends TraitableServiceImpl<AchatCarteSotra, Long,TraitementAchatCarteSotra> implements AchatCarteSotraService {
	
	private static final long serialVersionUID = 8509906777706320841L;

	@Inject private DelegueSotraDao delegueSotraDao;
	@Inject private CommandeCarteSotraDao commandeCarteSotraDao;
	@Inject private CarteSotraDao carteSotraDao;
	@Inject private TraitementAchatCarteSotraDao traitementAchatCarteSotraDao;
	@Inject private TraitementPieceProduiteDao traitementPieceProduiteDao;
	
	@Inject
	public AchatCarteSotraServiceImpl(AchatCarteSotraDao dao) {
		super(dao);
	} 

	@Override
	protected Object idValue(AchatCarteSotra achat) {
		return achat.getId();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public AchatCarteSotra findDemande(Long id,String operationAttendue) throws ServiceException {
		AchatCarteSotra achat;
		if(id==null){
			achat = ((AchatCarteSotraDao)dao).readRecentByDelegue((AgentEtat) utilisateur());
			System.out.println("XXXXXX "+achat);
			if(achat==null || Code.NATURE_OPERATION_RETRAIT_CARTE_LCS.equals(achat.getTraitable().getDernierTraitement().getOperation().getNature().getCode())){
				achat = new AchatCarteSotra();
				init(achat, Code.NATURE_OPERATION_OUVERTURE_LCS);
			}else{
				init(achat, operationAttendue);
				if(achat.getTraitable().getDernierTraitement().getOperation().getNature().getCode().equals(Code.NATURE_OPERATION_GENERATION_CCS) && 
						achat.getTraitable().getDernierTraitement().getStatut().getCode().equals(Code.STATUT_REJETE)){		
					
				}else if(operationEffectuee(achat, Code.NATURE_OPERATION_ANNULATION_LCS)){		
					//serviceException("Vous avez annulée votre liste d'achat de carte sotra. Veuillez attendre le mois prochain.");
					achat = new AchatCarteSotra();
					init(achat, Code.NATURE_OPERATION_OUVERTURE_LCS);
				}else if(operationEffectuee(achat, Code.NATURE_OPERATION_VALIDATION_LCS)){		
					Collection<TraitementPieceProduite> ts = traitementPieceProduiteDao.readByPieceProduiteByNatureOperationIdByStatutId(achat.getBonCommande(), Code.NATURE_OPERATION_DISTRIBUTION_CCS_DELEGUE, Code.STATUT_ACCEPTE);
					if(ts.isEmpty())
						serviceException("Votre liste d'achat de carte sotra est en cours de traitement.");
				}
			}
		}else{
			throw new ServiceException("Pas supporté");
		}
		
		return achat;
	}
	
	@Override
	public void annuler(AchatCarteSotra achat) {	
		achat.getTraitable().setTraitement(new TraitementAchatCarteSotra());
		achat.getTraitable().setNatureOperation(genericDao.readByClass(NatureOperation.class, Code.NATURE_OPERATION_ANNULATION_LCS));
		achat.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
		
		valider(Arrays.asList(achat));
	}
	
	@Override
	protected void natureOperationAExecuter(AchatCarteSotra achat,String natureOperationCode) {
		if(achat.getId()!=null && achat.getTraitable().getDernierTraitement().getOperation().getNature().getCode().equals(Code.NATURE_OPERATION_GENERATION_CCS) && 
				achat.getTraitable().getDernierTraitement().getStatut().getCode().equals(Code.STATUT_REJETE)){		
			natureOperationCode = Code.NATURE_OPERATION_VALIDATION_LCS;
		}
		super.natureOperationAExecuter(achat, natureOperationCode);
	}
	
	@Override
	protected void associerTraitement(AchatCarteSotra achat) {
		achat.getTraitable().getTraitement().setAchat(achat);
	}
	
	@Override
	protected void validationAccepter(AchatCarteSotra achatCarteSotra) {
		if(!achatCarteSotra.getTraitable().getNatureOperation().getCode().equals(Code.NATURE_OPERATION_ANNULATION_LCS)){
			if(achatCarteSotra.getCartes()==null || achatCarteSotra.getCartes().isEmpty())
				serviceException("Une liste d'inscrit vide ne peut pas être traitée");
			super.validationAccepter(achatCarteSotra);
		}
	}
	
	@Override
	protected void initFirstFlow(AchatCarteSotra achat) {
		super.initFirstFlow(achat);
		AchatCarteSotra ancien = ((AchatCarteSotraDao)dao).readRecentByDelegue((AgentEtat) utilisateur());
		int moisIndex = Calendar.getInstance().get(Calendar.MONTH);
		//TODO temporaire
		if(ancien!=null)
			moisIndex = ancien.getMois()+1;
		
		achat.getMoisChoix().add(moisIndex);
		achat.getAnneesChoix().add(Calendar.getInstance().get(Calendar.YEAR));
		achat.setDelegue(delegueSotraDao.readByAgentEtat((AgentEtat) utilisateur()));
	}
		
	@Override
	protected void initMiddleFlow(AchatCarteSotra achat) {
		super.initMiddleFlow(achat);
		achat.setBonCommande(commandeCarteSotraDao.readByAchat(achat));
		achat.setNombreCarte(carteSotraDao.countByAchat(achat).intValue());
		achat.setCartes(carteSotraDao.readByAchat(achat));
		
	}
		
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<AchatCarteSotra> findDemandes() {
		return null;
	}

	@Override
	protected TraitementAchatCarteSotra traitementInstance() {
		return new TraitementAchatCarteSotra();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public AchatCarteSotra find(Long id, String natureOperationCode)throws ServiceException {
		AchatCarteSotra achatCarteSotra = dao.read(id);
		init(achatCarteSotra, natureOperationCode);
		return achatCarteSotra;
	}

	@Override
	protected Collection<TraitementAchatCarteSotra> historiqueTraitements(AchatCarteSotra objetTraite) {
		return traitementAchatCarteSotraDao.readAll();
	}

	@Override
	public Collection<AchatCarteSotra> findDemandesSolde() {
		// TODO Auto-generated method stub
		return null;
	}

}
