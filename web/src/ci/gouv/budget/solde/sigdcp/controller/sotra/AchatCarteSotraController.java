package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.sotra.AchatCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;
import ci.gouv.budget.solde.sigdcp.service.sotra.AchatCarteSotraService;

@Named @ViewScoped
public class AchatCarteSotraController extends AbstractEntityFormUIController<AchatCarteSotra> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;
 
	@Inject private AchatCarteSotraService achatCarteSotraService;
	
	@Getter private Boolean showAcheterus = Boolean.TRUE,showDateRetrait,dateRetraitEditable;
	
	@Getter private Collection<SelectItem> mois=new ArrayList<>(),annees=new ArrayList<>();
	@Getter private String moisString;
	
	//@Getter private FormCommand<AchatCarteSotra> modifierListe;
	
	@Override
	protected void initDto() {
		super.initDto();
		entity = achatCarteSotraService.findDemande(null,null);
	}
		
	@Override
	protected void afterDtoInit() {
		super.afterDtoInit();
		if(entity.getTraitable().getNatureOperation()!=null){
			title = entity.getTraitable().getNatureOperation().getLibelle();
			switch(entity.getTraitable().getNatureOperation().getCode()){
			case Code.NATURE_OPERATION_OUVERTURE_LCS:
				crudType = CRUDType.CREATE;
				defaultSubmitCommand.setValue("Ouvrir");
				showAcheterus = false;
				DateFormatSymbols dfs = new DateFormatSymbols();
				for(Integer m : entity.getMoisChoix())
					mois.add(new SelectItem(m, dfs.getMonths()[m]));
				for(Integer a : entity.getAnneesChoix())
					annees.add(new SelectItem(a, a+""));
				defaultSubmitCommand.setNotificationMessageId("notification.sotra.liste.ouverte");
				break;
			case Code.NATURE_OPERATION_CLOTURE_LCS:
				defaultSubmitCommand.setValue("Cloturer");
				break;
			case Code.NATURE_OPERATION_VALIDATION_LCS:
				defaultSubmitCommand.setValue("Valider");
				break;
			case Code.NATURE_OPERATION_RETRAIT_CARTE_LCS:
				defaultSubmitCommand.setValue("Valider");
				break;
			}
			if(!Code.NATURE_OPERATION_OUVERTURE_LCS.equals(entity.getTraitable().getNatureOperation().getCode())){
				moisString = new DateFormatSymbols().getMonths()[entity.getMois()];
			}
		}
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		achatCarteSotraService.valider(Arrays.asList(entity));
	}
	
	public boolean isRetraitCarte(){
		return Code.NATURE_OPERATION_RETRAIT_CARTE_LCS.equals(entity.getTraitable().getNatureOperation().getCode());
	}
	
	public boolean isValidationListeCarte(){
		return Code.NATURE_OPERATION_VALIDATION_LCS.equals(entity.getTraitable().getNatureOperation().getCode());
	}
	
	public boolean isListeCarteAnnunable(){
		return entity.getTraitable().getDernierTraitement()!=null &&
				!Code.NATURE_OPERATION_VALIDATION_LCS.equals(entity.getTraitable().getDernierTraitement().getOperation().getNature().getCode()) &&
				!Code.NATURE_OPERATION_GENERATION_CCS.equals(entity.getTraitable().getDernierTraitement().getOperation().getNature().getCode()) &&
				!Code.NATURE_OPERATION_RETRAIT_CARTE_LCS.equals(entity.getTraitable().getDernierTraitement().getOperation().getNature().getCode());
	}
	
}
