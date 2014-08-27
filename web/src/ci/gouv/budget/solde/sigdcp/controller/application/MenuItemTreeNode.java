package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuItemTreeNode implements Serializable {

	private static final long serialVersionUID = -2790284664115698832L;

	private String label,onClick,icon,tooltip;
	private boolean expanded=false;

	public MenuItemTreeNode(String label, String onClick,String icon, String tooltip) {
		this.label = label;
		this.onClick = onClick;
		this.icon = icon;
		this.tooltip = tooltip;
	}
	
	
	
}
