package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;

import ci.gouv.budget.solde.sigdcp.dao.prestation.CommandeDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.prestation.Commande;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractPieceProuiteServiceImpl;

@Stateless
public class CommandeServiceImpl extends AbstractPieceProuiteServiceImpl<Commande> implements CommandeService {
	
	private static final long serialVersionUID = 8509906777706320841L;

	@Inject private CommandeDao commandeDao;
	 
	@Inject
	public CommandeServiceImpl() {
		super(null);
	}
	
	@PostConstruct
	private void postConstruct(){
		dao = commandeDao;
	}
	
	@Override
	protected void creation(Commande commande) {
		super.creation(commande);
		commande.setNumero(RandomStringUtils.randomAlphanumeric(6));
	}
	
	@Override
	protected void associerTraitement(Commande commande) {
		commande.getTraitable().getTraitement().setPieceProduite(commande);
	}
	
			
	@Override
	public Collection<Commande> findATraiter(Collection<NatureDeplacement> natureDeplacements,String codeNatureOperation) {
		return null;
	}

	@Override
	public Commande find(Long id, String natureOperationCode)throws ServiceException {
		return null;
	}






}
