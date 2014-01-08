package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.time.DateFormatUtils;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionDTO;

@Named @ViewScoped 
public class DemandeCotationBilletAvionMissionController extends AbstractAcquisitionTitreTransportMissionController<DemandeCotationMission> {

	private static final long serialVersionUID = -2494512246140789877L;
	
	/*
	 * Services
	 */ 
	
	/* 
	 * Dtos 
	 */
	@Getter @Setter private List<Prestataire> agenceVoyageSelectionnees;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = messageManager.getTextService().find("formulaire.cotationbilletavion.titre");
		defaultSubmitCommand.setNotificationMessageId("notification.demande.cotation");
	}
	
	protected String buildParticipantMessage(DossierMissionDTO dossierMissionDTO){
		return String.format("%s billet allez retour %s - %s en %s pour un départ le %s", "1", dossierMissionDTO.getDossier().getDeplacement().getLocaliteDepart(),
				dossierMissionDTO.getDossier().getDeplacement().getLocaliteArrivee(),dossierMissionDTO.getClasseVoyage(),
				DateFormatUtils.format(dossierMissionDTO.getDossier().getDeplacement().getDateDepart(),constantResources.getDateTimePattern()));
	}
	
	protected String messageFormat(){
		return  "Objet : Information tarifaire"+nl(2)
		+"Madame, Monsieur,"+nl(1)
		+ "Nous souhaitons obtenir une cotation de billet d'avion."+nl(2)
		+ "%s"+nl(2)
		+ "nous vous prions d'agréer, Madame, Monsieur, l'expression de nos cordiales salutations."
		;
	}
	
}
