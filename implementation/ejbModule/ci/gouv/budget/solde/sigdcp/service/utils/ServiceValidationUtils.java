package ci.gouv.budget.solde.sigdcp.service.utils;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import ci.gouv.budget.solde.sigdcp.service.resources.ConstantResources;

@Singleton
@Deprecated
public class ServiceValidationUtils {

	static final String MATRICULE_FORMAT = "\\d\\d\\d\\d\\d\\d[a-zA-Z]";
	
	private @Inject ConstantResources constantResources;
	
	/*
	 * fonctions de validation de dates
	 */
	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return true if date1 < date2
	 */
	public Boolean isOrdonne(Date date1,Date date2){
		if(date1==null || date2==null) return true;// TODO remove , just for test we put it
		return date1.before(date2);
	}
	
	public Boolean isMatrciuleFormatCorrect(String matricule){
		return matricule.matches(constantResources.getMatriculePattern());
	}
	
	public Boolean isEmailFormatCorrect(String email){
		return true;
	}
	
	public Boolean isMajeur(Date dateNaissance){
		return dateNaissance.before(constantResources.getDateNaissanceMinimum());
	}
	
	public static void main(String[] args) {
		ServiceValidationUtils sv = new ServiceValidationUtils();
		String[] ms = {"123456a","1234567","111111111a"};
		for(String s : ms)
			System.out.println(s+" : "+sv.isMatrciuleFormatCorrect(s));
	}
	
	
	
}
