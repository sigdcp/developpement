package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeAFournirService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeService;

@Getter @Setter @Deprecated
public abstract class AbstractDossierUIController<DOSSIER extends Dossier> extends AbstractEntityFormUIController<DOSSIER> implements Serializable {
	
	private static final long serialVersionUID = 6615049982603373278L;
	
	/*
	 * Services
	 */
	@Inject protected PieceJustificativeService pieceJustificativeService; 
	@Inject protected PieceJustificativeAFournirService pieceJustificativeAFournirService;
	
	/*
	 * DTOs
	 */
	
	@Inject @Getter protected PieceJustificativeUploader pieceJustificativeUploader;
	@Setter @Getter protected PieceJustificative pieceJustificativeSelectionne;
	
	/*
	 * Paramètres de requete
	 */
	@Setter @Getter protected NatureDeplacement natureDaplacement;
	
	
	
	public DOSSIER getDossier(){
		return entity;
	}
	
		
}
		
