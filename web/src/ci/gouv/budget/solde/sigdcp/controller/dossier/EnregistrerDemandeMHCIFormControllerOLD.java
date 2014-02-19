package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;

//@Named @ViewScoped
@Getter @Setter
public class EnregistrerDemandeMHCIFormControllerOLD extends AbstractDossierUIController<DossierMission,DossierMissionService> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Services
	 */
	@Inject private DossierMissionService dossierMissionService;
	
	
	@Override
	protected void initialisation() {
		super.initialisation();
		requiredEnabled=Boolean.FALSE;
	}
	
	@Override
	protected Deplacement createDeplacement() {
		return null;//new Mission();
	}
	
	@Override
	protected DossierMissionService getDossierService() {
		return dossierMissionService;
	}
	
	public Mission getMission(){
		return null;//(Mission) entity.getDeplacement();
	}
	

}
	