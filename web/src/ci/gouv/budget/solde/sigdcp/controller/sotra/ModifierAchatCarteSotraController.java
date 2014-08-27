package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.CarteSotra;
import ci.gouv.budget.solde.sigdcp.service.sotra.AchatCarteSotraService;
import ci.gouv.budget.solde.sigdcp.service.sotra.CarteSotraService;

@Named @ViewScoped
public class ModifierAchatCarteSotraController extends AbstractEntityFormUIController<AchatCarteSotra> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;
 
	@Inject private AchatCarteSotraService achatCarteSotraService;
	@Inject private CarteSotraService carteSotraService;
	
	@Getter private String moisString;
	@Getter @Setter private List<CarteSotra> carteSotras;// carte a supprimer
	
	@Override
	protected void initDto() {
		super.initDto();
		entity = achatCarteSotraService.findDemande(null,null);
	}
		
	@Override
	protected void afterDtoInit() {
		super.afterDtoInit();
		if(entity.getTraitable().getNatureOperation()!=null){
			title = "Retrait d'inscrit de la liste d'achat mensuelle";
			moisString = new DateFormatSymbols().getMonths()[entity.getMois()];
		}
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		carteSotraService.annuler(carteSotras);
	}
	
}
