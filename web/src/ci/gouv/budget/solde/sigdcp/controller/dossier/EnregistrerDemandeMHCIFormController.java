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
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMHCI;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;



@Named @FlowScoped(value=FlowDefinitions.FLOW_DEMANDE_MHCI_ID) @Getter @Setter
public class EnregistrerDemandeMHCIFormController extends AbstractDossierUIControllerController<DossierMHCI> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private DossierMHCI dossierMHCI=new DossierMHCI();
	private Mission deplacement=new Mission();
	
	@PostConstruct
	private void postEnregistrerDemandeMHCIFormController() { 
		//title="Mission Hors Côte D'ivoire";
		// TODO Auto-generated constructor stub
		flowId = FlowDefinitions.FLOW_DEMANDE_MHCI_ID;
		dossierMHCI.setDeplacement(deplacement);
		for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(Code.NATURE_DEPLACEMENT_MISSION_HCI)){
			pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, dossierMHCI));
		
		}
	}
	
	private boolean ordonner(Date date1, Date date2) {
		if(date1==null || date2==null)
			return true;
		return date1.before(date2);
		
	}
	
	@Override
	protected DossierMHCI createDossierInstance() {
		DossierMHCI dossierMHCI = new DossierMHCI();
		dossierMHCI.setDeplacement(new Mission());
		return dossierMHCI;
	}


	public String enregistrer() {
		
		Date datecourante = new Date();
		boolean succes= true;				
		
		if (!ordonner(datecourante,dossierMHCI.getDeplacement().getDateDepart()))
		{
			addMessageError("la date de debut de la mission ne doit pas être inférieure à la date d'aujourd'hui");
			succes=false;
		}
		
		if (!ordonner(dossierMHCI.getDeplacement().getDateDepart(),dossierMHCI.getDeplacement().getDateArrivee()))
		{
			addMessageError("la date de fin de la mission ne doit pas être inférieure à la date de debut de la mission");
			succes=false;
		}
		//dossierService.inscrire(dossierService);
		
			if (succes)
				return "succes";
			
			return null;
	}
	
	public Mission getMission(){
		return (Mission) dossierMHCI.getDeplacement();
	}
	

}
	