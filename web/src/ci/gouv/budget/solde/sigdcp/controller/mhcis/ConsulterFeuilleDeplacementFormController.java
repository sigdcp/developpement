package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

@Deprecated
public class ConsulterFeuilleDeplacementFormController extends AbstractEntityFormUIController<PieceProduite> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	
	//@Inject private DossierMissionService dossierMissionService;
	/*
	 * Dto
	 */
	
	
	/*
	 * Paramètres url
	 */
	
	@Override
	public void initialisation() {
		super.initialisation();
		title = "Ecran de consultation détaillée d'une feuille de déplacement";
		internalCode = "FS_MHCI_02_Ecran_04";
		
		closeCommand.setValue(text("retouraliste"));
		defaultSubmitCommand.setRendered(Boolean.FALSE);
	}
	



	
	
	
}
