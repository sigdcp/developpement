package ci.gouv.budget.solde.sigdcp.dao.prestation;

import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitableDao;
import ci.gouv.budget.solde.sigdcp.model.prestation.Commande;

public interface AbstractCommandeDao<COMMANDE extends Commande> extends TraitableDao<COMMANDE,Long> {

	

}
