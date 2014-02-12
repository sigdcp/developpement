package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.TraitementService;

@Named @RequestScoped
public class DemandeListeController extends AbstractEntityListUIController<Traitement> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject private TraitementService traitementService;
	
	/*
	 * Dto
	 */
	
	
	/*
	 * Paramètres url
	 */

	@Override
	protected void initialisation() {
		super.initialisation();
		title="Liste des demandes";
		//list = traitementService.fin findAll(); 
	}
	
	public String href(Dossier dossier){//System.out.println("DemandeListeController.href() "+nextViewOutcome);
		return navigationHelper.addQueryParameters(nextViewOutcome, 
				new String[]{webConstantResources.getRequestParamDossier(),dossier.getNumero(),webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudRead()});
	}

	@Override
	protected List<Traitement> load() {
		return null;//new LinkedList<>(dossierService.findAll());
	}
	
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
}
