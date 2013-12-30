package ci.gouv.budget.solde.sigdcp.service.dossier;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

//@Remote
public interface DossierDDService extends AbstractDossierService<DossierDD> {
	
	/**
	 * Creer une pièce justificative de type feuille de déplacement avec un numéro attribué par le système.
	 * Cette pièce est ajoutée à la liste des pièces justificatives du dossier
	 * la date de signature est mise à null , ce qui signifie que la pièce n'est pas encore retournée
	 * @param dossier
	 * @return le flux de l'état a imprimer
	 * @throws ServiceException
	 */
	byte[] editerFeuilleDeplacement(DossierDD dossier) throws ServiceException;
	
	void soumettreFeuilleDeplacement(DossierDD dossier,PieceJustificative pieceJustificative) throws ServiceException;
	 
}
 