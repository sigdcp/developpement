package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class SouscriptionComptePersonneValidator extends AbstractValidator<SouscriptionComptePersonne> implements Serializable {
 
	private static final long serialVersionUID = -261860698364195138L;
		
	@AssertTrue(message="Matricule incorrect",groups=Client.class)
	public boolean isMatriculeFormatCorrect(){
		try {
			validationPolicy.validateMatricule(object.getPersonneDemandeur().getType(),object.getPersonneDemandeur().getMatricule());
			return true;
		} catch (Exception e) {return false;}
	}
	
	@AssertTrue(message="Vous devez avoir plus de 18 ans",groups=Client.class)
	public boolean isMajeur(){
		try {
			validationPolicy.validateDateNaissance(object.getPersonneDemandeur().getPersonne().getDateNaissance());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="le type de la piece d'identite est obligatiore",groups=Client.class)
	public boolean isPieceIdentiteTypeCorrect(){
		return object.getPersonneDemandeur().getPersonne().getPieceIdentiteType()!=null;
	}
	
	@AssertTrue(message="numero de la piece d'identite est obligatiore",groups=Client.class)
	public boolean isPieceIdentiteNumeroCorrect(){
		return StringUtils.isNotEmpty(object.getPersonneDemandeur().getPersonne().getPieceIdentiteNumero());
	}
	
	@AssertTrue(message="cette adresse email est déja lié à un compte",groups=Client.class)
	public boolean isAddresseElectroniqueUnique(){
		try {
			validationPolicy.validateUsernameUnique(object.getPersonneDemandeur().getPersonne().getContact().getEmail());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
}
