package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;

@Getter @AllArgsConstructor
public class PieceProduiteDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3571075679263115724L;
	
	private Traitement traitement;
	
	
	
}
