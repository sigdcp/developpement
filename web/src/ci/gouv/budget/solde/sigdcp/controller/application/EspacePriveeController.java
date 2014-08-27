package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.demande.DemandeCarteSotraListeController;
import ci.gouv.budget.solde.sigdcp.controller.demande.DemandeIndemnisationListeController;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractUIController;

@Named @ViewScoped
public class EspacePriveeController extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = 8647732982234588342L;
	
	@Getter
	@Inject private DemandeIndemnisationListeController demandeIndemnisationListeController;
	
	@Getter
	@Inject private DemandeCarteSotraListeController demandeCarteSotraListeController;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		demandeIndemnisationListeController.preRenderView();
		
		if(roleManager.isAgentEtat())
			demandeCarteSotraListeController.preRenderView();
		
	}
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
}
