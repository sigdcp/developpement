package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;

public class DossierDaoImpl extends AbstractDossierDaoImpl<Dossier> implements DossierDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;


	@Override
	public Collection<Dossier> readStatistiqueDetailByNatureDeplacementByNatureOperationByStatut(Collection<NatureDeplacement> natureDeplacements, NatureOperation natureOperation, Statut statut) {
		return entityManager.createQuery("SELECT d FROM Dossier d WHERE d.deplacement.nature IN :natureDeplacements "
				+ " AND EXISTS (SELECT td FROM TraitementDossier td WHERE td.dossier=d and td.operation.nature= :nop and td.statut= :statut) ", clazz)
				.setParameter("natureDeplacements", natureDeplacements)
				.setParameter("nop", natureOperation)
				.setParameter("statut", statut)
				.getResultList();
	}


	@Override
	public Collection<Dossier> readStatiqueDetailBySection(Collection<Section> sections) {
		return entityManager.createQuery("SELECT d FROM Dossier d WHERE d.service IN :sections "
				+ "AND EXISTS (SELECT td FROM TraitementDossier td WHERE td.dossier = d "
				+ "AND td.operation.nature.code = :nop AND td.statut.code = :statut)", clazz)
				.setParameter("sections", sections)
				.setParameter("nop", Code.NATURE_OPERATION_VALIDATION_BL)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getResultList();
	} 

}
