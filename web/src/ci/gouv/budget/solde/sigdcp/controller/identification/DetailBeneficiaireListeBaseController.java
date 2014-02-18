package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;

@Named @ViewScoped
public class DetailBeneficiaireListeBaseController extends AbstractEntityListUIController<AgentEtat> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject private AgentEtatService agentEtatService;
	
	/*
	 * Dto
	 */
	

	@Override
	public void initialisation() {
		super.initialisation();
		title = "Détails des bénéficiares";
		internalCode = "FS_SOTRA_03_Ecran_03_01";
		defaultSubmitCommand.setRendered(Boolean.FALSE);
	}

	@Override
	protected List<AgentEtat> load() {
		return agentEtatService.findAll();
	}

	@Override
	public String href(AgentEtat agentEtat) {
		return null;//navigationHelper.addQueryParameters(nextViewOutcome, new Object[]{webConstantResources.getRequestParamSouscription(), agentEtat.getCode()});
	}
	
	@Override
	public String getRecordIdentifierFieldName() {
		return "code";
	}
	
}
