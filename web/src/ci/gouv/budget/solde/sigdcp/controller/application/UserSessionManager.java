package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import lombok.Getter;
import lombok.Setter;

@Named @SessionScoped 
public class UserSessionManager implements Serializable{

	private static final long serialVersionUID = 258649685790992448L;

	@Getter @Setter
	private CompteUtilisateur compte;
	
	public Boolean isLoggedIn(){
		return compte!=null;
	}
	
}
