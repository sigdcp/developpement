package ci.gouv.budget.solde.sigdcp.controller.dossier.validation;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.service.DynamicEnumerationService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.OperationService;

@Named @ViewScoped
public class ValidationRecevabiliteDossierController extends AbstractEntityListUIController<Dossier> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	//private TraitementService traitementService;
	@Inject private OperationService operationService;
	@Inject private DynamicEnumerationService dynamicEnumerationService;
	@Inject private DossierService dossierService;
	/*
	 * Dto
	 */
	@Inject private UserSessionManager userSessionManager;

	/*
	 * Paramètres url
	 */
	@Getter @Setter protected Statut statut;
	@Getter @Setter protected NatureDeplacement natureDeplacement;
	
	@Override
	protected void initialisation() {
		statut = dynamicEnumerationService.findByClass(Statut.class, Code.STATUT_SOUMIS);
		natureDeplacement = dynamicEnumerationService.findByClass(NatureDeplacement.class, Code.NATURE_DEPLACEMENT_AFFECTATION);
		
		super.initialisation();
		title = "Validation de la reçevabilité";//text("liste.dossier.ayantstatut",new Object[]{statut});
		internalCode = "FS_DEM_01_Ecran_01";
		
		defaultSubmitCommand.setValue(text("bouton.valider"));
		//enableSearch();
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		operationService.valider(Code.NATURE_OPERATION_RECEVABILITE, selectedMultiple, userSessionManager.getUser());
	}
	
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
	
	@Override
	protected List<Dossier> load() {
		List<Dossier> dossiers = new LinkedList<>(dossierService.findByStatut(statut));
		for(Dossier dossier : dossiers)
			dossier.setDernierTraitement(new Traitement());
		//System.out.println(dossiers);
		return dossiers;
	}
	/*
	public String href(Dossier dossier){
		String outcome = navigationManager.url(nextViewOutcome+outcomeSuffix(dossier),getIsBatchProcessing());
		return navigationHelper.addQueryParameters(outcome, 
				new Object[]{
				webConstantResources.getRequestParamDossier(),dossier.getNumero()
				,webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudRead()
				//,webConstantResources.getRequestParamPreviousURL(),navigationManager.getRequestUrl()
				});
	}
	*/
	/*
	@Override
	protected void onSearchCommandAction() {
		if(natureDeplacement==null)
			list = load();
		else
			list = new LinkedList<>(dossierService.findByNatureDeplacementAndStatut(natureDeplacement, statut));
	}*/
	
	/*
	public boolean isSelected(Traitement entity){
		if(selectedMultiple==null || entity==null)
			return false;
		for(Traitement traitement : selectedMultiple)
			if(entity.getDossier().equals(traitement.getDossier()))
				return true;
		return false;
	}
	*/
}
