package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;

public interface DossierDao extends AbstractDossierDao<Dossier> {
Collection<Dossier> readStatistiqueDetailByNatureDeplacementByNatureOperationByStatut(Collection<NatureDeplacement> natureDeplacements,	NatureOperation natureOperation, Statut statut);

Collection<Dossier> readStatiqueDetailBySection(Collection<Section> sections);
}
  