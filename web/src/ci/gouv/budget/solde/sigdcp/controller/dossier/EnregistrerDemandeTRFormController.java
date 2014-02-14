package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierTransitService;


@Named @ViewScoped
public class EnregistrerDemandeTRFormController extends AbstractDossierUIController<DossierTransit,DossierTransitService> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Services
	 */
	@Inject private DossierTransitService dossierTransitService;
	
	@Getter @Setter private Boolean mae;
	
	@Override
	protected void initialisation() {
		typeDepense =  genericService.findByClass(TypeDepense.class, String.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE);
		super.initialisation();
		mae = Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE.equals(entity.getDeplacement().getNature().getCode());
	}
		
	@Override
	protected DossierTransitService getDossierService() {
		return dossierTransitService;
	}
	
	public void typeAgentListener(){
		parametres.put(constantResources.getFormParamMae(), mae);
		updatePieceJustificatives();
	}
	
	public void typeDepenseListener(){
		updatePieceJustificatives();
		//System.out.println(typeDepense+" : "+pieceJustificativeUploader.getPieceJustificatives());
	}
	


}
		
