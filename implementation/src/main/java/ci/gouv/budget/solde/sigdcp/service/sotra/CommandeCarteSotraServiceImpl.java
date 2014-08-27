package ci.gouv.budget.solde.sigdcp.service.sotra;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;

import ci.gouv.budget.solde.sigdcp.dao.prestation.CommandeCarteSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.sotra.AchatCarteSotraDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementAchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractPieceProuiteServiceImpl;

@Stateless
public class CommandeCarteSotraServiceImpl extends AbstractPieceProuiteServiceImpl<CommandeCarteSotra> implements CommandeCarteSotraService {
	
	private static final long serialVersionUID = 8509906777706320841L;

	@Inject private AchatCarteSotraDao achatCarteSotraDao;
	@Inject private AchatCarteSotraService achatCarteSotraService;
	@Inject private CommandeCarteSotraDao commandeCarteSotraDao;
	
	public CommandeCarteSotraServiceImpl() {
		super(null);
		//dao = commandeCarteSotraDao;
	}
	
	@PostConstruct
	private void postConstruct(){
		dao = commandeCarteSotraDao;
	}
	
	@Override
	protected void creation(CommandeCarteSotra commande) {
		super.creation(commande);
		commande.setNumero(RandomStringUtils.randomAlphanumeric(6));
	}
	
	@Override
	protected void associerTraitement(CommandeCarteSotra commande) {
		commande.getTraitable().getTraitement().setPieceProduite(commande);
	}
		
	@Override
	protected Collection<CommandeCarteSotra> firstFlow(NatureOperation natureOperation) {
		Collection<CommandeCarteSotra> list = new ArrayList<>();
		Collection<AchatCarteSotra> achats = achatCarteSotraDao.readCommandeIsNullByNatureOperationIdByStatutId(Code.NATURE_OPERATION_VALIDATION_LCS, Code.STATUT_ACCEPTE);
		achats.addAll(achatCarteSotraDao.readCommandeIsNullByNatureOperationIdByStatutId(Code.NATURE_OPERATION_GENERATION_CCS, Code.STATUT_DIFFERE));
		for(AchatCarteSotra achat : achats){
			achatCarteSotraService.init(achat, natureOperation.getCode());
			list.add(new CommandeCarteSotra(null, achat));
		}
		return list;
	}
	
	@Override
	protected void validationDifferer(CommandeCarteSotra commandeCarteSotra) {
		if(commandeCarteSotra.getTraitable().getNatureOperation().getPrecedent()==null){
			AchatCarteSotra achat = commandeCarteSotra.getAchat();
			TraitementAchatCarteSotra traitementAchatCarteSotra = new TraitementAchatCarteSotra(commandeCarteSotra.getTraitable().getTraitement().getOperation(), 
					commandeCarteSotra.getTraitable().getTraitement().getStatut(), achat);
			traitementAchatCarteSotra.setObservation(commandeCarteSotra.getTraitable().getTraitement().getObservation());
			traitementAchatCarteSotra.setStatut(commandeCarteSotra.getTraitable().getTraitement().getStatut());
			traitementAchatCarteSotra.setValidationType(commandeCarteSotra.getTraitable().getTraitement().getValidationType());
			entityManager.persist(traitementAchatCarteSotra);
			achat.getTraitable().setDernierTraitement(traitementAchatCarteSotra);
			achatCarteSotraDao.update(achat);
		}
	}
	
	@Override
	protected void validationRejeter(CommandeCarteSotra objetTraite) {
		validationDifferer(objetTraite);
	}

	@Override
	public void init(CommandeCarteSotra commande, String natureOperationCode) {
		achatCarteSotraService.init(commande.getAchat(), natureOperationCode);
		commande.getTraitable().setTraitement(new TraitementPieceProduite());
		super.init(commande, natureOperationCode);
		commande.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
	}
	
	@Override
	protected void initFirstFlow(CommandeCarteSotra commande) {
		super.initFirstFlow(commande);
		commande.setType(genericDao.readByClass(TypePieceProduite.class, Code.TYPE_PIECE_PRODUITE_BC));
	}

	@Override
	public Collection<CommandeCarteSotra> findATraiter(Collection<NatureDeplacement> natureDeplacements,String codeNatureOperation) {
		return null;
	}

	@Override
	public CommandeCarteSotra find(Long id, String natureOperationCode) throws ServiceException {
		return null;
	}




}
