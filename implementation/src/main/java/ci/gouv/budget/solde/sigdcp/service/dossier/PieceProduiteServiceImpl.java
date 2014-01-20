package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceProduiteDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

@Stateless
public class PieceProduiteServiceImpl extends AbstractDocumentServiceImpl<PieceProduite> implements PieceProduiteService , Serializable {

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

}
