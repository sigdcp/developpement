package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceProduiteDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public class PieceProduiteServiceImpl extends AbstractPieceProuiteServiceImpl<PieceProduite> implements PieceProduiteService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public PieceProduiteServiceImpl(PieceProduiteDao dao) {
		super(dao);
	}
	 
	@Override
	public Collection<PieceProduite> findByTypeId(String typeId) {
		return ((PieceProduiteDao)dao).readByTypeId(typeId);
	}
	
	@Override
	public Collection<PieceProduite> findByCategorieIdByTypePieceId(String cdid, String typeId) {
		return null;
	}
	


	@Override
	public Collection<PieceProduite> findATraiter(
			Collection<NatureDeplacement> natureDeplacements,
			String codeNatureOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PieceProduite findDemande(Long id, String natureOperationCode)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PieceProduite find(Long id, String natureOperationCode)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void valider(Collection<PieceProduite> traitables)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<PieceProduite> findATraiter(String natureOperationCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(PieceProduite traitable, String natureOperationCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<PieceProduite> findDemandes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PieceProduite findById(Long identifiant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PieceProduite> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean exist(Long identifiant) {
		// TODO Auto-generated method stub
		return null;
	}

}
