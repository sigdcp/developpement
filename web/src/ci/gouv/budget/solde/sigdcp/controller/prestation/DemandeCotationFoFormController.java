package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierObsequesService;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationObsequeService;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireService;

@Getter @Setter @Named @ViewScoped
public class DemandeCotationFoFormController extends AbstractEntityFormUIController<DemandeCotationObseque> implements Serializable {

	private static final long serialVersionUID = -6419775515441198968L;
	
	
	@Inject private DossierObsequesService dossierObsequesService;
	@Inject private DemandeCotationObsequeService demandeCotationObsequeService;
	@Inject private PrestataireService prestataireService;
	
	private List<Prestataire> pompeFunebres=new ArrayList<>();
	 private List<Prestataire> prestataires; 
	private Fonction fonction;
	private Profession profession;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		entity = demandeCotationObsequeService.nouveau(dossierObsequesService.findById(Long.parseLong(requestParameter(webConstantResources.getRequestParamDossier()))));
		
		title="Demande de cotation de la demande N° "+entity.getDossier().getId();
		
		defaultSubmitCommand.setRendered(true);

		entity.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);		
		prestataires=((List<Prestataire>) prestataireService.findByType(Code.TYPE_PRESTATAIRE_PF));
		
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		entity.setPrestataires(pompeFunebres);
		
		demandeCotationObsequeService.valider(Arrays.asList(entity));
	}
	
	
}
