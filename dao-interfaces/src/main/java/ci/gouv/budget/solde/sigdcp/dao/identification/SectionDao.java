package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;

public interface SectionDao extends DataAccessObject<Section,String> {

	Collection<Section> readBySectionTypeId(String id);

}
