package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;

@Named @ViewScoped
public class DistribuerBCCSDelegueController extends AbstractBonCommandeCarteSotraListe implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;

	@Override
	protected void initialisation() {
		super.initialisation();
		defaultSubmitCommand.setValue("Enregistrer");
		showActionsColumn=false;
		listTitle="Liste des bons de commande de carte sotra";
		showValide=true;
		showDateDistributionDelegue=true;
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_DISTRIBUTION_CCS_DELEGUE;
	}
	
}
