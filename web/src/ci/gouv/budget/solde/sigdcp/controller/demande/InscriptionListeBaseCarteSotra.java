package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.service.identification.DelegueSotraService;
import ci.gouv.budget.solde.sigdcp.service.sotra.BeneficiaireCarteSotraService;

@Named @ViewScoped
public class InscriptionListeBaseCarteSotra extends AbstractEntityListUIController<DelegueSotra> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;

	@Inject private DelegueSotraService delegueSotraService;
	@Inject private BeneficiaireCarteSotraService adherentCarteSotraService;
	 
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Inscription à une liste de base de carte sotra";
		defaultSubmitCommand.setValue("S'inscrire");
		showCheckBox = false;
		showRadio=true;
		showActionsColumn=false;
		listTitle="Liste des délégués Sotra";
	}
	
	@Override
	protected List<DelegueSotra> load() {
		return new LinkedList<>(delegueSotraService.findAll());
	}

	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		adherentCarteSotraService.adherer(selectedOne);
	}
	
	@Override
	protected String identifierFieldName() {
		return "id";
	}
	
}
