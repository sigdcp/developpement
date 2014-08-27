package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;

import ci.gouv.budget.solde.sigdcp.dao.prestation.CommandeObsequeDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDemandeCotationObsequeDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractPieceProuiteServiceImpl;

@Stateless 
public class CommandeObsequeServiceImpl extends AbstractPieceProuiteServiceImpl<CommandeObseque> implements CommandeObsequeService {
	
	private static final long serialVersionUID = 8509906777706320841L;

	@Inject private PrestataireDemandeCotationObsequeDao prestataireDemandeCotationObsequeDao;
	@Inject private PrestataireDemandeCotationObsequeService prestataireDemandeCotationObsequeService;
	@Inject private CommandeObsequeDao commandeObsequeDao;
	
	@Inject
	public CommandeObsequeServiceImpl() {
		super(null);
	}
	
	@PostConstruct
	private void postConstruct(){
		dao = commandeObsequeDao;
	}
	
	@Override
	protected void creation(CommandeObseque commande) {
		super.creation(commande);
		commande.setNumero(RandomStringUtils.randomAlphanumeric(6));
	}
	
	@Override
	protected void associerTraitement(CommandeObseque commande) {
		commande.getTraitable().getTraitement().setPieceProduite(commande);
	}
	
	/*@Override
	public void init(CommandeObseque commande, String natureOperationCode) {
		commande.getTraitable().setTraitement(new TraitementPieceProduite());
		super.init(commande, natureOperationCode);
		commande.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
	}*/
	@Override
	public void init(CommandeObseque commande,String natureOperationCode) {
		// TODO Auto-generated method stub
		super.init(commande, natureOperationCode);
		prestataireDemandeCotationObsequeService.init(commande.getPrestataireDemandeCotationObseque());
		
	}
	@Override
	protected Collection<CommandeObseque> firstFlow(NatureOperation natureOperation) {
		Collection<CommandeObseque> list = new ArrayList<>();
		for(PrestataireDemandeCotationObseque reponse : prestataireDemandeCotationObsequeDao.readATraiter()){
			prestataireDemandeCotationObsequeService.init(reponse);
			list.add(new CommandeObseque(null, reponse));
		} 
		return list;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public CommandeObseque nouveau(PrestataireDemandeCotationObseque cotation, String natureOperationCode){
		CommandeObseque c = new CommandeObseque();
		prestataireDemandeCotationObsequeService.nouveau(cotation);
		c.setPrestataireDemandeCotationObseque(cotation);
		init(c,natureOperationCode);
		return c;
	}
	
	@Override
	protected void initFirstFlow(CommandeObseque commande) {
		super.initFirstFlow(commande);
		commande.setType(genericDao.readByClass(TypePieceProduite.class, Code.TYPE_PIECE_PRODUITE_BC));
	}



	
	
	@Override
	public Collection<CommandeObseque> findATraiter(Collection<NatureDeplacement> natureDeplacements,String codeNatureOperation) {
		return null;
	}

	@Override
	public CommandeObseque find(Long id, String natureOperationCode)throws ServiceException {
		return null;
	}

	@Override
	public Collection<CommandeObseque> findByPrestataire(Long prestataire) {
		Collection<CommandeObseque> list = new ArrayList<>();
		for(CommandeObseque c : commandeObsequeDao.readByPrestataire(prestataire)){
			init(c,null);
			list.add(c);
		} 
		return list;
	}






}
