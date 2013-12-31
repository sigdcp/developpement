package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface AbstractDossierService<DOSSIER extends Dossier> extends AbstractService<DOSSIER,String> {

	/**
	 * Transactions : service métier
	 */
	
	/**
	 * Attribut un numero au dossier et l'enregistre 
	 * @param dossier
	 * @throws ServiceException
	 */
	void enregistrer(DOSSIER dossier) throws ServiceException;
	
	void enregistrer(DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives) throws ServiceException;
	
	void deposer(DOSSIER dossier) throws ServiceException;
	
	void accepterRecevabilite(DOSSIER dossier) throws ServiceException;
	
	void accepterRecevabilite(Collection<DOSSIER> dossiers) throws ServiceException;
	
	void rejeterRecevabilite(DOSSIER dossier,String motif) throws ServiceException;
	
	void rejeterRecevabilite(Collection<DOSSIER> dossiers,String motif) throws ServiceException;
	
	void accepterConformite(DOSSIER dossier) throws ServiceException;
	
	void accepterConformite(Collection<DOSSIER> dossiers) throws ServiceException;
	
	void rejeterConformite(DOSSIER dossier,String motif) throws ServiceException;
	
	void rejeterConformite(Collection<DOSSIER> dossiers,String motif) throws ServiceException;
	
	//public void liquider(DOSSIER dossier) throws ServiceException;
	
	/**
	 * Lectures
	 */
	
	//DOSSIER findByIdWithPieceJustificative(String identifiant);
	
	Collection<DOSSIER> findAll(NatureDeplacement natureDeplacement,Statut statut);
}