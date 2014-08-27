package ci.gouv.budget.solde.sigdcp.controller.dossier.validation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEffectuerOperationPersonnelController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.prestation.Facture;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.service.prestation.FactureService;

@Named @ViewScoped
public class ReceptionFactureController extends AbstractEffectuerOperationPersonnelController<Facture> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Inject private FactureService factureService;
	  
	@Override 
	protected void initialisation() {
		super.initialisation();
		rechercherCommande=null;
		showObservation=showValidation=false;
		//editDetailsCommand.setRendered(false);
		//editDetailsCommand.setValue("Selectionner");
		//detailsCommand.setRendered(false);
		defaultSubmitCommand.setRendered(false);
		listTitle="Liste des commandes";
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_RECEPTION_FACTURE;
	}

	/*
	@Override
	protected String defaultNatureDeplacementCode() {
		return Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE;
	}
	*/
	@Override
	protected String[] defaultNatureDeplacmentCodeListe() {
		return new String[]{Code.NATURE_DEPLACEMENT_MISSION_HCI,Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS};
	}
	
	@Override
	protected Boolean canShowAllNatureDeplacment() {
		return true;
	}

	@Override
	protected Collection<Facture> data(Collection<NatureDeplacement> natureDeplacements) {
		return factureService.findATraiter(natureOperationCode());
	}

	@Override
	public Traitement dernierTraitement(Facture facture) {
		return facture.getTraitable().getDernierTraitement();
	}

	@Override
	public BigDecimal depense(Facture data) {
		return null;
	}

	@Override
	public String numero(Facture data) {
		return data.getCommande().getNumero();
	}

	@Override
	public Date dateCreation(Facture data) {
		return null;
	}

	@Override
	protected void valider(Collection<Facture> datas) {
		factureService.valider(datas);
	}
	
	@Override
	protected String identifierFieldName() {
		return "numeroCommande";
	}
	@Override
	public String href(Facture facture){
		return navigationManager.url("receptionfacture_form", new Object[]{webConstantResources.getRequestParamCommande(),facture.getCommande().getId(),webConstantResources.getRequestParamTabMenuItemIndex(), userSessionManager.getUiMenuTabReceptionFactureIndex()},false);
	}
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
	
	
	/*
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, Commande commande) {
		super.detailsOutcomeParameters(parameters, dossier);
		addParameters(parameters, webConstantResources.getRequestParamNatureOperation(), natureOperationCode());
	}
	*/
	
	
}
