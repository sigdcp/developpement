package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.model.Code;

@Named
@Singleton
@Getter
@Setter
public class RoleManager implements Serializable {

	private static final long	serialVersionUID	= -2312071699088910783L;

	public boolean isAgentEtat() {
		return Faces.isUserInRole(Code.ROLE_AGENT_ETAT);
	}
	public boolean isAgentMission() {
		return Faces.isUserInRole(Code.ROLE_AGENT_MISSION);
	}

	public boolean isLiquidateur() {
		return Faces.isUserInRole(Code.ROLE_LIQUIDATEUR);
	}

	public boolean isResponsable() {
		return Faces.isUserInRole(Code.ROLE_RESPONSABLE);
	}

	public boolean isDirecteur() {
		return Faces.isUserInRole(Code.ROLE_DIRECTEUR);
	}

	public boolean isPayeur() {
		return Faces.isUserInRole(Code.ROLE_PAYEUR);
	}
	
	public boolean isPointFocal() {
		return Faces.isUserInRole(Code.ROLE_POINT_FOCAL);
	}
	
	public boolean isGestionnaireCarteSotra() {
		return Faces.isUserInRole(Code.ROLE_GESTIONNAIRE_CARTE_SOTRA);
	}
	public boolean isPrestataire() {
		return Faces.isUserInRole(Code.ROLE_PRESTATAIRE);
	}
	
	public boolean isAyantDroit() {
		return Faces.isUserInRole(Code.ROLE_AYANT_DROIT_FO);
	}
	
	public boolean isAgentSolde() {
		return Faces.isUserInRole(Code.ROLE_AGENT_SOLDE);
	}
	public boolean isDelegueSotra() {
		return Faces.isUserInRole(Code.ROLE_DELEGUE_SOTRA);
	}
	
	public boolean isAdministrateur() {
		return Faces.isUserInRole(Code.ROLE_ADMINISTRATEUR);
	}
}
