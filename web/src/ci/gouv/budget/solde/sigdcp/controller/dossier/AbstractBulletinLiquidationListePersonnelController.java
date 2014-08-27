package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEffectuerOperationPersonnelController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;
import ci.gouv.budget.solde.sigdcp.service.dossier.BulletinLiquidationService;

public abstract class AbstractBulletinLiquidationListePersonnelController extends AbstractEffectuerOperationPersonnelController<BulletinLiquidation> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject protected BulletinLiquidationService bulletinLiquidationService;
	
	/*
	 * Dto
	 */
	@Getter @Setter protected Boolean showBordereauNumero=Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		listTitle = "Liste des bulletins de liquidation";
		//if(selected==null){
			selected = new BulletinLiquidation();
			selected.getTraitable().setTraitement(new TraitementPieceProduite());
		//}
		//debug(selected.getTraitable().getTraitement());
	}
	
	@Override
	protected String defaultNatureDeplacementCode() {
		return Code.NATURE_DEPLACEMENT_AFFECTATION;
	}
		
	@Override
	protected String identifierFieldName() {
		return "id";
	}
			
	@Override
	protected String detailsOutcome(BulletinLiquidation object) {
		return navigationManager.outcome(object.getDossier());
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, BulletinLiquidation dto) {
		addParameters(parameters, webConstantResources.getRequestParamDossier(), dto.getDossier().getId().toString());
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), webConstantResources.getRequestParamCrudRead());
	}
	
	@Override
	protected String objectOutcome(Object entity) {
		return navigationManager.outcome( ((BulletinLiquidation) entity).getDossier());
	}
	
	@Override
	public BigDecimal depense(BulletinLiquidation data) {
		if(data==null  || data.getMontant()==null)
			return BigDecimal.ZERO;
		return data.getMontant();
	}
	
	@Override
	public Traitement dernierTraitement(BulletinLiquidation data) {
		return data.getTraitable().getDernierTraitement();
	}
	
	@Override
	public String numero(BulletinLiquidation data) {
		return data.getNumero();
	}
	
	@Override
	public Date dateCreation(BulletinLiquidation data) {
		return data.getDateCreation();
	}
	
	@Override
	protected void valider(Collection<BulletinLiquidation> datas) {
		bulletinLiquidationService.valider(datas);
	}
	
	@Override
	protected Collection<BulletinLiquidation> data(Collection<NatureDeplacement> natureDeplacements) {
		return bulletinLiquidationService.findATraiter(natureDeplacements, natureOperationCode());
	}

}
