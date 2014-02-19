package ci.gouv.budget.solde.sigdcp.controller.application;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeAFournirService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.StatutService;
import ci.gouv.budget.solde.sigdcp.service.fichier.FichierService;

@Getter
public abstract class AbstractDemandeController<ENTITY extends AbstractModel<?>> extends AbstractEntityFormUIController<ENTITY> {

	private static final long serialVersionUID = 8101383916459828348L;

	/*
	 * Services
	 */
	@Inject protected PieceJustificativeService pieceJustificativeService; 
	@Inject protected PieceJustificativeAFournirService pieceJustificativeAFournirService;
	@Inject protected StatutService statutService;
	@Inject protected FichierService fichierService;
	
	/*
	 * Dtos
	 */
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader;
	protected FormCommand<ENTITY> enregistrerCommand;
	protected Boolean enSaisie = Boolean.FALSE;
	protected Map<String, Object> parametres;
	protected Boolean showCourrier=Boolean.FALSE,courrierEditable=Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		pieceJustificativeUploader.setEditable(isEditable());
		super.initialisation();
	}
	
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
		
		defaultSubmitCommand.setValue(text(showCourrier?"bouton.enregistrer":"bouton.soumettre"));
		defaultSubmitCommand.setNotificationMessageId("notification.demande.soumise");
		defaultSubmitCommand.setAjax(Boolean.FALSE);
		defaultSubmitCommand.setRendered((isEditable() && enSaisie) || showCourrier);
	
		defaultSubmitCommand.set_successOutcome(new Action() {
			private static final long serialVersionUID = -6851391666779599546L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				return onSoumettreSuccessOutcome();
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
		pieceJustificativeUploader.setEditable(isEditable());
		//enregistrerCommand.setImmediate(true);//to remove , just for test
		//enregistrerCommand.setProcess("@form");
	}
	
	protected abstract void enregistrer() throws Exception;
	
	protected String onEnregistrerSuccessOutcome() {
		Collection<PieceJustificativeAFournir> imprimes = onEnregistrerSuccessPieceJustificativeAFournir();
		return navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{webConstantResources.getRequestParamMessageId(),
				(imprimes==null || imprimes.isEmpty())?"notification.demande.enregistree.soumettre":"notification.demande.enregistree.imprimer",
				webConstantResources.getRequestParamMessageParameters(),StringUtils.join(imprimes,","),
						webConstantResources.getRequestParamUrl(),url},true);
	}
	
	protected Collection<PieceJustificativeAFournir> onEnregistrerSuccessPieceJustificativeAFournir(){
		return null;
	}
	
	protected abstract String onSoumettreSuccessOutcome();
	
}
