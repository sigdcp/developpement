package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.TraitementService;

@Named @ViewScoped
public class ConsulterDemandeController extends AbstractDossierUIController<Dossier, AbstractDossierService<Dossier>> implements Serializable {

	private static final long serialVersionUID = 1027310826427829321L;
	
	@Inject private DossierService dossierService;
	@Inject private TraitementService traitementService;
	
	/*
	 * Infos sur le dossier selectionné
	 */

	@Getter private List<Traitement> traitements;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Consultation des demandes";
		traitements = new LinkedList<>(traitementService.findByDossier(entity));
		
		defaultSubmitCommand.setRendered(Boolean.FALSE);
	}
	
	@Override
	protected AbstractDossierService<Dossier> getDossierService() {
		return dossierService;
	}
	
	public Statut getStatutCourant(){
		return traitements.isEmpty()?null: traitements.get(traitements.size()-1).getStatut();
	}
	
}
