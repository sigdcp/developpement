package ci.gouv.budget.solde.sigdcp.controller.fichier;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.fichier.FichierService;

public class PieceJustificativeUploader implements Serializable {

	private static final long serialVersionUID = 2682591481106580763L;
	
	@Getter private List<PieceJustificativeDTO> collection = new LinkedList<>();

	@Inject private FichierService fichierService;
	
	/*
	public PieceJustificativeUploader(List<PieceJustificative> pieces) {
		super();
		this.pieces = pieces;
		this.files = new LinkedList<UploadedFile>();
		if(this.pieces!=null){
			for(int i=0;i<this.pieces.size();i++)
				files.add(null);
		}
	}
	*/
	
	public PieceJustificativeDTO addPieceJustificative(PieceJustificative pieceJustificative) {
		PieceJustificativeDTO dto = new PieceJustificativeDTO(pieceJustificative);
		collection.add(dto);
		return dto;
	}
	
	public Collection<PieceJustificative> process(/*FichierService fichierService*/) throws IOException{
		Collection<PieceJustificative> pieceJustificatives = new LinkedList<>();
		for(PieceJustificativeDTO dto : collection){
			dto.getPiece().setFichier(fichierService.convertir(dto.getFile().getContents(),dto.getFile().getFileName() ));
			pieceJustificatives.add(dto.getPiece());
		}
		return pieceJustificatives;
	}
	
	
}
