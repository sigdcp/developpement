package ci.gouv.budget.solde.sigdcp.controller.sotra;


import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionGestionnaireCarteSotra;
 
@Named @ViewScoped
public class InscriptionListeGestionnaireCarteSotraForm extends AbstractEntityFormUIController<AgentEtat> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */  
	//@Inject private DossierDDService dossierDDService;
	
	/*
	 * Attributs de parametrages de la vue
	 */
	@Getter @Setter private SouscriptionGestionnaireCarteSotra gestionnaire;
	
	@Override
	public void initialisation() {
		super.initialisation();
		title = "Formulaire d'inscription à une liste de base de carte sotra";
		internalCode = "FS_SOTRA_03_Ecran_01_01";
		defaultSubmitCommand.setValue(messageManager.getTextService().find("bouton.enregistrer"));
		//defaultSubmitAction.setAjax(false);
	}
	
	
}
