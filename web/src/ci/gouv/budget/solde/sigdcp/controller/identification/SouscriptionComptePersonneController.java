package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne.PieceIdentiteType;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.service.GenericService;
import ci.gouv.budget.solde.sigdcp.service.dossier.LocaliteService;
import ci.gouv.budget.solde.sigdcp.service.identification.SouscriptionComptePersonneService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.SouscriptionComptePersonneValidator;

@Named @ViewScoped
public class SouscriptionComptePersonneController extends AbstractEntityFormUIController<SouscriptionComptePersonne> implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	/*
	 * Services
	 */
	@Inject private SouscriptionComptePersonneService souscriptionService;
	@Inject private LocaliteService localiteService;
	@Inject private GenericService genericService;
	
	/*
	 * Validation
	 */
	@Inject private SouscriptionComptePersonneValidator validator;
	
	/*
	 * DTOs
	 */

	@Getter private IdentitePersonneDTO demandeurDto;
	@Valid @Getter @Setter private ReponseSecrete reponseSecrete;
	@NotNull(groups=Client.class) @Getter @Setter  private String confirmationMotPasse;
	@Getter private Boolean inscriptionAgentEtat=Boolean.TRUE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		instructions=text("souscription.compte.personne.reserve.a");
        if(inscriptionAgentEtat)
        	title = "Formulaire de souscription";
        
        demandeurDto = new IdentitePersonneDTO(isCreate(),entity.getPersonneDemandeur(),inscriptionAgentEtat);
        demandeurDto.getInfosSouscriptionComptePersonne().getPersonne().setPieceIdentiteType(PieceIdentiteType.CNI);
        demandeurDto.getInfosSouscriptionComptePersonne().getPersonne().setNationalite(localiteService.findById(Code.LOCALITE_COTE_DIVOIRE));
        demandeurDto.getInfosSouscriptionComptePersonne().setType(genericService.findByClass(TypeAgentEtat.class, String.class,Code.TYPE_AGENT_ETAT_FONCTIONNAIRE));
        
        defaultSubmitCommand.setValue(text("bouton.souscrirecompte"));
        defaultSubmitCommand.setNotificationMessageId("notification.compte.ouvert");
        defaultSubmitCommand.setSuccessOutcome("login");
        
        reponseSecrete = new ReponseSecrete();
        entity.getReponseSecretes().add(reponseSecrete);
        
        requiredEnabled=false;
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
