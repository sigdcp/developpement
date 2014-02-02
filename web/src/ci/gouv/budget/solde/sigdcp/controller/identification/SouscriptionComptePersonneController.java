package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

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
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceValidationUtils;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ObjectValidator;
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
	@Inject private ServiceValidationUtils validationUtils;
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
        if(inscriptionAgentEtat)
        	title = "Formulaire de souscription";
        
        demandeurDto = new IdentitePersonneDTO(isCreate(),entity.getPersonneDemandeur(),inscriptionAgentEtat);
        
        //pieceIdentite.setModel(new PieceJustificativeAFournir(null,Boolean.FALSE, 3, 2, new TypePieceJustificative("cni", "CNI")));
        demandeurDto.getInfosSouscriptionComptePersonne().getPersonne().setPieceIdentiteType(PieceIdentiteType.CNI);
        demandeurDto.getInfosSouscriptionComptePersonne().getPersonne().setNationalite(localiteService.findById(Code.LOCALITE_COTE_DIVOIRE));
        demandeurDto.getInfosSouscriptionComptePersonne().setType(genericService.findByClass(TypeAgentEtat.class, String.class,Code.TYPE_AGENT_ETAT_FONCTIONNAIRE));
        
        defaultSubmitCommand.getObjectValidators().add(new ObjectValidator<SouscriptionComptePersonne>(entity, validator));
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
	protected SouscriptionComptePersonne createEntityInstance() {
		return new SouscriptionComptePersonne();
	}
    	
	/* Validation */
	
	public void validateConfirmationMotPasse(FacesContext facesContext,UIComponent uiComponent,Object value){
		String p1 = (String) value,p2 = (String) ((UIInput)uiComponent.getAttributes().get("password")).getValue();
		if(StringUtils.isEmpty(p1) || StringUtils.isEmpty(p2))
			return;// Let required="true" do its job.
		if (!p1.equals(p2))
			validationException((UIInput) uiComponent,"La confirmation doit correspondre au mot de passe.");
	}
	
	public void validateMatricule(FacesContext facesContext,UIComponent uiComponent,Object value){
		String matricule = (String) value;
		TypeAgentEtat typeAgentEtat = (TypeAgentEtat) ((UIInput)uiComponent.getAttributes().get("typePersonne")).getValue();
		if(StringUtils.isEmpty(matricule) || typeAgentEtat==null)
			return;// Let required="true" do its job.
		try {
			validationUtils.validateMatricule(typeAgentEtat, matricule);
		} catch (Exception e) {
			
		}
			
	}
	
	public void validateDateNaissance(FacesContext facesContext,UIComponent uiComponent,Object value){
		Date dateNaissance = (Date) value;
		if(dateNaissance==null)
			return;// Let required="true" do its job.
		try {
			validationUtils.validateDateNaissance(dateNaissance);
		} catch (Exception e) {
			validationException((UIInput) uiComponent,e.getMessage());
		}
	}
	
	@AssertTrue(message="Les mots de passe ne correspondent pas",groups=Client.class)
	public boolean isPasswordsMatch(){
		return entity.getPassword().equals(confirmationMotPasse);
	}

}
