package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.service.dossier.TraitementService;

@Named
public class BordereauSelectionPieceController extends AbstractEntityListUIController<Traitement> implements Serializable {

	private static final long serialVersionUID = -1807054682171071378L;

	@Inject
	private TraitementService traitementService;
	
	@Override
	protected List<Traitement> load() {
		return traitementService.findAll();
	}

	@Override
	public String href(Traitement traitement) {
		return null;
	}
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
	@Override
	protected String identifierFieldName() {
		return "id";
	}
	
	@Override
	protected String detailsOutcome() {
		return "blDialog";
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, Traitement object) {
		
	}
}
