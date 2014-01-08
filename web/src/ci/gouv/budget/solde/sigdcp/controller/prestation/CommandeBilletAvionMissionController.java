package ci.gouv.budget.solde.sigdcp.controller.prestation;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.time.DateFormatUtils;

import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.Commande;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionDTO;

@Named @ViewScoped 
public class CommandeBilletAvionMissionController extends AbstractAcquisitionTitreTransportMissionController<Commande> {

	private static final long serialVersionUID = -2494512246140789877L;
	
	/*
	 * Services
	 */ 
	
	/* 
	 * Dtos 
	 */
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = messageManager.getTextService().find("formulaire.commandebilletavion");
		defaultSubmitCommand.setNotificationMessageId("notification.commandebilletavion");
	}
	
	protected String buildParticipantMessage(DossierMissionDTO dossierMissionDTO){
		DossierMission dossier = dossierMissionDTO.getDossier();
		Deplacement deplacement = dossierMissionDTO.getDossier().getDeplacement();
		return String.format(
				"Nom et prénoms : %s"+nl(1)
				+"Email : %s"+nl(1)
				+ "Téléphone : %s"+nl(1)
				+ "Classe : %s"+nl(1)
				+ "Trajet : %s"+nl(1)
				+ "Départ le %s"+nl(1)+"<hr/>",dossier.getBeneficiaire().getNomPrenoms(),dossier.getBeneficiaire().getContact().getEmail(),dossier.getBeneficiaire().getContact().getTelephone(),
				dossierMissionDTO.getClasseVoyage(),deplacement.getLocaliteDepart()+" - "+deplacement.getLocaliteArrivee()+" - "+deplacement.getLocaliteDepart(),
				DateFormatUtils.format(deplacement.getDateDepart(),constantResources.getDateTimePattern()));
	}
	
	protected String messageFormat(){
		return  "Objet : Commande"+nl(2)
		+"Madame, Monsieur,"+nl(1)
		+ "Nous commandons les billets suivant : "+nl(2)
		+ "%s"
		;
	}
	
}
