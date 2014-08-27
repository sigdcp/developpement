package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.sotra.BeneficiaireCarteSotra;

@Named @ViewScoped
public class ListeAdherentBaseCarteSotraController extends AbstractAdherentCarteSotraListe implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;

	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Liste des adherents";
		defaultSubmitCommand.setRendered(false);
		showActionsColumn=false;
		listTitle="Liste";
	}
	
	@Override
	protected List<BeneficiaireCarteSotra> load() {
		return new LinkedList<>(adherentCarteSotraService.findAdherents());
	}

	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		adherentCarteSotraService.validerInscription(selectedMultiple);
	}
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
	
}
