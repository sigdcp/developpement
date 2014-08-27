package ci.gouv.budget.solde.sigdcp.dao.dossier;

import ci.gouv.budget.solde.sigdcp.dao.AbstractDynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Groupe;

public interface AbstractGroupeDao<GROUPE extends Groupe> extends AbstractDynamicEnumerationDao<GROUPE> {

	GROUPE readByGrade(Grade grade);

}
