package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;

import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;


@ManagedBean @ViewScoped @Getter @Setter
public class EnregistrerMissionMHCIFormController {
	
	private Mission mission=new Mission();


	public void ajouterParticipant() {
		
        Map<String,Object> options = new HashMap<String, Object>();    
        options.put("modal", true);  
        options.put("draggable", false);  
        options.put("resizable", false);  
        options.put("contentHeight", 320);
		RequestContext.getCurrentInstance().openDialog("ajouterParticipantMHCIForm");
	}

	
	public void enregistrer() {
		
		//dossierService.inscrire(dossierService);
	}
	
	
	

	
}
