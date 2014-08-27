package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne.PieceIdentiteType;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.service.geographie.LocaliteService;
import ci.gouv.budget.solde.sigdcp.service.identification.SouscriptionComptePersonneService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.SouscriptionComptePersonneValidator;

public abstract class AbstractSouscriptionComptePersonneController extends AbstractEntityFormUIController<SouscriptionComptePersonne> implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	/*
	 * Services
	 */
	@Inject @Getter private SouscriptionComptePersonneService souscriptionService;
	@Inject private LocaliteService localiteService;
	
	/*
	 * Validation
	 */
	@Inject private SouscriptionComptePersonneValidator validator;
	
	/*
	 * DTOs
	 */

	@Getter protected IdentitePersonneDTO demandeurDto;
	@Valid @Getter @Setter protected ReponseSecrete reponseSecrete;
	@NotNull(groups=Client.class) @Getter @Setter  protected String confirmationMotPasse;
	@Getter protected Boolean inscriptionAgentEtat=Boolean.TRUE;
	
	
	@Override
	protected void initialisation() {
		crudType=CRUDType.CREATE;
		super.initialisation();
		//instructions=text("souscription.compte.personne.reserve.a");
		entity = new SouscriptionComptePersonne();
        if(inscriptionAgentEtat)
        	title = "Formulaire de souscription ";
       
        demandeurDto = new IdentitePersonneDTO(isCreate(),entity.getPersonneDemandeur(),inscriptionAgentEtat);
        demandeurDto.getInfosSouscriptionComptePersonne().getPersonne().setPieceIdentiteType(PieceIdentiteType.CNI);
        demandeurDto.getInfosSouscriptionComptePersonne().getPersonne().setNationalite(localiteService.findById(Code.LOCALITE_COTE_DIVOIRE));
        demandeurDto.getInfosSouscriptionComptePersonne().setType(genericService.findByClass(TypeAgentEtat.class, String.class,Code.TYPE_AGENT_ETAT_FONCTIONNAIRE));
        
        defaultSubmitCommand.setValue(text("bouton.souscrirecompte"));
        defaultSubmitCommand.setNotificationMessageId("notification.compte.ouvert");

        defaultSubmitCommand.setSuccessOutcome(navigationManager.url(NavigationManager.OUTCOME_SUCCESS_VIEW,new Object[]{
			webConstantResources.getRequestParamMessageId(),Code.TYPE_AGENT_ETAT_GENDARME.equals(entity.getPersonneDemandeur().getType())?"notification.souscription.enregistree":"notification.compte.ouvert",
			webConstantResources.getRequestParamUrl(),navigationManager.url("index",false),
			webConstantResources.getRequestParamPageTemplate(),webConstantResources.getRequestParamPageTemplateDialog()
        }));
        
        //defaultSubmitCommand.setImmediate(true);
        reponseSecrete = new ReponseSecrete();
        entity.getReponseSecretes().add(reponseSecrete);
        
    }
		
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		souscriptionService.souscrire(entity);
	}
	
	@Override
	protected AbstractValidator<SouscriptionComptePersonne> validator() {
		return validator;
	}
	

	
}
