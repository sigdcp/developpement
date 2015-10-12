package ci.gouv.budget.solde.sigdcp.controller.demande;


import java.io.Serializable;

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractDossierValidator;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierDDValidator;
 
@Named @ViewScoped
public class FaireDemandeDDSoldeController extends FaireDemandeDDController implements Serializable {
	
	
	private static final long serialVersionUID = -5676681554247539927L;

	/*
	@Inject private DossierDDService dossierDDService;

	@Inject private DossierDDValidator validator;
	
	@Getter private Boolean showDatePriseService = Boolean.FALSE;
	@Getter private Boolean showDateCessationService = Boolean.FALSE;
	@Getter private Boolean showDateMiseRetraite = Boolean.FALSE;
	@Getter private Boolean showServiceOrigine = Boolean.FALSE;
	@Getter private Boolean showServiceAcceuil = Boolean.FALSE;
	@Getter private String libelleGrade;
	
	@Override
	protected DossierDDService getDossierService() {
		return dossierDDService;
	}
	
	@Override
	protected AbstractDossierValidator<DossierDD> validator() {
		return validator;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		libelleGrade = "Grade à ";
		if(Code.NATURE_DEPLACEMENT_AFFECTATION.equals(entity.getDeplacement().getNature().getCode())){
			showDatePriseService = Boolean.TRUE;
			showServiceAcceuil = Boolean.TRUE;
			libelleGrade += "l'affectation";
		}else if(Code.NATURE_DEPLACEMENT_MUTATION.equals(entity.getDeplacement().getNature().getCode())){
			showDatePriseService = Boolean.TRUE;
			showDateCessationService = Boolean.TRUE;
			showServiceOrigine = Boolean.TRUE;
			showServiceAcceuil = Boolean.TRUE;
			libelleGrade += "la mutation";
		}else if(Code.NATURE_DEPLACEMENT_RETRAITE.equals(entity.getDeplacement().getNature().getCode())){
			showDateMiseRetraite = Boolean.TRUE;
			showServiceOrigine = Boolean.TRUE;
			libelleGrade += "la retraite";
		}
	}
	
	public void marieListener(ValueChangeEvent valueChangeEvent){
		entity.setMarie((Boolean) valueChangeEvent.getNewValue());
		updatePieceJustificatives();
	}
	
	public void nombreEnfantListener(ValueChangeEvent valueChangeEvent){
		entity.setNombreEnfant((Integer) valueChangeEvent.getNewValue());
		updatePieceJustificatives();
	}
		*/
}
