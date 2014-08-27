package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

@Deprecated
public class ConsulterFeuilleDeplacementListeController extends AbstractEntityListUIController<PieceProduite> implements Serializable {

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
		title = "Ecran de consultation des feuilles de déplacement";
		internalCode = "FS_MHCI_02_Ecran_03";
		
		closeCommand.setRendered(Boolean.FALSE);
		defaultSubmitCommand.setRendered(Boolean.FALSE);
	}
	



	@Override
	public String href(PieceProduite entity) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected List<PieceProduite> load() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
