package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.calendrier.ExerciceDao;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class ExerciceServiceImpl extends DefaultServiceImpl<Exercice, Integer> implements ExerciceService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	
	@Inject
	public ExerciceServiceImpl(ExerciceDao dao) {
		super(dao);
	}


	@Override
	public Exercice findCurrent() {
		Collection<Exercice> exercices = ((ExerciceDao)dao).readByStatut(true);
		return exercices.iterator().next();
	}
	

	
}
