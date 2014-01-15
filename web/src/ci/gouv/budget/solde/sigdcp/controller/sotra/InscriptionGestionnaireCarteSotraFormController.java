package ci.gouv.budget.solde.sigdcp.controller.sotra;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
 
@Named @ViewScoped
public class InscriptionGestionnaireCarteSotraFormController extends AbstractEntityFormUIController<DelegueSotra> implements Serializable {
	
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
		title = "Inscription des candidats gestionnaires";
		internalCode = "FS_Sotra_02_Ecran_01";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
		defaultSubmitCommand.setAjax(false);
	}
	
	
	

	
}
