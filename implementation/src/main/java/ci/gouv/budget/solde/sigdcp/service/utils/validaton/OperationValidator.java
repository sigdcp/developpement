package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.dao.traitement.StatutDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;

public class OperationValidator extends AbstractValidator<NatureOperation> implements Serializable {

	private static final long serialVersionUID = -9154448652669543194L;

	@Setter private String natureOperationCode;
	@Setter private Collection<Traitement> traitements;
	@Inject private StatutDao statutDao;
	
	@AssertTrue(message="Les dossiers ne sont pas soumis")
	public boolean isDossierSoumis(){
		//TODO 
		/*for(Traitement traitement : traitements){
			Statut statutCourant = statutDao.readCourantByDossier(traitement.getDossier());
			if(statutCourant==null || !Code.STATUT_SOUMIS.equals(statutCourant.getCode()))
				return false;
		}*/
		return true;
	}
	
	
	

	
}
