package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;

public interface AbstractDossierDao<DOSSIER extends Dossier> extends DataAccessObject<DOSSIER,String> {

	//DOSSIER readWithPieceJustificative(String identifiant);
	
	Collection<DOSSIER> readByNatureDeplacementAndStatut(NatureDeplacement natureDeplacement,Statut statut);
	
	Collection<DOSSIER> readByStatut(Statut statut);
	
	Collection<DOSSIER> readByNatureDeplacement(NatureDeplacement natureDeplacement);
 
} 
   