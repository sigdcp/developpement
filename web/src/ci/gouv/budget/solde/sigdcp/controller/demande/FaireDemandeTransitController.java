package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierTransitService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierTransitValidator;


@Named @ViewScoped
public class FaireDemandeTransitController extends AbstractFaireDemandeController<DossierTransit,DossierTransitService> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Services
	 */
	@Inject private DossierTransitService dossierTransitService;
	 
	@Inject private DossierTransitValidator validator;
	
	@Getter @Setter private Boolean mae;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		mae = Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE.equals(entity.getDeplacement().getNature().getCode());
	}
		
	@Override
	protected DossierTransitService getDossierService() {
		return dossierTransitService;
	}
	
	public void typeDepenseListener(){
		updatePieceJustificatives();
	}
	
	@Override
	protected AbstractValidator<DossierTransit> validator() {
		return validator;
	}
	
	@Override
	protected Collection<PieceJustificativeAFournir> onEnregistrerSuccessPieceJustificativeAFournir() {
		return new ArrayList<>();
	}

}
		
