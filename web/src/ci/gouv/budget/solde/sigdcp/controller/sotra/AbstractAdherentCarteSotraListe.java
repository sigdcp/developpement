package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.sotra.BeneficiaireCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.sotra.BeneficiaireCarteSotraService;

public abstract class AbstractAdherentCarteSotraListe extends AbstractEntityListUIController<BeneficiaireCarteSotra> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;

	@Inject protected BeneficiaireCarteSotraService adherentCarteSotraService;
	 
	@Getter @Setter
	protected Boolean showValide = false;
	
	@Override
	protected String identifierFieldName() {
		return "id";
	}
	
}
