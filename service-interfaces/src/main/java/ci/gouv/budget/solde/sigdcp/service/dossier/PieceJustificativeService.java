package ci.gouv.budget.solde.sigdcp.service.dossier;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface PieceJustificativeService extends AbstractService<PieceJustificative,Long> {

	//Collection<PieceJustificative> findByDossier(Dossier dossier);
	
	//Collection<PieceJustificative> findByDossierByType(Dossier dossier,TypePieceJustificative type);
	
	/**
	 * La liste des pieces de base a fournir
	 * @param dossier
	 * @return
	 * @throws ServiceException
	 */
	/*
	Collection<PieceJustificative> findByNatureDeplacement(NatureDeplacement natureDeplacement) throws ServiceException;
	*/
	/**
	 * Suivant les informations fournis , le systeme determine la liste des pieces a fournir
	 * @param dossier
	 * @param pieceJustificatives
	 * @param parametres
	 * @return
	 * @throws ServiceException
	 */
	//Collection<PieceJustificative> findByDossier(Dossier dossier,Collection<PieceJustificative> pieceJustificatives,Map<String,Object> parametres) throws ServiceException;
	
	//Map<String,Object> findParametresByDossier(Dossier dossier,Collection<PieceJustificative> pieceJustificatives) throws ServiceException;
	
	
	
}
