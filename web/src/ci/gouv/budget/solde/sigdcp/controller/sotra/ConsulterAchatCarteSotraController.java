package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;
import java.text.DateFormatSymbols;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.sotra.AchatCarteSotraService;

@Named @ViewScoped
public class ConsulterAchatCarteSotraController extends AbstractEntityFormUIController<AchatCarteSotra> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;
 
	@Inject private AchatCarteSotraService achatCarteSotraService;
	
	@Getter private String moisString;
	
	@Override
	protected void initDto() {
		super.initDto();
		entity = achatCarteSotraService.find(requestParameterLong(webConstantResources.getRequestParamAchatCarteSotra()),null);
	}
		
	@Override
	protected void afterDtoInit() {
		super.afterDtoInit();
		if(entity.getTraitable().getNatureOperation()!=null){
			title = "Liste d'achat mensuelle de carte sotra";
			moisString = new DateFormatSymbols().getMonths()[entity.getMois()];
		}
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		defaultSubmitCommand.setRendered(false);
	}
	
}
