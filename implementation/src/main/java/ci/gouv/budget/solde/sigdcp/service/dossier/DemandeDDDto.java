package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;

@Getter @Setter @AllArgsConstructor
public class DemandeDDDto implements Serializable {
	
	private static final long serialVersionUID = -6010016517556367533L;
	private DossierDD dossier;
	private Collection<PieceJustificative> pieceJustificatives;
	private Boolean marie,nombreEnfant;

}
