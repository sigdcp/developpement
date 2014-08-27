package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;

public class DossierObsequesDaoImpl extends AbstractDossierDaoImpl<DossierObseques> implements DossierObsequesDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<DossierObseques> readACoter() {
		return entityManager.createQuery("SELECT d FROM DossierObseques d WHERE d.traitable.dernierTraitement.operation.nature.code = :natureOperationId "
				+ "AND d.traitable.dernierTraitement.statut.code = :statutId  "
				+ " AND NOT EXISTS (SELECT dde FROM  DemandeCotationObseque dde WHERE dde.dossier=d)", clazz)
				.setParameter("natureOperationId", Code.NATURE_OPERATION_CONFORMITE)
				.setParameter("statutId", Code.STATUT_ACCEPTE)
				.getResultList();
	}

	
	
	 

}
