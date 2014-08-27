package ci.gouv.budget.solde.sigdcp.controller.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public abstract class AbstractEntityListUIController<ENTITY extends AbstractModel<?>> extends AbstractFormUIController<ENTITY> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	protected static final String CSS_SEVERITY_INFO = "info";
	protected static final String CSS_SEVERITY_WARN = "warn";
	protected static final String CSS_SEVERITY_ERROR = "error";
	
	public enum ProcessingType{BATCH,SINGLE}
	public enum LinkStrategy{HREF,ONCLICK}
	
	/*
	 * Services
	 */
	
	/*
	 * Dto
	 */
	@Getter protected List<ENTITY> list;
	@Getter @Setter protected String numeroLigne,numeroLibelle;
	
	@Size(min=1,message="Sélectionner au moins un élément dans la liste")
	@Getter @Setter protected List<ENTITY> selectedMultiple;
	
	@Getter @Setter protected ENTITY selectedOne,selected,dialogSelected;
	@Getter @Setter protected Object selection;
	@Getter @Setter protected Boolean showCheckBox=Boolean.FALSE,showActionsColumn=Boolean.TRUE,showRadio=false;
	@Getter @Setter protected String recordIdentifierFieldName,selectLabel,listTitle;
	
	@Getter protected FormCommand<ENTITY> rechercherCommande;
	@Getter protected FormCommand<ENTITY> detailsCommand,addDetailsCommand,removeDetailsCommand,editDetailsCommand;
	protected LinkStrategy linkStrategy = LinkStrategy.HREF;
	
	
	/*
	 * Paramètres url
	 */
	@Getter @Setter protected String nextViewOutcome;
	
	protected Map<String,Object> detailsDialogOptions;
		
	@Override
	protected void initialisation() {
		super.initialisation();
		
		nextViewOutcome = Faces.getRequestParameter(webConstantResources.getRequestParamNextViewOutcome());
		selectLabel = "bouton.afficher";
		showFieldRequired = Boolean.FALSE;
		
		defaultSubmitCommand.setRendered(ProcessingType.BATCH.equals(getProcessingType()));
		
		closeCommand.setRendered(ProcessingType.BATCH.equals(getProcessingType()));
		
		list = load();
		
		if(list!=null)
			for(ENTITY e : list)
				processLoaded(e);
		
		recordIdentifierFieldName = identifierFieldName();
		
		if(getIsBatchProcessing()){
			showCheckBox=Boolean.TRUE;
			detailsCommand = createCommand().init("bouton.details", "ui-icon-extlink", null, new Action() {
				private static final long serialVersionUID = -4170008297787009528L;
				@SuppressWarnings("unchecked")
				@Override
				protected Object __execute__(Object object) throws Exception {
					onDetailsCommandAction((ENTITY) object);
					return null;
				}
			}).onSuccessStayOnCurrentView();
			detailsCommand.setImmediate(true);
			detailsCommand.setProcess("@this");
			detailsCommand.getObjectValidators().clear();
			
			numeroLibelle=text("numero");
			addDetailsCommand = createCommand().init("bouton.ajouter", "ui-icon-plus", null, new Action() {
				private static final long serialVersionUID = -4170008297787009528L;
				@Override
				protected Object __execute__(Object object) throws Exception {
					onAddDetailsCommandAction();
					return null;
				}
			}).onSuccessStayOnCurrentView();
			addDetailsCommand.setImmediate(true);
			addDetailsCommand.setProcess("numeroLigne");
			addDetailsCommand.getObjectValidators().clear();
			addDetailsCommand.setRendered(false);
			
			
			
			//hint: available options are modal, draggable, resizable, width, height, contentWidth and contentHeight
			detailsDialogOptions = new HashMap<>();
	        detailsDialogOptions.put("modal", true);  
	        detailsDialogOptions.put("draggable", false);
	        detailsDialogOptions.put("resizable", false);  
	        //System.out.println("Dialog count = "+webConstantResources.getDialogCount());
	        detailsDialogOptions.put("contentWidth", 1400 - webConstantResources.getDialogCount() *38); 
	        detailsDialogOptions.put("contentHeight", 700 - webConstantResources.getDialogCount() *38); 
		}else{
			
		}
		
		removeDetailsCommand = createCommand().init("bouton.supprimer", "ui-icon-close", null, new Action() {
			private static final long serialVersionUID = -4170008297787009528L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) throws Exception {
				onRemoveDetailsCommandAction((ENTITY) object);
				return null;
			}
		}).onSuccessStayOnCurrentView();
		removeDetailsCommand.setImmediate(true);
		removeDetailsCommand.setProcess("@this");
		removeDetailsCommand.getObjectValidators().clear();
		removeDetailsCommand.setRendered(false);
		
		editDetailsCommand = createCommand().init("bouton.editer", "ui-icon-pencil", null, new Action() {
			private static final long serialVersionUID = -4170008297787009528L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) throws Exception {
				onEditDetailsCommandAction((ENTITY) object);
				return null;
			}
		}).onSuccessStayOnCurrentView();
		editDetailsCommand.setImmediate(true);
		editDetailsCommand.setProcess("@this");
		editDetailsCommand.getObjectValidators().clear();
		editDetailsCommand.setRendered(false);
		
		defaultSubmitCommand.setRendered(showPageHeader);
		closeCommand.setRendered(showPageHeader);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		/*pageTemplate = webConstantResources.getPageTemplate();
		System.out.println(pageTemplate);
		if(pageTemplate.equals(webConstantResources.getRequestParamPageTemplateDialog()))
			showActionsColumn = Boolean.FALSE;*/
		//selection = selectedMultiple;// Boolean.TRUE.equals(showCheckBox)?selectedMultiple:selectedOne;
	}
	
	protected abstract List<ENTITY> load();
	
	protected void processLoaded(ENTITY entity){}
	
	public String onClick(ENTITY entity){
		return onClickNewWindow(entity);
	}
	
	public String onClickNewWindow(ENTITY entity){
		String outcome = objectOutcome(entity);
		Map<String, List<String>>  mapParameters = new HashMap<>();
		addPopupDefaultParameters(mapParameters);
		detailsOutcomeParameters(mapParameters, entity);
		Object[] parameters = new Object[mapParameters.size()*2];
		int i= 0;
		for(Entry<String, List<String>> entry : mapParameters.entrySet()){
			parameters[i++] = entry.getKey();
			parameters[i++] = entry.getValue().get(0);
		}
		return String.format("window.open('%s','list','width=1024,height=600');",navigationManager.url(outcome, parameters, false, false)) ;
	}
	
	protected String objectOutcome(Object entity){
		return navigationManager.outcome(entity);
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
		rechercherCommande.getObjectValidators().clear();
	}
	
	protected void onSearchCommandAction(){
		list = load();
		for(ENTITY e : list)
			processLoaded(e);
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		
	}
	
	protected void onEditDetailsCommandAction(ENTITY object) throws Exception {
		onDetailsCommandAction(object, webConstantResources.getRequestParamCrudUpdate());
	}
	
	protected void onRemoveDetailsCommandAction(ENTITY object) throws Exception {
		if(getIsBatchProcessing())
			list.remove(object);
		else
			Faces.redirect(Faces.getServletContext().getContextPath()+"/"+_href(object,webConstantResources.getRequestParamCrudDelete()));
	}
	
	public Boolean canRemove(ENTITY entity){
		return true;
	}
	
	public Boolean actionable(ENTITY entity){
		return false;
	}
	
	public String actionLabel(ENTITY entity){return null;}
	
	public String actionHref(ENTITY entity){
		return href(entity);
	}
	
	public String href(ENTITY entity){
		return _href(entity, webConstantResources.getRequestParamCrudRead());
	}
	
	protected String _href(ENTITY object,String crudType){
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(webConstantResources.getRequestParamCrudType(), crudType);
		hrefParameters(parameters, object);
		List<Object> l= new ArrayList<>();
		for(Entry<String, Object> entry : parameters.entrySet()){
			l.add(entry.getKey());
			l.add(entry.getValue());
		}
		Object[] params = l.toArray();
		String url = navigationHelper.addQueryParameters(navigationManager.url(objectOutcome(hrefObjectOutcome(object)),false),params);
		return url;
	}
	
	protected Object hrefObjectOutcome(ENTITY entity){
		return entity;
	}
	
	protected void hrefParameters(Map<String, Object> parameters,ENTITY object){
		
	}
	
	protected void onAddDetailsCommandAction() throws Exception {
		
	}
	
	protected void onDetailsCommandAction(ENTITY object,String crud) {
		dialogSelected = object;
		Map<String, List<String>> parameters = new HashMap<>();
		detailsOutcomeParameters(parameters,object);
		addPopupDefaultParameters(parameters,crud);
        RequestContext.getCurrentInstance().openDialog(detailsOutcome(object), detailsDialogOptions, parameters);  
    }
	
	protected void onDetailsCommandAction(ENTITY object) {
		onDetailsCommandAction(object, webConstantResources.getRequestParamCrudRead());
	}
	
	protected void addPopupDefaultParameters(Map<String, List<String>> parameters,String crud){
		addParameters(parameters, webConstantResources.getRequestParamCrudType(), crud);
		addParameters(parameters, webConstantResources.getRequestParamDialogCount(), (webConstantResources.getDialogCount()+1)+"");
		addParameters(parameters, webConstantResources.getRequestParamViewType(), webConstantResources.getRequestParamDialog());
		addParameters(parameters, webConstantResources.getRequestParamPageTemplate(), webConstantResources.getRequestParamPageTemplateDialog());
	}
	
	protected void addPopupDefaultParameters(Map<String, List<String>> parameters){
		addPopupDefaultParameters(parameters, webConstantResources.getRequestParamCrudRead());
	}
	
	protected String identifierFieldName(){
		//serviceUtils.getPersistenceUtils().identifierFieldName();
		return null;
	}
	
	protected String detailsOutcome(ENTITY object){
		return navigationManager.outcome(object);
		//throw new RuntimeException("No outcome defined");
	}
	
	protected String detailsOutcome(){
		return detailsOutcome(null);
	}
	
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters,ENTITY object){
		throw new RuntimeException("No outcome parameters defined");	
	}
	
	protected void addParameters(Map<String, List<String>> parameters,String name,String...values){
		parameters.put(name,Arrays.asList(values));
	}
	
	public void onRowSelect(SelectEvent selectEvent){}
	
	public boolean isSelected(ENTITY entity){
		if(selectedMultiple==null || entity==null)
			return false;
		return selectedMultiple.contains(entity);
	}
	
	public String rowStyleClass(ENTITY entity){
		return rowMessageStyleClass(entity);
	}
	
	public String rowMessageStyleClass(ENTITY entity){
		String severity = rowSeverity(entity);
		return StringUtils.isNotEmpty(severity)?"ui-message-"+rowSeverity(entity):null;
	}
	
	public String rowSeverity(ENTITY entity){
		return null;
	}
	
	public Boolean getUseHref(){
		return LinkStrategy.HREF.equals(linkStrategy);
	}
	
	public Boolean getUseOnlick(){
		return LinkStrategy.ONCLICK.equals(linkStrategy);
	}
}
