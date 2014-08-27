package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.AssertTrue;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class DossierTransitValidator extends AbstractDossierValidator<DossierTransit> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@Override
	public boolean isValidDatePriseService() {
		if(Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE.equals(object.getDeplacement().getNature().getCode()))
			return super.isValidDatePriseService();
		return true;
	}
	
	@AssertTrue(message="la date de fin de service n'est pas valide",groups=Client.class)
	public boolean isValidDateFinService(){
		if(Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE.equals(object.getDeplacement().getNature().getCode()))
			try {
				validationPolicy.validateDateFinService(object.getDatePriseService(), object.getDateFin());
				return true;
			} catch (Exception e) {
				return false;
			}
		return true;
	}
	
	@AssertTrue(message="la date de mise en stage n'est pas valide",groups=Client.class)
	public boolean isValidDateMiseStage(){
		if(Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE.equals(object.getDeplacement().getNature().getCode()))
			try {
				validationPolicy.validateDateMiseStage(object.getBeneficiaire(), object.getDateMiseStage());
				return true;
			} catch (Exception e) {
				return false;
			}
		return true;
	}
	
	@AssertTrue(message="la date de fin de stage n'est pas valide",groups=Client.class)
	public boolean isValidDateFinStage(){
		if(Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE.equals(object.getDeplacement().getNature().getCode()))
			try {
				validationPolicy.validateDateFinStage(object.getDateMiseStage(), object.getDateFin());
				return true;
			} catch (Exception e) {
				return false;
			}
		return true;
	}
	
	@AssertTrue(message="le montant de la facture n'est pas valide",groups=Client.class)
	public boolean isValidMontantFacture(){
		try {
			validationPolicy.validateMontantFacture(new BigDecimal(object.getMontantFacture()));
			return true;
		} catch (Exception e) {
			return false;
		}
	}	
	
}
