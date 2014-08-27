package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.controller.NavigationManager;
import ci.gouv.budget.solde.sigdcp.service.utils.TextService;


public abstract class AbstractMenuManager<ELEMENT,ITEM,SUBMENU> implements MenuManager<ELEMENT,ITEM,SUBMENU>,Serializable {

	private static final long serialVersionUID = 3655022050936615701L;
	
	@Inject protected TextService textService;
	@Inject protected NavigationManager navigationManager;
	
	public SUBMENU addSubmenu(String labelId,String icon){
		return addSubmenu(null, labelId,icon);
	}
	
	public ITEM addMenuItem(ELEMENT parent,String labelId,String icon,String outcome){
		return addMenuItem(parent, labelId, outcome, new Object[]{});
	}
	
	public ITEM addMenuItem(ELEMENT parent,String labelId,String outcome,Object[] parameters){
		return addMenuItem(parent, labelId, null,outcome, parameters);
	}
	
	public ITEM addMenuItem(ELEMENT parent,String labelId,String outcome){
		return addMenuItem(parent, labelId, null,outcome, new Object[]{});
	}
	
	public ITEM addMenuItem(String labelId,String icon,String outcome,Object[] parameters){
		return addMenuItem(null, labelId, icon, outcome, parameters);
	}
	
	public ITEM addMenuItem(String labelId,String icon,String outcome){
		return addMenuItem(null, labelId, icon, outcome, new Object[]{});
	}
	
	public ITEM addMenuItem(String labelId,String outcome,Object[] parameters){
		return addMenuItem(labelId, null, outcome, parameters);
	}
	
	public ITEM addMenuItem(String labelId,String outcome){
		return addMenuItem(labelId, outcome,null, new Object[]{});
	}

}
