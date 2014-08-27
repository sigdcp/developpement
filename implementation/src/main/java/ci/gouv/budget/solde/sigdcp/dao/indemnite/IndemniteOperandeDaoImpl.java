package ci.gouv.budget.solde.sigdcp.dao.indemnite;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMissionId;

public class IndemniteOperandeDaoImpl extends JpaDaoImpl<LocaliteGroupeMission, LocaliteGroupeMissionId> implements IndemniteOperandeDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Inject private EntityManager entityManager;

}
