package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.calendrier.LettreAvance;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;

public class LettreAvanceDaoImpl extends JpaDaoImpl<LettreAvance, Long> implements LettreAvanceDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<LettreAvance> readbySection(Collection<Section> sections) {
		return entityManager.createQuery("SELECT l FROM LettreAvance l WHERE l.beneficiaire.code IN :sections", clazz)
		.setParameter("sections", sections)
		.getResultList();
	}
 

	
	
}
