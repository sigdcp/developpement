package ci.gouv.budget.solde.sigdcp.service.geographie;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocalite;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocaliteId;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface DistanceEntreLocaliteService extends AbstractService<DistanceEntreLocalite,DistanceEntreLocaliteId> {

	Collection<DistanceEntreLocalite> findDistLocLibelle();
}
