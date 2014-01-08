package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;

//@ManagedBean @ViewScoped
public class EditerFeuilleDeplacementController implements Serializable {

	private static final long serialVersionUID = 572544943898480486L;

	private DossierDDService dossierDDService;
	
	//@Inject @Getter
	private EnregistrerDemandeDDController enregistrerDemandeDDController;
	
	
	public String editer(){
		//byte[] etat = dossierDDService.editerFeuilleDeplacement(enregistrerDemandeDDController.getDossierDD());
		return "success";
	}
	
}
