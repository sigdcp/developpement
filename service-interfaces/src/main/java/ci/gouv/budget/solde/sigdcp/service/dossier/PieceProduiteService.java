package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.List;

import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface PieceProduiteService extends AbstractService<PieceProduite,String> {

	
	public void reglerBulletinLiquidation(PieceProduite pieceProduite) throws ServiceException ;
	
	public List<PieceProduite> listerBulletinLiquidation(NatureDeplacement natureDeplacement);
	
}
