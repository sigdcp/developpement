package ci.gouv.budget.solde.sigdcp.controller.dossier;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeDTO;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;


@Named @ViewScoped
public class EnregistrerPiecesJustificativeController extends AbstractDossierUIControllerController<DossierDD> implements Serializable {
	
	private static final long serialVersionUID = -611561465509440427L;
	
	/*
	 * Services
	 */ 
	@Inject protected DossierDDService dossierDDService;
	
	/*
	 * Objet de transfert de données (DTO)
	 */
		
	/*
	 * Attributs de parametrages de la vue
	 */
		
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
			for(PieceJustificative pieceJustificative : pjs){
				//uploadedFiles.add(new PieceUploadHelper(pieceJustificative));
				pieceJustificativeUploader.addPieceJustificative(pieceJustificative);
			}
		} */
		
	}
	
	public String editerFD(){
		/*
		if(Boolean.FALSE.equals(enregistrer)){
			addMessageError("Vous devez enregistrer votre demande dabord avant d'éditer une feuille de déplacement");
			return null;
		}
		if(Boolean.FALSE.equals(fdEditer)){
			PieceJustificativeAFournir fdm = new PieceJustificativeAFournir(null, true, 1, 1, new TypePieceJustificative(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT, "Feuille de déplacement"));
			feuilleDeplacement = editerPiece(fdm, "FD402");
			fdEditer = true;
			return null;
		}
		addMessage(FacesMessage.SEVERITY_INFO, "Une feuille de déplacement N° "+feuilleDeplacement.getNumero()+" a été editée");
		*/
		return "demandeddPiecesJustificative";
	}
	
	public String editerBT(){
		/*
		if(Boolean.FALSE.equals(enregistrer)){
			addMessageError("Vous devez enregistrer votre demande dabord avant d'éditer un bon de transport");
			return null;
		}
		if(Boolean.FALSE.equals(btEditer)){
			PieceJustificativeAFournir fdm = new PieceJustificativeAFournir(null, true, 1, 1, new TypePieceJustificative(Code.TYPE_PIECE_BON_TRANSPORT, "Bon de transport"));
			bonTransport = editerPiece(fdm, "BT517");
			btEditer= true;
		}
		addMessage(FacesMessage.SEVERITY_INFO, "Un bon de transport N° "+bonTransport.getNumero()+" a été edité");
		*/
		return "demandeddPiecesJustificative";
	}
	
	private PieceJustificative editerPiece(PieceJustificativeAFournir model,String numero){
		PieceJustificative pj = new PieceJustificative(model, dossier);
		pj.setNumero(numero);
		pj.setDateEtablissement(new Date());
		PieceJustificativeDTO dto = pieceJustificativeUploader.addPieceJustificative(pj);
		dto.setNumeroEditable(Boolean.FALSE);
		dto.setDateEtablissementEditable(Boolean.FALSE);
		return pj;
	}
	
	@Override
	protected Boolean valide() {
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
	}
	
	public String enregistrerBase() {
		/*
		PieceJustificativeAFournir extraitModel = new PieceJustificativeAFournir(null, true, 1, 1, new TypePieceJustificative(Code.TYPE_PIECE_EXTRAIT_NAISSANCE, "Extrait de naissance"));
		if(null!=nombreEnfant)
			for (int i=0; i<nombreEnfant;i++){
				pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(extraitModel, dossier));
			}
		*/
		return null;	
	}

}
