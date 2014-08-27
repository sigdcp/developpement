package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class DossierMissionValidator extends AbstractDossierValidator<DossierMission> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@Override
	public boolean isValidDatePriseService() {
		return true;
	}
	
	@AssertTrue(message="L'objet n'est pas valide",groups=Client.class)
	public boolean isValidObjet() {
		return true;
		/*
		try{
			validationPolicy.validateObjetMission( ((Mission)object.getDeplacement()).getObjetifs() );
			return true;
		}catch(Exception exception){
			return false;
		}
		*/
	}
	
	@AssertTrue(message="Une fonction ou une profession est obligatoire",groups=Client.class)
	public boolean isValidProfessionNonNull() {
		try{
			validationPolicy.validateProfessionMission(object.getFonction(),object.getProfession());
			return true;
		}catch(Exception exception){
			return false;
		}
	}

	

	
}
