package ci.gouv.budget.solde.sigdcp.controller.edition;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.service.dossier.BordereauTransmissionService;

@Named @ViewScoped
public class EditionBordereauTransmissionController extends AbstractEditionController<BordereauTransmission> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Inject private BordereauTransmissionService bordereauTransmissionService;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Edition de bordereau de transmission de bulletin de liquidation";
		listTitle="Liste des bordereaux de transmission de bulletin de liquidation";
	}
	
	@Override
	protected List<BordereauTransmission> load() {
		return bordereauTransmissionService.findAll();
	}
	
	
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
	
	@Override
	public String href(BordereauTransmission bt) {
		bordereauTransmissionService.init(bt, null);
		return super.href(bt);
	}

	@Override
	protected PieceProduite piece(BordereauTransmission bt) {
		return bt;
	}
		
	
	
}
