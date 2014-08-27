package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.dao.AbstractDynamicEnumerationDaoImpl;
import ci.gouv.budget.solde.sigdcp.dao.dossier.AbstractGroupeDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Groupe;

public abstract class AbstractGroupeDaoImpl<GROUPE extends Groupe> extends AbstractDynamicEnumerationDaoImpl<GROUPE> implements AbstractGroupeDao<GROUPE>, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public GROUPE readByGrade(Grade grade) {
		try {
			return entityManager.createQuery("SELECT groupe FROM "+clazz.getSimpleName()+" groupe WHERE :grade MEMBER OF groupe.grades", clazz)
					.setParameter("grade", grade)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	
}
