package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Date;
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
import ci.gouv.budget.solde.sigdcp.service.dossier.TraitementService;

@Named @ViewScoped
public class ConsulterDemandeController extends AbstractDossierEnregistrementUIController<Dossier, AbstractDossierService<Dossier>> implements Serializable {

	private static final long serialVersionUID = 1027310826427829321L;
	
	//@Inject private DossierDDService dossierService;
	@Inject private TraitementService traitementService;
	
	/*
	 * Infos sur le dossier selectionné
	 */
	@Getter private Statut statut;
	@Getter private Date dateSoumission = new Date(),dateValidation=new Date();
	@Getter private List<Traitement> traitements;
	
	@Override
	public void __firstPreRenderView__() {
		super.__firstPreRenderView__();
		title = "Consultation des demandes";
		traitements = new LinkedList<>(traitementService.findByDossier(entity));
		if(!traitements.isEmpty())
			statut = traitements.get(0).getStatut();
	}
	
	@Override
	protected AbstractDossierService<Dossier> getDossierService() {
		return null;
	}
	
}
