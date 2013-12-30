package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;

/**
 * Les constantes du systeme
 * @author christian
 *
 */
@Singleton @Named(value="webConstantResources")
public class ConstantResources implements Serializable{
	
	private static final long serialVersionUID = 1583754563831914427L;

	
	
	@Getter private final String requestParamNatureDeplacement = "nd";
	@Getter private final String requestParamAction = "action";
	@Getter private final String requestParamActionEditer = "editer";
	@Getter private final String requestParamActionConsulter = "consulter";
	@Getter private final String requestParamEntityId = "eid";
	
	@Getter private final String inputMatriculePattern = "999999a";
	@Getter private final String inputDatePattern = "99/99/9999*";
	@Getter private final String inputPhoneNumberPattern = "99 99 99 99";
	//@Getter private final String inputMailBoxPattern = "99 99 99 99";
	

	@Getter @Produces @Named private final String valueRequiredMessage = "Champ obligatoire";
	@Getter @Produces @Named private final String valuesRequiredMessage = "Tous les champs avec * sont obligatiore";
	@Getter @Produces @Named private final String noSelectionOptionMessage = "--Selectionnez--";

	
}
