package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.Inscription;
import ci.gouv.budget.solde.sigdcp.service.identification.InscriptionService;

@Named @ViewScoped
public class InscriptionPersonneController extends AbstractEntityFormUIController<Inscription> implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	/*
	 * Services
	 */
	@Inject private InscriptionService inscriptionService;
	
	/*
	 * DTOs
	 */

	@Getter private IdentitePersonneDTO demandeurDto;
	@Getter private Boolean inscriptionAgentEtat=Boolean.TRUE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
        if(inscriptionAgentEtat)
        	title = "Ouverture de compte";
     
        demandeurDto = new IdentitePersonneDTO(entity.getPersonneDemandeur(),inscriptionAgentEtat);
        PieceJustificative pieceIdentite = new PieceJustificative();
        
        //pieceIdentite.setModel(new PieceJustificativeAFournir(null,Boolean.FALSE, 3, 2, new TypePieceJustificative("cni", "CNI")));
        demandeurDto.getPersonne().setPieceIdentite(pieceIdentite);
        
        //demandeurDto.getPersonne().setNationalite();
        
        defaultSubmitCommand.setValue(text("bouton.ouvrircompte"));
        defaultSubmitCommand.setNotificationMessageId("notification.compte.ouvert");
       
    }
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		inscriptionService.inscrire(entity);
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
	
	public Inscription getInscription() {
		return entity;
	}

}
