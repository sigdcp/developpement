package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;

import javax.persistence.NoResultException;

import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;

public class CommandeCarteSotraDaoImpl extends AbstractCommandeDaoImpl<CommandeCarteSotra> implements CommandeCarteSotraDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public CommandeCarteSotra readByAchat(AchatCarteSotra achat) {
		try {
			return entityManager.createQuery("SELECT commande FROM CommandeCarteSotra commande WHERE commande.achat = :achat", clazz)
					.setParameter("achat", achat)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	
	
}

