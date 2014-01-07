package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;



@Named @ViewScoped @Getter @Setter
public class EnregistrerDemandeMHCIFormController extends AbstractDossierUIController<DossierMission,DossierMissionService> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Services
	 */
	@Inject private DossierMissionService dossierMissionService;
	
	/*
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
	*/
	
	@Override
	protected Deplacement createDeplacement() {
		return new Mission();
	}
	
	@Override
	protected DossierMissionService getDossierService() {
		return dossierMissionService;
	}
	
	public Mission getMission(){
		return (Mission) entity.getDeplacement();
	}
	

}
	