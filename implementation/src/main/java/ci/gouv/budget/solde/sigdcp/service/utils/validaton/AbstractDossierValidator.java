package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class AbstractDossierValidator<DOSSIER extends Dossier> extends AbstractValidator<DOSSIER> implements Serializable {

	private static final long serialVersionUID = -7546358962718005449L;

	@Inject protected PieceJustificativeAFournirDao pieceJustificativeAFournirDao;
	@Inject protected PieceJustificativeValidator pieceJustificativeValidator;
	@Setter protected Collection<PieceJustificative> pieceJustificatives;
	@Setter protected Collection<PieceJustificativeAFournir> pieceJustificativeAFournirs;
	@Setter protected boolean soumission;
	@Setter String typeDepenseId;
	
	@AssertTrue(message="Les pieces justificatives ne sont pas complètes",groups=Client.class)
	public boolean isPiecesJustificativesComplet(){
		if(soumission){
			//les pieces de base et les pieces derivee
			Collection<PieceJustificativeAFournir> pieceJustificativeAImprimer = pieceJustificativeAFournirDao.readDeriveeByNatureDeplacementIdByTypeDepenseId(object.getDeplacement().getNature().getCode(),
					typeDepenseId);
			//croisement
			for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAImprimer){
				boolean trouve = false;
				for(PieceJustificative pieceJustificative : pieceJustificatives)
					if(pieceJustificative.getModel().equals(pieceJustificativeAFournir)){
						trouve = true;
						break;
					}
				if(!trouve)
					return false;
					//pieceManquantes.add(ServiceExceptionType.DOSSIER_PIECE_JUSTIFICATIVE_MANQUANTE.getLibelle());
					
			}
			//if(!pieceManquantes.isEmpty())
			//	serviceException(ServiceExceptionType.DOSSIER_PIECE_JUSTIFICATIVE_MANQUANTE);
		}
		return true;
	}
	
}
