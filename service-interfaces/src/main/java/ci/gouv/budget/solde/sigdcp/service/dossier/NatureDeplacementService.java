package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;
import java.util.Date;

import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface NatureDeplacementService extends AbstractService<NatureDeplacement,String> {
	
	Collection<NatureDeplacement> findByCategorieId(String code);

	//Collection<NatureDeplacement> findStatistiqueByCategorieDeplacementIdByNatureOperationIdByStatutId(String code, String nopId, String statutId);
	
	DeplacementStatistiques<NatureDeplacement> findStatistiqueByNatureOperationByStatut(Collection<NatureDeplacement> natureDeplacements,Date debut,Date fin, NatureOperation nopId, Statut statutId);

	/*
	NatureDeplacement findByIdWithPieceJustificativeAFournir(String identifiant);
	*/
}
