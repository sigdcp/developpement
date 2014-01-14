package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.Inscription;
import ci.gouv.budget.solde.sigdcp.service.identification.InscriptionService;


@Named @ViewScoped
public class ValidationInscriptionController extends AbstractEntityListUIController<Inscription> implements Serializable {

	private static final long serialVersionUID = 6591392098578555259L;
	
	@Inject private InscriptionService inscriptionService;
	 
	//@Getter @Setter protected List<Inscription> inscriptionsSelectionnes;
	//@Setter @Getter protected Inscription inscriptionSelectionne;
	
	@Getter @Setter
	private Boolean accepte;
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Validation des souscriptions";
		internalCode = "FS_ID_02_Ecran_01";
		/*
		wizardHelper = new WizardHelper<Inscription>(this,"selection","confirmation"){
			private static final long serialVersionUID = -2560968105025120145L;
			@Override
			protected void move(Integer stepCount) {
				super.move(stepCount);
				showFieldRequired = !getSubmitAction().isRendered();
			}
		};*/
	}
	
	@Override
	protected List<Inscription> load() {
		return new LinkedList<Inscription>(inscriptionService.findInscriptionsAValider());
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		if(Boolean.TRUE.equals(accepte))
			inscriptionService.accepterInscription(selectedMultiple);
		else
			inscriptionService.rejeterInscription(selectedMultiple);
	}
	
	@Override
	public String href(Inscription entity) {
		return null;
	}
	
	@Override
	public String getRecordIdentifierFieldName() {
		return "code";
	}
	
	@Override
	protected String detailsOutcome() {
		return "souscriptionDialog";
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters,Inscription inscription) {
		addParameters(parameters, webConstantResources.getRequestParamInscription(), inscription.getCode());
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), webConstantResources.getRequestParamCrudRead());
		addParameters(parameters, webConstantResources.getRequestParamViewType(), webConstantResources.getRequestParamDialog());
	}

}

