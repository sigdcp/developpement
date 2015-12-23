package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class AbstractDossierValidator<DOSSIER extends Dossier> extends AbstractValidator<DOSSIER> implements Serializable {

	private static final long serialVersionUID = -7546358962718005449L;

	@Inject protected AgentEtatDao agentEtatDao;
	@Inject protected PieceJustificativeAFournirDao pieceJustificativeAFournirDao;
	@Inject protected PieceJustificativeValidator pieceJustificativeValidator;
	@Setter protected Collection<PieceJustificativeAFournir> pieceJustificativeAFournirs;
	@Getter @Setter protected Boolean soumission;
	
	@AssertTrue(message="le matricule n'est pas valide",groups=Client.class)
	public boolean isMatriculeFormatCorrect(){
		try {
			if(!object.getDeplacement().getNature().getSceSolde())
				return true;
			validationPolicy.validateMatricule(object.getBeneficiaire().getType(),object.getBeneficiaire().getMatricule());
			return true;
		} catch (Exception e) {return false;}
	}
	
	@AssertTrue(message="la date de prise de service n'est pas valide",groups=Client.class)
	public boolean isValidDatePriseService(){
		if(Code.NATURE_DEPLACEMENT_RETRAITE.equals(object.getDeplacement().getNature().getCode()))
			return true;
		try {
			validationPolicy.validateDatePriseService(object.getBeneficiaire(), object.getDatePriseService(),null);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="la date de départ n'est pas valide",groups=Client.class)
	public boolean isValidDateDepart(){
		Date dateFin=null;
		if(object instanceof DossierTransit)dateFin=((DossierTransit)object).getDateFin();
		try {
			validationPolicy.validateDateDepart(object.getDeplacement().getTypeDepense(),object.getBeneficiaire(), object.getDeplacement().getDateDepart(),null,dateFin);
			return true;
		} catch (Exception e) {e.printStackTrace();
			return false;
		}
	}
	
	@AssertTrue(message="la date d'arrivée n'est pas valide",groups=Client.class)
	public boolean isValidDateArrivee(){
		try {
			//System.out.println("AbstractDossierValidator.isValidDateArrivee() : "+object.getDeplacement().getTypeDepense().getCode());
			if(!Code.CATEGORIE_DEPLACEMENT_MISSION.equals(object.getDeplacement().getNature().getCategorie().getCode()))
				validationPolicy.validateDateArrivee(object.getDeplacement().getTypeDepense(),null,object.getDeplacement().getDateDepart(), object.getDeplacement().getDateArrivee());
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
		if(Boolean.TRUE.equals(soumission)){
			//les pieces de base et les pieces derivee
			Collection<PieceJustificativeAFournir> pieceJustificativeAImprimer = pieceJustificativeAFournirDao.readDeriveeByNatureDeplacementIdByTypeDepenseId(object.getDeplacement().getNature().getCode(),
					object.getDeplacement().getTypeDepense().getCode());
			//croisement
			for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAImprimer){
				boolean trouve = false;
				for(PieceJustificative pieceJustificative : object.getPieceJustificatives())
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
		pieceJustificativeValidator.setAutoClearMessages(Boolean.FALSE);
		pieceJustificativeValidator.setSoumission(Boolean.TRUE.equals(soumission));
		for(PieceJustificative pj : object.getPieceJustificatives()){
			pieceJustificativeValidator.validate(pj);
		}
		messages.addAll(pieceJustificativeValidator.getMessages());
		pieceJustificativeValidator.getMessages().clear();
		return pieceJustificativeValidator.isSucces();
	}
	
	/*
	@AssertTrue(message="Deux pieces portent le même numéro",groups=Client.class)
	public boolean isPieceJustificativeNumeroUnique(){
		if(pieceJustificatives!=null){
			List<PieceJustificative> list = new ArrayList<>(pieceJustificatives);
			for(int i=0;i<list.size();i++)
				for(int j=0;j<list.size();j++)
					if(i!=j && list.get(i).getNumero().equalsIgnoreCase(list.get(j).getNumero()))
						return false;
		}
		return true;
	}*/
	
}
