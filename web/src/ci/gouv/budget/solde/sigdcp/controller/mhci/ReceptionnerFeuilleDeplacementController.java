package ci.gouv.budget.solde.sigdcp.controller.mhci;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
 
@Named @ViewScoped
public class ReceptionnerFeuilleDeplacementController extends AbstractEntityFormUIController<Exercice> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */  
	//@Inject private DossierDDService dossierDDService;
	
	/*
	 * Attributs de parametrages de la vue
	 */
	
	@Override
	public void initialisation() {
		super.initialisation();
		title = "Formulaire de réception feuille de déplacement visée";
		internalCode = "FS_MHCI_02_Ecran_02";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
		//defaultSubmitAction.setAjax(false);
	}
	
	public Exercice getExercice(){
		return entity;
	}
	
}
