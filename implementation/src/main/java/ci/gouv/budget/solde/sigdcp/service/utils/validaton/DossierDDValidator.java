package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class DossierDDValidator extends AbstractDossierValidator<DossierDD> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@AssertTrue(message="le grade n'est pas valide",groups=Client.class)
	public boolean isValidGrade(){
		try {
			validationPolicy.validateGrade(object.getGrade());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="le service d'accueil n'est pas valide",groups=Client.class)
	public boolean isValidServiceAcceuil(){
		if(Code.NATURE_DEPLACEMENT_AFFECTATION.equals(object.getDeplacement().getNature().getCode()))
			try {
				validationPolicy.validateServiceAccueil(object.getService());
				return true;
			} catch (Exception e) {
				return false;
			}
		return true;
	}
	
	@AssertTrue(message="vous ne pouvez pas effectuer plus d'une demande de départ à la retraite",groups=Client.class)
	public boolean isValidDepartRetraite(){
		if(Code.NATURE_DEPLACEMENT_RETRAITE.equals(object.getDeplacement().getNature().getCode()))
			try {
				validationPolicy.validateDepartRetraite(object.getBeneficiaire().getMatricule());
				return true;
			} catch (Exception e) {
				return false;
			}
		return true;
	}

}
