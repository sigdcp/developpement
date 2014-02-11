package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;

public class PieceJustificativeValidator extends AbstractValidator<PieceJustificative> implements Serializable {

	private static final long serialVersionUID = -9154448652669543194L;

	@Setter
	private boolean soumission;
	
	@AssertTrue(message="Le numéro de la piece justificative est obligatoire")
	public boolean isNumeroNotNull(){
		if(soumission)
			return !isNull(object.getNumero());
		if(!isNull(object.getDateEtablissement()) || !isNull(object.getFonctionSignataire()))
			return !isNull(object.getNumero());
		return true;
	}
	
	@AssertTrue(message="La date d'établissement de la piece justificative est obligatoire")
	public boolean isDateEtablissementNotNull(){
		if(soumission)
			return !isNull(object.getDateEtablissement());
		if(!isNull(object.getNumero()) || !isNull(object.getFonctionSignataire()))
			return !isNull(object.getDateEtablissement());
		return true;
	}
	
	@AssertTrue(message="La fonction du signataire de la piece justificative est obligatoire")
	public boolean isFonctionSignataireNotNull(){
		if(soumission)
			return !isNull(object.getFonctionSignataire());
		if(Boolean.TRUE.equals(object.getModel().getDerivee()))
			return true;
		if(!isNull(object.getDateEtablissement()) || !isNull(object.getNumero()))
			return !isNull(object.getFonctionSignataire());
		return true;
	}
	

	
}
