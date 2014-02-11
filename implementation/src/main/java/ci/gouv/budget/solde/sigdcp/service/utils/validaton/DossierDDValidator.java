package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class DossierDDValidator extends AbstractDossierValidator<DossierDD> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	
	
	@AssertTrue(message="le service est obligatoire",groups=Client.class)
	public boolean isServiceAcceuil(){
		if(Code.NATURE_DEPLACEMENT_AFFECTATION.equals(object.getDeplacement().getNature().getCode()))
			return object.getService()!=null;
		return true;
	}
	
	@AssertTrue(message="la date d'arrivée est obligatoire",groups=Client.class)
	public boolean isDateArrivee(){
		return object.getDeplacement().getDateArrivee()!=null;
	}
	
	@AssertTrue(message="la date de départ doit etre inférieure à la date d'arrivée",groups=Client.class)
	public boolean isDateDepartAvantDateArrivee(){
		if(object.getDeplacement().getDateArrivee()==null)
			return true;
		return /*object.getDeplacement().getDateArrivee()!=null &&*/ object.getDeplacement().getDateDepart().before(object.getDeplacement().getDateArrivee());
	}
	
	@AssertTrue(message="###",groups=Client.class)
	public boolean isPieceJustificativesCorrect(){
		pieceJustificativeValidator.setAutoClearMessages(Boolean.FALSE);
		pieceJustificativeValidator.setSoumission(soumission);
		for(PieceJustificative pj : pieceJustificatives)
			pieceJustificativeValidator.validate(pj);
		messages.addAll(pieceJustificativeValidator.getMessages());
		pieceJustificativeValidator.getMessages().clear();
		return pieceJustificativeValidator.isSucces();
	}
	
	/*
	@AssertTrue(message="la date de prise de service est obligatoire",groups=Client.class)
	private boolean isDatePriseNotNull(){
		return object.getDatePriseService()!=null;
	}
	*/
	/*
	@AssertTrue(message="Le dossier doit appartenir a un bénéficiaire",groups=System.class)
	private boolean hasBenficiaire(){
		return object.getBeneficiaire()!=null;
	}
	*/
	/*
	@AssertTrue(message="la date de prise de service doit être inférieur a la date de cessation de service",groups=Client.class)
	private boolean isDatePriseServiceBeforeDateCessationService(){
		return object.getDatePriseService().before(object.getDateCessationService());
	}
	*/
	
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
