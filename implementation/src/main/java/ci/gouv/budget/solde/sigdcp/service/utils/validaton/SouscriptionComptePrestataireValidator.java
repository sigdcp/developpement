package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePrestataire;

public class SouscriptionComptePrestataireValidator extends AbstractValidator<SouscriptionComptePrestataire> implements Serializable {
 
	private static final long serialVersionUID = -261860698364195138L;
	
	//@Inject private SouscriptionComptePersonneDao souscriptionComptePersonneDao;
	
	/*@AssertTrue(message="le matricule n'est pas valide",groups=Client.class)
	public boolean isMatriculeFormatCorrect(){
		try {
			validationPolicy.validateMatricule(object.getPersonneDemandeur().getType(),object.getPersonneDemandeur().getMatricule());
			return true;
		} catch (Exception e) {return false;}
	}
	
	@AssertTrue(message="la date de naissance n'est pas valide",groups=Client.class)
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
	
	@AssertTrue(message="l'adresse email est déja lié à un compte",groups=Client.class)
	public boolean isAddresseElectroniqueUnique(){
		try {
			validationPolicy.validateUsernameUnique(object.getPersonneDemandeur().getPersonne().getContact().getEmail());
			return true;
		} catch (Exception e) {
			return false;
		}
	}*/
	/*
	@Override
	public void manualProcess() {
		// Est ce qu'il a déja une inscription en cours de validation ou acceptée
		SouscriptionComptePersonne souscriptionComptePersonneExistante = souscriptionComptePersonneDao.readByMatricule(object.getPersonneDemandeur().getMatricule());
		
		if(souscriptionComptePersonneExistante!=null){			
			if(souscriptionComptePersonneExistante.getDateValidation()==null)
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_ENCOURS);
		
			if( Boolean.TRUE.equals(souscriptionComptePersonneExistante.getAcceptee()))
				serviceException(IDENTIFICATION_SOUSCRIPTION_COMPTE_ACCEPTE);
		}
		
		
		
	}
	*/
	
	
}
