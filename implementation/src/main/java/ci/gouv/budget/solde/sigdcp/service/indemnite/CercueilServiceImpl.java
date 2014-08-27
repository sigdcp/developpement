package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.indemnite.CercueilDao;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Cercueil;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class CercueilServiceImpl extends DefaultServiceImpl<Cercueil,String> implements CercueilService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	private CercueilDao cercueilDao;
	
	@Inject
	public CercueilServiceImpl(CercueilDao dao) {
		super(dao); 
	}

	@Override
	public Cercueil findByIndice(BigDecimal indice) {
		return cercueilDao.readByIndice(indice);
	}
	

}
