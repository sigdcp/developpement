package ci.gouv.budget.solde.sigdcp.controller.infos;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

@Named @RequestScoped
public class NatureDeplacementInfosController extends AbstractEntityFormUIController<NatureDeplacement> implements Serializable {

	private static final long serialVersionUID = 7404320093212948431L;

	@Getter private String outcome;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = entity.getLibelle();
		
		if(Code.CATEGORIE_DEPLACEMENT_DEFINITIF.equals(entity.getCategorie().getCode()))
			outcome = "demande_dd";
		else if(Code.CATEGORIE_DEPLACEMENT_MISSION.equals(entity.getCategorie().getCode())){
			if(Code.NATURE_DEPLACEMENT_MISSION_HCI.equals(entity.getCode()))
				outcome = "demande_m";
			else
				outcome = "demande_tt";
		}else if(Code.CATEGORIE_DEPLACEMENT_OBSEQUE.equals(entity.getCategorie().getCode()))
			outcome = "demande_o";
		else if(Code.CATEGORIE_DEPLACEMENT_TRANSIT.equals(entity.getCategorie().getCode()))
			outcome = "demande_t";
		
	}
	
	
}
