package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation.AspectLiquide;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitableService;

public interface AbstractDossierService<DOSSIER extends Dossier> extends TraitableService<DOSSIER,Long> {
	
	void enregistrer(ActionType actionType,DOSSIER dossier) throws ServiceException;
	
	void deposer(Collection<DOSSIER> dossiers) throws ServiceException;
	
	void annuler(Collection<DOSSIER> dossiers) throws ServiceException;
	
	void mettreAJourPiecesJustificatives(Collection<DOSSIER> dossiers);
	
	/**
	 * Lectures
	 */
	
	DOSSIER findDemande(NatureDeplacement natureDeplacement,Long numero,String codeNatureOperation);
	
	Collection<DOSSIER> findATraiter(Collection<NatureDeplacement> natureDeplacements,String natureOperationCode);
	
	Collection<DOSSIER> findByNatureDeplacementAndStatut(NatureDeplacement natureDeplacement,Statut statut);
	
	Collection<DOSSIER> findByNatureDeplacementsByStatut(Collection<NatureDeplacement> natureDeplacements,Statut statut);
		
	Collection<DOSSIER> findByNatureDeplacement(NatureDeplacement natureDeplacement);
	
	Collection<DOSSIER> findByDeplacement(Deplacement deplacement);
	
	DOSSIER findDernierCreeByAgentEtat(AgentEtat agentEtat);
	
	Collection<DOSSIER> findByBordereauId(Long bordereauId);
	
	//Collection<DOSSIER> findATraiterByNatureDeplacementsByNatureOperationId(Collection<NatureDeplacement> natureDeplacements,String natureOperationId);
	
	Collection<DOSSIER> findByNatureDeplacementsByNatureOperationIdByStatutId(Collection<NatureDeplacement> natureDeplacements,String natureOperationId,String statutId);
	
	Collection<DOSSIER> findALiquiderByNatureDeplacementsByAspectLiquide(Collection<NatureDeplacement> natureDeplacements,AspectLiquide aspectLiquide);
	
	Collection<DOSSIER> findEditerProjetRemboursement();

	
	
}
