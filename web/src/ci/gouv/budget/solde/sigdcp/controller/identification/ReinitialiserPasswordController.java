package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Named @ViewScoped
public class ReinitialiserPasswordController extends DeverouillerCompteUtilisateurController implements Serializable {
 
	private static final long serialVersionUID = 6591392098578555259L;
	
	/*
	 * Dtos
	 */
	@NotNull(groups=Client.class)
	@Getter @Setter private String confirmationMotPasse;
	
	
}
