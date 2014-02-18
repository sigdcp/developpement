package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

public class DossierObsequesValidator extends AbstractDossierValidator<DossierObseques> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@Override
	public boolean isValidDatePriseService() {
		return true;
	}
	
	@Override
	public boolean isValidDateArrivee() {
		return true;
	}
	
	@Override
	public boolean isValidDateDepart() {
		return true;
	}
	
	@AssertTrue(message="le date de naissance du declarant n'est pas valide",groups=Client.class)
	public boolean isValidDateNaissanceDeclarant(){
		try {
			//System.out.println("Ayant droit : "+ToStringBuilder.reflectionToString(object.getBeneficiaire().getAyantDroit(),ToStringStyle.MULTI_LINE_STYLE));
			validationPolicy.validateDateNaissance(object.getBeneficiaire().getAyantDroit().getDateNaissance());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="la profession du declarant n'est pas valide",groups=Client.class)
	public boolean isValidProfessionDeclarant(){
		if(object.getBeneficiaire().getAyantDroit() instanceof AgentEtat)
			if( agentEtatDao.exist(((AgentEtat)object.getBeneficiaire().getAyantDroit()).getId()) )
					return true;
		
		try {
			validationPolicy.validateNotNull(object.getBeneficiaire().getAyantDroit().getProfession());
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	@AssertTrue(message="la nationalite du declarant n'est pas valide",groups=Client.class)
	public boolean isValidNationaliteDeclarant(){
		try {
			validationPolicy.validateNotNull(object.getBeneficiaire().getAyantDroit().getNationalite());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="le matricule du défunt n'est pas valide",groups=Client.class)
	public boolean isValidMatriculeDefunt(){
		try {
			validationPolicy.validateMatricule(object.getBeneficiaire().getType(),object.getBeneficiaire().getMatricule());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@AssertTrue(message="le date de naissance du défunt n'est pas valide",groups=Client.class)
	public boolean isValidDateNaissanceDefunt(){
		try {
			validationPolicy.validateDateNaissance(object.getBeneficiaire().getDateNaissance());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
		
	@AssertTrue(message="la profession du défunt n'est pas valide",groups=Client.class)
	public boolean isValidProfessionDefunt(){
		try {
			validationPolicy.validateNotNull(object.getBeneficiaire().getProfession());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**/
	
	@AssertTrue(message="la date de décès n'est pas valide",groups=Client.class)
	public boolean isValidDateDeces(){
		try {
			validationPolicy.validateDateDeces(object.getBeneficiaire().getDateNaissance(),object.getDateDeces());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*
	@AssertTrue(message="la localité de décès n'est pas valide",groups=Client.class)
	public boolean isValidLocaliteDeces(){
		try {
			validationPolicy.validateNotNull(object.getBeneficiaire().getProfession());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
*/
	
	/*
	@AssertTrue(message="la localite de transfert n'est pas valide",groups=Client.class)
	public boolean isValidLocaliteTransfert(){
		try {
			validationPolicy.validateNotNull(object.getDeplacement());
			return true;
		} catch (Exception e) {
			return false;
		}
	}*/
	
}
