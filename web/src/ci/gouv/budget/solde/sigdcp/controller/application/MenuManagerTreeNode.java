package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import lombok.Getter;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class MenuManagerTreeNode extends AbstractMenuManager<TreeNode,TreeNode, TreeNode> implements Serializable {

	private static final long serialVersionUID = 3655022050936615701L;
	private static final String ON_CLICK_FORMAT = "window.location.href=\"%s\"";
	
	@Getter private TreeNode model;

	public void initModel(){
		model = new DefaultTreeNode("root", null);  
	}
	
	public TreeNode addSubmenu(TreeNode parent,String labelId,String icon){
		DefaultTreeNode submenu = new DefaultTreeNode(new MenuItemTreeNode(textService.find(labelId),null,icon,null),parent==null?model:parent);  
	    return submenu;
	}
	
	public TreeNode addMenuItem(TreeNode parent,String labelId,String icon,String outcome,Object[] parameters){
		String onClick = String.format(ON_CLICK_FORMAT, navigationManager.url(outcome,parameters,false,false));
		DefaultTreeNode menuItem = new DefaultTreeNode(new MenuItemTreeNode(textService.find(labelId),onClick,null,null),parent==null?model:parent);   
	    //menuItem.setTitle((String)menuItem.getValue());
	    //menuItem.setOutcome(navigationManager.url(outcome,parameters));
		//menuItem.setOutcome(outcome);
		/*
		if(parameters!=null)
			for(int i=0;i<parameters.length-1;i=i+2)
				menuItem.setParam((String)parameters[i], parameters[i+1]);
	    */
	    
		//menuItem.setIcon(icon); 
	    return menuItem;
	}
	
}
