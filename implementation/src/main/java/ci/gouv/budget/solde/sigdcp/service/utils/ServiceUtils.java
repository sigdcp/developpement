package ci.gouv.budget.solde.sigdcp.service.utils;

import java.util.ArrayList;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation.AspectLiquide;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public class ServiceUtils {
	
	//@Inject @Getter private PersistenceUtils persistenceUtils;
	
	public void throwNotYetImplemented(){
		throw new ServiceException("Ce service n'a pas encore été implémenté!");
	}
	
	public BulletinLiquidation getBulletinLiquidationDD(Collection<BulletinLiquidation> bulletinLiquidations){
		for(BulletinLiquidation bulletinLiquidation : bulletinLiquidations)
			if(Code.CATEGORIE_DEPLACEMENT_DEFINITIF.equals(bulletinLiquidation.getDossier().getDeplacement().getNature().getCategorie().getCode()))
				return bulletinLiquidation;
		return null;
	}
	
	public BulletinLiquidation getBulletinLiquidationMissionTransport(Collection<BulletinLiquidation> bulletinLiquidations){
		Collection<BulletinLiquidation> l = getBulletinLiquidationMission(bulletinLiquidations, AspectLiquide.TITRE_TRANSPORT);
		return l.isEmpty()?null:l.iterator().next();
	}
	
	public Collection<BulletinLiquidation> getBulletinLiquidationMissionDemande(Collection<BulletinLiquidation> bulletinLiquidations){
		return getBulletinLiquidationMission(bulletinLiquidations, AspectLiquide.DEMANDE);
	}
	
	public Collection<BulletinLiquidation> getBulletinLiquidationMission(Collection<BulletinLiquidation> bulletinLiquidations,AspectLiquide aspectLiquide){
		Collection<BulletinLiquidation> l =new ArrayList<>();
		for(BulletinLiquidation bulletinLiquidation : bulletinLiquidations)
			if(Code.CATEGORIE_DEPLACEMENT_MISSION.equals(bulletinLiquidation.getDossier().getDeplacement().getNature().getCategorie().getCode()) && bulletinLiquidation.getAspect().equals(aspectLiquide))
				l.add(bulletinLiquidation);
		return l;
	}
	
}
