package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.sotra.AchatCarteSotraService;

public abstract class AbstractAchatCarteSotraListe extends AbstractEntityListUIController<AchatCarteSotra> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;

	@Inject protected AchatCarteSotraService achatMensuelCarteSotraService;
	 
	@Getter @Setter
	protected Boolean showValide = false;
	
	@Override
	protected String identifierFieldName() {
		return "id";
	}
	
}
