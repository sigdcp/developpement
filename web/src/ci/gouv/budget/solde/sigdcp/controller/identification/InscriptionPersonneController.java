package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.AbstractFormSubmitAction;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
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
	public void __firstPreRenderView__() {
		super.__firstPreRenderView__();
        if(inscriptionAgentEtat)
        	title = "Ouverture de compte";
 
        demandeurDto = new IdentitePersonneDTO(entity.getPersonneDemandeur(),inscriptionAgentEtat);
        PieceJustificative pieceIdentite = new PieceJustificative();
        
        //pieceIdentite.setModel(new PieceJustificativeAFournir(null,Boolean.FALSE, 3, 2, new TypePieceJustificative("cni", "CNI")));
        demandeurDto.getPersonne().setPieceIdentite(pieceIdentite);
        
        //demandeurDto.getPersonne().setNationalite();
        
        defaultSubmitAction = new AbstractFormSubmitAction<Inscription>(this,"bouton.ouvrircompte","ui-icon-check","notification.compte.ouvert",
				Boolean.FALSE,Boolean.TRUE) {
			private static final long serialVersionUID = -2683422739395829063L;
			@Override
			protected void action() {
				inscriptionService.inscrire(entity);
			}
		};
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
