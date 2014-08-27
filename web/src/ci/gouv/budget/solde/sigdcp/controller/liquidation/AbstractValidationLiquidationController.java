package ci.gouv.budget.solde.sigdcp.controller.liquidation;

import java.io.Serializable;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.dossier.AbstractBulletinLiquidationListePersonnelController;
import ci.gouv.budget.solde.sigdcp.model.Code;

public abstract class AbstractValidationLiquidationController extends AbstractBulletinLiquidationListePersonnelController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	protected String document;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		String r = Faces.getRequestParameter(webConstantResources.getRequestParamLiquidation());
		if(webConstantResources.getRequestParamLiquidationBulletin().equals(r))
			document = "bulletin de liquidation";
		else
			document = "bon de transport";
	}

	@Override
	protected String[] defaultNatureDeplacmentCodeListe() {
		return new String[]{Code.NATURE_DEPLACEMENT_AFFECTATION,Code.NATURE_DEPLACEMENT_MUTATION,Code.NATURE_DEPLACEMENT_RETRAITE,Code.NATURE_DEPLACEMENT_MISSION_HCI};
	}
		
}
