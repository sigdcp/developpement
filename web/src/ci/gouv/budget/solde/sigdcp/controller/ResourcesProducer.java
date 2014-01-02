package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;

public class ResourcesProducer implements Serializable {

	private static final long serialVersionUID = 3781956367658401359L;

	@Produces
	public FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	@Produces
	public ConfigurableNavigationHandler getNavigationHandler(){
		return (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
	}
	
}
