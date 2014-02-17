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
	
	@AssertTrue(message="la date de prise de service n'est pas valide",groups=Client.class)
	public boolean isValidDatePriseService(){
		try {
			validationPolicy.validateDatePriseService(object.getBeneficiaire(), object.getDatePriseService());
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
	
	@AssertTrue(message="la date de départ n'est pas valide",groups=Client.class)
	public boolean isValidDateDepart(){
		try {
			validationPolicy.validateDateDepart(object.getBeneficiaire(), object.getDeplacement().getDateDepart());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="la date d'arrivée n'est pas valide",groups=Client.class)
	public boolean isValidDateArrivee(){
		try {
			validationPolicy.validateDateArrivee(object.getDeplacement().getDateDepart(), object.getDeplacement().getDateArrivee());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="la ville de depart n'est pas valide",groups=Client.class)
	public boolean isValidVilleDepart(){
		try {
			validationPolicy.validateVilleDepart(object.getBeneficiaire(), object.getDeplacement().getLocaliteDepart());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="la ville d'arrivee n'est pas valide",groups=Client.class)
	public boolean isValidVilleArrivee(){
		try {
			validationPolicy.validateVilleArrivee(object.getDeplacement().getLocaliteDepart(), object.getDeplacement().getLocaliteArrivee());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*
	Date datecourante = new Date();
	boolean succes= true;
	
	if (!validationUtils.isOrdonne(dossier.getDatePriseService(),datecourante)){
		addMessageError("la date de prise de service ne doit pas être supérieure à la date d'aujourd'hui");
		succes=false;
	}

	if (!validationUtils.isOrdonne(dossier.getDateCessationService(),dossier.getDatePriseService()))
	{
		addMessageError("la date de cessation de service ne doit pas être supérieure à la date de prise de service");
		succes=false;
	}
	if (!validationUtils.isOrdonne(dossier.getDateMariage(),datecourante))
	{
		addMessageError("la date de mariage ne doit pas être supérieure à la date d'aujourd'hui");
		succes=false;
	}
	if (!validationUtils.isOrdonne(dossier.getDateMiseRetraite(),datecourante))
	{
		addMessageError("la date de mise à la retraite ne doit pas être supérieure à la date d'aujourd'hui");
		succes=false;
	}
	if (!validationUtils.isOrdonne(dossier.getDeplacement().getDateArrivee(),datecourante))
	{
		addMessageError("la date de d'arrivée ne doit pas être supérieure à la date d'aujourd'hui");
		succes=false;
	}
	if (!validationUtils.isOrdonne(dossier.getDeplacement().getDateArrivee(),dossier.getDatePriseService()))
	{
		addMessageError("la date de d'arrivée ne doit pas être supérieure à la date de prise de service");
		succes=false;
	}
	if (!validationUtils.isOrdonne(dossier.getDeplacement().getDateArrivee(),dossier.getDateMiseRetraite()))
	{
		addMessageError("la date de d'arrivée ne doit pas être supérieure à la date de mise à la retraite");
		succes=false;
	}
	if (!validationUtils.isOrdonne(dossier.getDeplacement().getDateDepart(),dossier.getDeplacement().getDateArrivee()))
	{
		addMessageError("la date de départ ne doit pas être supérieure à la date d'arrivée");
		succes=false;
	}
	
	return succes;
	*/
	
	
}
