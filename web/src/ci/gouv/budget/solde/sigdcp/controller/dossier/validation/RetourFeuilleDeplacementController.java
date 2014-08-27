package ci.gouv.budget.solde.sigdcp.controller.dossier.validation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;

@Named @ViewScoped
public class RetourFeuilleDeplacementController extends AbstractValidationDossierController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		rechercherCommande=null;
		showObservation=showValidation=false;
		editDetailsCommand.setRendered(true);
		editDetailsCommand.setValue("Selectionner");
		detailsCommand.setRendered(false);
		defaultSubmitCommand.setRendered(false);
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_RETOUR_FD;
	}

	@Override
	protected String defaultNatureDeplacementCode() {
		return Code.NATURE_DEPLACEMENT_MISSION_HCI;
	}
	
	@Override
	protected String[] defaultNatureDeplacmentCodeListe() {
		return new String[]{Code.NATURE_DEPLACEMENT_MISSION_HCI};
	}
	
	@Override
	protected Boolean canShowAllNatureDeplacment() {
		return false;
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, Dossier dossier) {
		super.detailsOutcomeParameters(parameters, dossier);
		addParameters(parameters, webConstantResources.getRequestParamNatureOperation(), natureOperationCode());
	}
	
	
}
