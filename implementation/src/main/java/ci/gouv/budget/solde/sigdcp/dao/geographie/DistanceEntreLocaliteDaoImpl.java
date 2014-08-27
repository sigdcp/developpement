package ci.gouv.budget.solde.sigdcp.dao.geographie;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.dao.geographie.DistanceEntreLocaliteDao;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocalite;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocaliteId;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

public class DistanceEntreLocaliteDaoImpl extends JpaDaoImpl<DistanceEntreLocalite, DistanceEntreLocaliteId> implements DistanceEntreLocaliteDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public DistanceEntreLocalite readByLocalite1ByLocalite2(Localite localite1, Localite localite2) {
		try{
			return entityManager.createQuery("SELECT dl FROM DistanceEntreLocalite dl WHERE "
					+ " (dl.id.localite1 = :localite1Code AND dl.id.localite2 = :localite2Code) OR "
					+ " (dl.id.localite1 = :localite2Code AND dl.id.localite2 = :localite1Code)", clazz)
				.setParameter("localite1Code", localite1.getCode()).setParameter("localite2Code", localite2.getCode())
				.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
		
	}
	
}
