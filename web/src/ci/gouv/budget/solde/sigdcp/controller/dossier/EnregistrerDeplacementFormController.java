package ci.gouv.budget.solde.sigdcp.controller.dossier;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import lombok.Getter;
import lombok.Setter;




@ManagedBean @ViewScoped @Getter @Setter
public class EnregistrerDeplacementFormController {
	private Deplacement deplacement=new Deplacement();




	public void enregistrer() {
		
		//dossierService.inscrire(dossierService);
	}

	
}
