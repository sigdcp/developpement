package ci.gouv.budget.solde.sigdcp.controller.fichier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.UploadedFile;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;

@Getter @Setter
public class PieceJustificativeDto implements Serializable {
	
	private static final long serialVersionUID = -8894022422235831240L;
	
	private PieceJustificative piece;
	private UploadedFile file;
	private Boolean numeroEditable = Boolean.TRUE,dateEtablissementEditable=Boolean.TRUE,fichierEditable=Boolean.TRUE;
	private Boolean editable=Boolean.TRUE,imprimable=Boolean.FALSE;
	private String libelle,rowStyleClass,allowedFileTypes;
	private Boolean hasFichier,required;
	
	public PieceJustificativeDto(PieceJustificative piece,Boolean editable,Boolean imprimable) {
		super();
		this.piece = piece;
		this.editable = editable;
		this.imprimable = imprimable;
		
		numeroEditable = editable && Boolean.FALSE.equals(piece.getModel().getConfig().getDerivee());
		dateEtablissementEditable = numeroEditable;
		hasFichier = piece.getFichier()!=null;
		
		if(Boolean.TRUE.equals(piece.getModel().getConfig().getPrincipale())){
			rowStyleClass = "ui-piece-principale";
			required = Boolean.TRUE;
		}else if(Boolean.TRUE.equals(piece.getModel().getConfig().getDerivee())){
			rowStyleClass = "ui-piece-derivee";
			//imprimable=true && editable;
		}else if(Boolean.TRUE.equals(piece.getModel().getConfig().getConditionnee()))
			rowStyleClass = "ui-piece-conditionnee";
		else
			rowStyleClass = "ui-piece-defaut";
		fichierEditable = editable; //&& Boolean.TRUE.equals(piece.getModel().getConfig().getDerivee());
		//numeroEditable = dateEtablissementEditable = !Boolean.TRUE.equals(piece.getModel().getConfig().getDerivee());
	}
	
	public void supprimerFichier(){
		piece.setFichier(null);
		hasFichier=false;
	}

}
