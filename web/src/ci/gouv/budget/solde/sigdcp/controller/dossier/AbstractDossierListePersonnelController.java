package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEffectuerOperationPersonnelController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDossier;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;

public abstract class AbstractDossierListePersonnelController extends AbstractEffectuerOperationPersonnelController<Dossier> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject protected DossierService dossierService;
	/*
	 * Dto
	 */
	@Getter @Setter protected Boolean showBulletinNumero=Boolean.FALSE,showBordereauNumero=Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		listTitle = "Liste des demandes";
		//if(selected==null){
			selected = new Dossier(null);
			selected.getTraitable().setTraitement(new TraitementDossier());
		//}
	}
	
	@Override
	protected String defaultNatureDeplacementCode() {
		return Code.NATURE_DEPLACEMENT_AFFECTATION;
	}
		
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
			
	@Override
	protected String detailsOutcome(Dossier dossier) {
		return navigationManager.outcome(dossier);
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, Dossier dossier) {
		addParameters(parameters, webConstantResources.getRequestParamDossier(), dossier.getId().toString());
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), webConstantResources.getRequestParamCrudRead());
	}
	
	@Override
	protected void hrefParameters(Map<String, Object> parameters,Dossier dossier) {
		parameters.put(webConstantResources.getRequestParamDossier(), dossier.getId().toString());
		super.hrefParameters(parameters, dossier);
	}
	
	@Override
	protected String objectOutcome(Object entity) {
		return navigationManager.outcome(entity);
	}
	
	@Override
	protected Object hrefObjectOutcome(Dossier dossier) {
		return dossier;
	}
	
	@Override
	public BigDecimal depense(Dossier dossier) {
		if(dossier==null || dossier.getMontantIndemnisation()==null)
			return BigDecimal.ZERO;
		return dossier.getMontantIndemnisation();
	}
	
	@Override
	public Traitement dernierTraitement(Dossier dossier) {
		return dossier.getTraitable().getDernierTraitement();
	}
	
	@Override
	public String numero(Dossier dossier) {
		return dossier.getNumero();
	}
	
	@Override
	public Date dateCreation(Dossier dossier) {
		return dossier.getDateCreation();
	}


}
