package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Setter
public class MissionExecuteeValidator extends AbstractValidator<MissionExecutee> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@AssertTrue(message="La date d'arrivée n'est pas valide",groups=Client.class)
	public boolean isValidDateFin() {
		try{ 
			validationPolicy.validateDateArrivee(object.getDeplacement().getTypeDepense(),null,object.getDeplacement().getDateDepart(),object.getDeplacement().getDateArrivee());
			return true;
		}catch(Exception exception){
			return false;
		}
	}
	
	@AssertTrue(message="La ville d'arrivée n'est pas valide",groups=Client.class)
	public boolean isValidVilleArrivee() {
		try{
			validationPolicy.validateVilleArrivee(object.getDeplacement().getLocaliteDepart(),object.getDeplacement().getLocaliteArrivee());
			return true;
		}catch(Exception exception){
			return false;
		}
	}
	

	
}
