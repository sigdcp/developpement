package ci.gouv.budget.solde.sigdcp.controller.application;

public interface MenuManager<ELEMENT,ITEM,SUBMENU> {
	
	void initModel();
	
	SUBMENU addSubmenu(SUBMENU parent,String labelId,String icon);
	
	SUBMENU addSubmenu(String labelId,String icon);
	
	ITEM addMenuItem(ELEMENT parent,String labelId,String icon,String outcome,Object[] parameters);
	
	ITEM addMenuItem(ELEMENT parent,String labelId,String icon,String outcome);
	
	ITEM addMenuItem(ELEMENT parent,String labelId,String outcome,Object[] parameters);
	
	ITEM addMenuItem(ELEMENT parent,String labelId,String outcome);
	
	ITEM addMenuItem(String labelId,String icon,String outcome,Object[] parameters);
	
	ITEM addMenuItem(String labelId,String icon,String outcome);
	
	ITEM addMenuItem(String labelId,String outcome,Object[] parameters);
	
	ITEM addMenuItem(String labelId,String outcome);

}
