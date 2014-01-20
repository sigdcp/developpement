package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

public interface PieceProduiteService extends AbstractDocumentService<PieceProduite> {
	
	Collection<PieceProduite> findByTypeId(String typeId);

	/*
	public void reglerBulletinLiquidation(PieceProduite pieceProduite) throws ServiceException ;
	
	public List<PieceProduite> listerBulletinLiquidation(NatureDeplacement natureDeplacement);
	*/
	
	/**
	 * Peut etre utiliser pour avoir la liste des piece produite a porter sur bordereau
	 * @param cdid
	 * @param typeId
	 * @return
	 */
	Collection<PieceProduite> findByCategorieIdByTypePieceId(String cdid,String typeId);
	
}
