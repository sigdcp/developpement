package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePrestataire;

public class SouscriptionComptePrestataireDaoImpl extends AbstractSouscriptionDaoImpl<SouscriptionComptePrestataire> implements SouscriptionComptePrestataireDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	/*
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
	*/

	@Override
	public Collection<SouscriptionComptePrestataire> readDateValidationIsNullByTypePrestataireId(
			String typePrestataireId) {
		// TODO Auto-generated method stub
		return null;
	}
}
 