package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

import ci.gouv.budget.solde.sigdcp.controller.WebConstantResources;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;

public class MenuBuilder implements Serializable {

	private static final long serialVersionUID = -429285625794463130L;
	
	@Inject private MenuManager menuManager;
	@Inject transient private WebConstantResources webConstantResources;
	
	@Named @Produces /*@RequestScoped*/ @SessionScoped
	public MenuModel getMenuModel(){
		if(Faces.isUserInRole(Role.AGENT_ETAT.name()))
			agentEtat();
		
		if(Faces.isUserInRole(Role.GESTIONNAIRE_CARTE_SOTRA.name()))
			gestionnaireCarteSotra();
		
		if(Faces.isUserInRole(Role.DIRECTEUR.name()))
			directeur();
		
		if(Faces.isUserInRole(Role.LIQUIDATEUR.name()))
			liquidateur();
		
		if(Faces.isUserInRole(Role.PAYEUR.name()))
			payeur();
		
		if(Faces.isUserInRole(Role.RESPONSABLE.name()))
			responsable();
		/*
		Submenu calendrierMissions = menuManager.addSubmenu("menu.calendrier");
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.missions","enregistrerCalendrierForm",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()});
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.exercicecourant","enregistrerExerciceForm",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()});
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.ajoutermission","ajouterMissionForm",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()});
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.consultermissions","consulterCalendrierListe",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"calendrierDetailView"});
		menuManager.addMenuItem(calendrierMissions, "menu.calendrier.suivreexecutionmissions","suivreExecutionCalendrierListe",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"executionCalendrierMissionDetailView"});
		
		
		
		Submenu identification = menuManager.addSubmenu("menu.identification");
		menuManager.addMenuItem(identification, "menu.identification.validersouscriptions","souscriptionliste", new Object[]{});
		menuManager.addMenuItem(identification, "menu.identification.validergcs","souscriptiongestionnairecartesotraliste", new Object[]{
				webConstantResources.getRequestParamNextViewOutcome(),"validergestionairecartesotra"
		});
		menuManager.addMenuItem(identification, "menu.identification.validerlistebasegcs","inscritslistebasegcs",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"validationinscriptionlb"});
		menuManager.addMenuItem(identification, "menu.identification.consulterbeneficiaire","consulterbeneficiairelistebase",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"detailbeneficiairelistebase"});
		*/
		
		Submenu traitementDemande = menuManager.addSubmenu("menu.traitementdemande");
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.recevabilite","validationrecevabilite", new Object[]{
				/*webConstantResources.getRequestParamStatut(),Code.STATUT_SOUMIS/*,webConstantResources.getRequestParamNextViewOutcome(),"validationrecevabilite"*/
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.conformite","validationconformite", new Object[]{
				webConstantResources.getRequestParamStatut(),Code.STATUT_RECEVABLE
		});
		/*
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
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.realiserbtbl","realiserbtbl", new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.listerbt","listerbt", new Object[]{
				
		});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.listerbl","listerbl", new Object[]{
				
		});
		
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.cotationbilletavion","demandecotationbilletavionmission", new Object[]{});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.commandebilletavion","commandebilletavionmission", new Object[]{});
		menuManager.addMenuItem(traitementDemande, "menu.traitementdemande.consulterfdmhci","consulterFeuilleDeplacementListe", new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"feuilleDeplacementDetailView"});

		
		Submenu statistiques = menuManager.addSubmenu("menu.statistiques");
		menuManager.addMenuItem(statistiques, "menu.statistiques.tableaubord","tableaubord", new Object[]{});
		*/
		return menuManager.getModel();
	}
	
	private void agentEtat(){
		Submenu formulerUneDemande = menuManager.addSubmenu("menu.formulerdemande");
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.affectation", "demande_dd",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_AFFECTATION });
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.mutation","demande_dd",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_MUTATION});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.departretraite","demande_dd",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_RETRAITE});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.obseques","demande_o",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.mhci","demande_m",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_MISSION_HCI});
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.tr.mae","demande_t",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE});
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.tr.stage","demande_t",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE});
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.consulter","demandeliste",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demandeconsultation"});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.deposercourrier","demandeliste", new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demandeconsultation"});
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.gerercartesotra","inscriregestionairecartesotra",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
		});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.inscrirelistebasegestionnaire","listebasegestionnaire",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"inscriptionlistebasegcs"});
	}
	
	private void gestionnaireCarteSotra(){
		
	}
	
	private void liquidateur(){
		
	}
	
	private void responsable(){
		
	}
	
	private void directeur(){
		
	}
	
	private void payeur(){
		
	}

}
