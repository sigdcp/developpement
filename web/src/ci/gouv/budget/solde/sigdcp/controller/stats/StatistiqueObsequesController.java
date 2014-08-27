package ci.gouv.budget.solde.sigdcp.controller.stats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.service.dossier.NatureDeplacementService;

@Named @ViewScoped
public class StatistiqueObsequesController extends AbstractStatistiqueNatureDeplacementController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	@Inject private NatureDeplacementService natureDeplacementService;

	@Override
	protected void initialisation() {
		
		super.initialisation();
		title="Statistique : Bilan des obsèques";	
		natureDeplacements = new ArrayList<>(natureDeplacementService.findByCategorieId(Code.CATEGORIE_DEPLACEMENT_OBSEQUE));
		
		if(natureDeplacements!=null && natureDeplacements.size()<2)
		natureDeplacementSelectionnees=Arrays.asList(natureDeplacements.get(0));
	}


	
	
}
