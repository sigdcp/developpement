package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.Souscription;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;
import ci.gouv.budget.solde.sigdcp.service.identification.SouscriptionComptePersonneService;

@Named @ViewScoped
public class SouscriptionComptePersonneController extends AbstractEntityFormUIController<SouscriptionComptePersonne> implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	/*
	 * Services
	 */
	@Inject private SouscriptionComptePersonneService souscriptionService;
	
	
	/*
	 * DTOs
	 */

	@Getter private IdentitePersonneDTO demandeurDto;
	@Getter @Setter private ReponseSecrete reponseSecrete;
	@Getter @Setter private String confirmationMotPasse;
	@Getter private Boolean inscriptionAgentEtat=Boolean.TRUE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
        if(inscriptionAgentEtat)
        	title = "Ouverture de compte";
     
        demandeurDto = new IdentitePersonneDTO(isCreate(),entity.getPersonneDemandeur(),inscriptionAgentEtat);
        PieceJustificative pieceIdentite = new PieceJustificative();
        
        //pieceIdentite.setModel(new PieceJustificativeAFournir(null,Boolean.FALSE, 3, 2, new TypePieceJustificative("cni", "CNI")));
        demandeurDto.getInfosSouscriptionComptePersonne().getPersonne().setPieceIdentite(pieceIdentite);
        
        //demandeurDto.getPersonne().setNationalite();
        
        defaultSubmitCommand.setValue(text("bouton.ouvrircompte"));
        defaultSubmitCommand.setNotificationMessageId("notification.compte.ouvert");
        
        reponseSecrete = new ReponseSecrete();
        entity.getReponseSecretes().add(reponseSecrete);
        
        //defaultSubmitCommand.setImmediate(Boolean.TRUE);
    }
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		souscriptionService.souscrire(entity);
	}
    
    /*
    @Override
    protected Boolean valide() {
    	Boolean ok = Boolean.TRUE;
    	if(!validationUtils.isMatrciuleFormatCorrect(inscription.getPersonneDemandeur().getMatricule())){
    		addMessageError("Le matricule est incorrect");
    		ok=false;
    	}
    	if(!validationUtils.isMajeur(inscription.getPersonneDemandeur().getDateNaissance())){
    		addMessageError("Vous n'êtes pas majeur");
    		ok = false;
    	}
    	return ok;
    }
    */
	
	public Souscription getInscription() {
		return entity;
	}

}
