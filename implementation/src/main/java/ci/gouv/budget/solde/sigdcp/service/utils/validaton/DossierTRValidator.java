package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;

public class DossierTRValidator extends AbstractDossierValidator<DossierTransit> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	
	
	/*
	public String enregistrer() {
		Date datecourante = new Date();
		boolean succes= true;
			
		if (!ordonner(dossierTR.getDatePriseService(),datecourante))
		{
			addMessageError("la date de prise de service ne doit pas être supérieure à la date d'aujourd'hui");
			succes=false;
		}
		
		if (!ordonner(dossierTR.getDateMiseStage(),datecourante))
		{
			addMessageError("la date de mise en stage ne doit pas être supérieure à la date d'aujourd'hui");
			succes=false;
		}
		
		if (!ordonner(dossierTR.getDateFin(),dossierTR.getDateFin()))
		{
			addMessageError("la date de cessation de service ne doit pas être supérieure à la date de prise de service");
			succes=false;
		}
		if (!ordonner(dossierTR.getDateFin(),dossierTR.getDatePriseService()))
		{
			addMessageError("la date de cessation de service ne doit pas être supérieure à la date de prise de service");
			succes=false;
		}
		
		if (!ordonner(dossierTR.getDeplacement().getDateArrivee(),datecourante))
		{
			addMessageError("la date de d'arrivée ne doit pas être supérieure à la date d'aujourd'hui");
			succes=false;
		}
		
		if (!ordonner(dossierTR.getDeplacement().getDateArrivee(),dossierTR.getDateFin()))
		{
			addMessageError("la date de d'arrivée ne doit pas être supérieure à la date de cessation de service ou de fin de stage");
			succes=false;
		}
		if (!ordonner(dossierTR.getDeplacement().getDateDepart(),dossierTR.getDeplacement().getDateArrivee()))
		{
			addMessageError("la date de départ ne doit pas être supérieure à la date d'arrivée");
			succes=false;
		}
		//dossierService.inscrire(dossierService);
		if (succes)
			return "succes";
			return null;
		
	}
	*/
	
	
}
