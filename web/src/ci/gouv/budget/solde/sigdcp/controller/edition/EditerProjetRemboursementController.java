package ci.gouv.budget.solde.sigdcp.controller.edition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;

@Named @ViewScoped
public class EditerProjetRemboursementController extends AbstractEditionController<Dossier> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Inject private DossierService dossierService;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Editer projet de remboursement";
		listTitle="Liste des demandes";
		defaultSubmitCommand.setRendered(false);
	}
	
	@Override
	protected List<Dossier> load() {
		return new ArrayList<>(dossierService.findEditerProjetRemboursement());
	}
	
	
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
	
	@Override
	protected PieceProduite piece(Dossier dossier) {
		return dossier.traitementPieceProduite(Code.TYPE_PIECE_PRODUITE_DECISION_REMB).getPieceProduite();
	}
		
	
	
}
