package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import lombok.Getter;
import lombok.Setter;

@SessionScoped @Getter @Setter
public class AuthentificationInfos implements Serializable {

	private static final long serialVersionUID = -660836872078538822L;

	private CompteUtilisateur compteUtilisateur;
	private Integer nombreTentative;
	private Long timestampDebut;
	
	public AuthentificationInfos() {
		clear();
	}
	
	public void clear(){
		nombreTentative = 0;
		timestampDebut = null;
	}
	
	public Party getUtilisateur(){
		return compteUtilisateur.getUtilisateur();
	}
	
}
