package ci.gouv.budget.solde.sigdcp.controller.dossier.validation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.demande.AbstractFaireDemandeController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Named @ViewScoped
public class EnregistrerDecisionRemboursementController extends AbstractFaireDemandeController<Dossier,DossierService>  implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject private DossierService dossierService;
	
	/*
	 * Dto
	 */
		
	@Override
	protected void initialisation() {
		crudType=CRUDType.UPDATE;
		super.initialisation();
		title = "Décision de remboursement du dossier N°"+entity.getId();
		internalCode = "FS_REMBOURSEMENT_09_Ecran_01";
		defaultSubmitCommand.setValue(text("bouton.enregistrer"));
	}

	@Override
	protected DossierService getDossierService() {
		return dossierService;
	}
	
	
}
