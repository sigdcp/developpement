package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;

@Named @ViewScoped @Log
public class DossierListeController extends AbstractEntityListUIController<Dossier> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject protected DossierService dossierService;
	
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
		title = text("liste.dossier.ayantstatut",new Object[]{statut});
		internalCode = "FS_DEM_01_Ecran_01";
		enableSearch();
	}
	
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
	
	@Override
	protected List<Dossier> load() {
		return new LinkedList<>(dossierService.findByStatut(statut));
	}
	
	public String href(Dossier dossier){
		String outcome = navigationManager.url(nextViewOutcome+outcomeSuffix(dossier),getIsBatchProcessing());
		return navigationHelper.addQueryParameters(outcome, 
				new Object[]{
				webConstantResources.getRequestParamDossier(),dossier.getNumero()
				,webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudRead()
				//,webConstantResources.getRequestParamPreviousURL(),navigationManager.getRequestUrl()
				});
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
	protected void onSearchCommandAction() {
		if(natureDeplacement==null)
			list = load();
		else
			list = new LinkedList<>(dossierService.findByNatureDeplacementAndStatut(natureDeplacement, statut));
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters,Dossier dossier) {
		addParameters(parameters, webConstantResources.getRequestParamDossier(), dossier.getNumero());
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), webConstantResources.getRequestParamCrudRead());
		addParameters(parameters, webConstantResources.getRequestParamViewType(), webConstantResources.getRequestParamDialog());
	}
	
}
