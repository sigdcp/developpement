package ci.gouv.budget.solde.sigdcp.controller.remboursement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEffectuerOperationPersonnelController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.ProjetDecision;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;
import ci.gouv.budget.solde.sigdcp.service.dossier.ProjetDecisionService;

public abstract class AbstractProjetDecisionListePersonnelController extends AbstractEffectuerOperationPersonnelController<ProjetDecision> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject protected ProjetDecisionService projetDecisionService;
	
	/*
	 * Dto
	 */
	@Getter protected boolean showNumeroCabinet=false;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		listTitle = "Liste des pieces";
		//if(selected==null){
			selected = new ProjetDecision();
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
		return "dossierId";
	}
			
	@Override
	protected String detailsOutcome(ProjetDecision object) {
		return navigationManager.outcome(object.getDossier());
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, ProjetDecision dto) {
		addParameters(parameters, webConstantResources.getRequestParamDossier(), dto.getDossier().getId().toString());
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), webConstantResources.getRequestParamCrudRead());
	}
	
	@Override
	protected String objectOutcome(Object entity) {
		return navigationManager.outcome( ((ProjetDecision) entity).getDossier());
	}
	
	@Override
	public BigDecimal depense(ProjetDecision data) {
		return BigDecimal.ZERO;
	}
	
	@Override
	public Traitement dernierTraitement(ProjetDecision data) {
		return data.getTraitable().getDernierTraitement();
	}
	
	@Override
	public String numero(ProjetDecision data) {
		return data.getNumero();
	}
	
	@Override
	public Date dateCreation(ProjetDecision data) {
		return data.getDateCreation();
	}
	
	@Override
	protected void valider(Collection<ProjetDecision> datas) {
		projetDecisionService.valider(datas);
	}
	
	@Override
	protected Collection<ProjetDecision> data(Collection<NatureDeplacement> natureDeplacements) {
		return projetDecisionService.findATraiter(natureDeplacements, natureOperationCode());
	}
	
	@Override
	protected String[] defaultNatureDeplacmentCodeListe() {
		return new String[]{Code.NATURE_DEPLACEMENT_MISSION_HCI,Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS,Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE,Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE
				,Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_CONGE,Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_STAGE};
	}
	
	@Override
	protected Boolean canShowAllNatureDeplacment() {
		return true;
	}
	
	@Override
	protected void hrefParameters(Map<String, Object> parameters,ProjetDecision projetDecision) {
		super.hrefParameters(parameters, projetDecision);
		parameters.put(webConstantResources.getRequestParamDossier(), projetDecision.getDossier().getId());
	}

}
