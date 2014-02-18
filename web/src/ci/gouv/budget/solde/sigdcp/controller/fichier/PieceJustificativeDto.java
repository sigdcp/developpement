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
	private Boolean numeroEditable = Boolean.TRUE,dateEtablissementEditable=Boolean.TRUE;
	private Boolean editable=Boolean.TRUE;
	private String libelle,rowStyleClass;
	private Boolean showFile,required;
	
	public PieceJustificativeDto(PieceJustificative piece) {
		super();
		this.piece = piece;
		numeroEditable = Boolean.FALSE.equals(piece.getModel().getDerivee());
		dateEtablissementEditable = numeroEditable;
		showFile = piece.getFichier()==null;
		
		if(Boolean.TRUE.equals(piece.getModel().getPrincipale())){
			rowStyleClass = "ui-piece-principale";
			required = Boolean.TRUE;
		}else if(Boolean.TRUE.equals(piece.getModel().getDerivee()))
			rowStyleClass = "ui-piece-derivee";
		else if(Boolean.TRUE.equals(piece.getModel().getConditionnee()))
			rowStyleClass = "ui-piece-conditionnee";
		else
			rowStyleClass = "ui-piece-defaut";
	}
	
	public void supprimerFichier(){
		piece.setFichier(null);
		showFile=true;
	}

}
