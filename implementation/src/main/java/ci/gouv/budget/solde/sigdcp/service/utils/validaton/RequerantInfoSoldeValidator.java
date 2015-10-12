package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class RequerantInfoSoldeValidator extends AbstractDossierValidator<Dossier> implements Serializable {
 
	private static final long serialVersionUID = -261860698364195138L;
	
	
	@AssertTrue(message="le matricule n'est pas valide",groups=Client.class)
	public boolean isMatriculeFormatCorrect(){
		try {
			validationPolicy.validateMatricule(object.getBeneficiaire().getType(),object.getBeneficiaire().getMatricule());
			return true;
		} catch (Exception e) {return false;}
	}
	
	/*@AssertTrue(message="la date de naissance n'est pas valide",groups=Client.class)
	public boolean isMajeur(){
		try {
			validationPolicy.validateDateNaissance(object.getPersonneDemandeur().getPersonne().getDateNaissance());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="le type de la piece d'identite n'est pas valide",groups=Client.class)
	public boolean isPieceIdentiteTypeCorrect(){
		return object.getPersonneDemandeur().getPersonne().getPieceIdentiteType()!=null;
	}
	
	@AssertTrue(message="le numero de la piece d'identite n'est pas valide",groups=Client.class)
	public boolean isPieceIdentiteNumeroCorrect(){
		return StringUtils.isNotEmpty(object.getPersonneDemandeur().getPersonne().getPieceIdentiteNumero());
	}
	
*/
	
}
