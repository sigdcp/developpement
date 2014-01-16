package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

import ci.gouv.budget.solde.sigdcp.controller.WebConstantResources;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;

@Named @SessionScoped 
public class UserSessionManager implements Serializable{

	private static final long serialVersionUID = 258649685790992448L;

	/*
	 * Services
	 */
	@Inject private AgentEtatService agentEtatService;
	@Inject private MenuManager menuManager;
	@Inject transient private WebConstantResources webConstantResources;
	
	@Getter @Setter
	private CompteUtilisateur compte;
	
	public Boolean isLoggedIn(){
		return StringUtils.isNotEmpty(Faces.getRemoteUser());
	}
	
	/**
	 * The connected user
	 * @return
	 */
	@Named @SessionScoped @User
	public Personne getUser(){
		//return compte.getPersonne();
		return agentEtatService.findAll().get(0);// TODO to be removed : just for testing
	}
	
	@Named @Produces /*@RequestScoped*/ @SessionScoped
	public MenuModel getMenuModel(){
		Submenu formulerUneDemande = menuManager.addSubmenu("menu.formulerdemande");
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.affectation", "demandeddForm",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_AFFECTATION });
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.mutation","demandeddForm",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_MUTATION});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.departretraite","demandeddForm",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_RETRAITE});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.obseques","demandefoForm",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.mhci","demandemhciForm",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_MISSION_HCI});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.tr","demandetrForm",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.consulter","demandeliste",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demandeconsultation"});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.deposercourrier","demandeliste", new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demandeconsultation"});
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.gerercartesotra","inscriregestionairecartesotra",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
		});
		
		Submenu calendrierMissions = menuManager.addSubmenu("menu.calendrier");
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.missions","enregistrerCalendrierForm",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()});
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.exercicecourant","enregistrerExerciceForm",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()});
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.ajoutermission","ajouterMissionForm",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()});
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.consultermissions","consulterCalendrierListe",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"calendrierDetailView"});
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.suivreexecutionmissions","suivreExecutionMissionListe");
		
		
		Submenu identification = menuManager.addSubmenu("menu.identification");
		menuManager.addMenuItem(identification, "menu.identification.validersouscriptions","souscriptionliste", new Object[]{});
		menuManager.addMenuItem(identification, "menu.identification.validergcs","souscriptiongestionnairecartesotraliste", new Object[]{
				webConstantResources.getRequestParamNextViewOutcome(),"validergestionairecartesotra"
		});
		
		
		Submenu traitementDemande = menuManager.addSubmenu("menu.traitementdemande");
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.recevabilite","dossierliste", new Object[]{
				webConstantResources.getRequestParamStatut(),Code.STATUT_SOUMIS,webConstantResources.getRequestParamNextViewOutcome(),"validationrecevabilite"
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.conformite","validationconformite", new Object[]{
				webConstantResources.getRequestParamStatut(),Code.STATUT_RECEVABLE
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.viserdocuments","viserdocument", new Object[]{});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.realiserfdmhci","realiserFeuilleDeplacementListe", new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.receptionnerfdmhci","receptionnerFeuilleDeplacementListe", new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.attestationprisecharge","generationattestationpc", new Object[]{
				webConstantResources.getRequestParamStatut(),Code.STATUT_CONFORME
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.facture","facturedefinitiveForm", new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.facturedefinitives","listefacturedef", new Object[]{});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.genererprojetremboursement","generationprojetdecisionremb", new Object[]{
				webConstantResources.getRequestParamStatut(),Code.STATUT_CONFORME
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.receptionnerdecisionremboursement","dossierliste", new Object[]{
				webConstantResources.getRequestParamStatut(),Code.STATUT_CONFORME,webConstantResources.getRequestParamNextViewOutcome(),"decisionrembForm"
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.cotationbilletavion","demandecotationbilletavionmission", new Object[]{});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.commandebilletavion","commandebilletavionmission", new Object[]{});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.consulterfdmhci","consulterFeuilleDeplacementListe", new Object[]{});

		
		Submenu statistiques = menuManager.addSubmenu("menu.statistiques");
		menuManager.addMenuItem(statistiques, "menu.statistiques.tableaubord","tableaubord", new Object[]{});
		
		return menuManager.getModel();
	}
	
	
	
}
