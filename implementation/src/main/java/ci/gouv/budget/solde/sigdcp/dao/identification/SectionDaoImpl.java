package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;

public class SectionDaoImpl extends JpaDaoImpl<Section, String> implements SectionDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	@Override
	public Collection<Section> readBySectionTypeId(String sectionTypeId) {
		return entityManager.createQuery("SELECT section FROM Section section WHERE section.type.code = :sectionTypeId", clazz)
				.setParameter("sectionTypeId", sectionTypeId)
				.getResultList();
	}
	
}