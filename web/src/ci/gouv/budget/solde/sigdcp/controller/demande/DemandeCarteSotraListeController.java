package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.sotra.CarteSotra;
import ci.gouv.budget.solde.sigdcp.service.sotra.CarteSotraService;

@Named @ViewScoped //@Log
public class DemandeCarteSotraListeController extends AbstractEntityListUIController<CarteSotra> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject protected CarteSotraService carteSotraService;

	@Override
	protected void initialisation() {
		super.initialisation();
		title = text("menu.consulter.demande");
		internalCode = "FS_DEM_01_Ecran_01";
		defaultSubmitCommand.setRendered(Boolean.FALSE);
		closeCommand.setRendered(Boolean.FALSE);
		listTitle="Liste des demandes";
		selectLabel="bouton.consulter";
	}
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
		
	@Override
	protected String identifierFieldName() {
		return "id";
	}
	
	@Override
	protected List<CarteSotra> load() {
		return new LinkedList<>(carteSotraService.findDemandes());
	}
	
	@Override
	protected void hrefParameters(Map<String, Object> parameters, CarteSotra carteSotra) {
		super.hrefParameters(parameters, carteSotra);
		parameters.put(webConstantResources.getRequestParamCarteSotra(),carteSotra.getId());
	}
		
}
