package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;

@Named @ViewScoped @Log
public class DossierAgentEtatListeController extends AbstractEntityListUIController<Dossier> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject protected DossierService dossierService;
	@Inject private UserSessionManager userSessionManager;

	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Mes dossiers";
		internalCode = "FS_DEM_01_Ecran_01";
		defaultSubmitCommand.setRendered(Boolean.FALSE);
		closeCommand.setRendered(Boolean.FALSE);
		nextViewOutcome = "demande";
	}
	
	@Override
	protected ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController.ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
		
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
	
	@Override
	protected List<Dossier> load() {
		return new LinkedList<>(dossierService.findByAgentEtat((AgentEtat) userSessionManager.getUser()));
	}
	
	public String href(Dossier dossier){
		String outcome = navigationManager.url(nextViewOutcome+outcomeSuffix(dossier),getIsBatchProcessing());
		String url = navigationHelper.addQueryParameters(outcome, 
				new Object[]{
				webConstantResources.getRequestParamDossier(),dossier.getNumero()
				,webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudRead()
				//,webConstantResources.getRequestParamPreviousURL(),navigationManager.getRequestUrl()
				});
		System.out.println(url);
		return url;
	}
	
	private String outcomeSuffix(Dossier dossier){
		if("decisionrembForm".equals(nextViewOutcome))
			return "";
		if(dossier instanceof DossierDD) return "_dd";
		if(dossier instanceof DossierObseques) return "_o";
		if(dossier instanceof DossierMission) return "_m";
		if(dossier instanceof DossierTransit) return "_t";
		log.warning("Cannot build outcome suffix for "+dossier);
		return null;
	}

	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters,Dossier dossier) {
		addParameters(parameters, webConstantResources.getRequestParamDossier(), dossier.getNumero());
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), webConstantResources.getRequestParamCrudRead());
		addParameters(parameters, webConstantResources.getRequestParamViewType(), webConstantResources.getRequestParamDialog());
	}
	
}
