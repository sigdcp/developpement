package ci.gouv.budget.solde.sigdcp.controller.application;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;

public abstract class AbstractDemandeController<ENTITY extends AbstractModel<?>> extends AbstractEntityFormUIController<ENTITY> {

	private static final long serialVersionUID = 8101383916459828348L;

	/*
	 * Actions
	 */
	protected FormCommand<ENTITY> enregistrerCommand;
	
	protected Map<String, Object> parametres;
	protected Boolean showCourrier=Boolean.FALSE,courrierEditable=Boolean.FALSE;
	
	@Override
	protected void afterInitialisation() {
		enregistrerCommand = createCommand().init("bouton.enregistrer","ui-icon-check","notification.demande.dd.enregistree1", new Action() {
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				enregistrer();
				return null;
			} 
		});
		enregistrerCommand.setAjax(Boolean.FALSE);
		enregistrerCommand.setRendered(isEditable());
		//addValidator(validator(),enregistrerCommand);
		enregistrerCommand.set_successOutcome(new Action() {
			private static final long serialVersionUID = -6851391666779599546L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				return onEnregistrerSuccessOutcome();
			}
		});
		
		//enregistrerCommand.setImmediate(true);//to remove , just for test
		//enregistrerCommand.setProcess("@form");
	}
	
	protected abstract void enregistrer() throws Exception;
	
	protected abstract String onEnregistrerSuccessOutcome();
	
}
