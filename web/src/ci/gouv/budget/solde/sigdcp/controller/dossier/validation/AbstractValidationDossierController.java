package ci.gouv.budget.solde.sigdcp.controller.dossier.validation;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import ci.gouv.budget.solde.sigdcp.controller.dossier.AbstractDossierListePersonnelController;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;

public abstract class AbstractValidationDossierController extends AbstractDossierListePersonnelController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	@Override
	protected void initialisation() {
		super.initialisation();	
		showBulletinNumero=Boolean.FALSE;
	}
	
	@Override
	protected void valider(Collection<Dossier> datas) {
		dossierService.valider(datas);
	}
	
	@Override
	protected Collection<Dossier> data(Collection<NatureDeplacement> natureDeplacements) {		
		return dossierService.findATraiter(natureDeplacements,natureOperationCode());
	}
	
	@Override
	public List<ValidationType> validationTypes(Dossier data) {
		return data.getTraitable().getValidationTypes();
	}

	@Override
	public Date dateCreation(Dossier data) {
		return data.getDateCreation();
	}
}
