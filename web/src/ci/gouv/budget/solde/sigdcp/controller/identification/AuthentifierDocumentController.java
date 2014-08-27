package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Ajax;
import org.primefaces.event.CaptureEvent;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Document;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.signature.SignatureDocumentGenereDossier;
import ci.gouv.budget.solde.sigdcp.service.dossier.DocumentService;
import ci.gouv.budget.solde.sigdcp.service.identification.CodeBarreService;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Named @ViewScoped
public class AuthentifierDocumentController extends AbstractEntityFormUIController<Document> implements Serializable {

	private static final long serialVersionUID = -8067038151558697675L;

	@Inject private DocumentService documentService;
	@Inject transient private CodeBarreService codeBarreService;
	@Inject private CompteUtilisateurService compteUtilisateurService;
	
	@Getter private Document document;
	@Getter private String signature,signatureEncode;
	@Getter private SignatureDocumentGenereDossier signatureInfos;
	@Getter private Date compteUtilisateurDateCreation;
	@Getter @Setter private Boolean captureRunning=Boolean.FALSE;
	@Getter private Signature signatureBean;
	
	@Override
	protected void initialisation() {
		crudType = CRUDType.READ;
		super.initialisation();
		title="Authentification de document";
		//defaultSubmitCommand.setRendered(true);
	}
		
	@Override
	public CRUDType getCrudType() {
		return CRUDType.READ;
	}
	
	public void onCapture(CaptureEvent captureEvent) {
		byte[] data = captureEvent.getData();
		signature = codeBarreService.decoder(data);
		if(StringUtils.isEmpty(signature))
			return;
		//un code barre valide , on peut l'afficher meme si il n'est pas de notre système
		try {
			signatureEncode = URLEncoder.encode(signature, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Ajax.update("form:codeBarre");
		
		signatureInfos =  documentService.formatterSignature(signature);
		if(StringUtils.isEmpty(signatureInfos.getDocumentType())){
			
		}else{
			//un code barre connu du système
			try {
				document = documentService.findBySignature(signature);
				
				if(document!=null){
					if(document instanceof PieceJustificative){
						PieceJustificative pj = (PieceJustificative) document;
						compteUtilisateurDateCreation = compteUtilisateurService.findByParty(pj.getDossier().getBeneficiaire()).getDateCreation();
					}
				}
				
			} catch (Exception e) {
				//messageManager.addError(e.getMessage());
				//return;
			}
			
			signatureBean = new Signature(constantResources);
			if(document instanceof PieceJustificative)
				signatureBean.add("Type du document",signatureInfos.getDocumentType(),((PieceJustificative)document).getModel().getTypePieceJustificative().getLibelle());
			else if(document instanceof PieceProduite)
				signatureBean.add("Type du document",signatureInfos.getDocumentType(),((PieceProduite)document).getType().getLibelle());
			else
				signatureBean.add("Type du document",signatureInfos.getDocumentType(),null);
			
			signatureBean.add("Numéro du document",signatureInfos.getDocumentNumero(), document==null?null:document.getNumero());
			signatureBean.add("Date de création du document",signatureInfos.getDocumentDateCreation(), document==null?null:document.getDateEtablissement());
			
			Dossier dossier=null;
			if(document instanceof PieceJustificative)
				dossier = ((PieceJustificative)document).getDossier();
			else if(document instanceof PieceProduite)
				;
			else
				;
			
			signatureBean.add("Numéro du dossier",signatureInfos.getDossierNumero(), dossier==null?null:dossier.getNumero());
			signatureBean.add("Date de création du dossier",signatureInfos.getDossierDateCreation(), dossier==null?null:dossier.getDateCreation());
			
			signatureBean.add("Matricule du requérant",signatureInfos.getRequerantMatricule(), dossier==null?null:dossier.getBeneficiaire().getMatricule());
			signatureBean.add("Nom et prénoms du requérant",signatureInfos.getRequerantNomPrenoms(), dossier==null?null:dossier.getBeneficiaire().getNomPrenoms());
			signatureBean.add("Date création compte utilisateur du requérant",signatureInfos.getCompteUtilisateurDateCreation(), compteUtilisateurDateCreation);
		}
		
		//mise a jour de la vue
		Ajax.update("form:signature");
		Ajax.update("form:pollPanel");
		//RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Le code a été lu."));
		captureRunning=Boolean.FALSE;
	}
	
	public PieceJustificative getFeuilleDeplacement(){
		return (PieceJustificative) document;
	}
	
	public void demarrerCapture(){
		captureRunning = !captureRunning;
		signature=null;
		signatureInfos=null;
		document=null;
		compteUtilisateurDateCreation=null;
		signatureBean=null;
		Ajax.update("form:codeBarre");
	}
	
}
