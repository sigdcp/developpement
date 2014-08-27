package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named @ViewScoped
public class SouscriptionPrestataireController extends AbstractSouscriptionComptePrestataireController implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title += " des prestataires"; 
		
    }
	
	
}
