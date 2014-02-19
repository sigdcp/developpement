package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.application.AbstractDemandeController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Getter @Setter
public abstract class AbstractDossierUIController<DOSSIER extends Dossier,DOSSIER_SERVICE extends AbstractDossierService<DOSSIER>> extends AbstractDemandeController<DOSSIER> implements Serializable {
	
	private static final long serialVersionUID = 6615049982603373278L;
	
	/*
	 * DTOs
	 */
	
	//protected Statut statutCourant;
	/*
	 * Paramètres de requete
	 */
	@Setter @Getter protected NatureDeplacement natureDaplacement;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		//if(entity==null)
		//	initCreateOperation();
		DOSSIER dossierEnCoursSaisie = getDossierService().findSaisieByPersonneByNatureDeplacement((AgentEtat) userSessionManager.getUser(), entity.getDeplacement().getNature());
		//System.out.println("En cours de saisie : "+dossierEnCoursSaisie.getBeneficiaire().getMatricule());
		enSaisie = dossierEnCoursSaisie!=null;
		
		if(enSaisie)
			entity = dossierEnCoursSaisie;
		entity.setBeneficiaire(beneficiaire(dossierEnCoursSaisie));
		
		if(entity.getDernierTraitement()!=null) 
			if(Code.STATUT_SAISIE.equals(entity.getDernierTraitement().getStatut().getCode()))
				crudType=CRUDType.UPDATE;
			else if(Code.STATUT_RECEVABLE.equals(entity.getDernierTraitement().getStatut().getCode())){
				showCourrier=true;
				courrierEditable = StringUtils.isEmpty(entity.getCourrier().getNumero());
			}
	
		updatePieceJustificatives(true);
		if(entity.getDeplacement().getTypeDepense()==null)
			parametres = new HashMap<String, Object>();
		else
			parametres = pieceJustificativeService.findParametresByDossier(entity, pieceJustificativeUploader.getPieceJustificatives());
		
		title = "Formulaire - "+entity.getDeplacement().getNature().getLibelle();
		instructions = getDossierService().findInstructions(entity);
		
		/*
		defaultSubmitCommand.setValue(text(showCourrier?"bouton.enregistrer":"bouton.soumettre"));
		defaultSubmitCommand.setNotificationMessageId("notification.demande.soumise");
		defaultSubmitCommand.setAjax(Boolean.FALSE);
		defaultSubmitCommand.setRendered((isEditable() && dossierEnCoursSaisie!=null) || showCourrier);
	
		defaultSubmitCommand.set_successOutcome(new Action() {
			private static final long serialVersionUID = -6851391666779599546L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				return navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{webConstantResources.getRequestParamMessageId(),"notification.demande.soumise",
								webConstantResources.getRequestParamUrl(),navigationManager.url("demandeliste",null,false,false)},true);
			}
		});*/
		
		//enregistrerCommand.setImmediate(true);//to remove , just for test
		//enregistrerCommand.setProcess("@form");
				
	}
	
	@Override
	protected void enregistrer() throws Exception {
		getDossierService().enregistrer(entity, pieceJustificativeUploader.process(),creerPar(null));
	}
	
	@Override
	protected String onEnregistrerSuccessOutcome() {
		Collection<PieceJustificativeAFournir> imprimes = pieceJustificativeAFournirService.findDeriveeRestantByDossier(entity, pieceJustificativeUploader.getPieceJustificatives());
		return navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{webConstantResources.getRequestParamMessageId(),
				imprimes.isEmpty()?"notification.demande.enregistree.soumettre":"notification.demande.enregistree.imprimer",
				webConstantResources.getRequestParamMessageParameters(),StringUtils.join(imprimes,","),
						webConstantResources.getRequestParamUrl(),url},true);
	}
	
	protected Personne creerPar(DOSSIER dossier){
		return beneficiaire(dossier);
	}
	
	protected AgentEtat beneficiaire(DOSSIER dossier){
		return (AgentEtat) userSessionManager.getUser();
	}
	
	protected void updatePieceJustificatives(boolean first){
		if(entity.getDeplacement().getTypeDepense()==null)
			return;
		Collection<PieceJustificative> pieceJustificatives;
		if(first)
			pieceJustificatives = pieceJustificativeService.findByDossier(entity, null, parametres);
		else
			pieceJustificatives = pieceJustificativeService.findByDossier(entity,pieceJustificativeUploader.getPieceJustificatives(), parametres);
		
		pieceJustificativeUploader.clear();
		for(PieceJustificative pieceJustificative : pieceJustificatives)
			pieceJustificativeUploader.addPieceJustificative(pieceJustificative);
		pieceJustificativeUploader.update();
		
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
		entity.getDeplacement().setTypeDepense(genericService.findByClass(TypeDepense.class, String.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE));
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		if(Boolean.TRUE.equals(showCourrier))
			getDossierService().deposer(entity);
		else
			getDossierService().soumettre(entity, pieceJustificativeUploader.process(),userSessionManager.getUser());
	}
	
	@Override
	protected String onSoumettreSuccessOutcome() {
		return navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{webConstantResources.getRequestParamMessageId(),"notification.demande.soumise",
				webConstantResources.getRequestParamUrl(),navigationManager.url("demandeliste",null,false,false)},true);
	}
	
	protected Deplacement createDeplacement(){
		return new Deplacement();
	}
	
	
}
		
