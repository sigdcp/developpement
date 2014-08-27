package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierMissionValidator;


@Named @ViewScoped
public class FaireDemandeTitreTransportController extends AbstractFaireDemandeController<DossierMission,DossierMissionService> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Services
	 */
	@Inject private DossierMissionService dossierMissionService;
	 
	@Inject private DossierMissionValidator validator;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
	}
		
	@Override
	protected DossierMissionService getDossierService() {
		return dossierMissionService;
	}
	
	public void typeDepenseListener(){
		updatePieceJustificatives();
	}
	
	@Override
	protected AbstractValidator<DossierMission> validator() {
		return validator;
	}
	
	@Override
	protected Collection<PieceJustificativeAFournir> onEnregistrerSuccessPieceJustificativeAFournir() {
		return new ArrayList<>();
	}
	
	public void marieListener(ValueChangeEvent valueChangeEvent){
		entity.setMarie((Boolean) valueChangeEvent.getNewValue());
		updatePieceJustificatives();
	}
	
	public void nombreEnfantListener(ValueChangeEvent valueChangeEvent){
		entity.setNombreEnfant((Integer) valueChangeEvent.getNewValue());
		updatePieceJustificatives();
	}

}
		
