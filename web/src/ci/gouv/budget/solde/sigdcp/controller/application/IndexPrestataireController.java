package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.prestation.DemandeCotationMhciListeController;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractUIController;

@Named @ViewScoped
public class IndexPrestataireController extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = 8647732982234588342L;
	
	@Getter
	@Inject private DemandeCotationMhciListeController demandeCotationMhciListeController;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		demandeCotationMhciListeController.preRenderView();
		
	}
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
}
