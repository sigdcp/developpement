package ci.gouv.budget.solde.sigdcp.controller.mhcis;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
 
@Named @ViewScoped
public class EnregistrerCalendrierController extends AbstractEntityFormUIController<CalendrierMission> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */  
	//@Inject private DossierDDService dossierDDService;
	
	
	@Getter @Setter private Boolean ajouterMission ;
	

	/*
	 * Attributs de parametrages de la vue
	 */
	
	@Override
	public void initialisation() {
		super.initialisation();
		title = "Ecran de création d'un calendrier de mission";
		internalCode = "FS_MHCI_03_Ecran_02";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
		//defaultSubmitCommand.setAjax(false);
		defaultSubmitCommand.setSuccessOutcome("ajouterMissionForm");
	}
	
	public CalendrierMission getCalendrierMission(){
		return entity;
	}
	
	
}
