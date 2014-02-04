package ci.gouv.budget.solde.sigdcp.service.utils;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.service.resources.ServiceConstantResources;

@Singleton
public class ServiceValidationUtils {
	
	private @Inject ServiceConstantResources constantResources;
	
	/*
	 * Matricule
	 */
	public boolean isMatriculeFormatCorrect(TypeAgentEtat typeAgentEtat,String matricule){
		if(Code.TYPE_AGENT_ETAT_GENDARME.equals(typeAgentEtat.getCode()))
			return matricule.matches(constantResources.getMatriculeGendarmePattern());
		return matricule.matches(constantResources.getMatriculeFonctionnairePattern());
	}
	
	public void validateMatricule(TypeAgentEtat typeAgentEtat,String matricule) throws Exception{
		if(!isMatriculeFormatCorrect(typeAgentEtat, matricule))
			throw new Exception("incorrect");
	}
	
	/*
	 * Date de naissance
	 */
	
	public Boolean isMajeur(Date dateNaissance){
		return dateNaissance.before(constantResources.getDateNaissanceMinimum());
	}
	
	public void validateDateNaissance(Date dateNaissance) throws Exception{
		if(!isMajeur(dateNaissance))
			throw new Exception("Au moins 18 ans");
	}
	
	public void validateTokenDeverouillage(String token) throws Exception{
		
	}
	
	public void validatePassword(String password) throws Exception{
		if(password==null ||password.length()<8)
			throw new Exception("Au moins 8 caractères");
	}
	
	/*
	public static void main(String[] args) {
		ServiceValidationUtils sv = new ServiceValidationUtils();
		String[] ms = {"123456a","1234567","111111111a"};
		for(String s : ms)
			System.out.println(s+" : "+sv.isMatrciuleFormatCorrect(s));
	}
	*/
	
	
}
