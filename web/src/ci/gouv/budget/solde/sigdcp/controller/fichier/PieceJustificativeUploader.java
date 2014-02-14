package ci.gouv.budget.solde.sigdcp.controller.fichier;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.fichier.FichierService;

public class PieceJustificativeUploader implements Serializable {

	private static final long serialVersionUID = 2682591481106580763L;
	
	@Inject private FichierService fichierService;
	//@Getter @Setter private Collection<PieceJustificativeAFournir> aImprimer;
	@Setter @Getter protected PieceJustificative pieceJustificativeSelectionne;
	@Getter private List<PieceJustificativeDTO> collection = new LinkedList<>();
	
	@Getter @Setter private Boolean showInputs=Boolean.TRUE;
		
	public PieceJustificativeDTO addPieceJustificative(PieceJustificative pieceJustificative) {
		PieceJustificativeDTO dto = new PieceJustificativeDTO(pieceJustificative);
		collection.add(dto);
		return dto;
	}
	
	public void updateLibelle(){
		//quel type de piece avons nous
		Collection<PieceJustificativeAFournir> models = new LinkedHashSet<>();
		for(PieceJustificativeDTO dto :  collection)
			models.add(dto.getPiece().getModel());
		for(PieceJustificativeAFournir model : models){
			Collection<PieceJustificativeDTO> dtos = dtos(model);
			if(dtos.size()==1)
				updateLibelle(dtos.iterator().next(), 0);
			else{
				int i=1;
				for(PieceJustificativeDTO dto : dtos)
					updateLibelle(dto,i++);
			}
		}
	}
	
	private void updateLibelle(PieceJustificativeDTO dto,int index){
		dto.setLibelle(dto.getPiece().getModel().getTypePieceJustificative().getLibelle()+(index>0?" "+index:""));
	}
	
	private Collection<PieceJustificativeDTO> dtos(PieceJustificativeAFournir model){
		Collection<PieceJustificativeDTO> dtos = new LinkedHashSet<>();
		for(PieceJustificativeDTO dto :  collection)
			if(dto.getPiece().getModel().equals(model))
				dtos.add(dto);
		return dtos;
	}
	
	public Collection<PieceJustificative> process() throws IOException{
		Collection<PieceJustificative> pieceJustificatives = new LinkedList<>();
		for(PieceJustificativeDTO dto : collection){
			if(dto.getFile()!=null){
				
				dto.getPiece().setFichier(fichierService.convertir(dto.getFile().getContents(),dto.getFile().getFileName() ));
			}
			pieceJustificatives.add(dto.getPiece());
		}
		return pieceJustificatives;
	}
	
	public Collection<PieceJustificative> getPieceJustificatives(){
		Collection<PieceJustificative> pieceJustificatives = new LinkedList<>();
		for(PieceJustificativeDTO dto : collection)
			pieceJustificatives.add(dto.getPiece());
		return pieceJustificatives;
	}
	
	public void clear(){
		collection.clear();
	}
	
}
