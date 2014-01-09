package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public abstract class AbstractEntityListController<ENTITY extends AbstractModel<?>> extends AbstractFormUIController<ENTITY> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	public enum ProcessingType{BATCH,SINGLE}
	
	/*
	 * Services
	 */
	
	/*
	 * Dto
	 */
	@Getter protected List<ENTITY> list;
	@Getter @Setter protected List<ENTITY> selectedMultiple = new LinkedList<>();
	@Getter @Setter protected ENTITY selectedOne;
	@Getter private Boolean showActionsColumn=Boolean.TRUE;
	
	/*
	 * Paramètres url
	 */
	@Getter @Setter protected String nextViewOutcome;
	
	@Getter protected FormCommand<ENTITY> rechercherCommande;
		
	@Override
	protected void initialisation() {
		super.initialisation();
		
		defaultSubmitCommand.setRendered(ProcessingType.BATCH.equals(getProcessingType()));
		closeCommand.setRendered(ProcessingType.BATCH.equals(getProcessingType()));
		
		list = load();
	}
	
	protected abstract List<ENTITY> load();
	
	public abstract String href(ENTITY entity);
	
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
			protected void __execute__() throws Exception {
				onSearchCommandAction();
			}
		} ).onSuccessStayOnCurrentView();
	}
	
	protected void onSearchCommandAction(){
		
	}
	
	
}
