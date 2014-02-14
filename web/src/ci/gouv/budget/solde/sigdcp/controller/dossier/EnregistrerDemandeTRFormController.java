package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierTransitService;


@Named @ViewScoped
public class EnregistrerDemandeTRFormController extends AbstractDossierUIController<DossierTransit,DossierTransitService> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Services
	 */
	@Inject private DossierTransitService dossierTransitService;
	
	@Getter @Setter private Boolean mae;
	
	@Override
	protected void initialisation() {
		typeDepense =  genericService.findByClass(TypeDepense.class, String.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE);
		super.initialisation();
	}
		
	@Override
	protected DossierTransitService getDossierService() {
		return dossierTransitService;
	}
	
	public void typeAgentListener(){
		parametres.put(constantResources.getFormParamMae(), mae);
		updatePieceJustificatives();
	}
	
	public void typeDepenseListener(){
		System.out
				.println("EnregistrerDemandeTRFormController.typeDepenseListener()");
		//parametres.put(constantResources.getFormParamRemboursement(), remb);
		updatePieceJustificatives();
		System.out.println(typeDepense+" : "+pieceJustificativeUploader.getPieceJustificatives());
	}
	
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
		
