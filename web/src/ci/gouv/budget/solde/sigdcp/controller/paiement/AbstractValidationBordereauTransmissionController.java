package ci.gouv.budget.solde.sigdcp.controller.paiement;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

@Getter
public abstract class AbstractValidationBordereauTransmissionController extends AbstractBordereauTransmissionListeController implements Serializable {

	private static final long serialVersionUID = -3441386175797582945L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		showMontant=true;
	}
	
	@Override
	protected Collection<BordereauTransmission> data(Collection<NatureDeplacement> natureDeplacements) {
		return bordereauTransmissionService.findATraiter(natureDeplacements,natureOperationCode());
	}
		
	@Override
	protected void valider(Collection<BordereauTransmission> datas) {
		bordereauTransmissionService.valider(datas);
	}
	
	
}
