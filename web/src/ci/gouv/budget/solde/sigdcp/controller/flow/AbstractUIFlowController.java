package ci.gouv.budget.solde.sigdcp.controller.flow;

import java.io.Serializable;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.AbstractFormUIController;

@Getter @Deprecated
public class AbstractUIFlowController extends AbstractFormUIController<Object> implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	
	private Boolean showPrevisousAction,showCancelAction=Boolean.TRUE,showNextAction=Boolean.TRUE;
	
	protected String flowId;
	    
    // fonction de sortie du flow
    public String getReturnValue() {
        return null;
    }   
    

}
