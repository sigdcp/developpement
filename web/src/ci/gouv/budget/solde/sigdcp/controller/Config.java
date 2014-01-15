package ci.gouv.budget.solde.sigdcp.controller;

/*
import javax.annotation.PostConstruct;
import javax.el.ELContextEvent;
import javax.el.ELContextListener;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean (eager=true)
@ApplicationScoped
public class Config {
	
	public Config() {
		System.out.println("Config.Config()");
	}
	
    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getApplication().addELContextListener(new ELContextListener() {
            @Override
            public void contextCreated(ELContextEvent event) {
            	System.out
						.println("Config.init().new ELContextListener() {...}.contextCreated()");
                event.getELContext().getImportHandler().importClass("ci.gouv.budget.solde.sigdcp.model.Code");
            }
        });
    }

}*/