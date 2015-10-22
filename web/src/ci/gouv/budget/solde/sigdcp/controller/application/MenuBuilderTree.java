package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.TreeNode;

import ci.gouv.budget.solde.sigdcp.controller.WebConstantResources;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.service.sotra.BeneficiaireCarteSotraService;

public class MenuBuilderTree implements Serializable {

	private static final long serialVersionUID = -429285625794463130L;
	
	@Inject private MenuManagerTreeNode menuManager;
	@Inject transient private WebConstantResources webConstantResources;
	@Inject transient private RoleManager roleManager;
	@Inject transient private UserSessionManager userSessionManager;
	@Inject private BeneficiaireCarteSotraService beneficiaireCarteSotraService;
	
	@Named @Produces /*@RequestScoped*/ @SessionScoped
	public TreeNode getMenuModelTree(){
		menuManager.initModel();
		
		if(roleManager.isAdministrateur())
			administrateur();
		else if(roleManager.isDirecteur())
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
		else if(roleManager.isAgentMission())
			agentMission();
		else if(roleManager.isPrestataire())
			prestataire();
		else if(roleManager.isAyantDroit())
			ayantDroitFo();
	
		
		return menuManager.getModel();
	}
		
	/* roles */
	
	private void administrateur(){
		directeur();
		//a sup
		//prestataire();
	}
	
	private void directeur(){
		responsable();
		//a sup
		//prestataire();
	}
		
	private void responsable(){
		demandeSolde();
		liquidateur();
		mission();
		identification();
		edition();
		statistique();
		parametrage();
	}
	
	private void liquidateur(){
		agentEtat();
		traitement();
		
	}

	private void pointFocal(){
		agentEtat();
		mission();
		traitement();
	}
	
	private void gestionnaireCarte(){
		agentEtat(); 
		sotra(); 
		
	}
	
	private void delegueCarteSotra(){
		agentEtat();
	}
	
	private void agentEtat(){
		demande();
		sotra();
		consultation();
	}
	
	private void agentMission(){
		demande();
		consultation();
	}
	
	private void ayantDroitFo(){
		demande();
		consultation();
	}
	
	private void payeur(){
		agentEtat();
		traitement();
		
	}
	
	private void prestataire(){
		prestation();
	}
	
	/* menu blocks */
	
	private void identification(){
		if(roleManager.isAdministrateur()){
			TreeNode block = menuManager.addSubmenu("menu.identification",null);
			
			menuManager.addMenuItem(block, "menu.identification.validersouscriptions",null,"", new Object[]{});
			menuManager.addMenuItem(block, "menu.identification.gererdroits",null,"profilesagentetat", new Object[]{});
			
			/*
			if(!roleManager.isGestionnaireCarteSotra())
				menuManager.addMenuItem(block, "menu.identification.gestionnairecartesotra",null,"", new Object[]{});
			if(!roleManager.isAgentSolde())
				menuManager.addMenuItem(block, "menu.identification.agentsolde",null,"", new Object[]{});
			if(!roleManager.isPointFocal())
				menuManager.addMenuItem(block, "menu.identification.pointfocal",null,"", new Object[]{});
			*/
		}
	}

	private void demandeSolde(){
		TreeNode block = menuManager.addSubmenu("menu.formulerdemandesolde",null);
	//if(!roleManager.isAgentSolde())
		menuManager.addMenuItem(block, "menu.formulerdemande.affectation", "demande_dd",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_AFFECTATION,webConstantResources.getRequestParamSolde(),Code.OPERATER_SOLDE });
		menuManager.addMenuItem(block, "menu.formulerdemande.mutation","demande_dd",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_MUTATION,webConstantResources.getRequestParamSolde(),Code.OPERATER_SOLDE});
		menuManager.addMenuItem(block, "menu.formulerdemande.departretraite","demande_dd",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_RETRAITE,webConstantResources.getRequestParamSolde(),Code.OPERATER_SOLDE});
		menuManager.addMenuItem(block, "menu.formulerdemande.tr.mae","demande_t",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE,webConstantResources.getRequestParamSolde(),Code.OPERATER_SOLDE});
		menuManager.addMenuItem(block, "menu.formulerdemande.tr.stage","demande_t",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE,webConstantResources.getRequestParamSolde(),Code.OPERATER_SOLDE});
		menuManager.addMenuItem(block,"menu.formulerdemande.mission",null, "demande_m",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudUpdate()
				,webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_MISSION_HCI});	
		menuManager.addMenuItem(block,"menu.formulerdemande.mtt",null, "demande_tt",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_STAGE,webConstantResources.getRequestParamSolde(),Code.OPERATER_SOLDE});
		menuManager.addMenuItem(block,"menu.formulerdemande.mtt.exp",null, "demande_tt",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_CONGE,webConstantResources.getRequestParamSolde(),Code.OPERATER_SOLDE});
		menuManager.addMenuItem(block, "menu.formulerdemande.obseques","demande_o",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS,webConstantResources.getRequestParamSolde(),Code.OPERATER_SOLDE});
	}
	
	private void demande(){
		TreeNode block = menuManager.addSubmenu("menu.formulerdemande",null);
		if(roleManager.isAgentEtat() || roleManager.isAgentMission()){
			if( userSessionManager.getIsIvoirien() ){
				
				if(roleManager.isAgentEtat()){
				menuManager.addMenuItem(block, "menu.formulerdemande.affectation", "demande_dd",new Object[]{
						webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_AFFECTATION });
				
				menuManager.addMenuItem(block, "menu.formulerdemande.mutation","demande_dd",new Object[]{
						webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_MUTATION});
				menuManager.addMenuItem(block, "menu.formulerdemande.departretraite","demande_dd",new Object[]{
						webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_RETRAITE});
				
				menuManager.addMenuItem(block, "menu.formulerdemande.tr.mae","demande_t",new Object[]{
						webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE});
				
				menuManager.addMenuItem(block, "menu.formulerdemande.tr.stage","demande_t",new Object[]{
						webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE});
				}
				
				menuManager.addMenuItem(block,"menu.formulerdemande.mission",null, "demande_m",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudUpdate()
						,webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_MISSION_HCI});	
				
				if(roleManager.isAgentEtat())
				menuManager.addMenuItem(block,"menu.formulerdemande.mtt",null, "demande_tt",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
						,webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_STAGE});
			}else
				menuManager.addMenuItem(block,"menu.formulerdemande.mtt",null, "demande_tt",new Object[]{webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
					,webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_CONGE});
		}
		
		if( userSessionManager.getIsIvoirien()  && !roleManager.isAgentMission())
			menuManager.addMenuItem(block, "menu.formulerdemande.obseques","demande_o",new Object[]{
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate(),webConstantResources.getRequestParamNatureDeplacement(),Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS});
		
		if(roleManager.isAgentEtat() && userSessionManager.getIsIvoirien()){
			if(beneficiaireCarteSotraService.estAdherent())
				menuManager.addMenuItem(block,"menu.formulerdemande.cartesotra",null, "demande_cartesotra",new Object[]{});
			else
				menuManager.addMenuItem(block,"menu.sotra.listebase.sinscrire",null, "joindrelistebasesotra",new Object[]{});
		}
	}
	
	private void sotra(){
		if(roleManager.isGestionnaireCarteSotra() || roleManager.isDelegueSotra()){
			TreeNode block = menuManager.addSubmenu("menu.sotra",null);
			
			if(roleManager.isDelegueSotra()){
				menuManager.addMenuItem(block,"menu.sotra.gestion",null, "valideradherentlistebasesotra",new Object[]{});
				/*
				menuManager.addMenuItem(block,"menu.sotra.listebase",null, "valideradherentlistebasesotra",new Object[]{});
				menuManager.addMenuItem(block,"menu.sotra.achatcarte",null, "achatcartesotra",new Object[]{});
				*/
			}
			if(roleManager.isGestionnaireCarteSotra()){
				menuManager.addMenuItem(block,"menu.sotra.boncommande",null, "realiserboncommandecartesotra",new Object[]{});
			}
		}
		
	}

	private void traitement() {
		if (roleManager.isLiquidateur() || roleManager.isResponsable() || roleManager.isDirecteur() || roleManager.isPayeur()){
			TreeNode traitement = menuManager.addSubmenu("menu.traitement",null);
				
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
	
				menuManager.addMenuItem(traitement, "menu.traitement.liquider", liquidationFirstTab, new Object[] {
						webConstantResources.getRequestParamLiquidation(),webConstantResources.getRequestParamLiquidationBulletin()});	
				//menuManager.addMenuItem(traitement, "menu.traitement.liquider.bontransport", liquidationFirstTab, new Object[] {
				//		webConstantResources.getRequestParamLiquidation(),webConstantResources.getRequestParamLiquidationBonTransport()});	
			}
			
			if (roleManager.isPayeur())
				menuManager.addMenuItem(traitement, "menu.paiement", "ui-icon-cart", "priseenchargebordereau", new Object[] {});
			
			if (roleManager.isResponsable()){
				//menuManager.addMenuItem(traitement, "menu.traitement.prestation", null, "cotation_mhci", new Object[] {});
				menuManager.addMenuItem(traitement, "menu.traitement.commande", null, "mission_mhci_a_coter", new Object[] {});
				//menuManager.addMenuItem(traitement, "menu.traitement.acheter", null, "", new Object[] {});
				
			}
			
			if (roleManager.isLiquidateur() || roleManager.isResponsable() || roleManager.isDirecteur()){
				menuManager.addMenuItem(traitement, "menu.traitement.rembourser", null, "etablirprojetdecision", new Object[] {});
				menuManager.addMenuItem(traitement, "menu.traitement.authentifierdoc", null, "authentificationdoc", new Object[] {});
			}
		}

	}
	
	private void mission(){
		TreeNode block = menuManager.addSubmenu("menu.mission",null);
		
		if (roleManager.isAgentSolde())
			menuManager.addMenuItem(block,"menu.mission.lettreavance",null, "lettreavance",new Object[]{});
		
		menuManager.addMenuItem(block, "menu.traitement.organisermission", null, "demande_mission_pointfocal", new Object[] {
				webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudCreate()
		});
		
		if (roleManager.isPointFocal()){
			menuManager.addMenuItem(block,"menu.traitement.agentmission",null, "agentmission",new Object[]{});
			menuManager.addMenuItem(block,"menu.consultation.listemissionexecutee",null, "missionexecuteeliste",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demande_mission_pointfocal"});
		}
	}
	
	private void consultation(){
		TreeNode block = menuManager.addSubmenu("menu.consultation",null);
		menuManager.addMenuItem(block,"menu.consultation.listedemande","ui-icon-star", "demandeliste",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demandeconsultation"});
		
	}
	
	private void edition(){
		TreeNode block = menuManager.addSubmenu("menu.edition",null);
		//menuManager.addMenuItem(block,"menu.edition.listedemandenonliquide",null, "",new Object[]{});
		menuManager.addMenuItem(block,"menu.edition.bl",null, "blediterliste",new Object[]{});
		menuManager.addMenuItem(block,"menu.edition.listebt",null, "btblliste",new Object[]{});
		menuManager.addMenuItem(block,"menu.edition.attpec",null, "attestationpecliste",new Object[]{});
		menuManager.addMenuItem(block,"menu.edition.projetremb",null, "projetrembliste",new Object[]{});
		menuManager.addMenuItem(block,"menu.edition.listedemandesolde","ui-icon-star", "demandeliste",new Object[]{webConstantResources.getRequestParamNextViewOutcome(),"demandeconsultation",webConstantResources.getRequestParamSolde(),Code.OPERATER_SOLDE});
	}
	
	private void prestation(){
		if(userSessionManager.getUserAsPrestataire()==null)
			return;
		TreeNode block = menuManager.addSubmenu("menu.prestation",null);
		switch(userSessionManager.getUserAsPrestataire().getType().getCode()){
		case Code.TYPE_PRESTATAIRE_TR:
			menuManager.addMenuItem(block,"menu.prestation.facture.transitbagagges",null, "facturationtransitbagages",new Object[]{});
			break;
		case Code.TYPE_PRESTATAIRE_AV:
			menuManager.addMenuItem(block,"menu.prestation.cotationmhci.recues",null, "demande_cotation_mhci_liste",new Object[]{});
			menuManager.addMenuItem(block,"menu.prestation.consulter.commande.mhci",null, "consultercommandemhci",new Object[]{});		
			break;
		case Code.TYPE_PRESTATAIRE_PF:
			menuManager.addMenuItem(block,"menu.prestation.cotationfo.recues",null, "demande_cotation_fo_liste",new Object[]{});
			menuManager.addMenuItem(block,"menu.prestation.consulter.commande.fo",null, "consultercommandefo",new Object[]{});
			break;
		}
	}
		
	private void parametrage(){
		TreeNode block = menuManager.addSubmenu("menu.entitereference",null);
		menuManager.addMenuItem(block,"menu.entitereference.agentetat",null, "paramagentetat",new Object[]{});
		
		menuManager.addMenuItem(block,"menu.entitereference.categorie",null, "paramcategorie",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.grade",null, "paramgrade",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.groupedd",null, "paramgroupedd",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.groupemission",null, "paramgroupemhci",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.typeclassevoyage",null, "paramtypeClasseVoyage",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.typesection",null, "paramtypesection",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.section",null, "paramsection",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.typepiecejustificative",null, "paramtypepiecejustificative",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.typepieceproduite",null, "paramtypepieceproduite",new Object[]{});
		
		menuManager.addMenuItem(block,"menu.entitereference.role",null, "paramrole",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.categoriedeplacement",null, "paramcategoriedeplacement",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.position",null, "paramposition",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.questionsecrete",null, "paramquestionsecrete",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.situationmatrimoniale",null, "paramsituationmatrimoniale",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.statut",null, "paramstatut",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.fonction",null, "paramfonction",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.typedepense",null, "paramtypedepense",new Object[]{});
		
		menuManager.addMenuItem(block,"menu.entitereference.profession",null, "paramprofession",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.typelocalite",null, "paramtypelocalite",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.causedeces",null, "paramcausedeces",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.distancelocalite",null, "paramdistancelocalite",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.echelon",null, "paramechelon",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.formuleindemnite",null, "paramformuleindemnite",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.localite",null, "paramlocalite",new Object[]{});
		menuManager.addMenuItem(block,"menu.entitereference.cercueil",null, "paramcercueil",new Object[]{});
	}
	
	private void statistique(){
		TreeNode block = menuManager.addSubmenu("menu.statistique.bilan",null);
		
		/*menuManager.addMenuItem(block,"menu.statistique.lettreavance",null, "statlettreavance",new Object[]{});*/
		
		TreeNode smenudep=menuManager.addSubmenu(block,"menu.statistique.depdef",null);
		menuManager.addMenuItem(smenudep,"menu.statistique.depdef.bilan",null, "statbilandd",new Object[]{});
		
		TreeNode smenuobs=menuManager.addSubmenu(block,"menu.statistique.obseque",null);
		menuManager.addMenuItem(smenuobs,"menu.statistique.obseque.bilan",null, "statbilanobsq",new Object[]{});
		
		TreeNode smenutr=menuManager.addSubmenu(block,"menu.statistique.transit",null);
		menuManager.addMenuItem(smenutr,"menu.statistique.transit.bilan",null, "statbilantr",new Object[]{});
		
		TreeNode smenum=menuManager.addSubmenu(block,"menu.statistique.mission",null);
		menuManager.addMenuItem(smenum,"menu.statistique.mission.bilan",null, "statbilanmis",new Object[]{});
		menuManager.addMenuItem(smenum,"menu.statistique.mission.lettreavance",null, "bilanlettreavance",new Object[]{});
		menuManager.addMenuItem(smenum,"menu.statistique.mission.situationindemnite",null, "situationindemnite",new Object[]{});
		menuManager.addMenuItem(smenum,"menu.statistique.mission.consomationindemnite",null, "consomationindemnite",new Object[]{});
		menuManager.addMenuItem(smenum,"menu.statistique.mission.consomationbilletlocalite",null, "consomationbilletlocalite",new Object[]{});
		menuManager.addMenuItem(smenum,"menu.statistique.mission.consomationbilletprestataire",null, "consomationbilletprestataire",new Object[]{});
		
		/*menuManager.addMenuItem(block,"menu.statistique.depdef.recevable",null, "statdd",new Object[]{webConstantResources.getRequestParamNatureOperation(),Code.NATURE_OPERATION_RECEVABILITE,webConstantResources.getRequestParamStatut(),Code.STATUT_ACCEPTE});
		menuManager.addMenuItem(block,"menu.statistique.depdef.non.recevable",null, "statdd",new Object[]{webConstantResources.getRequestParamNatureOperation(),Code.NATURE_OPERATION_RECEVABILITE,webConstantResources.getRequestParamStatut(),Code.STATUT_REJETE});
		menuManager.addMenuItem(block,"menu.statistique.depdef.conforme",null, "statdd",new Object[]{webConstantResources.getRequestParamNatureOperation(),Code.NATURE_OPERATION_CONFORMITE,webConstantResources.getRequestParamStatut(),Code.STATUT_ACCEPTE});
		menuManager.addMenuItem(block,"menu.statistique.depdef.non.conforme",null, "statdd",new Object[]{webConstantResources.getRequestParamNatureOperation(),Code.NATURE_OPERATION_CONFORMITE,webConstantResources.getRequestParamStatut(),Code.STATUT_REJETE});*/
	}
	
	/*
	private void executeUneMission(){
		Submenu mission = menuManager.addSubmenu("menu.mission");
		menuManager.addMenuItem(mission, "menu.mission.demande.pointfocal","demande_mission_pointfocal", new Object[]{});
		menuManager.addMenuItem(mission, "menu.mission.demande.liste","missionexecuteeliste", new Object[]{});
	}*/
	
}
