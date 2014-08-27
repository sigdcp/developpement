package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ValidationPolicy.InfosFichierATelecharger;

public class PieceJustificativeValidator extends AbstractValidator<PieceJustificative> implements Serializable {

	private static final long serialVersionUID = -9154448652669543194L;

	@Setter
	private boolean soumission;
	
	@AssertTrue(message="Le numéro n'est pas valide")
	public boolean isValidNumero(){
		try{
			validationPolicy.validatePieceJustificativeNumero(soumission, object.getNumero(), object.getDateEtablissement(), object.getFonctionSignataire()
					,null,object.getModel());
			return true;
		}catch(Exception exception){
			return false;
		}
		/*
		if(soumission)
			return !isNull(object.getNumero());
		if(!isNull(object.getDateEtablissement()) || !isNull(object.getFonctionSignataire()))
			return !isNull(object.getNumero());
		return true;
		*/
	}
	
	@AssertTrue(message="La date d'établissement n'est pas valide")
	public boolean isValidDateEtablissement(){
		try{
			validationPolicy.validatePieceJustificativeDateEtablissement(soumission, object.getNumero(), object.getDateEtablissement(), object.getFonctionSignataire()
					,null,object.getModel());
			return true;
		}catch(Exception exception){
			return false;
		}
		/*if(soumission)
			return !isNull(object.getDateEtablissement());
		if(!isNull(object.getNumero()) || !isNull(object.getFonctionSignataire()))
			return !isNull(object.getDateEtablissement());*/
	}
	
	@AssertTrue(message="La fonction du signataire n'est pas valide")
	public boolean isValidFonctionSignataire(){
		try{
			//validationPolicy.validatePieceJustificativeFonctionSignataire(soumission, object.getNumero(), object.getDateEtablissement(), object.getFonctionSignataire()
			//		,object.getFichier()==null?null:object.getFichier().getBytes(),object.getModel());
			return true;
		}catch(Exception exception){
			return false;
		}
		/*if(soumission)
			return !isNull(object.getFonctionSignataire());
		if(Boolean.TRUE.equals(object.getModel().getDerivee()))
			return true;
		if(!isNull(object.getDateEtablissement()) || !isNull(object.getNumero()))
			return !isNull(object.getFonctionSignataire());*/
	}
	
	@AssertTrue(message="Le fichier n'est pas valide")
	public boolean isValidFichier(){
		try{
			if(object.getFichier()==null)
				return !soumission;
			InfosFichierATelecharger f = new InfosFichierATelecharger("fichier."+object.getFichier().getExtension(), (long) object.getFichier().getBytes().length);
			validationPolicy.validatePieceJustificativeFichier(soumission, object.getNumero(), object.getDateEtablissement(), object.getFonctionSignataire()
					,f,object.getModel());
			return true;
		}catch(Exception exception){
			return false;
		}
		
	}
	

	
}
