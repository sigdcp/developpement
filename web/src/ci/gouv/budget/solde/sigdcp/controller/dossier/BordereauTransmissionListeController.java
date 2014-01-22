package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;

@Named @ViewScoped //@Log
public class BordereauTransmissionListeController extends AbstractTraitementListeController<BordereauTransmissionDto> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	@Override
	protected void initialisation() {
		super.initialisation();
		title ="Liste des bordereaux";
		//internalCode = "FS_REC_03_Ecran_01";
	}
	
	@Override
	protected List<BordereauTransmissionDto> load() {
		List<BordereauTransmissionDto> l = new LinkedList<>();
		for(Traitement p :  traitementService.findByPieceProduiteTypeId(Code.TYPE_PIECE_PRODUITE_BT))
			l.add(new BordereauTransmissionDto(p,3,"BL",200000));
		return l;
	}
	
	@Override
	protected String detailsOutcome() {
		return "btDialog";
	}
	
}
