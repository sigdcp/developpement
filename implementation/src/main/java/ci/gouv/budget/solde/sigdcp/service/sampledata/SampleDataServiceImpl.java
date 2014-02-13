package ci.gouv.budget.solde.sigdcp.service.sampledata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.IOUtils;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Mission;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.CauseDeces;
import ci.gouv.budget.solde.sigdcp.model.dossier.Courrier;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.GroupeTypePiece;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Operation;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.geographie.TypeLocalite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.model.identification.Echelon;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.identification.Position;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.identification.QuestionSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.ReponseSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.SituationMatrimoniale;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.TypePersonne;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeSection;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.InfosSouscriptionComptePersonne;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionGestionnaireCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.service.SampleDataService;


@Stateless//(mappedName="SampleDataService") @Remote
public class SampleDataServiceImpl implements SampleDataService {

	@PersistenceContext
	private EntityManager em ;
	private Exercice exercice2012,exercice2013,exercice2014;
	private TypeDepense priseEnCharge,remboursement;
	private TypeAgentEtat fonctionnaire,contractuel,policier,gendarme;
	private TypePersonne ayantDroit;
	private TypePieceJustificative extraitNaissance,extraitMariage,cni,feuilleDep,bonTransport;
	private Prestataire elohimVoyages,mistralVoyages;
	private Localite abidjan,bouake,paris,dakar,delhi,coteDivoire;
	private NatureDeplacement mhci,natureDeplacementMutation,natureDeplacementAffectation,natureDeplacementDepartRetraite;
	private AgentEtat agentEtat1,agentEtat2,agentEtat3,agentEtat4;
	private Section ministereEconomie,ministereBudget,ministereSante,serviceExploitation,serviceEtude;
	private List<CalendrierMission> calendrierMissions=new LinkedList<>();
	private List<DossierMission> dossierMissions = new LinkedList<>();
	private List<PieceProduite> pieceProduites = new LinkedList<>();
	private List<Object[]> bulletinLiquidations = new LinkedList<>();
	private NatureOperation natureOperationLiquidation,natureOperationRBTBL,natureOperationRBTF,natureOperationRFDMission;
	private TypePieceProduite typePieceProduiteBL,typePieceProduiteBT;
	private QuestionSecrete  questionSecrete1,questionSecrete2,questionSecrete3;
	private NatureOperation saisieO,soumission;
	private Statut saisieS,soumis;
	
	@Override 
	public void create() {
		em.persist(questionSecrete1 = new QuestionSecrete("Quel est le nom de votre 1er Chef de service"));
		em.persist(questionSecrete2 = new QuestionSecrete("Quel est le nom de votre fruit préféré?"));
		em.persist(questionSecrete3 = new QuestionSecrete("Quel direction vous visiez pour votre 1ere affection?"));
		
		em.persist(typePieceProduiteBL = new TypePieceProduite(Code.TYPE_PIECE_PRODUITE_BL, "Bulletin de liquidation"));
		em.persist(typePieceProduiteBT = new TypePieceProduite(Code.TYPE_PIECE_PRODUITE_BT, "Bordereau de transmission"));
		
		em.persist(exercice2012 = new Exercice(2012, date(), date(), null, false));
		em.persist(exercice2013 = new Exercice(2013, date(), date(), null, false));
		em.persist(exercice2014 = new Exercice(2014, date(), date(), null, true));
		
		em.persist(new TypeClasseVoyage(Code.TYPE_CLASSE_VOYAGE_1ERE,"1ère classe"));
		em.persist(new TypeClasseVoyage(Code.TYPE_CLASSE_VOYAGE_2EME,"2ème classe"));
		em.persist(new TypeClasseVoyage(Code.TYPE_CLASSE_VOYAGE_TOURISTE,"classe touriste"));
		
		em.persist(new GroupeMission(Code.GROUPE_MISSION_HORS_GROUPE, "Hors groupe"));
		em.persist(new GroupeMission(Code.GROUPE_MISSION_A, "Groupe A"));
		em.persist(new GroupeMission(Code.GROUPE_MISSION_B, "Groupe B"));
		
		em.persist(ayantDroit = new TypePersonne(Code.TYPE_PERSONNE_AYANT_DROIT, "Ayant droit"));
		
		em.persist(fonctionnaire = new TypeAgentEtat(Code.TYPE_AGENT_ETAT_FONCTIONNAIRE, "Fonctionnaire"));
		em.persist(contractuel = new TypeAgentEtat(Code.TYPE_AGENT_ETAT_CONTRACTUEL, "Contractuel"));
		em.persist(policier = new TypeAgentEtat(Code.TYPE_AGENT_ETAT_POLICIER, "Policier"));
		em.persist(gendarme = new TypeAgentEtat(Code.TYPE_AGENT_ETAT_GENDARME, "Gendarme"));
		
		em.persist(priseEnCharge = new TypeDepense(Code.TYPE_DEPENSE_PRISE_EN_CHARGE, "Prise en charge"));
		em.persist(remboursement = new TypeDepense(Code.TYPE_DEPENSE_RMBOURSEMENT, "Remboursement"));
		
		em.persist(extraitNaissance = new TypePieceJustificative(Code.TYPE_PIECE_EXTRAIT_NAISSANCE, "Extrait de naissance"));
		em.persist(extraitMariage = new TypePieceJustificative(Code.TYPE_PIECE_EXTRAIT_MARIAGE, "Extrait de mariage"));
		TypePieceJustificative decisionAffectation = new TypePieceJustificative(Code.TYPE_PIECE_DECISION_AFFECTATION, "Decision d'affectation");
		em.persist(decisionAffectation);
		TypePieceJustificative avisMutation = new TypePieceJustificative(Code.TYPE_PIECE_AVIS_MUTATION, "Avis de mutation");
		em.persist(avisMutation);
		TypePieceJustificative arrmut = new TypePieceJustificative(Code.TYPE_PIECE_ARRETE_MUTATION, "Arrêté de mutation");
		em.persist(arrmut);	
		em.persist(cni = new TypePieceJustificative(Code.TYPE_PIECE_CNI, "Carte nationale d'identité"));
		TypePieceJustificative com = new TypePieceJustificative(Code.TYPE_PIECE_COMMUNICATION, "Communication");
		em.persist(com);
		TypePieceJustificative om = new TypePieceJustificative(Code.TYPE_PIECE_ORDRE_MISSION, "Ordre de mission");
		em.persist(om);
		TypePieceJustificative attsg = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_SG, "Attestation du sécrétariat général du gouvernement");
		em.persist(attsg);
		TypePieceJustificative att = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_TRANSPORT, "Attestation de transport");
		em.persist(att);
		TypePieceJustificative passport = new TypePieceJustificative(Code.TYPE_PIECE_PASSPORT, "Passport");
		em.persist(passport);
		TypePieceJustificative attms = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_MISE_STAGE, "Attestation de mise en stage");
		em.persist(attms);
		TypePieceJustificative attfs = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_FIN_STAGE, "Attestation de fin de stage");
		em.persist(attfs);
		TypePieceJustificative attmae = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_MAE, "Attestation du ministère des affaires étrangères");
		em.persist(attmae);
		TypePieceJustificative decisrappel = new TypePieceJustificative(Code.TYPE_PIECE_DECISION_RAPPEL, "Decision de rappel");
		em.persist(decisrappel);
		TypePieceJustificative cps = new TypePieceJustificative(Code.TYPE_PIECE_CERTIFICAT_PRISE_SERVICE, "Certificat de prise de service");
		em.persist(cps);
		TypePieceJustificative ccs = new TypePieceJustificative(Code.TYPE_PIECE_CERTIFICAT_CESSATION_SERVICE, "Certificat de cessation de service");
		em.persist(ccs);
		TypePieceJustificative arrmr = new TypePieceJustificative(Code.TYPE_PIECE_ARRETE_MISE_RETRAITE, "Arrêté de mise à la retraite");
		em.persist(arrmr);			
		TypePieceJustificative arrpref = new TypePieceJustificative(Code.TYPE_PIECE_ARRETE_PREFECTORAL, "Arrêté Préfectoral");
		em.persist(arrpref);
		TypePieceJustificative arradiation = new TypePieceJustificative(Code.TYPE_PIECE_ARRETE_RADIATION, "Arrêté de radiation");
		em.persist(arradiation);
		TypePieceJustificative bullsal = new TypePieceJustificative(Code.TYPE_PIECE_BULLETIN_SALAIRE, "Bulletin de salaire");
		em.persist(bullsal);
		TypePieceJustificative cpps = new TypePieceJustificative(Code.TYPE_PIECE_CERTIFICAT_PREMIERE_PRISE_SERVICE, "Certificat de première prise de service");
		em.persist(cpps);
		TypePieceJustificative factprof = new TypePieceJustificative(Code.TYPE_PIECE_FACTURE_PROFORMA, "Facture proforma");
		em.persist(factprof);
		TypePieceJustificative extdeces = new TypePieceJustificative(Code.TYPE_PIECE_EXTRAIT_DECES, "Extrait de décès");
		em.persist(extdeces);
		TypePieceJustificative cpc = new TypePieceJustificative(Code.TYPE_PIECE_CERTIFICAT_PRESENCE_CORPS, "Certificat de Présence au corps");
		em.persist(cpc);
		TypePieceJustificative cartprof = new TypePieceJustificative(Code.TYPE_PIECE_CARTE_PROFESSIONNELLE, "Carte professionnelle");
		em.persist(cartprof);
		TypePieceJustificative certdeces = new TypePieceJustificative(Code.TYPE_PIECE_CERTIFICAT_DECES, "Certificat de décès");
		em.persist(certdeces);
		TypePieceJustificative lettmin = new TypePieceJustificative(Code.TYPE_PIECE_LETTRE_MINISTERIELLE, "Lettre ministérielle");
		em.persist(lettmin);
		
		GroupeTypePiece identitte = new GroupeTypePiece(Code.GROUPE_TYPE_PIECE_IDENTITE, "Identité");
		identitte.getTypePieces().add(cni);
		identitte.getTypePieces().add(passport);
		em.persist(identitte);
		
		em.persist(feuilleDep = new TypePieceJustificative(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT, "Feuille de déplacement"));
		em.persist(bonTransport = new TypePieceJustificative(Code.TYPE_PIECE_BON_TRANSPORT, "Bon de transport"));
		
		CategorieDeplacement deplacementDefinitif = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_DEFINITIF, "Déplacement définitif");
		em.persist(deplacementDefinitif);
		CategorieDeplacement mission = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_MISSION, "Mission");
		em.persist(mission);
		CategorieDeplacement transit = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_TRANSIT, "Transit");
		em.persist(transit);
		CategorieDeplacement obseque = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_OBSEQUE, "Déplacement définitif");
		em.persist(obseque);
		CategorieDeplacement sotra = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_TRANSPORT_URBAIN, "Sotra");
		em.persist(sotra);
		
		natureDeplacementAffectation = creerNatureDeplacement(deplacementDefinitif,Code.NATURE_DEPLACEMENT_AFFECTATION,"Affectation");
		em.persist(pjaf(natureDeplacementAffectation,remboursement,fonctionnaire, decisionAffectation));
		communPieceJustificativeAFournir(natureDeplacementAffectation, remboursement, fonctionnaire);
		communDDPieceJustificativeAFournir(natureDeplacementAffectation);
		
		natureDeplacementMutation =creerNatureDeplacement(deplacementDefinitif,Code.NATURE_DEPLACEMENT_MUTATION,"Mutation");
		em.persist(natureDeplacementMutation);
		em.persist(pjaf(natureDeplacementMutation,remboursement,fonctionnaire,avisMutation));
		communPieceJustificativeAFournir(natureDeplacementMutation, remboursement, fonctionnaire);
		communDDPieceJustificativeAFournir(natureDeplacementMutation);
			
		natureDeplacementDepartRetraite = creerNatureDeplacement(deplacementDefinitif,Code.NATURE_DEPLACEMENT_RETRAITE,"Départ à la retraite");
		em.persist(natureDeplacementDepartRetraite);
		em.persist(pjaf(natureDeplacementDepartRetraite,remboursement,fonctionnaire,avisMutation));
		communPieceJustificativeAFournir(natureDeplacementDepartRetraite, remboursement, fonctionnaire);
		communDDPieceJustificativeAFournir(natureDeplacementDepartRetraite);
		
		em.persist(mhci = creerNatureDeplacement(mission, Code.NATURE_DEPLACEMENT_MISSION_HCI,"Mission Hors Côte d'Ivoire"));
		em.persist(pjaf(mhci,priseEnCharge,null, com));
		em.persist(pjaf(mhci,priseEnCharge,null, att));
		em.persist(pjaf(mhci,priseEnCharge,null, om));
		communPieceJustificativeAFournir(mhci, priseEnCharge, null);
		
		NatureDeplacement fo = creerNatureDeplacement(obseque, Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS,"Frais d'obsèques");
		em.persist(fo);
		em.persist(pjaf(fo,priseEnCharge,fonctionnaire,certdeces));
		em.persist(pjaf(fo,priseEnCharge,fonctionnaire, extdeces));
		em.persist(pjaf(fo,priseEnCharge,fonctionnaire, bullsal));
		em.persist(pjaf(fo,priseEnCharge,fonctionnaire, lettmin));
		em.persist(pjaf(fo,priseEnCharge,ayantDroit, cni));
		communPieceJustificativeAFournir(fo, priseEnCharge, fonctionnaire);
		
		
		NatureDeplacement tr =creerNatureDeplacement(transit, Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES,"Transit de Bagages");
		em.persist(tr);
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, attms,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, attfs,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, decisrappel,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, cps,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, ccs,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, attmae,Boolean.TRUE));
		communPieceJustificativeAFournir(tr, priseEnCharge, fonctionnaire);
		
		NatureDeplacement carteBusSotra =creerNatureDeplacement(sotra, Code.NATURE_DEPLACEMENT_TRANSPORT_CARTE_SOTRA,"Carte de bus SOTRA");
		
		CauseDeces causeDeces1 = new CauseDeces("c1", "Maladie");
		em.persist(causeDeces1);
		CauseDeces causeDeces2 = new CauseDeces("c2", "Accident");
		em.persist(causeDeces2);
		CauseDeces causeDeces3 = new CauseDeces("c3", "Homicide");
		em.persist(causeDeces3);
		
		TypeLocalite pays = new TypeLocalite(Code.TYPE_LOCALITE_PAYS,"Pays");
		em.persist(pays);
		TypeLocalite ville = new TypeLocalite(Code.TYPE_LOCALITE_VILLE,"Ville");
		em.persist(ville);
		TypeLocalite zone = new TypeLocalite(Code.TYPE_LOCALITE_ZONE,"Zone");
		em.persist(zone);
		
		em.persist(coteDivoire = new Localite(Code.LOCALITE_COTE_DIVOIRE, "Côte d'Ivoire", null, pays));
		em.persist(abidjan = new Localite("ABJ", "Abidjan", coteDivoire,ville));
		em.persist(bouake = new Localite("BK", "Bouaké", coteDivoire,ville));
		em.persist(paris = new Localite("PAR", "Paris", null,ville));
		em.persist(dakar = new Localite("DK", "Dakar", null,ville));
		em.persist(delhi = new Localite("DH", "New Dheli", null,ville));
		
		Categorie categorieA = new Categorie("A", "A");
		em.persist(categorieA);
		Categorie categorieB = new Categorie("B", "B");
		em.persist(categorieB);
		
		Grade a1 = new Grade(categorieA, "A1", "A1");
		em.persist(a1);
		Grade a2 = new Grade(categorieA, "A2", "A2");
		em.persist(a2);
		
		Grade b1 = new Grade(categorieB, "B1", "B1");
		em.persist(b1);
		
		Echelon echelon1 = new Echelon("ech01", "1er Echelon");em.persist(echelon1);
		Echelon echelon2 = new Echelon("ech02", "2ème Echelon");em.persist(echelon2);
		
		Profession profession1 = new Profession("p01","Ingenieur Informaticien");
		em.persist(profession1);
		Profession profession2 = new Profession("p02","Comptable");
		em.persist(profession2);
		Profession profession3 = new Profession("p03","Ingénieur TP");
		em.persist(profession3);
		
		Fonction fonction1 = new Fonction("f01", "Chargé d'études");em.persist(fonction1);
		Fonction fonction2 = new Fonction("f02", "Conseiller");em.persist(fonction2);
		Fonction fonction3 = new Fonction("f03", "Sous Directeur");em.persist(fonction3);
		
		Position position1 = new Position("pos01", "Détaché");em.persist(position1);
		Position position2 = new Position("pos02", "Attaché");em.persist(position2);
		
		SituationMatrimoniale situationMatrimoniale1 = new SituationMatrimoniale(Code.SITUATION_MATRIMONIALE_MARIE, "Marié");
		em.persist(situationMatrimoniale1);
		SituationMatrimoniale situationMatrimoniale2 = new SituationMatrimoniale(Code.SITUATION_MATRIMONIALE_CELIBATAIRE, "Célibataire");
		em.persist(situationMatrimoniale2);
		SituationMatrimoniale situationMatrimoniale3 = new SituationMatrimoniale(Code.SITUATION_MATRIMONIALE_VEUF, "Veuf");
		em.persist(situationMatrimoniale3);
		
		TypeSection ministere = new TypeSection(Code.TYPE_SECTION_MINISTERE, "Ministère");
		em.persist(ministere);
		TypeSection service = new TypeSection(Code.TYPE_SECTION_SERVICE, "Service");
		em.persist(service);
		
		em.persist(ministereEconomie = new Section(null,Code.SECTION_MIN_MEF, "Economie et finances", ministere));
		em.persist(ministereBudget= new Section(null,Code.SECTION_MIN_MB, "Budget", ministere));
		
		em.persist(serviceExploitation = new Section(ministereBudget,Code.SECTION_SERV_EXP, "Exploitation", service));
		em.persist(serviceEtude = new Section(ministereBudget,Code.SECTION_SERV_ET, "Etude et développement", service));
		
		em.persist(saisieO = new NatureOperation(Code.NATURE_OPERATION_SAISIE, "Saisie"));
		em.persist(soumission = new NatureOperation(Code.NATURE_OPERATION_SOUMISSION, "Soumission"));
		
		NatureOperation validationRecevabilite = new NatureOperation("VAL_REC", "Validation Reçevabilité");
		em.persist(validationRecevabilite);
		NatureOperation validationConformite = new NatureOperation("VAL_CON", "Validation Conformité");
		em.persist(validationConformite);
		
		em.persist(natureOperationLiquidation = new NatureOperation(Code.NATURE_OPERATION_LIQUIDATION, "Liquidation"));
		em.persist(natureOperationRBTBL = new NatureOperation(Code.NATURE_OPERATION_REALISTION_BTBL, "Réalisation de bordereau de bulletins de liquidation"));
		em.persist(natureOperationRBTF = new NatureOperation(Code.NATURE_OPERATION_REALISTION_BTF, "Réalisation de bordereau de factures"));
		NatureOperation reglement = new NatureOperation("PAIE", "Reglement");
		em.persist(reglement);		
		
		em.persist(saisieS = new Statut(Code.STATUT_SAISIE, "Saisie", 0));
		em.persist(soumis = new Statut(Code.STATUT_SOUMIS, "Soumis", 0));
		
		Statut recevable = new Statut(Code.STATUT_RECEVABLE, "Reçevable", 0);
		em.persist(recevable);
		Statut conforme = new Statut(Code.STATUT_CONFORME, "Conforme", 0);
		em.persist(conforme);
		Statut liquide = new Statut(Code.STATUT_LIQUIDE, "Liquide", 0);
		em.persist(liquide);
		Statut paye = new Statut("P", "Paye", 0);
		em.persist(paye);
		
		agentEtat1 = creerAgentEtat(fonctionnaire,"096000T", "Fiellou", "N'Dri", date(), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		agentEtat2 = creerAgentEtat(fonctionnaire,"101000G", "Edoh", "Vincent", date(), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		agentEtat3 = creerAgentEtat(fonctionnaire,"201000L", "Losseni", "Diarrassouba", date(), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		agentEtat4 = creerAgentEtat(fonctionnaire,"175000H", "Thio", "Bekpancha", date(), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		
		creerCompteUtilisateur(agentEtat1, "christian.komenan@budget.gouv.ci", "sigdcp", new Object[][]{ {questionSecrete3,"Dgbf"} });
		
		/*
		creerCompteUtilisateur(agentEtat2, "sigdcp1", "sigdcp", new Role[]{Role.AGENT_ETAT}, new Object[][]{ {questionSecrete3,"tresor"} });
		creerCompteUtilisateur(agentEtat3, "sigdcp2", "sigdcp", new Role[]{Role.AGENT_ETAT}, new Object[][]{ {questionSecrete2,"orange"},{questionSecrete3,"Dgbf"} });
		creerCompteUtilisateur(agentEtat4, "sigdcp3", "sigdcp", new Role[]{Role.AGENT_ETAT}, new Object[][]{ {questionSecrete1,"Tata pion"},{questionSecrete2,"mangue"},{questionSecrete3,"Dgbf"} });
		*/
		
		/*
		em.persist(agentEtat1 = new AgentEtat("AE1","A99", "Tata", "Pion", dateNaiss, new Contact("tatmail@yahoo.com", "123456", "02 BP Abidjan", "Rue des masques", null), Sexe.MASCULIN, situationMatrimoniale1, 
				coteDivoire, new Date(),  a1, echelon1, position1, 2000, fonction1, serviceEtude, profession1, null));
		
		em.persist(agentEtat2 = new AgentEtat("AE2","A18", "Toto", "Tata", dateNaiss, new Contact("tatmail@yahoo.com", "123456", "02 BP Abidjan", "Rue des masques", null), Sexe.FEMININ, situationMatrimoniale1, 
				coteDivoire, new Date(),  a2, echelon1, position1, 2000, fonction1, serviceEtude, profession2, null));
		
		em.persist(agentEtat3 = new AgentEtat("AE3","A500", "Zaza", "Tata", dateNaiss, new Contact("tatmail@yahoo.com", "123456", "02 BP Abidjan", "Rue des masques", null), Sexe.MASCULIN, situationMatrimoniale1, 
				coteDivoire, new Date(),  a2, echelon1, position1, 2000, fonction1, serviceExploitation, profession2, null));
		
		em.persist(agentEtat4 = new AgentEtat("AE4","A800", "Zaza", "Tata", dateNaiss, new Contact("tatmail@yahoo.com", "123456", "02 BP Abidjan", "Rue des masques", null), Sexe.MASCULIN, situationMatrimoniale1, 
				coteDivoire, new Date(),  a2, echelon1, position1, 2000, fonction1, serviceExploitation, profession2, null));
		*/
		/*
		inscrireAgentEtat("DZ12", "Zadi", "Alain", new Date(), new Contact("mail@yahoo.com", "123456", "01 BP Abidjan", "Rue des jardins", null), Sexe.MASCULIN, 
				situationMatrimoniale1, coteDivoire, null, null, null, null, null, ministereBudget, profession1,gendarme);
		
		inscrireAgentEtat("DZ44", "Tata", "Mole", new Date(), new Contact("mail@yahoo.com", "123456", "01 BP Abidjan", "Rue des jardins", null), Sexe.MASCULIN, 
				situationMatrimoniale1, coteDivoire, null, null, null, null, null, ministereBudget, profession1,gendarme);
		
		inscrireAgentEtat("DZ100", "Kadi", "mariam", new Date(), new Contact("mail@yahoo.com", "123456", "01 BP Abidjan", "Rue des jardins", null), Sexe.FEMININ, 
				situationMatrimoniale1, coteDivoire, null, null, null, null, null, ministereBudget, profession1,policier);
		
		
		inscrireGCS(agentEtat1, ministereEconomie,"Trésor",null, agentEtat2);
		inscrireGCS(agentEtat2, ministereBudget,"Direction générale du budget","DGBF", agentEtat3);
		inscrireGCS(agentEtat3, ministereEconomie, "Impots","DGI",agentEtat4);
		
		creerDossierDD(natureDeplacementAffectation, agentEtat2);
		creerDossierDD(natureDeplacementAffectation, agentEtat3);
		
		DossierDD dossierDDAff1 = creerDossierDD(natureDeplacementAffectation, agentEtat1),
				dossierDDAff2 = creerDossierDD(natureDeplacementAffectation, agentEtat2),
				dossierDDAff3 = creerDossierDD(natureDeplacementAffectation, agentEtat3),
				dossierDDMut1 = creerDossierDD(natureDeplacementMutation, agentEtat1),
				dossierDDMut2 = creerDossierDD(natureDeplacementMutation, agentEtat2),
				dossierDDRet1 = creerDossierDD(natureDeplacementDepartRetraite, agentEtat1),
				dossierDDRet2 = creerDossierDD(natureDeplacementDepartRetraite, agentEtat3);
		
		DossierTransit dossierTransit1 = creerDossierTR(tr, agentEtat1),
				dossierTransit2 = creerDossierTR(tr, agentEtat2),
				dossierTransit3 = creerDossierTR(tr, agentEtat3),
				dossierTransit4 = creerDossierTR(tr, agentEtat4);
		

		
		operation(soumission, dossierDDAff1, soumis);
		operation(validationRecevabilite, dossierDDAff1, recevable);
		operation(validationConformite, dossierDDAff1, conforme);
		operation(natureOperationLiquidation, dossierDDAff1, liquide);
		operation(reglement, dossierDDAff1, paye);
		
		operation(soumission, dossierDDAff3, soumis);
		
		operation(soumission, dossierDDMut2, soumis);
		operation(validationRecevabilite, dossierDDMut2, recevable);
		
		operation(soumission, dossierDDRet1, soumis);
		
		operation(soumission, dossierDDRet2, soumis);
		operation(validationRecevabilite, dossierDDRet2, recevable);
		
		operation(soumission, dossierTransit1, soumis);
		operation(validationRecevabilite, dossierTransit1, recevable);
		operation(validationConformite, dossierTransit1, conforme);
		
		operation(soumission, dossierTransit2, soumis);
		operation(validationRecevabilite, dossierTransit2, recevable);
		operation(validationConformite, dossierTransit2, conforme);
		
		operation(soumission, dossierTransit3, soumis);
		operation(validationRecevabilite, dossierTransit3, recevable);
		
		operation(soumission, dossierTransit4, soumis);
				
		em.persist(elohimVoyages = new Prestataire("P1","Elohim Voyages",contact(),date()));
		em.persist(mistralVoyages = new Prestataire("P2","Mistral Voyages",new Contact("mistral@mail.com", "44556677", "02 BP Abidjan 02", null, abidjan),date()));
		
		creerCalendrierMission(10000000f, ministereEconomie, exercice2012);
		creerCalendrierMission(20000000f, ministereEconomie, exercice2013);
		creerCalendrierMission(15000000f, ministereEconomie, exercice2014);
		
		creerCalendrierMission(50000000f, ministereBudget, exercice2012);
		creerCalendrierMission(35000000f, ministereBudget, exercice2013);
		
		creerCalendrierMission(5000000f, ministereSante, exercice2012);
		creerCalendrierMission(47000000f, ministereSante, exercice2014);
		
		creerBordereauTransmission();
		creerBordereauTransmission();
		creerBordereauTransmission();
		*/
	}
	
	public AgentEtat creerAgentEtat(TypeAgentEtat typeAgentEtat,String matricule, String nom, String prenoms, Date dateNaissance, Sexe sexe, SituationMatrimoniale situationMatrimoniale, 
			Localite nationalite, Grade grade, Echelon echelon, Position position, Integer indice, Fonction fonction, Section ministere, Profession profession){
		AgentEtat agentEtat = new AgentEtat(nextIdString(),matricule, nom, prenoms, dateNaissance, new Contact(), sexe, situationMatrimoniale, 
				nationalite, new Date(),  grade, echelon, position, indice, fonction, ministere, profession, null);
		agentEtat.setType(typeAgentEtat);
		em.persist(agentEtat);
		return agentEtat;
	}
	
	public DelegueSotra creerDelegueSotra(String matricule, String nom, String prenoms, Date dateNaissance, Contact contact, Sexe sexe, SituationMatrimoniale situationMatrimoniale, 
			Localite nationalite, Grade grade, Echelon echelon, Position position, Integer indice, Fonction fonction, Section ministere, Profession profession){
		DelegueSotra delegueSotra = new DelegueSotra(nextIdString(),matricule, nom, prenoms, dateNaissance, contact, sexe, situationMatrimoniale, 
				nationalite, new Date(),  grade, echelon, position, indice, fonction, ministere, profession, null,agentEtat1,ministereBudget);
		
		em.persist(delegueSotra);
		return delegueSotra;
	}
	
	private Contact contact(){
		return new Contact("christian.komenan@budget.gouv.ci", "11223344", "01 BP Abidjan 01", null, abidjan);
	}
	
	public Personne creerPersonne(String nom, String prenoms,PieceJustificative pieceIdentite){
		Personne personne = new Personne(numero(), nom, prenoms, date(), contact(), Sexe.MASCULIN, null, coteDivoire,null, date());
		return personne;
	}
	
	public InfosSouscriptionComptePersonne creerInfosSouscriptionComptePersonne(String matricule, String nom, String prenoms,TypeAgentEtat typeAgentEtat,PieceJustificative pieceIdentite){
		return new InfosSouscriptionComptePersonne(null,creerPersonne(nom, prenoms, pieceIdentite),gendarme,matricule);
	}
	
	public SouscriptionComptePersonne inscrireAgentEtat(String matricule, String nom, String prenoms, Date dateNaissance, Contact contact, Sexe sexe, SituationMatrimoniale situationMatrimoniale, 
			Localite nationalite, Grade grade, Echelon echelon, Position position, Integer indice, Fonction fonction, Section ministere, Profession profession, TypeAgentEtat typeAgentEtat){
		SouscriptionComptePersonne inscription = new SouscriptionComptePersonne(nextIdString(),date(),null,null,creerInfosSouscriptionComptePersonne(matricule, nom, prenoms, typeAgentEtat, null),null,null);
		em.persist(inscription);
		return inscription;
	}
	
	public SouscriptionGestionnaireCarteSotra inscrireGCS(AgentEtat gestionnaire,Section ministere,String libelle,String sigle,AgentEtat interimaire){
		SouscriptionGestionnaireCarteSotra inscription = new SouscriptionGestionnaireCarteSotra(nextIdString(),date(),null,null,gestionnaire,ministere,libelle,sigle,interimaire);
		em.persist(inscription);
		return inscription;
	}
	
	public void creerMission(CalendrierMission calendrierMission,String designation,Integer mois,Integer dureeJour,Localite lieu,AgentEtat[] agentEtats){
		Mission mission = new Mission(calendrierMission,date(),date(),mhci,abidjan,lieu,designation,mois,dureeJour,"Environnement","Bonne gestion");
		em.persist(mission);
		List<DossierMission> dossiers = new ArrayList<>();
		for(AgentEtat agentEtat : agentEtats){
			DossierMission dossier = new DossierMission("DM"+nextIdString(),courrier(),mission,agentEtat.getGrade(),agentEtat);
			em.persist(dossier);
			dossiers.add(dossier);
		}
		mission.setDossierDuResponsable(dossiers.get(0));
		em.merge(mission);
	}
	
	public void creerCalendrierMission(Float montant,Section ministere,Exercice exercice){
		CalendrierMission calendrierMission = new CalendrierMission(montant,false,ministere,exercice);
		em.persist(calendrierMission);
		calendrierMissions.add(calendrierMission);
		
		creerMission(calendrierMission,"Conférence panafricaine des ministres de la fonction publique",3,7,paris,new AgentEtat[]{ agentEtat1,agentEtat2 });
		creerMission(calendrierMission,"Forum sur la modernisation de l'administration publique et des institutions de l'Etat",5,7,dakar,new AgentEtat[]{ agentEtat1,agentEtat3 });
		creerMission(calendrierMission,"Suivi des stagiaires en formation à l'étranger",9,12,delhi,new AgentEtat[]{ agentEtat3,agentEtat2 });
		
	}
	
	public DossierDD creerDossierDD(NatureDeplacement natureDeplacement,AgentEtat agentEtat){
		Deplacement deplacement = new Deplacement(date(), date(), date(), null, natureDeplacement, abidjan, bouake);
		em.persist(deplacement);
		DossierDD dossier = new DossierDD(numero(), courrier(), date(), deplacement, agentEtat.getGrade(), agentEtat, 500, 500, date(), numero(), abidjan, date(), date());
		dossier.setServiceOrigine(serviceExploitation);
		dossier.setService(serviceEtude);
		em.persist(dossier);
		return dossier;
	}
	
	public DossierTransit creerDossierTR(NatureDeplacement natureDeplacement,AgentEtat agentEtat){
		Deplacement deplacement = new Deplacement(date(), date(), date(), null, natureDeplacement, abidjan, bouake);
		em.persist(deplacement);
		DossierTransit dossier = new DossierTransit(numero(), courrier(), date(), deplacement, agentEtat.getGrade(), agentEtat, date(),date(),500f, 500f);
		em.persist(dossier);
		return dossier;
	}
	
	public void operation(NatureOperation natureOperation,Dossier dossier,Statut statut){
		Operation operation = new Operation(date(), natureOperation,null);
		em.persist(operation);
		Traitement traitement = new Traitement(operation,null,dossier,statut);
		em.persist(traitement);
		if(natureOperationLiquidation.equals(natureOperation)){
			BulletinLiquidation bl = new BulletinLiquidation(numero(), typePieceProduiteBL, date(), null, 120000);
			em.persist(bl);
			bulletinLiquidations.add(new Object[]{dossier,bl});
			traitement.setPieceProduite(bl);
			em.merge(traitement);
		}else if(natureOperationRBTBL.equals(natureOperation))
			;//em.persist(new PieceProduite(numero(), typePieceProduiteBTBL));
		else if(natureOperationRBTF.equals(natureOperation))
			;//em.persist(new PieceProduite(numero(), typePieceProduiteBTF));
	}
	
	public BordereauTransmission creerBordereauTransmission(){
		BordereauTransmission bt = new BordereauTransmission(numero(), typePieceProduiteBT, date());
		em.persist(bt);
		Operation operation = new Operation(date(), null,null);
		em.persist(operation);
		for(Object[] d : bulletinLiquidations){
			((BulletinLiquidation)d[1]).setBordereauTransmission(bt);
			em.merge(d[1]);
			em.persist(new Traitement(operation,bt,(Dossier) d[0],null));
		}
		return bt;
	}
	
	static long ID = 0;
	
	static String nextIdString(){
		return ++ID+"";
	}
	
	static Date date(){
		return new Date();
	}
	
	static String numero(){
		return UUID.randomUUID().toString();
	}
	
	static Courrier courrier(){
		return new Courrier(UUID.randomUUID().toString(),new Date());
	}
	
	public PieceJustificativeAFournir pjaf(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne,
			TypePieceJustificative typePieceJustificative,Integer quantite,Boolean original,Integer periodeValiditeEnMois,Boolean conditionnee,String description){
		return new PieceJustificativeAFournir(natureDeplacement, typeDepense, typePersonne, typePieceJustificative, quantite, original, periodeValiditeEnMois,conditionnee,description);
	}
	
	public PieceJustificativeAFournir pjaf(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne,TypePieceJustificative typePieceJustificative,Boolean conditionnee){
		return pjaf(natureDeplacement, typeDepense, typePersonne, typePieceJustificative, 1,Boolean.TRUE, -6,conditionnee,null);
	}
	
	public PieceJustificativeAFournir pjaf(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne,TypePieceJustificative typePieceJustificative){
		return pjaf(natureDeplacement, typeDepense, typePersonne, typePieceJustificative, 1,Boolean.TRUE, -6,Boolean.FALSE,null);
	}
	
	public void communPieceJustificativeAFournir(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne){
		em.persist(pjaf(natureDeplacement, typeDepense, typePersonne, cni));
	}
	
	public void communDDPieceJustificativeAFournir(NatureDeplacement natureDeplacement){
		em.persist(pjaf(natureDeplacement, remboursement, fonctionnaire, extraitNaissance,Boolean.TRUE));
		em.persist(pjaf(natureDeplacement, remboursement, fonctionnaire, extraitMariage,Boolean.TRUE));
		PieceJustificativeAFournir p;
		em.persist(p = pjaf(natureDeplacement, remboursement, fonctionnaire, feuilleDep));
		p.setDerivee(Boolean.TRUE);
		em.persist(p = pjaf(natureDeplacement, remboursement, fonctionnaire, bonTransport));
		p.setDerivee(Boolean.TRUE);
	}
	
	private NatureDeplacement creerNatureDeplacement(CategorieDeplacement categorie,String code,String libelle){
		NatureDeplacement natureDeplacement = new NatureDeplacement(categorie, code, libelle,0);
		
		try {
			natureDeplacement.setDescription(IOUtils.toString(this.getClass().getResourceAsStream("naturedep/"+code+".html"),"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		em.persist(natureDeplacement);
		return natureDeplacement;
	}
	
	private void creerCompteUtilisateur(Party utilisateur,String username,String password,Object[][] rs){
		Set<Role> r = new HashSet<Role>();
		if(utilisateur instanceof AgentEtat)
			r.add(Role.AGENT_ETAT);
		CompteUtilisateur compteUtilisateur = new CompteUtilisateur(new Credentials(username, password),utilisateur,r);
		for(Object[] o : rs)
			compteUtilisateur.getReponseSecretes().add(new ReponseSecrete((QuestionSecrete)o[0], (String)o[1]));
		
		em.persist(compteUtilisateur);
	}

}
