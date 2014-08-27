package ci.gouv.budget.solde.sigdcp.controller.remboursement;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.ProjetDecision;

@Named @ViewScoped
public class EtablirProjetDecisionController extends AbstractProjetDecisionListePersonnelController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		listTitle="Liste des demandes";
		selectLabel="bouton.selectionner";
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_ETABLISSEMENT_PROJDEC;
	}
	
	@Override
	public Date dateCreation(ProjetDecision projetDecision) {
		return projetDecision.getDossier().getDateCreation();
	}
	
	@Override
	public String numero(ProjetDecision projetDecision) {
		return projetDecision.getDossier().getNumero();
	}

	
	
				
}
