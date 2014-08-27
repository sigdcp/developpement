package ci.gouv.budget.solde.sigdcp.controller.mhcis;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Deprecated
public class SuivreExecutionCalendrierMissionController extends CalendrierMissionController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	

	@Override
	public void initialisation() {
		super.initialisation();
		title = "Ecran de suivi de l'exécution des calendriers de missions";
		internalCode = "FS_MHCI_04_Ecran_01";
		
		enableSearch();
	}

	
}
