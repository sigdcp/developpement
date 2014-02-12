package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDto;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface AbstractDossierService<DOSSIER extends Dossier> extends AbstractService<DOSSIER,String> {
	
	void enregistrer(DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives,Personne personne) throws ServiceException;
	
	void soumettre(DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives,Personne personne) throws ServiceException;
	
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
	
	DOSSIER findSaisieByPersonneByNatureDeplacement(Personne personne,NatureDeplacement natureDeplacement);
	
	Collection<DOSSIER> findByNatureDeplacementAndStatut(NatureDeplacement natureDeplacement,Statut statut);
	
	Collection<DOSSIER> findByStatut(Statut statut);
	
	Collection<DOSSIER> findByAgentEtat(AgentEtat agentEtat);
	
	Collection<DossierDto> findByAgentEtatDto(AgentEtat agentEtat);
	
	Collection<DOSSIER> findByNatureDeplacement(NatureDeplacement natureDeplacement);

}
