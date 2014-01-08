package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;

@ManagedBean @RequestScoped
public class DemandeListeController extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject private DossierDDService dossierService;
	
	/*
	 * Dto
	 */
	@Getter private List<DossierDD> list;
	
	/*
	 * Paramètres url
	 */
	@Getter @Setter private String nextViewOutcome;

	@Override
	protected void initialisation() {
		super.initialisation();
		list = dossierService.findAll(); 
	}
	
	public String href(Dossier dossier){
		return navigationManager.addQueryParameters(nextViewOutcome, 
				new String[]{webConstantResources.getRequestParamDossier(),dossier.getNumero(),webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudUpdate()});
	}
	
	
}
