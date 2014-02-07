package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

@Named @SessionScoped 
public class UserSessionManager implements Serializable{

	private static final long serialVersionUID = 258649685790992448L;

	/*
	 * Services
	 */
	
	@Getter @Setter
	private CompteUtilisateur compteUtilisateur; 
	
	public Boolean isLoggedIn(){
		return StringUtils.isNotEmpty(Faces.getRemoteUser());
	}
	
	/**
	 * The connected user
	 * @return
	 */
	@Named @SessionScoped @User
	public Personne getUser(){
		return (Personne) compteUtilisateur.getUtilisateur();
	}

	/*
	public void longRun(){
		try {O
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/
	/*
	public void onNewOrder(@Observes OrderBy event) {
		System.out.println("Event captured - " + event);
	}*/
	/*
	 public void onNewOrder(@Observes String event) {
		 
	     System.out.println("UserSessionManager.onNewOrder() : "+event);
	  }*/
	
}
