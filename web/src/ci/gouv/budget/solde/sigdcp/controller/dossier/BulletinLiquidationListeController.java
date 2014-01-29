package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;

@Named @ViewScoped //@Log
public class BulletinLiquidationListeController extends AbstractTraitementListeController<TraitementDto> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	@Override
	protected void initialisation() {
		super.initialisation();
		title ="Liste des bulletins de liquidation";
		//internalCode = "FS_REC_03_Ecran_01";
	}
	
	@Override
	protected List<TraitementDto> load() {
		List<TraitementDto> l = new LinkedList<>();
		for(Traitement trt : traitementService.findByPieceProduiteTypeId(Code.TYPE_PIECE_PRODUITE_BL))
			l.add(new TraitementDto(trt));
		return l;
	}
	
	@Override
	protected String detailsOutcome() {
		return "blDialog";
	}
	
}
