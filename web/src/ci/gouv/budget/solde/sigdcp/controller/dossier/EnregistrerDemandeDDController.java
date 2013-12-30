package ci.gouv.budget.solde.sigdcp.controller.dossier;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierDDValidator;

 
@Named @ViewScoped
public class EnregistrerDemandeDDController extends AbstractDossierUIControllerController<DossierDD> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */ 
	@Inject protected DossierDDService dossierDDService;
	
	/*
	 * Objet de transfert de données (DTO)
	 */
		
	/*
	 * Paramètres de vue (requete)
	 */
	
	@Getter @Setter private Boolean marie;
	@Getter @Setter private Integer nombreEnfant;
	

	/*
	 * Attributs de parametrages de la vue
	 */
	@Getter Boolean showDatePriseService;
	@Getter Boolean showDateCessationService;
	@Getter Boolean showDateMiseRetraite;
	@Getter Boolean showserviceOrigine;
	@Getter Boolean showserviceAcceuil;
	
	@Setter @Getter PieceJustificative extraitNaissanceUploader;
		
	private String pjo;
	
	@Override
	protected Class<DossierDD> entityClass() {
		return DossierDD.class;
	}
	
	@Override
	protected AbstractDossierService<DossierDD> getDossierService() {
		return dossierDDService;
	}


	@Override
	public void __firstPreRenderView__() {
		super.__firstPreRenderView__();
		//natureDaplacementCode = Faces.getRequestParameter(constantResources.getRequestParamNatureDeplacement());
		//System.out.println("Nature deplacement code : "+natureDaplacementCode);
		/*
		NatureDeplacement natureDeplacement = natureDeplacementService.findById(natureDaplacementCode);
		title = "Formulaire de demande : "+natureDeplacement.getLibelle();
		String action = Faces.getRequestParameter(constantResources.getRequestParamAction());
		if(constantResources.getRequestParamActionEditer().equals(action))
			editable = Boolean.TRUE;
		dossierCode = Faces.getRequestParameter(constantResources.getRequestParamEntityId());
		
		if(Boolean.TRUE.equals(getEditable())){
			dossier.setDeplacement(new Deplacement());
			dossier.getDeplacement().setNature(natureDeplacementService.findById(natureDaplacementCode));
			for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(natureDaplacementCode))
				pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, dossier));
		}else{
			dossier = dossierDDService.findById(dossierCode); 
			Collection<PieceJustificative> pjs = pieceJustificativeService.findByDossier(dossier);
			for(PieceJustificative pieceJustificative : pjs ){
				//uploadedFiles.add(new PieceUploadHelper(pieceJustificative));
				pieceJustificativeUploader.addPieceJustificative(pieceJustificative);
			}
		} 
		*/
		
		if(isNatureDeplacementAffectation()){
			showDatePriseService = Boolean.TRUE;
			showserviceAcceuil = Boolean.TRUE;
		}else if(isNatureDeplacementMutation()){
			showDatePriseService = Boolean.TRUE;
			showDateCessationService = Boolean.TRUE;
			showserviceOrigine = Boolean.TRUE;
			showserviceAcceuil = Boolean.TRUE;
		}else if(isNatureDeplacementDepartRetraite()){
			showDateMiseRetraite = Boolean.TRUE;
			showserviceOrigine = Boolean.TRUE;
		}
		
		pjo = outcome("piecesJustificativeForm", new String[]{});
		
	}
	
	@Override
	protected Boolean valide() {
		DossierDDValidator validator = new DossierDDValidator();
		validator.validate(entity);
		for(String m : validator.getMessages())
			addMessageError(m);
		return validator.isSucces();

	}
	
	@Override
	protected void action() {
		dossierDDService.enregistrer(entity);
	}
	
	@Override
	protected String succes() {
		addMessage(FacesMessage.SEVERITY_INFO, "Demande enregistrée");
		return pjo //"/private/demande/piecesjustificative.xhtml"
				+ "?faces-redirect=true&"+constantResources.getRequestParamEntityId()+"="+entity.getNumero(); //"piecesjustificativeForm";
	}
	
	public boolean isNatureDeplacementAffectation(){
		return Code.NATURE_DEPLACEMENT_AFFECTATION.equals(entity.getDeplacement().getNature().getCode());
	}
	
	public boolean isNatureDeplacementDepartRetraite(){
		return Code.NATURE_DEPLACEMENT_RETRAITE.equals(entity.getDeplacement().getNature().getCode());
	}
	
	public boolean isNatureDeplacementMutation(){
		return Code.NATURE_DEPLACEMENT_MUTATION.equals(entity.getDeplacement().getNature().getCode());
	}

}
