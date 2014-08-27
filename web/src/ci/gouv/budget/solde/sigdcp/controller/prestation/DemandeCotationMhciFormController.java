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
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.service.calendrier.MissionExecuteeService;
import ci.gouv.budget.solde.sigdcp.service.prestation.DemandeCotationMissionService;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireService;

@Getter @Setter @Named @ViewScoped
public class DemandeCotationMhciFormController extends AbstractEntityFormUIController<DemandeCotationMission> implements Serializable {

	private static final long serialVersionUID = -6419775515441198968L;
	
	
	@Inject private MissionExecuteeService missionExecuteeService;
	@Inject private DemandeCotationMissionService demandeCotationMissionService;
	@Inject private PrestataireService prestataireService;
	
	private List<Prestataire> agenceVoyages=new ArrayList<>();
	 private List<Prestataire> prestataires; 
	private Fonction fonction;
	private Profession profession;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		entity = demandeCotationMissionService.nouveau(missionExecuteeService.findById(Long.parseLong(requestParameter(webConstantResources.getRequestParamMission()))));
		
		title="Demande de cotation de la mission N° "+entity.getMission().getId();
		
		defaultSubmitCommand.setRendered(true);

		entity.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);		
		prestataires=((List<Prestataire>) prestataireService.findByType(Code.TYPE_PRESTATAIRE_AV));
		
		defaultSubmitCommand.onSuccessGoTo(navigationManager.url("mission_mhci_a_coter",false), "", null);
		
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		entity.setPrestataires(agenceVoyages);
		demandeCotationMissionService.valider(Arrays.asList(entity));
	}
	
	
}
