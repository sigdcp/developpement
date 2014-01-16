package ci.gouv.budget.solde.sigdcp.dao.calendrier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;

public class CalendrierMissionDaoImpl extends JpaDaoImpl<CalendrierMission, Long> implements CalendrierMissionDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<CalendrierMission> readByExercice(Exercice exercice) {
		return entityManager.createQuery("SELECT calendrier FROM CalendrierMission calendrier WHERE calendrier.exercice = :exercice", clazz)
				.setParameter("exercice", exercice)
				.getResultList();
	}

	@Override
	public Collection<CalendrierMission> readByMinistere(Section ministere) {
		return entityManager.createQuery("SELECT calendrier FROM CalendrierMission calendrier WHERE calendrier.section = :ministere", clazz)
				.setParameter("ministere", ministere)
				.getResultList();
	}
	

}
