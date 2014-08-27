package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.InfosSouscriptionComptePrestataire;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;

public class IdentitePrestataireDTO implements Serializable {

	private static final long serialVersionUID = 1673292991759713069L;
	@Getter
	private InfosSouscriptionComptePrestataire infosSouscriptionComptePrestataire;
	
	@Getter @Setter
	private Boolean editable=true;
	
	@Getter @Setter
	private String tp;

	public IdentitePrestataireDTO(Boolean editable,InfosSouscriptionComptePrestataire infosSouscriptionComptePrestataire) {
		super();
		this.editable=editable;
		this.infosSouscriptionComptePrestataire = infosSouscriptionComptePrestataire;
	}

	public Prestataire getPrestataire(){
		return infosSouscriptionComptePrestataire.getPrestataire();
	}
	
	
	
}
