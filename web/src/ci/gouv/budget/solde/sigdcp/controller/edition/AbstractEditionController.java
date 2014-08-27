package ci.gouv.budget.solde.sigdcp.controller.edition;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

public abstract class AbstractEditionController<ENTITY extends AbstractModel<?>> extends AbstractEntityListUIController<ENTITY> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	protected abstract PieceProduite piece(ENTITY entity);
	
	@Override
	public String href(ENTITY entity) {
		return navigationManager.url("imprimerpieceproduite",new Object[]{webConstantResources.getRequestParamPieceJustificative(),piece(entity).getId()},false);
	}
		
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
	
}
