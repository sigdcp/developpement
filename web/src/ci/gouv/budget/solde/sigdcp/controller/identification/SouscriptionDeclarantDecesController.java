package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named @ViewScoped
public class SouscriptionDeclarantDecesController extends AbstractSouscriptionComptePersonneController implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title += " des ayants droit"; 
		demandeurDto.setShowTypePersonne(false);
		demandeurDto.setAgentEtat(false);
		demandeurDto.getInfosSouscriptionComptePersonne().setType(null);
    }
	
}
