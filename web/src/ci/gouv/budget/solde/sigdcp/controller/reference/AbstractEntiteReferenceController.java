package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.RowEditEvent;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.service.CrudService;

public abstract class AbstractEntiteReferenceController<ENTITY extends AbstractModel<ID>,ID> extends AbstractEntityListUIController<ENTITY> implements Serializable {

	private static final long serialVersionUID = -692739505580067624L;

	@Inject protected CrudService crudService;
	
	@Getter @Setter protected boolean etatBoutonAjouter;
	
	@Getter @Setter protected List<ENTITY> enAjout = new ArrayList<>();
	
	//@Getter @Setter protected List<ENTITY> list2 = new ArrayList<>();
	
	
	//@Getter  private LazyDataModel lazyTableModel;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = "Gestion des entités de références - "+nomEntite();
		defaultSubmitCommand.setRendered(false);
		etatBoutonAjouter = true;
		
		/*
		lazyTableModel = new LazyDataModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<ENTITY> load(int first, int pageSize, List multiSortMeta,Map filters) {
				return new ArrayList<>(genericService.findAllByClass(clazz(), first, pageSize));
			}
	 	 };*/
	 	 
	}
	
	@SuppressWarnings("unchecked")
	public void onRowEdit(RowEditEvent event){	
		if(enAjout.isEmpty())
			enregistrer((ENTITY) event.getObject());
		else{
			if(crudService.existe(clazz(), identifiant((ENTITY) event.getObject()))){
				messageManager.addError("Cet enregistrement existe déja!", false);
			}else{				
				enregistrer((ENTITY) event.getObject());
				supprimerObject(enAjout,event.getObject());
				redirectUrl(url);
			}
		}
		
	}
	
	private void enregistrer(ENTITY entity){
		try {
			crudService.enregistrer(entity);
			//HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			//Faces.redirect(url);
			
		} catch (Exception e) {
			messageManager.addError(e);
		}
		
	}
	
	public void onRowEditCancel(RowEditEvent event){
		if(supprimerObject(enAjout,event.getObject())){
			redirectUrl(url);
		}
		
	}
	
	@Override
	protected List<ENTITY> load() {
		return new ArrayList<>(genericService.findAllByClass(clazz()));
	}
	
	protected abstract String nomEntite(); 
	
	public Boolean identifiantEditable(ENTITY entity){
		return enAjout.isEmpty()?false:enAjout.get(0)==entity;
	}
	
	public abstract ID identifiant(ENTITY entity); 
	
	protected abstract Class<ENTITY> clazz(); 
	
	public void ajouter(){
		
		if(enAjout.isEmpty()){
			
			try {
				ENTITY entity = clazz().newInstance();
				list.clear();
				list.add(entity);
				enAjout.add(entity);
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
	}
	
	public void supprimer(ENTITY entity){
		crudService.supprimer(Arrays.asList(entity));
		supprimerObject(list, entity);
		if(!enAjout.isEmpty())supprimerObject(enAjout,entity);
		
	}
	
	private Boolean supprimerObject(List<ENTITY> objects,Object object){
		for(int i=0;i<objects.size();)
			if(object==objects.get(i)){
				objects.remove(i);
				return true;
			}else
				i++;
		return false;
	}
		
}
