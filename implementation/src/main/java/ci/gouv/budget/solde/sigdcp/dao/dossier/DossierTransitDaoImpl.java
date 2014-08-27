package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

public class DossierTransitDaoImpl extends AbstractDossierDaoImpl<DossierTransit> implements DossierTransitDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public DossierTransit readByAttestationPriseEnCharge(PieceProduite piece) {
		try {
			return entityManager.createQuery("SELECT d FROM DossierTransit d WHERE EXISTS (SELECT t FROM TraitementDossier t WHERE t.dossier = d AND t.pieceProduite = :piece )"
					, clazz)
					.setParameter("piece", piece)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	 

}
 