package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.application.UserSessionManager;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeAFournirService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.StatutService;

@Getter @Setter
public abstract class AbstractDossierUIController<DOSSIER extends Dossier,DOSSIER_SERVICE extends AbstractDossierService<DOSSIER>> extends AbstractEntityFormUIController<DOSSIER> implements Serializable {
	
	private static final long serialVersionUID = 6615049982603373278L;
	
	/*
	 * Services
	 */
	@Inject protected PieceJustificativeService pieceJustificativeService; 
	@Inject protected PieceJustificativeAFournirService pieceJustificativeAFournirService;
	@Inject protected StatutService statutService;
	/*
	 * DTOs
	 */
	
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader;
	protected Map<String, Object> parametres;
	protected Boolean showDepotDossier=Boolean.FALSE;
	protected Statut statutCourant;
	/*
	 * Paramètres de requete
	 */
	@Setter @Getter protected NatureDeplacement natureDaplacement;
	@Inject protected UserSessionManager userSessionManager;
	
	/*
	 * Actions
	 */
	protected FormCommand<DOSSIER> enregistrerCommand/*,depotCommand*/;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		DOSSIER dossierEnCoursSaisie = getDossierService().findSaisieByPersonneByNatureDeplacement((AgentEtat) userSessionManager.getUser(), entity.getDeplacement().getNature());
		if(dossierEnCoursSaisie!=null)
			entity = dossierEnCoursSaisie;
		statutCourant = statutService.findCourantByDossier(entity);
		if(statutCourant!=null && Code.STATUT_SOUMIS.equals(statutCourant.getCode()))
			showDepotDossier=true;
		if(dossierEnCoursSaisie!=null || getDossierService().exist(entity.getNumero()))
			pieceJustificativeUploader.setAImprimer(pieceJustificativeAFournirService.findDeriveeByNatureDeplacementId(entity.getDeplacement().getNature().getCode()));
		updatePieceJustificatives(true);
		parametres = pieceJustificativeService.findParametresByDossier(entity, pieceJustificativeUploader.getPieceJustificatives());
		
		
		title = "Formulaire de : "+entity.getDeplacement().getNature().getLibelle();
	
		defaultSubmitCommand.setValue(text("bouton.soumettre"));
		defaultSubmitCommand.setNotificationMessageId("notification.demande.soumise");
		defaultSubmitCommand.setAjax(Boolean.FALSE);
		defaultSubmitCommand.setRendered(isEditable() && StringUtils.isNotEmpty(entity.getNumero()));
		
		enregistrerCommand = createCommand().init("bouton.enregistrer","ui-icon-check","notification.demande.enregistree1", new Action() {
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				getDossierService().enregistrer(entity, pieceJustificativeUploader.process(),creerPar());
				return null;
			} 
		}).onSuccessStayOnCurrentView();
		enregistrerCommand.setAjax(Boolean.FALSE);
		enregistrerCommand.setRendered(isEditable());
		/*
		depotCommand = createCommand().init("bouton.valider","ui-icon-check","notification.demande.deposer", new Action() {
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				getDossierService().deposer(entity);
				return null;
			}
		}).onSuccessStayOnCurrentView();
		*/
		enregistrerCommand.setAjax(Boolean.FALSE);
		/*
		if(natureDaplacement==null)
			natureDaplacement = entity.getDeplacement().getNature();
		*/
				
	}
	
	protected Personne creerPar(){
		return userSessionManager.getUser();
	}
	
	protected void updatePieceJustificatives(boolean first){
		Collection<PieceJustificative> pieceJustificatives;
		if(first)
			pieceJustificatives = pieceJustificativeService.findByDossier(entity, null, parametres);
		else
			pieceJustificatives = pieceJustificativeService.findByDossier(entity, pieceJustificativeUploader.getPieceJustificatives(), parametres);
		
		pieceJustificativeUploader.clear();
		for(PieceJustificative pieceJustificative : pieceJustificatives)
			pieceJustificativeUploader.addPieceJustificative(pieceJustificative);
		pieceJustificativeUploader.updateLibelle();
	}
	
	protected void updatePieceJustificatives(){
		updatePieceJustificatives(false);
	}
	
	protected abstract DOSSIER_SERVICE getDossierService();
	
	@Override
	protected void initCreateOperation() {
		super.initCreateOperation();
		entity.setDeplacement(createDeplacement());
		entity.getDeplacement().setNature(natureDaplacement);
		
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		getDossierService().soumettre(entity, pieceJustificativeUploader.process(),userSessionManager.getUser());
	}
	
	protected Deplacement createDeplacement(){
		return new Deplacement();
	}
	
	public DOSSIER getDossier(){
		return entity;
	}
	
	
	
		
}
		
