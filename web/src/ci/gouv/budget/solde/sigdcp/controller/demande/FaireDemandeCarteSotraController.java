package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Arrays;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.sotra.CarteSotra;
import ci.gouv.budget.solde.sigdcp.service.sotra.CarteSotraService;

@Named @ViewScoped
public class FaireDemandeCarteSotraController extends AbstractEntityFormUIController<CarteSotra> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;

	@Inject private CarteSotraService carteSotraService;
	
	@Getter private String moisString;
	
	@Override
	protected void initDto() {
		entity = carteSotraService.findDemande(null,null);
	}
	
	@Override
	protected void afterDtoInit() {
		super.afterDtoInit();
		
		if(entity.getTraitable().getNatureOperation()!=null){
			moisString = new DateFormatSymbols().getMonths()[entity.getAchat().getMois()];
			title = entity.getTraitable().getNatureOperation().getLibelle();
			switch(entity.getTraitable().getNatureOperation().getCode()){
			case Code.NATURE_OPERATION_DEMANDE_CS:
				defaultSubmitCommand.setValue("Demander");
				break;
			case Code.NATURE_OPERATION_RETRAIT_CS:
				defaultSubmitCommand.setValue("Valider");
				break;
			case Code.NATURE_OPERATION_ANNULATION_CS:
				defaultSubmitCommand.setValue("Annuler");
				break;
			}
		}
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		carteSotraService.valider(Arrays.asList(entity));
	}

	public ValidationType getValidationAccepter(){
		return ValidationType.ACCEPTER;
	}
	
	public ValidationType getValidationRejeter(){
		return ValidationType.REJETER;
	}
	
	public Boolean getRetraitCarte(){
		if(entity!=null && entity.getTraitable().getNatureOperation()!=null)
			return Code.NATURE_OPERATION_RETRAIT_CS.equals(entity.getTraitable().getNatureOperation().getCode());
		return false;
	}
	
}
