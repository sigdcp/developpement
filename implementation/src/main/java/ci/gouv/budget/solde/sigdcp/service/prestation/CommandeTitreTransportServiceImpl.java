package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;

import ci.gouv.budget.solde.sigdcp.dao.prestation.CommandeTitreTransportDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDemandeCotationMissionDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractPieceProuiteServiceImpl;

@Stateless
public class CommandeTitreTransportServiceImpl extends AbstractPieceProuiteServiceImpl<CommandeTitreTransport> implements CommandeTitreTransportService {
	
	private static final long serialVersionUID = 8509906777706320841L;

	@Inject private PrestataireDemandeCotationMissionDao prestataireDemandeCotationMissionDao;
	@Inject private PrestataireDemandeCotationMissionService prestataireDemandeCotationMissionService;
	@Inject private CommandeTitreTransportDao commandeTitreTransportDao;
	
	@Inject
	public CommandeTitreTransportServiceImpl() {
		super(null);
	}
	
	@PostConstruct
	private void postConstruct(){
		dao = commandeTitreTransportDao;
	}
	
	@Override
	protected void creation(CommandeTitreTransport commande) {
		super.creation(commande);
		commande.setNumero(RandomStringUtils.randomAlphanumeric(6));
	}
	
	@Override
	protected void associerTraitement(CommandeTitreTransport commande) {
		commande.getTraitable().getTraitement().setPieceProduite(commande);
	}
	
	/*@Override
	public void init(CommandeTitreTransport commande, String natureOperationCode) {
		commande.getTraitable().setTraitement(new TraitementPieceProduite());
		super.init(commande, natureOperationCode);
		commande.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
	}*/
		
	@Override
	protected Collection<CommandeTitreTransport> firstFlow(NatureOperation natureOperation) {
		Collection<CommandeTitreTransport> list = new ArrayList<>();
		for(PrestataireDemandeCotationMission reponse : prestataireDemandeCotationMissionDao.readATraiter()){
			prestataireDemandeCotationMissionService.init(reponse);
			list.add(new CommandeTitreTransport(null, reponse));
		} 
		return list;
	}
	
	@Override
	public void init(CommandeTitreTransport commande,String natureOperationCode) {
		super.init(commande, natureOperationCode);
		prestataireDemandeCotationMissionService.init(commande.getPrestataireDemandeCotationMission());
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public CommandeTitreTransport nouveau(PrestataireDemandeCotationMission cotation, String natureOperationCode){
		CommandeTitreTransport c = new CommandeTitreTransport();
		prestataireDemandeCotationMissionService.nouveau(cotation);
		c.setPrestataireDemandeCotationMission(cotation);
		init(c,natureOperationCode);
		return c;
	}
	
	@Override
	protected void initFirstFlow(CommandeTitreTransport commande) {
		super.initFirstFlow(commande);
		commande.setType(genericDao.readByClass(TypePieceProduite.class, Code.TYPE_PIECE_PRODUITE_BC));
	}


	
	@Override
	public Collection<CommandeTitreTransport> findATraiter(Collection<NatureDeplacement> natureDeplacements,String codeNatureOperation) {
		return null;
	}

	@Override
	public CommandeTitreTransport find(Long id, String natureOperationCode)throws ServiceException {
		CommandeTitreTransport c ;//=new CommandeTitreTransport();
		c=commandeTitreTransportDao.read(id);
		init(c,null);
		return c;
	}

	@Override
	public Collection<CommandeTitreTransport> findByPrestataire(Long prestataire) {
		
		Collection<CommandeTitreTransport> list = new ArrayList<>();
		for(CommandeTitreTransport c : commandeTitreTransportDao.readByPrestataire(prestataire)){
			init(c,null);
			list.add(c);
		} 
		return list;
	}


}
