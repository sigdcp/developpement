package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

import ci.gouv.budget.solde.sigdcp.controller.WebConstantResources;
import ci.gouv.budget.solde.sigdcp.model.Code;

public class MenuBuilder implements Serializable {

	private static final long serialVersionUID = -429285625794463130L;
	
	@Inject private MenuManagerItem menuManager;
	@Inject transient private WebConstantResources webConstantResources;
	@Inject private RoleManager roleManager;
	
	@Named @Produces /*@RequestScoped*/ @SessionScoped
	public MenuModel getMenuModel(){
		menuManager.initModel();
		
		if(roleManager.isDirecteur())
			directeur();
		else if(roleManager.isPayeur())
			payeur();
		else if(roleManager.isResponsable())
			responsable();
		else if(roleManager.isLiquidateur())
			liquidateur();
		else if(roleManager.isPointFocal())
			pointFocal();
		else if(roleManager.isGestionnaireCarteSotra())
			gestionnaireCarte();
		else if(roleManager.isDelegueSotra())
			delegueCarteSotra();
		else if(roleManager.isAgentEtat())
			agentEtat();
		else if(roleManager.isPrestataire())
			prestataire();
	
		return menuManager.getModel();
	}
	
	/* roles */
		
	private void directeur(){
		responsable();
		//a sup
		//prestataire();
	}
		
	private void responsable(){
		liquidateur();
		edition();
		identification();
		parametrage();
	}
	
	private void liquidateur(){
		traitement();
		agentEtat();
	}

	private void pointFocal(){
		traitement();
		agentEtat();
	}
	
	private void gestionnaireCarte(){
		sotra(); 
		agentEtat(); 
	}
	
	private void delegueCarteSotra(){
		sotra();
		agentEtat();
	}
	
	private void agentEtat(){
		demande();
		sotra();
		consultation();
		
		
	}
	
	private void payeur(){
		traitement();
		agentEtat();
	}
	
	private void prestataire(){
		prestation();
	}
	
	/* menu blocks */
	
	private void identification(){
		Submenu block = menuManager.addSubmenu("menu.identification");
		if(roleManager.isResponsable()){
			menuManager.addMenuItem(block, "menu.identification.validersouscriptions",null,"", new Object[]{});
			menuManager.addMenuItem(block, "menu.identification.gererdroits",null,"", new Object[]{});
		}
	}
	
	private void demande(){
		Submenu formulerUneDemande = menuManager.addSubmenu("menu.formulerdemande");
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.affectation", "demande_dd",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_AFFECTATION });
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.mutation","demande_dd",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_MUTATION});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.departretraite","demande_dd",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_RETRAITE});
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.obseques","demande_o",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS});
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.tr.mae","demande_t",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE});
		
		menuManager.addMenuItem(formulerUneDemande, "menu.formulerdemande.tr.stage","demande_t",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE});
		
		menuManager.addMenuItem(formulerUneDemande,"menu.formulerdemande.mission",null, "demande_m",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudUpdate()
		});
		
		menuManager.addMenuItem(formulerUneDemande,"menu.formulerdemande.cartesotra",null, "demande_cartesotra",new Object[]{});
		
		if(!roleManager.isAgentSolde())
			menuManager.addMenuItem(formulerUneDemande, "menu.identification.agentsolde",null,"", new Object[]{});
		if(!roleManager.isPointFocal())
			menuManager.addMenuItem(formulerUneDemande, "menu.identification.pointfocal",null,"", new Object[]{});
		if(!roleManager.isGestionnaireCarteSotra())
			menuManager.addMenuItem(formulerUneDemande, "menu.identification.gestionnairecartesotra",null,"", new Object[]{});
		
	}
	
	private void sotra(){
		Submenu block = menuManager.addSubmenu("menu.sotra");
		
		if(!roleManager.isDelegueSotra())
			menuManager.addMenuItem(block,"menu.sotra.listebase.sinscrire",null, "joindrelistebasesotra",new Object[]{});
		else{
			menuManager.addMenuItem(block,"menu.sotra.listebase",null, "valideradherentlistebasesotra",new Object[]{});
			menuManager.addMenuItem(block,"menu.sotra.achatcarte",null, "achatcartesotra",new Object[]{});
			menuManager.addMenuItem(block,"menu.sotra.boncommande",null, "realiserboncommandecartesotra",new Object[]{});
			//menuManager.addMenuItem(block,"menu.sotra.distribuerboncommande",null, "distribuerboncommandecartesotra",new Object[]{});
		}
		
	}

	private void traitement() {
		Submenu traitement = menuManager.addSubmenu("menu.traitement");
		
		if (roleManager.isPointFocal())
			menuManager.addMenuItem(traitement, "menu.traitement.organisermission", null, "demande_mission_pointfocal", new Object[] {
					webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
			});
			
		if (roleManager.isResponsable()){
			menuManager.addMenuItem(traitement, "menu.traitement.receptionner", "ui-icon-transferthick-e-w", "validationrecevabilite", new Object[] {});
			
		}
		
		if (roleManager.isLiquidateur() || roleManager.isResponsable() || roleManager.isDirecteur()){
			String liquidationFirstTab = "";
			if (roleManager.isLiquidateur())
				liquidationFirstTab = "mettreenliquidation";
			else if (roleManager.isResponsable())
				liquidationFirstTab = "validermiseenliquidation";
			else if (roleManager.isDirecteur())
				liquidationFirstTab = "viserbulletinliquidation";

			menuManager.addMenuItem(traitement, "menu.traitement.liquider.bulletin", liquidationFirstTab, new Object[] {
					webConstantResources.getRequestParamLiquidation(),webConstantResources.getRequestParamLiquidationBulletin()});	
			menuManager.addMenuItem(traitement, "menu.traitement.liquider.bontransport", liquidationFirstTab, new Object[] {
					webConstantResources.getRequestParamLiquidation(),webConstantResources.getRequestParamLiquidationBonTransport()});	
		}
		
		if (roleManager.isPayeur())
			menuManager.addMenuItem(traitement, "menu.paiement", "ui-icon-cart", "priseenchargebordereau", new Object[] {});
		
		if (roleManager.isResponsable()){
			//menuManager.addMenuItem(traitement, "menu.traitement.prestation", null, "cotation_mhci", new Object[] {});
			menuManager.addMenuItem(traitement, "menu.traitement.commander", null, "cotation_mhci", new Object[] {});
			menuManager.addMenuItem(traitement, "menu.traitement.acheter", null, "", new Object[] {});
			
		}

	}
	
	private void consultation(){
		Submenu block = menuManager.addSubmenu("menu.consultation");
		menuManager.addMenuItem(block,"menu.consultation.listedemande","ui-icon-star", "demandeliste",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demandeconsultation"});
		if (roleManager.isPointFocal())
			menuManager.addMenuItem(block,"menu.consultation.listemissionexecutee",null, "missionexecuteeliste",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demande_mission_pointfocal"});
	}
	
	private void edition(){
		Submenu block = menuManager.addSubmenu("menu.edition");
		menuManager.addMenuItem(block,"menu.edition.listedemandenonliquide",null, "",new Object[]{});
		menuManager.addMenuItem(block,"menu.edition.listebt",null, "btblliste",new Object[]{});
		menuManager.addMenuItem(block,"menu.edition.attpec",null, "attestationpecliste",new Object[]{});
		
	}
	
	private void prestation(){
		Submenu block = menuManager.addSubmenu("menu.prestation");
		menuManager.addMenuItem(block,"menu.prestation.facture.transitbagagges",null, "facturationtransitbagages",new Object[]{});
		
	}
		
	private void parametrage(){
		Submenu block = menuManager.addSubmenu("menu.parametrage");
		menuManager.addMenuItem(block,"menu.parametrage.indemnite",null, "",new Object[]{});
		menuManager.addMenuItem(block,"menu.parametrage.localite",null, "",new Object[]{});
	}
	
	/*
	private void executeUneMission(){
		Submenu mission = menuManager.addSubmenu("menu.mission");
		menuManager.addMenuItem(mission, "menu.mission.demande.pointfocal","demande_mission_pointfocal", new Object[]{});
		menuManager.addMenuItem(mission, "menu.mission.demande.liste","missionexecuteeliste", new Object[]{});
	}*/
	
}
