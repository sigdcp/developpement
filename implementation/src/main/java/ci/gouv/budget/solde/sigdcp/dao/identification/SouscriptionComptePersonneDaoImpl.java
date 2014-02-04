package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;

public class SouscriptionComptePersonneDaoImpl extends AbstractSouscriptionDaoImpl<SouscriptionComptePersonne> implements SouscriptionComptePersonneDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public SouscriptionComptePersonne readByMatricule(String matricule) {
		try {
			return entityManager.createQuery("SELECT scp FROM SouscriptionComptePersonne scp WHERE scp.personneDemandeur.matricule = :matricule", clazz)
					.setParameter("matricule", matricule)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw e;
		} 
	}
	
}
 