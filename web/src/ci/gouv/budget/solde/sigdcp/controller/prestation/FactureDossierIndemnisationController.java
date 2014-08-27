package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;

@Named @ViewScoped
public class FactureDossierIndemnisationController extends AbstractEntityFormUIController<Dossier> implements Serializable {

	private static final long serialVersionUID = -2494512246140789877L;
	
	/*
	 * Services
	 */ 
	
	
	/* 
	 * Dtos 
	 */
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Facture";
		internalCode="FS_TRANSIT_07_Ecran_01";
		defaultSubmitCommand.setValue(text("bouton.enregistrer"));
	}
	
}
