package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;

@Named @ViewScoped
public class SouscriptionGendarmeController extends AbstractSouscriptionComptePersonneController implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title += " des gendarmes"; 
		demandeurDto.setShowTypePersonne(false);
		 demandeurDto.getInfosSouscriptionComptePersonne().setType(genericService.findByClass(TypeAgentEtat.class, String.class,Code.TYPE_AGENT_ETAT_GENDARME));
    }
	
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		defaultSubmitCommand.setSuccessOutcome(navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{
        		webConstantResources.getRequestParamMessageId(),"notification.compte.ouvert.gendarme",webConstantResources.getRequestParamUrl(),navigationManager.url("index",false)
        }));
	}
	
}
