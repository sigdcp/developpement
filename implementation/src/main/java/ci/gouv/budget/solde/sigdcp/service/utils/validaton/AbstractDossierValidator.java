package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class AbstractDossierValidator<DOSSIER extends Dossier> extends AbstractValidator<DOSSIER> implements Serializable {

	private static final long serialVersionUID = -7546358962718005449L;

	@Inject protected AgentEtatDao agentEtatDao;
	@Inject protected PieceJustificativeAFournirDao pieceJustificativeAFournirDao;
	@Inject protected PieceJustificativeValidator pieceJustificativeValidator;
	@Setter protected Collection<PieceJustificative> pieceJustificatives;
	@Setter protected Collection<PieceJustificativeAFournir> pieceJustificativeAFournirs;
	@Setter protected boolean soumission;
	
	@AssertTrue(message="la date de prise de service n'est pas valide",groups=Client.class)
	public boolean isValidDatePriseService(){
		try {
			validationPolicy.validateDatePriseService(object.getBeneficiaire(), object.getDatePriseService());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="la date de départ n'est pas valide",groups=Client.class)
	public boolean isValidDateDepart(){
		try {
			validationPolicy.validateDateDepart(object.getBeneficiaire(), object.getDeplacement().getDateDepart());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="la date d'arrivée n'est pas valide",groups=Client.class)
	public boolean isValidDateArrivee(){
		try {
			validationPolicy.validateDateArrivee(object.getDeplacement().getDateDepart(), object.getDeplacement().getDateArrivee());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="la ville de depart n'est pas valide",groups=Client.class)
	public boolean isValidVilleDepart(){
		try {
			validationPolicy.validateVilleDepart(object.getBeneficiaire(), object.getDeplacement().getLocaliteDepart());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="la ville d'arrivee n'est pas valide",groups=Client.class)
	public boolean isValidVilleArrivee(){
		try {
			validationPolicy.validateVilleArrivee(object.getDeplacement().getLocaliteDepart(), object.getDeplacement().getLocaliteArrivee());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="Les pieces justificatives ne sont pas complètes",groups=Client.class)
	public boolean isPiecesJustificativesComplet(){
		if(soumission){
			//les pieces de base et les pieces derivee
			Collection<PieceJustificativeAFournir> pieceJustificativeAImprimer = pieceJustificativeAFournirDao.readDeriveeByNatureDeplacementIdByTypeDepenseId(object.getDeplacement().getNature().getCode(),
					object.getDeplacement().getTypeDepense().getCode());
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
	
	@AssertTrue(message="###",groups=Client.class)
	public boolean isPieceJustificativesCorrect(){
		if(pieceJustificatives==null)
			return true;
		pieceJustificativeValidator.setAutoClearMessages(Boolean.FALSE);
		pieceJustificativeValidator.setSoumission(soumission);
		for(PieceJustificative pj : pieceJustificatives){
			
			pieceJustificativeValidator.validate(pj);
		}
		messages.addAll(pieceJustificativeValidator.getMessages());
		pieceJustificativeValidator.getMessages().clear();
		return pieceJustificativeValidator.isSucces();
	}
	
}
