package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;

@ManagedBean @ViewScoped 
public class DossierListeController {

	@Getter
	private List<DossierDD> list;
	
	@Setter
	private String nextViewId;

	@EJB
	private DossierDDService dossierService ;
	
	@PostConstruct
	public void dossierListeController() {
		list = dossierService.findAll(); 
	}
	
	public String outcome(Dossier dossier){
		return nextViewId;
	}
	
	
}
