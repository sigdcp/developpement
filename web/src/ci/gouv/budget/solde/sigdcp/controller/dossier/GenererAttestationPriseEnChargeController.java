package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.dossier.DossierListeController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.service.dossier.NatureDeplacementService;

@Named @ViewScoped
public class GenererAttestationPriseEnChargeController extends DossierListeController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject private NatureDeplacementService natureDeplacementService;
	
	/*
	 * Dto
	 */
		
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Ecran de Génération des attestation de prise en charge";
		internalCode = "FS_TRANSIT_06_Ecran_01";
		defaultSubmitCommand.setValue(text("bouton.generer"));
		detailsCommand.setValue(text("bouton.apercu"));
		
		rechercherCommande = null;
	}
	
	@Override
	protected List<Dossier> load() {
		return new LinkedList<>(dossierService.findByNatureDeplacementAndStatut(natureDeplacementService.findById(Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE), statut));
	}
	
	@Override
	protected String detailsOutcome() {
		return "apcDialog";
	}

	
	
	
	
}
