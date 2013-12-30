package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

public class LocaliteDaoImpl extends JpaDaoImpl<Localite, String> implements LocaliteDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public Collection<Localite> findByTypeId(String typeId) {
		return entityManager.createQuery("SELECT localite FROM Localite localite WHERE localite.type.code = :typeId", clazz)
				.setParameter("typeId", typeId)
				.getResultList();
	}
	
}
 