package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentMissionReference;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentMissionReferenceService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Named @ViewScoped
public class AgentMissionController extends AbstractEntityFormUIController<AgentMissionReference> implements Serializable {

	private static final long serialVersionUID = -8293772128779434233L;
	
	@Inject AgentMissionReferenceService agentMissionReferenceService;

	@Getter private boolean editable;

	@Override
	protected void initialisation() {
		crudType=CRUDType.CREATE;
		super.initialisation();
		title="Enregistrer agent de mission";
		defaultSubmitCommand.setAjax(false);
		defaultSubmitCommand.setValue(text("bouton.enregistrer"));
		editable=true;
	
		defaultSubmitCommand.onSuccessGoTo(navigationManager.url("agentmission",false), "", null);
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		agentMissionReferenceService.enregistrer(entity);
	}
	
}
