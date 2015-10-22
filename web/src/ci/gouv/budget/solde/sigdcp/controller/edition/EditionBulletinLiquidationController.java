package ci.gouv.budget.solde.sigdcp.controller.edition;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.service.dossier.BulletinLiquidationService;

@Named @ViewScoped
public class EditionBulletinLiquidationController extends AbstractEditionController<BulletinLiquidation> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Inject private BulletinLiquidationService bulletinLiquidationService;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Edition de bulletin de liquidation";
		listTitle="Liste des bulletins de liquidation";
		defaultSubmitCommand.setRendered(false);
	}
	
	@Override
	protected List<BulletinLiquidation> load() {
		return bulletinLiquidationService.findAll();
	}
	
	
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
	
	@Override
	public String href(BulletinLiquidation bl) {
		bulletinLiquidationService.init(bl, null);
		return super.href(bl);
	}

	@Override
	protected PieceProduite piece(BulletinLiquidation bl) {
		return bl;
	}
		
	
	
}
