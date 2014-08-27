package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeObseque;

@Named @ViewScoped
public class ValiderBonCommandeObsequeController extends AbstractBonCommandeObsequeListe implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;

	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Validation de bon de commande de frais d'obsèque";
		defaultSubmitCommand.setValue("Valider");
		showActionsColumn=true;
		showValidation=true;
		listTitle="Liste des bons de commande de frais d'obsèque";
		showValide=true;
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_VISA_BCFO;
	}

	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, CommandeObseque commande) {
		super.detailsOutcomeParameters(parameters, commande);
		addParameters(parameters, webConstantResources.getRequestParamDemandeCotation(), commande.getId().toString());
		
	}


	
}
