package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

public interface AbstractDossierDao<DOSSIER extends Dossier> extends DataAccessObject<DOSSIER,String> {
	
	/**
	 * Ramene le dossier en saisie. Un dossier en saisie est un dossier avec un et un seul statut qui est SAISI
	 * @param agentEtat
	 * @param natureDeplacement
	 * @return
	 */
	DOSSIER readSaisieByPersonneByNatureDeplacement(Personne personne,NatureDeplacement natureDeplacement);
	
	Collection<DOSSIER> readByNatureDeplacementAndStatut(NatureDeplacement natureDeplacement,Statut statut);
	
	Collection<DOSSIER> readByStatut(Statut statut);
	
	Collection<DOSSIER> readByNatureDeplacement(NatureDeplacement natureDeplacement);
 
} 
   