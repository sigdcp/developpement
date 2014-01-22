package ci.gouv.budget.solde.sigdcp.controller.ui;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;

public abstract class AbstractEntityListUIController<ENTITY extends AbstractModel<?>> extends AbstractFormUIController<ENTITY> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	public enum ProcessingType{BATCH,SINGLE}
	
	/*
	 * Services
	 */
	
	/*
	 * Dto
	 */
	@Getter protected List<ENTITY> list;
	
	@Size(min=1,message="Sélectionner au moins un élément dans la liste")
	@Getter @Setter protected List<ENTITY> selectedMultiple;
	
	@Getter @Setter protected ENTITY selectedOne;
	@Getter protected Boolean showActionsColumn=Boolean.TRUE;
	@Getter protected String recordIdentifierFieldName;
	
	@Getter protected FormCommand<ENTITY> rechercherCommande;
	@Getter protected FormCommand<ENTITY> detailsCommand;
	
	/*
	 * Paramètres url
	 */
	@Getter @Setter protected String nextViewOutcome;
	
	protected Map<String,Object> detailsDialogOptions;
		
	@Override
	protected void initialisation() {
		super.initialisation();
		showFieldRequired = Boolean.FALSE;
		
		defaultSubmitCommand.setRendered(ProcessingType.BATCH.equals(getProcessingType()));
		defaultSubmitCommand.addExtraValidator(new AbstractValidator<Object>(){
			private static final long serialVersionUID = 1401664740657460715L;
		}, this);
		
		closeCommand.setRendered(ProcessingType.BATCH.equals(getProcessingType()));
		
		list = load();
		
		recordIdentifierFieldName = identifierFieldName();
		
		if(getIsBatchProcessing()){
			detailsCommand = createCommand().init("bouton.details", "ui-icon-extlink", null, new Action() {
				private static final long serialVersionUID = -4170008297787009528L;
				@SuppressWarnings("unchecked")
				@Override
				protected Object __execute__(Object object) throws Exception {
					onDetailsCommandAction((ENTITY) object);
					return null;
				}
			}).onSuccessStayOnCurrentView();
			//hint: available options are modal, draggable, resizable, width, height, contentWidth and contentHeight
			detailsDialogOptions = new HashMap<>();
	        detailsDialogOptions.put("modal", true);  
	        detailsDialogOptions.put("draggable", false);  
	        //detailsDialogOptions.put("resizable", false);  
	        detailsDialogOptions.put("contentWidth", 1200); 
	        detailsDialogOptions.put("contentHeight", 700); 
		}else{
			
		}
	}
	
	protected abstract List<ENTITY> load();
	
	public String href(ENTITY entity){
		return null;
	}
	
	protected ProcessingType getProcessingType(){
		return StringUtils.isEmpty(nextViewOutcome)?ProcessingType.BATCH:ProcessingType.SINGLE;
	}
	
	public Boolean getIsBatchProcessing(){
		return ProcessingType.BATCH.equals(getProcessingType());
	}
	
	protected void enableSearch(){
		rechercherCommande = createCommand().init("bouton.rechercher", "ui-icon-search", null, new Action() {
			private static final long serialVersionUID = -5307893903678626614L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				onSearchCommandAction();
				return null;
			}
		} ).onSuccessStayOnCurrentView();
	}
	
	protected void onSearchCommandAction(){
		
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		
	}
	
	protected void onDetailsCommandAction(ENTITY object) {
		Map<String, List<String>> parameters = new HashMap<>();
		detailsOutcomeParameters(parameters,object);
        RequestContext.getCurrentInstance().openDialog(detailsOutcome(), detailsDialogOptions, parameters);  
    }  
	
	protected String identifierFieldName(){
		//serviceUtils.getPersistenceUtils().identifierFieldName();
		return null;
	}
	
	protected String detailsOutcome(){
		throw new RuntimeException("No outcome defined");
	}
	
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters,ENTITY object){
		throw new RuntimeException("No outcome parameters defined");
	}
	
	protected void addParameters(Map<String, List<String>> parameters,String name,String...values){
		parameters.put(name,Arrays.asList(values));
	}
	
}
