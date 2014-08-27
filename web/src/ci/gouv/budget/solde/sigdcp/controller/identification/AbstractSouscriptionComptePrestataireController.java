package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePrestataire;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.service.identification.SouscriptionComptePrestataireService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.SouscriptionComptePrestataireValidator;

public abstract class AbstractSouscriptionComptePrestataireController extends AbstractEntityFormUIController<SouscriptionComptePrestataire> implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	/*
	 * Services
	 */
	@Inject @Getter private SouscriptionComptePrestataireService souscriptionService; 
	 
	/*
	 * Validation
	 */
	@Inject private SouscriptionComptePrestataireValidator validator;
	
	/*
	 * DTOs
	 */

	@Getter protected IdentitePrestataireDTO demandeurDto;
	@Valid @Getter @Setter protected ReponseSecrete reponseSecrete;
	@NotNull(groups=Client.class) @Getter @Setter  protected String confirmationMotPasse;
	@Getter protected Boolean inscriptionPrestataire=Boolean.TRUE;
	
	
	@Override
	protected void initialisation() {
		crudType=CRUDType.CREATE;
		super.initialisation();
		//instructions=text("souscription.compte.personne.reserve.a");
		entity = new SouscriptionComptePrestataire();
        if(inscriptionPrestataire)
        	title = "Formulaire de souscription ";
       
       demandeurDto = new IdentitePrestataireDTO(isCreate(),entity.getPrestataireDemandeur());
       
        
        
        defaultSubmitCommand.setValue(text("bouton.souscrirecompte"));
        defaultSubmitCommand.setNotificationMessageId("notification.compte.ouvert");

        reponseSecrete = new ReponseSecrete();
        entity.getReponseSecretes().add(reponseSecrete);
        
    }
		
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		souscriptionService.souscrire(entity);
	}
	
	@Override
	protected AbstractValidator<SouscriptionComptePrestataire> validator() {
		return validator;
	}
	

	
}
