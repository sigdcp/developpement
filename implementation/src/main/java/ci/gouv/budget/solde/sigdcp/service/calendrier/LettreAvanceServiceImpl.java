package ci.gouv.budget.solde.sigdcp.service.calendrier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.calendrier.LettreAvanceDao;
import ci.gouv.budget.solde.sigdcp.model.calendrier.LettreAvance;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class LettreAvanceServiceImpl extends DefaultServiceImpl<LettreAvance, Long> implements LettreAvanceService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private LettreAvanceDao lettreAvanceDao;
	
	@Inject
	public LettreAvanceServiceImpl(LettreAvanceDao dao) {
		super(dao);
	}

	@Override
	public void enregistrer(LettreAvance lettreAvance) {		
		dao.create(lettreAvance); 		 
	}

	@Override
	public Collection<LettreAvance> findBySection(Collection<Section> sections) {
		return lettreAvanceDao.readbySection(sections);
	}

	


}
