package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;

public interface DossierService extends AbstractDossierService<Dossier> {
	Collection<Dossier> findStatistiqueDetailByNatureDeplacementsByNatureOperationByStatut(Collection<NatureDeplacement> natureDeplacements,NatureOperation natureOperation, Statut statut);

	Collection<Dossier> findStatiqueDetailBySection(Collection<Section> sections);
	 
}
 