package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.flow.FlowDefinitions;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTR;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;


@Named @FlowScoped(value=FlowDefinitions.FLOW_DEMANDE_TR_ID)
public class EnregistrerDemandeTRFormController extends AbstractDossierUIControllerController<DossierTR> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter private DossierTR dossierTR =new DossierTR();
	
	@Getter @Setter private Boolean mae;
	
	private boolean ordonner(Date date1, Date date2) {
		if(date1==null || date2==null)
			return true;
		return date1.before(date2);
		
	}
		
	@PostConstruct
	private void postConstructTr() {
		flowId = FlowDefinitions.FLOW_DEMANDE_TR_ID;		
		dossierTR.setDeplacement(new Deplacement());
		//title="Formulaire de demande de prise en charge des frais de transit de bagages";
		
		for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES)){
			pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, dossierTR));
		
		}
	}
	
	@Override
	protected DossierTR createDossierInstance() {
		DossierTR dossierTR = new DossierTR();
		dossierTR.setDeplacement(new Deplacement());
		return dossierTR;
	}
	
	
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
	

}
		
