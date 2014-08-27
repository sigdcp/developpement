package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;

@Deprecated
public class ReceptionnerFeuilleDeplacementListeController extends AbstractEntityListUIController<PieceProduite> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	
	
	/*
	 * Dto
	 */
	
	
	/*
	 * Paramètres url
	 */
	
	@Override
	public void initialisation() {
		super.initialisation();
		title = "Ecran de réception d'une feuille de déplacement";
		internalCode = "FS_MHCI_02_Ecran_01";
		
		closeCommand.setRendered(Boolean.FALSE);
		defaultSubmitCommand.setRendered(Boolean.FALSE);
	}
	
	



	@Override
	protected List<PieceProduite> load() {
		return null;
	}



	@Override
	public String href(PieceProduite entity) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
