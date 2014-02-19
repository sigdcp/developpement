package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.WizardHelper;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.service.DynamicEnumerationService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionDTO;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;

public abstract class AbstractAcquisitionTitreTransportMissionController<ENTITY extends AbstractModel<?>> extends AbstractEntityFormUIController<ENTITY> {

	private static final long serialVersionUID = -2494512246140789877L;
	
	/*
	 * Services
	 */ 
	@Inject private DossierMissionService dossierMissionService;
	//@Inject private CalendrierMissionService missionService;
	@Inject private DynamicEnumerationService dynamicEnumerationService;
	
	/* 
	 * Dtos 
	 */
	@Getter @Setter private List<Mission> missionsSelectionnees,missionsRecherchesDisponible,missionsRechercheSelectionnees;
	@Getter private List<DossierMissionDTO> dossierDtos = new ArrayList<>();
	@Getter private String message;
	
	@Override
	protected void initialisation() {
		super.initialisation();	
		//missionsSelectionnees = missionService.findAll();
		List<GroupeMission> gm = new ArrayList<>(dynamicEnumerationService.findAllByClass(GroupeMission.class));
		List<TypeClasseVoyage> tcv = new ArrayList<>(dynamicEnumerationService.findAllByClass(TypeClasseVoyage.class));
		for(DossierMission dossier : dossierMissionService.findAll()){
			dossierDtos.add(new DossierMissionDTO(dossier,gm.get(0), tcv.get(0)));
		}
		//missionsRecherchesDisponible = missionService.findAll();
		
		wizardHelper = new WizardHelper<ENTITY>(this,"definition","confirmation"){
			private static final long serialVersionUID = -2560968105025120145L;
			@Override
			protected void move(Integer stepCount) {
				super.move(stepCount);
				showFieldRequired = !getSubmitAction().isRendered();
				if(getCurrentStepIndex() == 1){
					message = buildMessage();
				}
			}
		};
		
	}
	
	public void ajouterMission(){
		
	}
	
	protected String buildMessage(){
		StringBuilder stringBuilder = new StringBuilder();
		for(DossierMissionDTO dossierMissionDTO : dossierDtos)
			stringBuilder.append(buildParticipantMessage(dossierMissionDTO)+nl(1));
		return String.format(messageFormat(), stringBuilder.toString());
	}
	
	protected abstract String buildParticipantMessage(DossierMissionDTO dossierMissionDTO);
	
	protected abstract String messageFormat();
	
	@Override
	public boolean isCreate() {
		return Boolean.TRUE;
	}
	
	protected static String nl(int c){
		return StringUtils.repeat("<br/>", c);
	}
	
	
}
