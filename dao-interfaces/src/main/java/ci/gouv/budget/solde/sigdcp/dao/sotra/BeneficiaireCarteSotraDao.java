package ci.gouv.budget.solde.sigdcp.dao.sotra;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.sotra.BeneficiaireCarteSotra;

public interface BeneficiaireCarteSotraDao extends DataAccessObject<BeneficiaireCarteSotra,Long> {

	Collection<BeneficiaireCarteSotra> readByValide(Boolean valide);
	
	Collection<BeneficiaireCarteSotra> readValideIsNull();

	/**
	 * Liste des adherents a la liste de base de carte sotra de cet agent de l'etat
	 * @param agentEtat
	 * @return
	 */
	Collection<BeneficiaireCarteSotra> readValideByDelegue(AgentEtat agentEtat);

	BeneficiaireCarteSotra readByAgent(AgentEtat agentEtat);
} 
