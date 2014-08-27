package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.prestation.Commande;

public abstract class AbstractCommandeDaoImpl<COMMANDE extends Commande> extends TraitableDaoImpl<COMMANDE, Long> implements AbstractCommandeDao<COMMANDE> , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	
}

