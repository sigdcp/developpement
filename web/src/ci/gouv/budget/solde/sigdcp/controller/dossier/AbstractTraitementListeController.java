package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.service.dossier.TraitementService;

public abstract class AbstractTraitementListeController<PIECE_PRODUITE_DTO extends TraitementDto> extends AbstractEntityListUIController<PIECE_PRODUITE_DTO> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject protected TraitementService traitementService;
	
	/*
	 * Dto
	 */

	//recherches
	@Getter @Setter Date dateDebut,dateFin;
	@Getter @Setter NatureDeplacement natureDeplacement;
	/*
	 * Paramètres url
	 */
	@Getter @Setter protected Statut statut;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		defaultSubmitCommand.setValue(text("bouton.transmettre"));
	}
	
	@Override
	protected String identifierFieldName() {
		return "id";
	}
		
	public String href(PIECE_PRODUITE_DTO pieceProduite){
		return null;
		/*
		String outcome = navigationManager.url(nextViewOutcome+outcomeSuffix(dossier),getIsBatchProcessing());
		return navigationManager.addQueryParameters(outcome, 
				new Object[]{
				webConstantResources.getRequestParamDossier(),dossier.getNumero()
				,webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudRead()
				//,webConstantResources.getRequestParamPreviousURL(),navigationManager.getRequestUrl()
				});
				*/
	}
	/*
	private String outcomeSuffix(Dossier dossier){
		if("decisionrembForm".equals(nextViewOutcome))
			return "";
		if(dossier instanceof DossierDD) return "_dd";
		if(dossier instanceof DossierObseques) return "_o";
		if(dossier instanceof DossierMission) return "_m";
		if(dossier instanceof DossierTransit) return "_t";
		log.warning("Cannot build outcome suffix for "+dossier);
		return null;
	}*/
	/*
	@Override
	protected void onSearchCommandAction() {
		if(natureDeplacement==null)
			list = load();
		else
			list = new LinkedList<>(dossierService.findByNatureDeplacementAndStatut(natureDeplacement, statut));
	}
	*/
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters,PIECE_PRODUITE_DTO pieceProduite) {/*
		addParameters(parameters, webConstantResources.getRequestParamDossier(), pieceProduite.getNumero());
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), webConstantResources.getRequestParamCrudRead());
		addParameters(parameters, webConstantResources.getRequestParamViewType(), webConstantResources.getRequestParamDialog());*/
	}
	
}
