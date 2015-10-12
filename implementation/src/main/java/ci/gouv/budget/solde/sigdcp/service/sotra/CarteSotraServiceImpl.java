package ci.gouv.budget.solde.sigdcp.service.sotra;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.DelegueSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.sotra.AchatCarteSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.sotra.BeneficiaireCarteSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.sotra.CarteSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.OperationValidationConfigDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.CarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.dossier.OperationService;

@Stateless
public class CarteSotraServiceImpl extends TraitableServiceImpl<CarteSotra, Long,TraitementCarteSotra> implements CarteSotraService {
	
	private static final long serialVersionUID = 8509906777706320841L;

	@Inject private DelegueSotraDao delegueSotraDao;
	@Inject private AchatCarteSotraDao achatCarteSotraDao;
	@Inject private BeneficiaireCarteSotraDao beneficiaireCarteSotraDao;

	@Inject private AchatCarteSotraService achatCarteSotraService;
	@Inject protected OperationService operationService;
	@Inject protected OperationValidationConfigDao operationValidationConfigDao;
	
	@Inject
	public CarteSotraServiceImpl(CarteSotraDao dao) {
		super(dao);
	} 

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<CarteSotra> findByAchat(AchatCarteSotra achat) {
		return ((CarteSotraDao)dao).readByAchat(achat);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public CarteSotra findDemande(Long id,String natureOperationCode) throws ServiceException {
		CarteSotra carteSotra;
		if(id==null){
			// a t il un delegue
			DelegueSotra delegue = delegueSotraDao.readByBeneficiaire((AgentEtat) utilisateur());
			if(delegue==null)
				serviceException("Aucune demande de carte sotra possible! Vous devez dabord vous enregistrer sur une liste de base de carte sotra");
			// a t il retire sa derniere carte
			carteSotra = ((CarteSotraDao)dao).readDernierByAgent((AgentEtat) utilisateur());
			if(carteSotra==null)
				carteSotra = nouvelleCarte(delegue);
			else{
				if(Code.NATURE_OPERATION_RETRAIT_CS.equals(carteSotra.getTraitable().getDernierTraitement().getOperation().getNature().getCode()))
					carteSotra = nouvelleCarte(delegue);
				else{
					//init(carteSotra, null);
					if(carteSotra.getAchat().getTraitable().getDernierTraitement().getOperation().getNature().getCode().equals(Code.NATURE_OPERATION_ANNULATION_LCS))	
						serviceException("Votre delegué a annulé la liste d'achat de carte de ce mois! Veuillez attendre l'ouverture de la prochaine liste.");
					else if(carteSotra.getTraitable().getDernierTraitement().getOperation().getNature().getCode().equals(Code.NATURE_OPERATION_ANNULATION_CS) &&
							carteSotra.getAchat().getTraitable().getDernierTraitement().getOperation().getNature().getCode().equals(Code.NATURE_OPERATION_OUVERTURE_LCS))
						init(carteSotra, Code.NATURE_OPERATION_DEMANDE_CS);
					else if(carteSotra.getAchat().getTraitable().getDernierTraitement().getOperation().getNature().getCode().equals(Code.NATURE_OPERATION_OUVERTURE_LCS))	
						init(carteSotra, Code.NATURE_OPERATION_ANNULATION_CS);
					else if(!carteSotra.getAchat().getTraitable().getDernierTraitement().getOperation().getNature().getCode().equals(Code.NATURE_OPERATION_RETRAIT_CARTE_LCS))
						serviceException("Votre demande de carte sotra est en cours de traitement");
					else 
						init(carteSotra, null);
						//
				}
			}
		}else{
			carteSotra = null;
			throw new ServiceException("Pas supporté");
		}
		
		return carteSotra;
	}
	
	private CarteSotra nouvelleCarte(DelegueSotra delegue){
		AchatCarteSotra achat = achatCarteSotraDao.readRecentByDelegue(delegue.getAgent());
		if(achat==null || Code.NATURE_OPERATION_RETRAIT_CARTE_LCS.equals(achat.getTraitable().getDernierTraitement().getOperation().getNature().getCode()))
			serviceException("Aucune demande de carte sotra possible! Votre delegue n'a pas encore ouvert de liste");
		else if(!Code.NATURE_OPERATION_OUVERTURE_LCS.equals(achat.getTraitable().getDernierTraitement().getOperation().getNature().getCode()))
			serviceException("Aucune demande de carte sotra possible! Votre delegue a clos la liste");
				
		CarteSotra carteSotra = new CarteSotra(achat, beneficiaireCarteSotraDao.readByAgent((AgentEtat) utilisateur()));
		init(carteSotra, Code.NATURE_OPERATION_DEMANDE_CS);
		return carteSotra;
	}
	
	@Override
	protected void associerTraitement(CarteSotra carteSotra) {
		carteSotra.getTraitable().getTraitement().setCarteSotra(carteSotra);
	}
	
	
	
	@Override
	protected void creation(CarteSotra carteSotra) {
		super.creation(carteSotra);
		carteSotra.setAchat(achatCarteSotraDao.readRecentByDelegue(delegueSotraDao.readByBeneficiaire((AgentEtat) utilisateur()).getAgent()));
		carteSotra.setBeneficiaire(beneficiaireCarteSotraDao.readByAgent((AgentEtat) utilisateur()));
	}
		
	@Override
	public void init(CarteSotra carteSotra, String natureOperationCode) {
		if(carteSotra.getAchat()==null)
			carteSotra.setAchat(achatCarteSotraDao.readRecentByDelegue(delegueSotraDao.readByBeneficiaire((AgentEtat) utilisateur()).getAgent()));
		achatCarteSotraService.init(carteSotra.getAchat(), natureOperationCode);
		super.init(carteSotra, natureOperationCode);
	}

	@Override
	public Collection<CarteSotra> findDemandes() {
		return ((CarteSotraDao)dao).readByAgent((AgentEtat)utilisateur());
	}

	@Override
	protected Object idValue(CarteSotra carte) {
		return carte.getId();
	}

	@Override
	protected TraitementCarteSotra traitementInstance() {
		return new TraitementCarteSotra();
	}

	@Override
	public CarteSotra find(Long id, String natureOperationCode)throws ServiceException {
		return null;
	}

	@Override
	protected Collection<TraitementCarteSotra> historiqueTraitements(CarteSotra objetTraite) {
		return null;
	}
	
	/**/
	
	@Override
	public void annuler(Collection<CarteSotra> carteSotras) {
		for(CarteSotra carteSotra : carteSotras){
			if(carteSotra.getTraitable().getTraitement()==null)
				carteSotra.getTraitable().setTraitement(new TraitementCarteSotra());
			carteSotra.getTraitable().setNatureOperation(genericDao.readByClass(NatureOperation.class, Code.NATURE_OPERATION_ANNULATION_CS));
			carteSotra.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
		}
		valider(carteSotras);
	}
	
	@Override
	protected void validationAccepter(CarteSotra carteSotra) {
		/*if(Code.NATURE_OPERATION_ANNULATION_CS.equals(carteSotra.getTraitable().getNatureOperation().getCode()))
			;
		else
			super.validationAccepter(carteSotra);
			*/
		if(carteSotra.getId()==null)
			super.validationAccepter(carteSotra);
	}

	@Override
	public Collection<CarteSotra> findDemandesSolde() {
		// TODO Auto-generated method stub
		return null;
	}

}
