package ci.gouv.budget.solde.sigdcp.controller.mhcis;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
 
@Deprecated
public class AjouterMissionController extends AbstractEntityFormUIController<Mission> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */  
	//
	
	
	@Getter @Setter private Boolean ajouterMission ;
	

	/*
	 * Attributs de parametrages de la vue
	 */
	
	@Override
	public void initialisation() {
		super.initialisation();
		title = "Ecran d'ajout d'une mission à un calendrier";
		internalCode = "FS_MHCI_03_Ecran_03";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
		//defaultSubmitAction.setAjax(false);
		
	}
	

	public Mission getMission(){
		return entity;
	}

	
}
