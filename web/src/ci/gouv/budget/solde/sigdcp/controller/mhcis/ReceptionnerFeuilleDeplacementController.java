package ci.gouv.budget.solde.sigdcp.controller.mhcis;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
 
@Deprecated
public class ReceptionnerFeuilleDeplacementController extends AbstractEntityFormUIController<PieceProduite> implements Serializable {
	
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
		title = "Formulaire de réception de feuille de déplacement visée";
		internalCode = "FS_MHCI_02_Ecran_02";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
		//defaultSubmitAction.setAjax(false);
	}
	
	
}
