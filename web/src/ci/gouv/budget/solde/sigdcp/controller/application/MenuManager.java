package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;

import org.primefaces.model.menu.BaseMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.Submenu;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.service.TextService;


public class MenuManager implements Serializable {

	private static final long serialVersionUID = 3655022050936615701L;
	
	@Inject private TextService textService;
	@Inject private NavigationManager navigationManager;
	
	@Getter private BaseMenuModel model = new DefaultMenuModel();

	@SuppressWarnings("unchecked")
	public Submenu addSubmenu(Submenu parent,String labelId){
		DefaultSubMenu submenu = new DefaultSubMenu();  
	    submenu.setLabel(textService.find(labelId));
	    if(parent==null)
	    	model.addElement(submenu);
	    else
	    	parent.getElements().add(submenu);
	    return submenu;
	}
	
	public Submenu addSubmenu(String labelId){
		return addSubmenu(null, labelId);
	}
	
	@SuppressWarnings("unchecked")
	public MenuItem addMenuItem(MenuElement parent,String labelId,String icon,String outcome,Object[] parameters){
		DefaultMenuItem menuItem = new DefaultMenuItem();  
	    menuItem.setValue(textService.find(labelId));  
	    menuItem.setTitle((String)menuItem.getValue());
	    menuItem.setOutcome(navigationManager.url(outcome,parameters));
		//menuItem.setOutcome(outcome);
		/*
		if(parameters!=null)
			for(int i=0;i<parameters.length-1;i=i+2)
				menuItem.setParam((String)parameters[i], parameters[i+1]);
	    */
		menuItem.setIcon(icon);
	 
	    if(parent==null)
	    	model.addElement(menuItem);
	    else if(parent instanceof Submenu)
	    	((Submenu)parent).getElements().add(menuItem);
	    else  if(parent instanceof BaseMenuModel)
	    	((BaseMenuModel)parent).addElement(menuItem);
	    
	    return menuItem;
	}
	
	public MenuItem addMenuItem(MenuElement parent,String labelId,String icon,String outcome){
		return addMenuItem(parent, labelId, outcome, new Object[]{});
	}
	
	public MenuItem addMenuItem(MenuElement parent,String labelId,String outcome,Object[] parameters){
		return addMenuItem(parent, labelId, null,outcome, parameters);
	}
	
	public MenuItem addMenuItem(MenuElement parent,String labelId,String outcome){
		return addMenuItem(parent, labelId, null,outcome, new Object[]{});
	}
	
	public MenuItem addMenuItem(String labelId,String icon,String outcome,Object[] parameters){
		return addMenuItem(null, labelId, icon, outcome, parameters);
	}
	
	public MenuItem addMenuItem(String labelId,String icon,String outcome){
		return addMenuItem(null, labelId, icon, outcome, new Object[]{});
	}
	
	public MenuItem addMenuItem(String labelId,String outcome,Object[] parameters){
		return addMenuItem(labelId, outcome,null, parameters);
	}
	
	public MenuItem addMenuItem(String labelId,String outcome){
		return addMenuItem(labelId, outcome,null, new Object[]{});
	}

}
