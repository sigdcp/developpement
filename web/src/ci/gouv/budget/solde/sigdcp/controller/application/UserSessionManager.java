package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.utils.TextService;

@Named @SessionScoped 
public class UserSessionManager implements Serializable{

	private static final long serialVersionUID = 258649685790992448L;

	/*
	 * Services
	 */
	@Inject transient private TextService textService;
	
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
	
	public String getUserInfosLine(){
		if(compteUtilisateur==null)
			return null;
		if(compteUtilisateur.getUtilisateur() instanceof AgentEtat)
			return textService.find("userinfos.top.agentetat",new Object[]{((AgentEtat)compteUtilisateur.getUtilisateur()).getMatricule(),
					((AgentEtat)compteUtilisateur.getUtilisateur()).getNomPrenoms()});
		return null;
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
