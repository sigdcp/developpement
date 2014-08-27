package ci.gouv.budget.solde.sigdcp.service.geographie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.geographie.DistanceEntreLocaliteDao;
import ci.gouv.budget.solde.sigdcp.dao.geographie.LocaliteDao;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocalite;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocaliteId;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class DistanceEntreLocaliteServiceImpl extends DefaultServiceImpl<DistanceEntreLocalite,DistanceEntreLocaliteId> implements DistanceEntreLocaliteService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	@Inject LocaliteDao localiteDao;
	
	@Inject
	public DistanceEntreLocaliteServiceImpl(DistanceEntreLocaliteDao dao) {
		super(dao);
	}

	@Override
	public Collection<DistanceEntreLocalite> findDistLocLibelle() {
		Collection<DistanceEntreLocalite> dl = new ArrayList<>();
		for(DistanceEntreLocalite distanceLocalite : dao.readAll()){
			distanceLocalite.setLocalite1(localiteDao.read(distanceLocalite.getId().getLocalite1()));
			distanceLocalite.setLocalite2(localiteDao.read(distanceLocalite.getId().getLocalite2()));
			dl.add(distanceLocalite);
		}
		return dl;
	}
	

}
