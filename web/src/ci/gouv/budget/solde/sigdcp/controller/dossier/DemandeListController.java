package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.AbstractUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;

@Named 
public class DemandeListController extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = 1027310826427829321L;
	
	@Inject private DossierDDService dossierService;
	
	@Getter private List<DossierDD> dossiers;
	
	@PostConstruct
	public void postConstructConsulterDemandeController() {
		title = "Consultation des demandes";
		dossiers = dossierService.findAll();
	}

}
