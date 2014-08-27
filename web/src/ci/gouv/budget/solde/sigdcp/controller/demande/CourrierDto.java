package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Courrier;

@Getter @Setter
public class CourrierDto implements Serializable {

	private static final long serialVersionUID = 6843082110404739576L;

	private Courrier courrier;
	private Boolean showCourrier=Boolean.FALSE,courrierEditable=Boolean.FALSE;
	
	public CourrierDto(Courrier courrier) {
		super();
		this.courrier = courrier;
	}
	
	
}
