package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;

@Named @ViewScoped
public class RealiserBonCommandeCarteSotraController extends AbstractBonCommandeCarteSotraListe implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;

	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Réalisation de bon de commande de carte sotra";
		defaultSubmitCommand.setValue("Valider");
		showActionsColumn=true;
		showValidation=true;
		listTitle="Liste des demandes d'achat de carte sotra";
		showValide=true;
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_GENERATION_CCS;
	}

	


	
}
