package ci.gouv.budget.solde.sigdcp.controller.paiement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEffectuerOperationPersonnelController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;
import ci.gouv.budget.solde.sigdcp.service.dossier.BordereauTransmissionService;

@Getter
public abstract class AbstractBordereauTransmissionListeController extends AbstractEffectuerOperationPersonnelController<BordereauTransmission> implements Serializable {

	private static final long serialVersionUID = -3441386175797582945L;
	
	@Inject protected BordereauTransmissionService bordereauTransmissionService;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		listTitle = "Liste des bordereaux de transmissions";
		if(selected==null){
			selected = new BordereauTransmission();
			selected.getTraitable().setTraitement(new TraitementPieceProduite());
		}
	}
	
	@Override
	protected String[] defaultNatureDeplacmentCodeListe() {
		return new String[]{Code.NATURE_DEPLACEMENT_AFFECTATION,Code.NATURE_DEPLACEMENT_MUTATION,Code.NATURE_DEPLACEMENT_RETRAITE,Code.NATURE_DEPLACEMENT_MISSION_HCI};
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, BordereauTransmission dto) {
		addParameters(parameters, webConstantResources.getRequestParamBordereau(), dto.getId()+"");
	}
	
	@Override
	protected String detailsOutcome(BordereauTransmission object) {
		return "realiserbtbl";
	}
	
	@Override
	public String getRecordIdentifierFieldName() {
		return "id";
	}
	
	@Override
	public BigDecimal depense(BordereauTransmission data) {
		if(data==null || data.getMontant()==null)
			return BigDecimal.ZERO;
		return data.getMontant();
	}
	
	@Override
	public Traitement dernierTraitement(BordereauTransmission data) {
		return data.getTraitable().getDernierTraitement();
	}
	
	@Override
	public String numero(BordereauTransmission data) {
		return data.getNumero();
	}
	
	@Override
	public Date dateCreation(BordereauTransmission data) {
		return data.getDateCreation();
	}

}
