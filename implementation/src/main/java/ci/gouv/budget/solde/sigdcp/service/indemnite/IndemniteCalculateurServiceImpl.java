package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import ci.gouv.budget.solde.sigdcp.dao.GenericDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.BulletinLiquidationDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteCalculee;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteCalculeeId;
import ci.gouv.budget.solde.sigdcp.model.indemnite.RegleCalcul;
import ci.gouv.budget.solde.sigdcp.model.indemnite.RegleCalcul.Type;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public class IndemniteCalculateurServiceImpl implements IndemniteCalculateurService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private IndemniteOperandeService indemniteOperandeService;
	@Inject private BulletinLiquidationDao bulletinLiquidationDao;
	@Inject private GenericDao genericDao;
	
	private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	private ScriptEngine engine;
	
	
	@Override
	public void calculer(BulletinLiquidation bulletinLiquidation) throws ServiceException {
		if(!bulletinLiquidationDao.existByBulletinLiquidationIdNotEqual(bulletinLiquidation))
			calculerDossier(bulletinLiquidation.getDossier());
		bulletinLiquidation.setMontant(new BigDecimal(0));
		bulletinLiquidation.getIndemniteCalculees().clear();
		for(IndemniteCalculee ict : bulletinLiquidation.getDossier().getIndemniteCalculees()){	
			BigDecimal resultat = ict.getMontant().multiply(bulletinLiquidation.getPourcentage());
			bulletinLiquidation.setMontant(bulletinLiquidation.getMontant().add(resultat));
			bulletinLiquidation.getIndemniteCalculees().add(new IndemniteCalculee(ict.getId(),resultat,genericDao.readByClass(RegleCalcul.class,ict.getId().getIndeminiteId())));
		}
	}

	private void calculerDossier(Dossier dossier){
		dossier.getIndemniteCalculees().clear();
		engine = scriptEngineManager.getEngineByName("JavaScript");
		//Variables globales
		engine.put("dossier", dossier);
		engine.put("o", indemniteOperandeService);
		engine.put("masculin",Sexe.MASCULIN.equals(dossier.getBeneficiaire().getSexe()));
		engine.put("groupe", dossier.getGroupe());
		
		dossier.setMontantIndemnisation(new BigDecimal(0));
		System.out.println("IndemniteCalculateurServiceImpl.calculerDossier() 01 :"+dossier.getDeplacement().getNature().getIndemnites());
		for(RegleCalcul indemnite : dossier.getDeplacement().getNature().getIndemnites()){
			System.out.println("IndemniteCalculateurServiceImpl.calculerDossier() 02 :");
			if(Type.INDEMNITE.equals(indemnite.getType()))
				try {System.out.println("IndemniteCalculateurServiceImpl.calculerDossier() 03 :");
					engine.eval(indemnite.getScript());
					BigDecimal resultat = new BigDecimal(engine.get("resultat").toString());
					dossier.setMontantIndemnisation(dossier.getMontantIndemnisation().add(resultat));
					dossier.getIndemniteCalculees().add(new IndemniteCalculee(new IndemniteCalculeeId(dossier.getId(),indemnite.getCode()),resultat,indemnite));
				} catch (ScriptException e) {
					throw new ServiceException(e);
				}
		}
	}
	

}
