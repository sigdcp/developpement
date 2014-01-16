package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;


@Named @ViewScoped
public class ValidationSouscriptionComptePersonneController extends AbstractEntityListUIController<SouscriptionComptePersonne> implements Serializable {

	private static final long serialVersionUID = 6591392098578555259L;
	
	//@Inject private SouscriptionCompteService souscriptionCompteService;
	 
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
	protected List<SouscriptionComptePersonne> load() {
		return null;//new LinkedList<Souscription>(souscriptionCompteService.findSouscriptionsAValider());
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		/*super.onDefaultSubmitAction();
		if(Boolean.TRUE.equals(accepte))
			souscriptionCompteService.accepter(selectedMultiple);
		else
			souscriptionCompteService.rejeter(selectedMultiple);*/
	}
	
	@Override
	public String href(SouscriptionComptePersonne entity) {
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
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters,SouscriptionComptePersonne souscriptionComptePersonne) {
		addParameters(parameters, webConstantResources.getRequestParamSouscription(), souscriptionComptePersonne.getCode());
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), webConstantResources.getRequestParamCrudRead());
		addParameters(parameters, webConstantResources.getRequestParamViewType(), webConstantResources.getRequestParamDialog());
	}

}

