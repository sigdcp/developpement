package ci.gouv.budget.solde.sigdcp.service.prestation;

import ci.gouv.budget.solde.sigdcp.model.prestation.Commande;
import ci.gouv.budget.solde.sigdcp.model.prestation.Facture;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitableService;

/**
 * 
 * @author christian
 *
 * @param <PRESTATAIRE>
 */
public interface FactureService extends TraitableService<Facture,Long> {

	Facture nouveau(Commande commande);
} 
