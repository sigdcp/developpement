package ci.gouv.budget.solde.sigdcp.controller.edition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierTransitService;

@Named @ViewScoped
public class EditerAttestationPriseEnChargeController extends AbstractEditionController<DossierTransit> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Inject private DossierTransitService dossierTransitService;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Attestation de prise en charge";
		listTitle="Liste des demandes";
	}
	
	@Override
	protected List<DossierTransit> load() {
		return new ArrayList<>(dossierTransitService.findEditerAttestationPriseEnCharge());
	}
	
	
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
	
	@Override
	protected PieceProduite piece(DossierTransit dossier) {
		return dossier.traitementPieceProduite(Code.TYPE_PIECE_PRODUITE_APEC).getPieceProduite();
	}
		
	
	
}
