package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceValidationUtils;

public class DossierDDValidator extends AbstractValidator<DossierDD> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@AssertTrue(message="Le dossier doit appartenir a un bénéficiaire")
	private boolean hasBenficiaire(){
		return object.getBeneficiaire()!=null;
	}
	
	@AssertTrue(message="la date de prise de service doit être inférieur a la date de cessation de service")
	private boolean hasDatePriseServiceBeforeDateCessationService(){
		return false;//object.getDatePriseService().before(object.getDateCessationService());
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
