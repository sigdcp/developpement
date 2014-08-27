package ci.gouv.budget.solde.sigdcp.dao.geographie;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.dao.geographie.LocaliteDao;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

public class LocaliteDaoImpl extends JpaDaoImpl<Localite, String> implements LocaliteDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public Collection<Localite> readByTypeId(String typeId) {
		return entityManager.createQuery("SELECT localite FROM Localite localite WHERE localite.type.code = :typeId", clazz)
				.setParameter("typeId", typeId)
				.getResultList();
	}

	@Override
	public Collection<Localite> readByTypeIdByParent(String typeId, Localite parent) {
		return entityManager.createQuery("SELECT localite FROM Localite localite WHERE localite.type.code = :typeId AND localite.parent = :parentLocalite", clazz)
				.setParameter("typeId", typeId)
				.setParameter("parentLocalite", parent)
				.getResultList();
	}
	
}
 