package ci.gouv.budget.solde.sigdcp.controller.mhcis;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
 
@Named @ViewScoped
public class EnregistrerExerciceController extends AbstractEntityFormUIController<Exercice> implements Serializable {
	
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
		title = "Ecran de définition de l'exercice courant";
		internalCode = "FS_MHCI_03_Ecran_01";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
		//defaultSubmitAction.setAjax(false);
	}
	
	public Exercice getExercice(){
		return entity;
	}
	
	
}
