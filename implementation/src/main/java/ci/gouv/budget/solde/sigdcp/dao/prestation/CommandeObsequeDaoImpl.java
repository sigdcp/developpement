package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeObseque;

public class CommandeObsequeDaoImpl extends AbstractCommandeDaoImpl<CommandeObseque> implements CommandeObsequeDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<CommandeObseque> readByPrestataire(Long prestataire) {
		return entityManager.createQuery("SELECT c FROM CommandeObseque c WHERE "
				+ "EXISTS (SELECT pd FROM PrestataireDemandeCotationObseque pd WHERE pd.id.prestataireId = :prestataire) "
				+ "AND EXISTS (SELECT t FROM Traitement t WHERE t=c.traitable.dernierTraitement AND t.operation.nature.code = :operation AND t.statut.code = :statut)", clazz)
				.setParameter("prestataire", prestataire)
				.setParameter("operation", Code.NATURE_OPERATION_VISA_BCTT)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getResultList();
	}

	
}

