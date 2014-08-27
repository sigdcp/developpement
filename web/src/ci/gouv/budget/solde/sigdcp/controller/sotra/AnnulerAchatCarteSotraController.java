package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;
import java.text.DateFormatSymbols;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.sotra.AchatCarteSotraService;

@Named @ViewScoped
public class AnnulerAchatCarteSotraController extends AbstractEntityFormUIController<AchatCarteSotra> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;
 
	@Inject private AchatCarteSotraService achatCarteSotraService;
	
	@Getter private String moisString;

	@Override
	protected void initDto() {
		super.initDto();
		entity = achatCarteSotraService.findDemande(null,Code.NATURE_OPERATION_ANNULATION_LCS);
	}
		
	@Override
	protected void afterDtoInit() {
		super.afterDtoInit();
		if(entity.getTraitable().getNatureOperation()!=null){
			title = entity.getTraitable().getNatureOperation().getLibelle();
			moisString = new DateFormatSymbols().getMonths()[entity.getMois()];
		}
		defaultSubmitCommand.setValue(text("bouton.annuler"));
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		achatCarteSotraService.annuler(entity);
	}
	
}
