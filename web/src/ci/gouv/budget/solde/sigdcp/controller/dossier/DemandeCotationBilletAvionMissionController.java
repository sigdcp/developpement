package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.primefaces.event.FlowEvent;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.WizardHelper;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.service.DynamicEnumerationService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionDTO;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;
import ci.gouv.budget.solde.sigdcp.service.dossier.MissionService;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireService;

@Named @ViewScoped 
public class DemandeCotationBilletAvionMissionController extends AbstractEntityFormUIController<DemandeCotationMission> {

	private static final long serialVersionUID = -2494512246140789877L;
	
	/*
	 * Services
	 */ 
	@Inject private DossierMissionService dossierMissionService;
	@Inject private MissionService missionService;
	@Inject private DynamicEnumerationService dynamicEnumerationService;
	
	/* 
	 * Dtos 
	 */
	@Getter @Setter private List<Mission> missionsSelectionnees,missionsRecherchesDisponible,missionsRechercheSelectionnees;
	@Getter private List<DossierMissionDTO> dossierDtos = new ArrayList<>();
	@Getter @Setter private List<Prestataire> agenceVoyageSelectionnees;
	
	@Override
	public void __firstPreRenderView__() {
		super.__firstPreRenderView__();
		title = messageManager.getTextService().find("formulaire.cotationbilletavion.titre");
		missionsSelectionnees = missionService.findAll();
		List<GroupeMission> gm = new ArrayList<>(dynamicEnumerationService.findAllByClass(GroupeMission.class));
		List<TypeClasseVoyage> tcv = new ArrayList<>(dynamicEnumerationService.findAllByClass(TypeClasseVoyage.class));
		for(DossierMission dossier : dossierMissionService.findAll()){
			dossierDtos.add(new DossierMissionDTO(dossier,gm.get(0), tcv.get(0)));
		}
		missionsRecherchesDisponible = missionService.findAll();
		
		//defaultSubmitAction.setValue(text("boutton.valider"));
		//defaultSubmitAction.setType("button");
		//defaultSubmitAction.setOnclick("myWizard.next();");
		//defaultSubmitAction.setRendered(Boolean.FALSE);
		
		wizardHelper = new WizardHelper<DemandeCotationMission>(new String[]{"definition","confirmation"},defaultSubmitAction, entity, messageManager,constantResources);
		
	}
	
	public void ajouterMission(){
		
	}
	
	@Override
	public boolean isCreate() {
		return Boolean.TRUE;
	}
	
}
