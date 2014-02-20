package ci.gouv.budget.solde.sigdcp.model.template.etat;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class FeuilleDeplacementEtat implements Serializable {

	private static final long serialVersionUID = -5973562425199996115L;

	private String numero;
	
	private DossierDD dossier;
	
	private String ordreservice;
	private String dateodreservice;
	
	private String groupe;
	
	private String indice;
	private String compagnons;

}
