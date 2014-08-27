package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import lombok.Getter;

import org.primefaces.model.menu.BaseMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.Submenu;


public class MenuManagerItem extends AbstractMenuManager<MenuElement,MenuItem, Submenu> implements Serializable {

	private static final long serialVersionUID = 3655022050936615701L;
	
	@Getter private BaseMenuModel model;

	public void initModel(){
		model = new DefaultMenuModel();
	} 
	
	@SuppressWarnings("unchecked")
	public Submenu addSubmenu(Submenu parent,String labelId,String icon){
		DefaultSubMenu submenu = new DefaultSubMenu();  
	    submenu.setLabel(textService.find(labelId));
	    if(parent==null)
	    	model.addElement(submenu);
	    else
	    	parent.getElements().add(submenu);
	    return submenu;
	}
	
	public Submenu addSubmenu(String labelId){
		return addSubmenu(null, labelId,null);
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
	
}
