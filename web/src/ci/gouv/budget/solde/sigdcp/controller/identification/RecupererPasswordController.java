package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;

@Named @ViewScoped
public class RecupererPasswordController extends AbstractFormUIController<Object> implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	/*
	 * Services
	 */
	@Inject private CompteUtilisateurService compteUtilisateurService;
	

	/*
	 * Dtos
	 */
	@Getter private AgentEtat agentEtat = new AgentEtat();
	@Getter private FormCommand<Object> validerAgentEtat;
	
	@Getter private Collection<ReponseSecrete> reponseSecretes;
	
	@Override
	protected InitWhen initWhen() {
		return InitWhen.POST_CONSTRUCT;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Récupérer mot de passe oublié";
		defaultSubmitCommand.setValue(text("bouton.valider"));
		defaultSubmitCommand.setRendered(Boolean.FALSE);
		defaultSubmitCommand.setSuccessOutcome("consulteremail");
		
		validerAgentEtat = createCommand().init("bouton.valider", "ui-icon-check", null, new Action() {
			private static final long serialVersionUID = 634657461132080790L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				reponseSecretes = compteUtilisateurService.recupererPasswordEtape1(agentEtat);
				defaultSubmitCommand.setRendered(Boolean.TRUE);
				validerAgentEtat.setRendered(Boolean.FALSE);
				return null;
			}
		}).onSuccessStayOnCurrentView();
			
	}
	
	@Override
	protected void onDefaultSubmitAction() {
		compteUtilisateurService.recupererPasswordEtape2(agentEtat, reponseSecretes);
		
	}
	
}
