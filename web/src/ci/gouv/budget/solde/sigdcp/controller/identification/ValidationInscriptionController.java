package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.flow.AbstractUIFlowController;
import ci.gouv.budget.solde.sigdcp.controller.flow.FlowDefinitions;
import ci.gouv.budget.solde.sigdcp.model.identification.Inscription;
import ci.gouv.budget.solde.sigdcp.service.identification.InscriptionService;
/*
@Named @FlowScoped(value=FlowDefinitions.FLOW_VALIDATION_INSCRIPTION_ID)
public class ValidationInscriptionController extends AbstractUIFlowController implements Serializable {

	private static final long serialVersionUID = 6591392098578555259L;
	
	public static final String FLOW_VALIDATION_INSCRIPTION_ID = "validationinscription";
	
	@Inject private InscriptionService inscriptionService;
	
	@Getter @Setter protected List<Inscription> inscriptions; 
	@Getter @Setter protected List<Inscription> inscriptionsSelectionnes;
	@Setter @Getter protected Inscription inscriptionSelectionne;
	
	@Getter @Setter
	private Boolean accepte;
	
	protected void postConstruct(){
		super.postConstruct();
		inscriptions = new LinkedList<Inscription>(inscriptionService.findInscriptionsAValider());
	}
	
	public String valider(){
		if(Boolean.TRUE.equals(accepte))
			inscriptionService.accepterInscription(inscriptionsSelectionnes);
		else
			inscriptionService.rejeterInscription(inscriptionsSelectionnes);
		return "succes";
	}
	
	public String accepter(){
		if(validInputs()){
			accepte = Boolean.TRUE;
			return FlowDefinitions.FLOW_VALIDATION_INSCRIPTION_CONFIRMATION_ID;
		}
		return null;
	}
	
	public String rejeter(){
		if(validInputs()){
			accepte = Boolean.FALSE;
			return FlowDefinitions.FLOW_VALIDATION_INSCRIPTION_CONFIRMATION_ID;
		}
		return null;
	}
	
	private Boolean validInputs(){
		if( inscriptionsSelectionnes==null || inscriptionsSelectionnes.isEmpty() ){
			addMessageError("Selectionnez au moins une inscription");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	

}*/
