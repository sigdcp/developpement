package ci.gouv.budget.solde.sigdcp.controller.fichier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.UploadedFile;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;

@Getter @Setter
public class PieceJustificativeDTO implements Serializable {
	
	private static final long serialVersionUID = -8894022422235831240L;
	
	private PieceJustificative piece;
	private UploadedFile file;
	private Boolean numeroEditable = Boolean.TRUE,dateEtablissementEditable=Boolean.TRUE;
	private Boolean editable=Boolean.TRUE;
	private String libelle;
	private Boolean showFile;
	
	public PieceJustificativeDTO(PieceJustificative piece) {
		super();
		this.piece = piece;
		numeroEditable = Boolean.FALSE.equals(piece.getModel().getDerivee());
		dateEtablissementEditable = numeroEditable;
		showFile = piece.getFichier().getBytes()==null;
	}
	
	public void supprimerFichier(){
		piece.getFichier().setBytes(null);
	}

}
