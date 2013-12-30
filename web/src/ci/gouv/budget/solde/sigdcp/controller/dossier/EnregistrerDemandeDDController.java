package ci.gouv.budget.solde.sigdcp.controller.dossier;


import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
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
	
	@Setter @Getter PieceJustificative extraitNaissanceUploader;
	@Getter Boolean showDatePriseService;
	@Getter Boolean showDateCessationService;
	@Getter Boolean showDateMiseRetraite;
	@Getter Boolean showserviceOrigine;
	@Getter Boolean showserviceAcceuil;
	
	@Getter @Setter private Boolean fdEditer = Boolean.FALSE;
	@Getter @Setter private Boolean btEditer = Boolean.FALSE;
	@Getter @Setter private Boolean fdSoumis = Boolean.FALSE;
	@Getter @Setter private Boolean btSoumis = Boolean.FALSE;
	
	private PieceJustificative feuilleDeplacement,bonTransport;
	
	@Override
	protected DossierDD createDossierInstance() {
		DossierDD dossierDD = new DossierDD();
		dossierDD.setDeplacement(new Deplacement());
		return dossierDD;
	}
	
	@Override
	protected AbstractDossierService<DossierDD> getDossierService() {
		return dossierDDService;
	}

	@PostConstruct
	protected void postConstruct2() {
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
		if(Code.NATURE_DEPLACEMENT_AFFECTATION.equals(dossier.getDeplacement().getNature().getCode())){
			showDatePriseService = Boolean.TRUE;
			showserviceAcceuil = Boolean.TRUE;
		}
		else if(Code.NATURE_DEPLACEMENT_MUTATION.equals(dossier.getDeplacement().getNature().getCode())){
			showDatePriseService = Boolean.TRUE;
			showDateCessationService = Boolean.TRUE;
			showserviceOrigine = Boolean.TRUE;
			showserviceAcceuil = Boolean.TRUE;
		}
		
		else if(Code.NATURE_DEPLACEMENT_RETRAITE.equals(dossier.getDeplacement().getNature().getCode())){
			showDateMiseRetraite = Boolean.TRUE;
			showserviceOrigine = Boolean.TRUE;
		}
		
	}
	
	@Override
	protected Boolean valide() {
		DossierDDValidator validator = new DossierDDValidator();
		validator.validate(dossier);
		for(String m : validator.getMessages())
			addMessageError(m);
		return validator.isSucces();
		/*
		Date datecourante = new Date();
		boolean succes= true;
		
		if (!validationUtils.isOrdonne(dossier.getDatePriseService(),datecourante)){
			addMessageError("la date de prise de service ne doit pas être supérieure à la date d'aujourd'hui");
			succes=false;
		}

		if (!validationUtils.isOrdonne(dossier.getDateCessationService(),dossier.getDatePriseService()))
		{
			addMessageError("la date de cessation de service ne doit pas être supérieure à la date de prise de service");
			succes=false;
		}
		if (!validationUtils.isOrdonne(dossier.getDateMariage(),datecourante))
		{
			addMessageError("la date de mariage ne doit pas être supérieure à la date d'aujourd'hui");
			succes=false;
		}
		if (!validationUtils.isOrdonne(dossier.getDateMiseRetraite(),datecourante))
		{
			addMessageError("la date de mise à la retraite ne doit pas être supérieure à la date d'aujourd'hui");
			succes=false;
		}
		if (!validationUtils.isOrdonne(dossier.getDeplacement().getDateArrivee(),datecourante))
		{
			addMessageError("la date de d'arrivée ne doit pas être supérieure à la date d'aujourd'hui");
			succes=false;
		}
		if (!validationUtils.isOrdonne(dossier.getDeplacement().getDateArrivee(),dossier.getDatePriseService()))
		{
			addMessageError("la date de d'arrivée ne doit pas être supérieure à la date de prise de service");
			succes=false;
		}
		if (!validationUtils.isOrdonne(dossier.getDeplacement().getDateArrivee(),dossier.getDateMiseRetraite()))
		{
			addMessageError("la date de d'arrivée ne doit pas être supérieure à la date de mise à la retraite");
			succes=false;
		}
		if (!validationUtils.isOrdonne(dossier.getDeplacement().getDateDepart(),dossier.getDeplacement().getDateArrivee()))
		{
			addMessageError("la date de départ ne doit pas être supérieure à la date d'arrivée");
			succes=false;
		}
		
		return succes;
		*/
	}
	
	@Override
	protected void action() {
		dossierDDService.enregistrer(dossier);
	}
	
	@Override
	protected String succes() {
		addMessage(FacesMessage.SEVERITY_INFO, "Demande enregistr�e");
		return "/private/demande/piecesjustificative.xhtml?faces-redirect=true&"+constantResources.getRequestParamEntityId()+"="+dossier.getNumero(); //"piecesjustificativeForm";
	}
	
	/*
	public String enregistrerBase() {
		super.enregistrerBase();
		PieceJustificativeAFournir extraitModel = new PieceJustificativeAFournir(null, true, 1, 1, new TypePieceJustificative(Code.TYPE_PIECE_EXTRAIT_NAISSANCE, "Extrait de naissance"));
		if(null!=nombreEnfant)
			for (int i=0; i<nombreEnfant;i++){
				pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(extraitModel, dossier));
			}
		return null;
	}
	*/

}
