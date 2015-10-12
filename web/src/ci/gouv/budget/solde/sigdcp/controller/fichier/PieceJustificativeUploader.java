package ci.gouv.budget.solde.sigdcp.controller.fichier;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.IOUtils;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.fichier.FichierService;

public class PieceJustificativeUploader implements Serializable {

	private static final long serialVersionUID = 2682591481106580763L;
	
	@Setter @Inject private FichierService fichierService;
	@Setter @Getter protected PieceJustificative pieceJustificativeSelectionne;
	@Getter private List<PieceJustificativeDto> collection = new LinkedList<>();
	@Getter @Setter private String title = "Pièces justificatives (Toutes obligatoires à la soumission)",allowedFileTypes;
	@Getter @Setter private Boolean showInputs=Boolean.TRUE,editable=Boolean.TRUE,showColumnCount=true,showColumnName=true,soumission=false,showDescriptions=false;
		
	public PieceJustificativeDto addPieceJustificative(PieceJustificative pieceJustificative,Boolean editable,Boolean imprimable) {
		PieceJustificativeDto dto = new PieceJustificativeDto(pieceJustificative,editable,imprimable);
		dto.setAllowedFileTypes(allowedFileTypes);
		collection.add(dto);
		return dto;
	}
	
	public void update(){
		//quel type de piece avons nous
		Collection<PieceJustificativeAFournir> models = new LinkedHashSet<>();
		for(PieceJustificativeDto dto :  collection)
			models.add(dto.getPiece().getModel());
		//System.out.println(models);
		for(PieceJustificativeAFournir model : models){
			Collection<PieceJustificativeDto> dtos = dtos(model);
			if(dtos.size()==1)
				updateLibelle(dtos.iterator().next(), 0);
			else{
				int i=1;
				for(PieceJustificativeDto dto : dtos)
					updateLibelle(dto,i++);
			}
		}
		Collections.sort(collection,Collections.reverseOrder(new PieceJustificativeComparator()));
	}
	
	private void updateLibelle(PieceJustificativeDto dto,int index){
		dto.setLibelle(dto.getPiece().getModel().toString()+(index>0?" "+index:""));
	}
	
	private Collection<PieceJustificativeDto> dtos(PieceJustificativeAFournir model){
		Collection<PieceJustificativeDto> dtos = new LinkedHashSet<>();
		for(PieceJustificativeDto dto :  collection)
			if(dto.getPiece().getModel().equals(model))
				dtos.add(dto);
		return dtos;
	}
	
	public Collection<PieceJustificative> process() throws IOException{
		Collection<PieceJustificative> pieceJustificatives = new LinkedList<>();
		for(PieceJustificativeDto dto : collection){
			if(dto.getFile()!=null){
				
				dto.getPiece().setFichier(fichierService.convertir(IOUtils.toByteArray(dto.getFile().getInputstream()),dto.getFile().getFileName() ));
			}
			pieceJustificatives.add(dto.getPiece());
		}
		return pieceJustificatives;
	}
	
	public Collection<PieceJustificative> getPieceJustificatives(){
		Collection<PieceJustificative> pieceJustificatives = new LinkedList<>();
		for(PieceJustificativeDto dto : collection)
			pieceJustificatives.add(dto.getPiece());
		return pieceJustificatives;
	}
	
	public void clear(){
		collection.clear();
	}
	
	public void voir(PieceJustificativeDto dto){
		
	}
	
	public void addPieces(Collection<PieceJustificative> pieces,Boolean editable){
		clear();
		//System.out.println("PieceJustificativeUploader.addPieces() YYYYYYYYYY ");
		for(PieceJustificative pieceJustificative : pieces){
			Boolean imprimable = Boolean.TRUE.equals(pieceJustificative.getModel().getConfig().getDerivee()) && editable;
			if(pieceJustificative.getEditable()!=null)
				imprimable = pieceJustificative.getEditable();
			if(pieceJustificative.getModifiable()!=null)
				addPieceJustificative(pieceJustificative,Boolean.TRUE.equals(pieceJustificative.getModifiable()) || !Boolean.FALSE.equals(pieceJustificative.getModifiable()),
						imprimable);
			else
				addPieceJustificative(pieceJustificative, editable,imprimable);
		}
		update();
	}
	
	private class PieceJustificativeComparator implements Comparator<PieceJustificativeDto>{
		
		@Override
		public int compare(PieceJustificativeDto p1, PieceJustificativeDto p2) {
			if(Boolean.TRUE.equals(p1.getPiece().getModel().getConfig().getPrincipale()))
				if(Boolean.TRUE.equals(p2.getPiece().getModel().getConfig().getPrincipale()))
					return p1.getLibelle().compareTo(p2.getLibelle());//les deux pieces sont principales
				else
					return 1;
			else 
				if(Boolean.TRUE.equals(p2.getPiece().getModel().getConfig().getPrincipale()))
					return -1;
				else
					if(Boolean.TRUE.equals(p1.getPiece().getModel().getConfig().getDerivee()))
						if(Boolean.TRUE.equals(p2.getPiece().getModel().getConfig().getDerivee()))
							return p1.getLibelle().compareTo(p2.getLibelle());//les deux pieces sont derivees
						else
							return -1;
					else
						if(Boolean.TRUE.equals(p2.getPiece().getModel().getConfig().getDerivee()))
							return 1;
						else
							return -(p1.getLibelle().compareTo(p2.getLibelle()));
		}
		
	}
	
}
