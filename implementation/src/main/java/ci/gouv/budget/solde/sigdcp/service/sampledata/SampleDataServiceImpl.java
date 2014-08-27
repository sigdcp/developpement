package ci.gouv.budget.solde.sigdcp.service.sampledata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
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
import org.apache.commons.lang3.ArrayUtils;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.CalendrierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.CauseDeces;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocalite;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.geographie.TypeLocalite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtatReference;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Credentials;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.model.identification.Echelon;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.identification.Personnel;
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
import ci.gouv.budget.solde.sigdcp.model.identification.TypePrestataire;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeSection;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeDD;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteTrancheDistance;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.LocaliteGroupeMissionId;
import ci.gouv.budget.solde.sigdcp.model.indemnite.RegleCalcul;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.sotra.BeneficiaireCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.OperationValidationConfig;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.service.SampleDataService;


@Stateless//(mappedName="SampleDataService") @Remote
public class SampleDataServiceImpl implements SampleDataService {
	
	@PersistenceContext
	private EntityManager em ;
	private Personnel personnelDirecteur,personnelDirecteur1,personnelDirecteur2,personnelDirecteur3;
	private RegleCalcul indemniteKP,indemniteBagageAgent,indemniteBagageConjoint,indemniteBagageEnfant,indemniteJournaliereAgent,indemniteDistanceObseque,indemniteCercueil,
	indemniteJournaliereConjoint,indemniteJournaliereEnfant,indemniteMissionSejour,indemniteMissionDivers;
	private SituationMatrimoniale situationMatrimonialeMarie,situationMatrimonialeCelibataire;
	private Profession professionIngInfo;
	private TypePrestataire pompesFunebres,agenceVoyages,transitaire;
	private Role roleAgentEtat,roleAgentMission,roleResponsable,rolePayeur,roleDirecteur,roleAgentSolde,roleLiquidateur,rolePF,roleGCS,roleDelegueSotra,rolePrestataire,roleAD,roleAdministrateur;
	private Grade a1,a2,a3,a4,a5,a6,a7,b1,b2,b3,b4,b5,c1,c2,c3,d1,d2,d3;
	private List<Grade> grades = new ArrayList<>();
	private CategorieDeplacement deplacementDefinitif,obseque;
	
	private TypeDepense priseEnCharge,remboursement;
	private TypeAgentEtat fonctionnaire,contractuel,policier,gendarme,agentMission;
	private TypePersonne ayantDroit;
	private TypePieceJustificative extraitNaissance,extraitMariage,cni,feuilleDep,bonTransport,factprof,factdef,attestationTransport,attmae/*,attpec*/ ;
	private Prestataire elohimVoyages,mistralVoyages;
	private Localite abidjan,sanpedro,bouake,paris,dakar,delhi,coteDivoire,texas,lagos,zoneAfrique,zoneResteMonde,europe,asie,amerique,afrique,australie;
	private NatureDeplacement natureDeplacementMutation,natureDeplacementAffectation,natureDeplacementDepartRetraite,natureDeplacementMhci,natureDeplacementTitreTransportExpatrie,
		natureDeplacementTitreTransportStage,natureDeplacementMae,natureDeplacementStagiaire,natureDeplacementFo;
	
	private Personnel personnel1,personnel2;
	private Section ministereEconomie,ministereBudget,sousDirectionDCP,serviceExploitation,serviceEtude,sectionServiceIdentification,sectionServiceLiquidation,sectionServicePaiement,sectionSousDirectionDCP;
	private List<CalendrierMission> calendrierMissions=new LinkedList<>();
	private List<DossierMission> dossierMissions = new LinkedList<>();
	private List<PieceProduite> pieceProduites = new LinkedList<>();
	private List<Object[]> bulletinLiquidations = new LinkedList<>();
	
	private Statut statutAccepte,statutRejete,statutDiffere;
	private TypePieceProduite typePieceProduiteBL,typePieceProduiteBT;
	private QuestionSecrete  questionSecrete1,questionSecrete2,questionSecrete3;
	private Personnel personnelResponsable;
	private Collection<Fonction> fonctionsHorsGroupe=new ArrayList<>(),fonctionsGroupeA=new ArrayList<>();
	
	@Override 
	public void executerParametrage() {
		parametrageGeneral();
		parametrageLocalisation();
		parametrageIdentification();
		
		parametrageModeleDossier();
		parametrageIndemnite();
	
		parametrageOperation();
		creationUtilisateur();
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	private void parametrageGeneral(){
		CauseDeces causeDeces1 = new CauseDeces("c1", "Maladie");
		em.persist(causeDeces1);
		CauseDeces causeDeces2 = new CauseDeces("c2", "Accident");
		em.persist(causeDeces2);
		CauseDeces causeDeces3 = new CauseDeces("c3", "Homicide");
		em.persist(causeDeces3);
	}
	
	private void parametrageLocalisation(){
		TypeLocalite continent = new TypeLocalite(Code.TYPE_LOCALITE_CONTINENT,"Continent");em.persist(continent);
		TypeLocalite pays = new TypeLocalite(Code.TYPE_LOCALITE_PAYS,"Pays");em.persist(pays);
		TypeLocalite ville = new TypeLocalite(Code.TYPE_LOCALITE_VILLE,"Ville");em.persist(ville);
		TypeLocalite mairie = new TypeLocalite(Code.TYPE_LOCALITE_MAIRIE,"Mairie");em.persist(mairie);
		TypeLocalite zone = new TypeLocalite(Code.TYPE_LOCALITE_ZONE,"Zone");em.persist(zone);
		
		em.persist(zoneAfrique = new Localite("ZA", "Zone afrique", null, zone));
		em.persist(zoneResteMonde = new Localite("ZR", "Reste du monde", null, zone));
		
		em.persist(afrique = new Localite("AFRIQUE", "Afrique", zoneAfrique, continent));
		em.persist(europe = new Localite("EUROPE", "Europe", zoneResteMonde, continent));
		em.persist(asie = new Localite("ASIE", "Asie", zoneResteMonde, continent));
		em.persist(amerique = new Localite("AMERIQUE", "Amérique", zoneResteMonde, continent));
		em.persist(australie = new Localite("AUSTRALIE", "Australie", zoneResteMonde, continent));
		
		em.persist(coteDivoire = new Localite(Code.LOCALITE_COTE_DIVOIRE, "Côte d'Ivoire", afrique, pays));
		em.persist(abidjan = new Localite(Code.LOCALITE_ABIDJAN, "Abidjan", coteDivoire,ville));
		em.persist(sanpedro = new Localite(Code.LOCALITE_SAN_PEDRO, "San pedro", coteDivoire,ville));
		em.persist(bouake = new Localite("BK", "Bouaké", coteDivoire,ville));
		
		em.persist(new Localite("P_MAL", "Mali", afrique, pays));
		em.persist(new Localite("P_FR", "France", europe, pays));
		em.persist(new Localite("P_BR", "Bresil", amerique, pays));
		em.persist(new Localite("P_CA", "Canada", amerique, pays));
		em.persist(new Localite("P_CH", "Chine", asie, pays));
		
		em.persist(paris = new Localite(Code.LOCALITE_PARIS, "Paris", europe,ville));
		
		em.persist(dakar = new Localite("DK", "Dakar", afrique,ville));
		em.persist(delhi = new Localite("DH", "New Dheli", asie,ville));
		em.persist(lagos = new Localite("LG", "Lagos", afrique,ville));
		em.persist(texas = new Localite("TX", "Texas", amerique,ville));
		
		//mairies d'abidjan
		em.persist(new Localite("CO", "Cocody", abidjan,mairie));
		em.persist(new Localite("ADJ", "Adjamé", abidjan,mairie));
		em.persist(new Localite("ATT", "Attécoubé", abidjan,mairie));
		em.persist(new Localite("MAC", "Marcory", abidjan,mairie));
		em.persist(new Localite("YOP", "Yopougon", abidjan,mairie));
		em.persist(new Localite("KOU", "Koumassi", abidjan,mairie));
		
		// distances
		em.persist(new DistanceEntreLocalite(abidjan, bouake, "400"));
		em.persist(new DistanceEntreLocalite(abidjan, sanpedro, "500"));
		em.persist(new DistanceEntreLocalite(bouake, sanpedro, "600"));
	
	}
	
	private void parametrageIdentification(){
		
		em.persist(ayantDroit = new TypePersonne(Code.TYPE_PERSONNE_AYANT_DROIT, "Ayant droit"));
		em.persist(gendarme = new TypeAgentEtat(Code.TYPE_AGENT_ETAT_GENDARME, "Gendarme"));
		em.persist(agentMission = new TypeAgentEtat(Code.TYPE_AGENT_ETAT_MISSION, "Agent de mission"));
		
		em.persist(fonctionnaire = new TypeAgentEtat(Code.TYPE_AGENT_ETAT_FONCTIONNAIRE, "Fonctionnaire"));
		em.persist(contractuel = new TypeAgentEtat(Code.TYPE_AGENT_ETAT_CONTRACTUEL, "Contractuel"));
		em.persist(policier = new TypeAgentEtat(Code.TYPE_AGENT_ETAT_POLICIER, "Policier"));
		
		
		em.persist(pompesFunebres = new TypePrestataire("PF", "Pompe Funèbres"));
		em.persist(agenceVoyages = new TypePrestataire("AV", "Agence de voyages"));
		em.persist(transitaire = new TypePrestataire("TR", "Transitaire"));
		
		em.persist(roleAdministrateur = new Role(Code.ROLE_ADMINISTRATEUR, "Administrateur"));
		em.persist(roleAgentEtat = new Role(Code.ROLE_AGENT_ETAT, "Agent etat"));
		em.persist(roleAgentMission = new Role(Code.ROLE_AGENT_MISSION, "Agent de mission"));
		em.persist(roleAgentSolde = new Role(Code.ROLE_AGENT_SOLDE, "Agent de la solde"));
		
		em.persist(roleLiquidateur = new Role(Code.ROLE_LIQUIDATEUR, "Liquidateur"));
		em.persist(roleResponsable = new Role(Code.ROLE_RESPONSABLE, "Responsable"));
		em.persist(rolePayeur = new Role(Code.ROLE_PAYEUR, "Payeur"));
		em.persist(roleDirecteur = new Role(Code.ROLE_DIRECTEUR, "Directeur"));
		em.persist(rolePF = new Role(Code.ROLE_POINT_FOCAL, "Point focal de mission"));
		em.persist(roleDelegueSotra = new Role(Code.ROLE_DELEGUE_SOTRA, "Delegue sotra"));
		em.persist(roleGCS = new Role(Code.ROLE_GESTIONNAIRE_CARTE_SOTRA, "Gestionnaire de carte sotra"));
		em.persist(rolePrestataire = new Role(Code.ROLE_PRESTATAIRE, "Prestataire"));
		em.persist(roleAD = new Role(Code.ROLE_AYANT_DROIT_FO, "Ayant Droit Obsèques"));
		
		Categorie categorieA = new Categorie("A", "A");
		em.persist(categorieA);
		Categorie categorieB = new Categorie("B", "B");
		em.persist(categorieB);
		Categorie categorieC = new Categorie("C", "C");
		em.persist(categorieC);
		Categorie categorieD = new Categorie("D", "D");
		em.persist(categorieD);
		
		a1 = creerGrade(categorieA, 1);
		a2 = creerGrade(categorieA, 2);
		a3 = creerGrade(categorieA, 3);
		a4 = creerGrade(categorieA, 4);
		a5 = creerGrade(categorieA, 5);
		a6 = creerGrade(categorieA, 6);
		a7 = creerGrade(categorieA, 7);
		
		b1 = creerGrade(categorieB, 1);
		b2 = creerGrade(categorieB, 2);
		b3 = creerGrade(categorieB, 3);
		
		c1 = creerGrade(categorieC, 1);
		c2 = creerGrade(categorieC, 2);
		c3 = creerGrade(categorieC, 3);
		
		d1 = creerGrade(categorieD, 1);
		d2 = creerGrade(categorieD, 2);
		d3 = creerGrade(categorieD, 3);
		
		em.persist(questionSecrete1 = new QuestionSecrete("Quel est le nom de votre 1er Chef de service"));
		em.persist(questionSecrete2 = new QuestionSecrete("Quel est le nom de votre fruit préféré?"));
		em.persist(questionSecrete3 = new QuestionSecrete("Quel direction vous visiez pour votre 1ere affection?"));
		
		Echelon echelon1 = new Echelon("ech01", "1er Echelon");em.persist(echelon1);
		Echelon echelon2 = new Echelon("ech02", "2ème Echelon");em.persist(echelon2);
		
		//Hors groupe
		for(String libelle : new String[]{"Ministre","Dir Cab du PR","Sécretaire G. du gouvernement","Sécretaire G. A. du gouvernement","Ambassadeur en fonction"} )
			fonctionsHorsGroupe.add(fonction(libelle));
		//Groupe A/1
		for(String libelle : new String[]{"Chef état major forces armées","Cmdt Sup. Gendarmerie","Dir. Cab. Ministériel","Prefet","Procureur de la republique","Recteur de l'université"} )
			fonctionsGroupeA.add(fonction(libelle));
		
		for(String libelle : new String[]{"Chargé d'études","Sous Directeur","Contrôleur budgetaire","DAAF","Contrôleur Financier","Trésorier"} )
			fonction(libelle);
		
		//Groupe B/2
		professionIngInfo = profession("Ingénieur des techniques",a3);
		for(String libelle : new String[]{"Démographe assistant","Urbaniste assisant"} )
			profession(libelle, a3);
		for(String libelle : new String[]{"Maitre-conseiller d'éducation surveillée","Infirmier spécialiste","Sage femme spécialiste"} )
			profession(libelle, a2);
		
		Position position1 = new Position("pos01", "Détaché");em.persist(position1);
		Position position2 = new Position("pos02", "Attaché");em.persist(position2);
		
		em.persist(situationMatrimonialeMarie = new SituationMatrimoniale(Code.SITUATION_MATRIMONIALE_MARIE, "Marié"));
		em.persist(situationMatrimonialeCelibataire = new SituationMatrimoniale(Code.SITUATION_MATRIMONIALE_CELIBATAIRE, "Célibataire"));
		
		TypeSection ministere = new TypeSection(Code.TYPE_SECTION_MINISTERE, "Ministère");
		em.persist(ministere);
		TypeSection direction = new TypeSection(Code.TYPE_SECTION_DIRECTION, "Direction");
		em.persist(direction);
		TypeSection service = new TypeSection(Code.TYPE_SECTION_SERVICE, "Service");
		em.persist(service);
		
		em.persist(ministereEconomie = new Section(null,Code.SECTION_MIN_MEF, "Economie et finances", ministere));
		em.persist(ministereBudget= new Section(null,Code.SECTION_MIN_MB, "Budget", ministere));
		em.persist(new Section(null,"MSANTE", "Sante", ministere));
		em.persist(new Section(null,"MTRANS", "Transport", ministere));
		
		em.persist(new Section(ministereBudget,Code.SECTION_DIRECTION_DGBF, "DGBF", direction));
		em.persist(new Section(ministereBudget,Code.SECTION_DIRECTION_TRESOR, "Trésor", direction));
		em.persist(new Section(ministereBudget,"DOUA", "Douanes", direction));
		em.persist(new Section(ministereBudget,"Imp", "Impots", direction));
	
		em.persist(sectionServiceIdentification = new Section(ministereBudget,"SISOLDE", "Service Identification DCP", direction));
		em.persist(sectionServiceLiquidation = new Section(ministereBudget,"SLSOLDE", "Service Liquidation DCP", direction));
		em.persist(sectionServicePaiement = new Section(ministereBudget,"SPSOLDE", "Service Paiement DCP", direction));
		em.persist(sectionSousDirectionDCP = new Section(ministereBudget,"SDCPSOLDE", "Sous Direction DCP", direction));
		
		em.persist(new Section(sectionSousDirectionDCP,"SEXPI", "Informatique", service));
		em.persist(new Section(sectionSousDirectionDCP,"SPAIE", "Statistique", service));
	}
	
	private void parametrageModeleDossier(){
		em.persist(priseEnCharge = new TypeDepense(Code.TYPE_DEPENSE_PRISE_EN_CHARGE, "Prise en charge"));
		em.persist(remboursement = new TypeDepense(Code.TYPE_DEPENSE_REMBOURSEMENT, "Remboursement"));
		em.flush();
		em.persist(extraitNaissance = new TypePieceJustificative(Code.TYPE_PIECE_EXTRAIT_NAISSANCE, "Extrait de naissance"));
		em.persist(extraitMariage = new TypePieceJustificative(Code.TYPE_PIECE_EXTRAIT_MARIAGE, "Extrait de mariage"));
		TypePieceJustificative decisionAffectation = new TypePieceJustificative(Code.TYPE_PIECE_DECISION_AFFECTATION, "Decision d'affectation");
		em.persist(decisionAffectation);
		TypePieceJustificative demandeOctroiBillet = new TypePieceJustificative(Code.TYPE_PIECE_DEMANDE_OCTROI_BILLET, "Demande d'octroi de billet");
		em.persist(demandeOctroiBillet);
		TypePieceJustificative avisMutation = new TypePieceJustificative(Code.TYPE_PIECE_AVIS_MUTATION, "Avis de mutation");
		em.persist(avisMutation);
		TypePieceJustificative arreteMutation = new TypePieceJustificative(Code.TYPE_PIECE_ARRETE_MUTATION, "Arrêté de mutation");
		em.persist(arreteMutation);	
		em.persist(cni = new TypePieceJustificative(Code.TYPE_PIECE_CNI, "Carte nationale d'identité"));
		TypePieceJustificative com = new TypePieceJustificative(Code.TYPE_PIECE_COMMUNICATION, "Communication");
		em.persist(com);
		TypePieceJustificative om = new TypePieceJustificative(Code.TYPE_PIECE_ORDRE_MISSION, "Ordre de mission");
		em.persist(om);
		TypePieceJustificative attsg = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_SG, "Attestation du sécrétariat général du gouvernement");
		em.persist(attsg);
		attestationTransport = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_TRANSPORT, "Attestation de transport");
		em.persist(attestationTransport);
		TypePieceJustificative passport = new TypePieceJustificative(Code.TYPE_PIECE_PASSPORT, "Passport");
		em.persist(passport);
		TypePieceJustificative attms = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_MISE_STAGE, "Attestation de mise en stage");
		em.persist(attms);
		TypePieceJustificative attfs = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_FIN_STAGE, "Attestation de fin de stage");
		em.persist(attfs);
		attmae = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_MAE, "Attestation du ministère des affaires étrangères");
		em.persist(attmae);
		/*
		attpec = new TypePieceJustificative(Code.TYPE_PIECE_ATTESTATION_PEC, "Attestation de prise en charge");
		em.persist(attpec);
		*/
		TypePieceJustificative decisrappel = new TypePieceJustificative(Code.TYPE_PIECE_DECISION_RAPPEL, "Decision de rappel");
		em.persist(decisrappel);
		TypePieceJustificative decisconge = new TypePieceJustificative(Code.TYPE_PIECE_DECISION_CONGE, "Decision de congé");
		em.persist(decisconge);
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
		TypePieceJustificative cpps = new TypePieceJustificative(Code.TYPE_PIECE_CERTIFICAT_PREMIERE_PRISE_SERVICE, "Certificat de prise de service");
		em.persist(cpps);
		em.persist(factprof = new TypePieceJustificative(Code.TYPE_PIECE_FACTURE_PROFORMA, "Facture proforma"));
		em.persist(factdef = new TypePieceJustificative(Code.TYPE_PIECE_FACTURE_DEFINITIVE, "Facture définitive"));
		TypePieceJustificative extdeces = new TypePieceJustificative(Code.TYPE_PIECE_EXTRAIT_DECES, "Extrait du régistre des actes de décès");
		em.persist(extdeces);
		TypePieceJustificative cpc = new TypePieceJustificative(Code.TYPE_PIECE_CERTIFICAT_PRESENCE_CORPS, "Certificat de Présence au corps");
		em.persist(cpc);
		TypePieceJustificative cartprof = new TypePieceJustificative(Code.TYPE_PIECE_CARTE_PROFESSIONNELLE, "Carte professionnelle");
		em.persist(cartprof);
		TypePieceJustificative certdeces = new TypePieceJustificative(Code.TYPE_PIECE_CERTIFICAT_DECES, "Certificat de décès");
		em.persist(certdeces);
		TypePieceJustificative lettmin = new TypePieceJustificative(Code.TYPE_PIECE_LETTRE_MINISTERIELLE, "Lettre ministérielle");
		em.persist(lettmin);
		TypePieceJustificative acteDeces = new TypePieceJustificative(Code.TYPE_PIECE_ACTE_DECES, "Acte de décès");
		em.persist(acteDeces);
		TypePieceJustificative decisionMiseStage = new TypePieceJustificative(Code.TYPE_PIECE_DECISION_MISE_STAGE, "Décision de mise en stage");
		em.persist(decisionMiseStage);
		
		em.persist(typePieceProduiteBL = new TypePieceProduite(Code.TYPE_PIECE_PRODUITE_BL, "Bulletin de liquidation"));
		em.persist(typePieceProduiteBT = new TypePieceProduite(Code.TYPE_PIECE_PRODUITE_BT, "Bordereau de transmission"));
		em.persist(new TypePieceProduite(Code.TYPE_PIECE_PRODUITE_BC, "Bon de commande"));
		em.persist(new TypePieceProduite(Code.TYPE_PIECE_PRODUITE_APEC, "Attestation de Prise en charge"));
		em.persist(new TypePieceProduite(Code.TYPE_PIECE_PRODUITE_DECISION_REMB, "Décision de remboursement"));
		
		em.persist(feuilleDep = new TypePieceJustificative(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT, "Feuille de déplacement"));
		em.persist(bonTransport = new TypePieceJustificative(Code.TYPE_PIECE_BON_TRANSPORT, "Bon de transport"));
		
		deplacementDefinitif = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_DEFINITIF, "Déplacement définitif");
		em.persist(deplacementDefinitif);
		CategorieDeplacement mission = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_MISSION, "Mission");
		em.persist(mission);
		CategorieDeplacement transit = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_TRANSIT, "Transit");
		em.persist(transit);
		obseque = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_OBSEQUE, "Déplacement définitif");
		em.persist(obseque);
		/*CategorieDeplacement deplacementTitreTransport = new CategorieDeplacement(Code.CATEGORIE_DEPLACEMENT_TITRE_TRANSPORT, "Titre de tranpsort");
		em.persist(deplacementTitreTransport);
		*/
		
		PieceJustificativeAFournir p;
		
		natureDeplacementAffectation = creerNatureDeplacement(deplacementDefinitif,Code.NATURE_DEPLACEMENT_AFFECTATION,"Affectation");
		natureDeplacementAffectation.getCategorie().setDisponible(new BigDecimal("1000000"));
		em.persist(p = pjaf(natureDeplacementAffectation,remboursement,fonctionnaire, decisionAffectation));
		p.getConfig().setPrincipale(Boolean.TRUE);
		p.getConfig().setAdministrative(true);
		em.persist(p = pjaf(natureDeplacementAffectation,remboursement,fonctionnaire, cpps));
		communPieceJustificativeAFournir(natureDeplacementAffectation, remboursement, fonctionnaire);
		communDDPieceJustificativeAFournir(natureDeplacementAffectation);
		
		natureDeplacementMutation =creerNatureDeplacement(deplacementDefinitif,Code.NATURE_DEPLACEMENT_MUTATION,"Mutation");
		//em.persist(natureDeplacementMutation);
		em.persist(p = pjaf(natureDeplacementMutation,remboursement,fonctionnaire,arreteMutation));
		p.getConfig().setPrincipale(true);
		p.getConfig().setAdministrative(true);
		em.persist(pjaf(natureDeplacementMutation,remboursement,fonctionnaire,cps));

		communPieceJustificativeAFournir(natureDeplacementMutation, remboursement, fonctionnaire);
		communDDPieceJustificativeAFournir(natureDeplacementMutation);
			
		natureDeplacementDepartRetraite = creerNatureDeplacement(deplacementDefinitif,Code.NATURE_DEPLACEMENT_RETRAITE,"Départ à la retraite");
		//em.persist(natureDeplacementDepartRetraite);
		em.persist(p = pjaf(natureDeplacementDepartRetraite,remboursement,fonctionnaire,arradiation));
		p.getConfig().setPrincipale(true);
		p.getConfig().setAdministrative(true);
		em.persist(p = pjaf(natureDeplacementDepartRetraite,remboursement,fonctionnaire,ccs));
		communPieceJustificativeAFournir(natureDeplacementDepartRetraite, remboursement, fonctionnaire);
		communDDPieceJustificativeAFournir(natureDeplacementDepartRetraite);
		
		em.persist(natureDeplacementMhci = creerNatureDeplacement(mission, Code.NATURE_DEPLACEMENT_MISSION_HCI,"Mission Hors Côte d'Ivoire"));
		em.persist(p = pjaf(natureDeplacementMhci,priseEnCharge,null, com));
		p.getConfig().setCommune(Boolean.TRUE);
		p.getConfig().setPrincipale(Boolean.TRUE);
		p.getConfig().setAdministrative(true);
		em.persist(pjaf(natureDeplacementMhci,priseEnCharge,null, attestationTransport));
		em.persist(pjaf(natureDeplacementMhci,priseEnCharge,null, om));
		em.persist(p = pjaf(natureDeplacementMhci, priseEnCharge, null, feuilleDep));
		p.getConfig().setDerivee(Boolean.TRUE);
		communPieceJustificativeAFournir(natureDeplacementMhci, priseEnCharge, null);
		
		em.persist(p = pjaf(natureDeplacementTitreTransportExpatrie,priseEnCharge,null, decisconge));
		p.getConfig().setPrincipale(Boolean.TRUE);
		p.getConfig().setAdministrative(true);
		
		em.persist(p = pjaf(natureDeplacementTitreTransportExpatrie,priseEnCharge,null, demandeOctroiBillet));
		p.getConfig().setPrincipale(Boolean.TRUE);
		p.getConfig().setAdministrative(true);
		
		em.persist(pjaf(natureDeplacementTitreTransportExpatrie,priseEnCharge,null, extraitMariage,true));
		em.persist(pjaf(natureDeplacementTitreTransportExpatrie,priseEnCharge,null, extraitNaissance,true));
		
		em.persist(natureDeplacementTitreTransportExpatrie = creerNatureDeplacement(mission, Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_CONGE,"Titre de transport pour expatrié"));
		for(TypeDepense typeDepense : new TypeDepense[]{priseEnCharge,remboursement}){
			em.persist(p = pjaf(natureDeplacementTitreTransportExpatrie,typeDepense,null, decisconge));
			p.getConfig().setPrincipale(Boolean.TRUE);
			p.getConfig().setAdministrative(true);
			
			em.persist(pjaf(natureDeplacementTitreTransportExpatrie,typeDepense,null, extraitMariage,true));
			em.persist(pjaf(natureDeplacementTitreTransportExpatrie,typeDepense,null, extraitNaissance,true));	
		}
		
		em.persist(natureDeplacementTitreTransportStage = creerNatureDeplacement(mission, Code.NATURE_DEPLACEMENT_TITRE_TRANSPORT_STAGE,"Titre de transport pour stagiaire"));
		for(TypeDepense typeDepense : new TypeDepense[]{priseEnCharge,remboursement}){
			em.persist(p = pjaf(natureDeplacementTitreTransportStage,typeDepense,null, demandeOctroiBillet));
			p.getConfig().setPrincipale(Boolean.TRUE);
			p.getConfig().setAdministrative(true);
		}
		
		natureDeplacementFo = creerNatureDeplacement(obseque, Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS,"Frais d'obsèques");
		em.persist(natureDeplacementFo);
		for(TypeDepense typeDepense : new TypeDepense[]{priseEnCharge,remboursement}){
			em.persist(p = pjaf(natureDeplacementFo,typeDepense,fonctionnaire,certdeces));
			p.getConfig().setPrincipale(Boolean.TRUE);
			em.persist(pjaf(natureDeplacementFo,typeDepense,fonctionnaire, extdeces));
			em.persist(pjaf(natureDeplacementFo,typeDepense,fonctionnaire, acteDeces));
			em.persist(pjaf(natureDeplacementFo,typeDepense,fonctionnaire, bullsal));
			em.persist(p = pjaf(natureDeplacementFo,typeDepense,fonctionnaire, cni));
			p.setDescription("défunt");
			em.persist(p = pjaf(natureDeplacementFo,typeDepense,ayantDroit, cni));
			p.setDescription("ayant droit");
			communPieceJustificativeAFournir(natureDeplacementFo, priseEnCharge, fonctionnaire);	
		}
		em.persist(pjaf(natureDeplacementFo,remboursement,fonctionnaire, lettmin));
		
		natureDeplacementMae =creerNatureDeplacement(transit, Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE,"Transit des agents MAE");
		em.persist(natureDeplacementMae);
		for(TypeDepense typeDepense : new TypeDepense[]{priseEnCharge,remboursement}){
			em.persist(p = pjaf(natureDeplacementMae,typeDepense,null, decisrappel));
			p.getConfig().setPrincipale(true);
			p.getConfig().setAdministrative(true);
			em.persist(pjaf(natureDeplacementMae,typeDepense,null, cps));
			//em.persist(pjaf(trmae,typeDepense,null, factprof));
			//em.persist(pjaf(trmae,typeDepense,null, ccs));		
		}
		communTRPieceJustificativeAFournir(natureDeplacementMae);
		
		natureDeplacementStagiaire =creerNatureDeplacement(transit, Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE,"Transit des stagiaires");
		em.persist(natureDeplacementStagiaire);
		for(TypeDepense typeDepense : new TypeDepense[]{priseEnCharge,remboursement}){
			//em.persist(pjaf(trstage,typeDepense,fonctionnaire, attms));
			em.persist(pjaf(natureDeplacementStagiaire,typeDepense,fonctionnaire, attfs));
			em.persist(p = pjaf(natureDeplacementStagiaire,typeDepense,fonctionnaire, decisionMiseStage));
			p.getConfig().setPrincipale(true);
			p.getConfig().setAdministrative(true);
			//em.persist(pjaf(trstage,typeDepense,fonctionnaire, factprof));
		}
		communTRPieceJustificativeAFournir(natureDeplacementStagiaire);
		
	}
	
	private void parametrageIndemnite(){
		TypeClasseVoyage _1Class,_2Class,_3Class;
		em.persist(_1Class = new TypeClasseVoyage(Code.TYPE_CLASSE_VOYAGE_1ERE,"1ère classe"));
		em.persist(_2Class = new TypeClasseVoyage(Code.TYPE_CLASSE_VOYAGE_2EME,"2ème classe"));
		em.persist(_3Class = new TypeClasseVoyage(Code.TYPE_CLASSE_VOYAGE_TOURISTE,"classe touriste"));
		
		groupeDD(Code.GROUPE_DD_2, "12600", "500", "9450", "500", "6300", "500",a1,a2,a3);
		groupeDD(Code.GROUPE_DD_3, "9450", "500", "7090", "300", "4725", "150",b1,b2,b3);
		groupeDD(Code.GROUPE_DD_4, "8400", "500", "5550", "300", "4200", "150",c1,c2,c3);
		groupeDD(Code.GROUPE_DD_5, "5350", "400", "5515", "250", "3675", "150",d1,d2,d3);
		
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("100"), new BigDecimal("0"), new BigDecimal("10")));
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("225"), new BigDecimal("11"), new BigDecimal("20")));
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("325"), new BigDecimal("25"), new BigDecimal("30")));
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("425"), new BigDecimal("35"), new BigDecimal("40")));
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("525"), new BigDecimal("45"), new BigDecimal("50")));
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("725"), new BigDecimal("65"), new BigDecimal("70")));
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("925"), new BigDecimal("71"), new BigDecimal("90")));
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("1125"), new BigDecimal("91"), new BigDecimal("110")));
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("1350"), new BigDecimal("111"), new BigDecimal("130")));
		em.persist(new IndemniteTrancheDistance(deplacementDefinitif,new BigDecimal("1550"), new BigDecimal("131"), new BigDecimal("150")));
		
		GroupeMission gmHg = groupeMission(Code.GROUPE_MISSION_HORS_GROUPE, "Hors groupe",_1Class);
		gmHg.setFonctions(fonctionsHorsGroupe);
		indemniteMission(gmHg, "170000","200000");
		
		GroupeMission gmA = groupeMission(Code.GROUPE_MISSION_A, "Groupe A",_2Class, a3,a2,a1);
		gmA.setFonctions(fonctionsGroupeA);
		indemniteMission(gmA, "85000","140000");
		
		GroupeMission gmB = groupeMission(Code.GROUPE_MISSION_B, "Groupe B",_3Class, d1,d2,c1,c2,c3,b1,b2,b3);
		indemniteMission(gmB, "75000","120000");
		
		em.persist(indemniteKP = new RegleCalcul(Code.INDEMNITE_KILOMETRIQUE_PERSONNE,"Indemnité kilométrique par personne",
				"var resultat;"
						+ "var d = o.distance(dossier);"
						+ "if(d<=150) resultat = o.montantDistance(d);"
						+ "else resultat = 1550 + (d-150)*8;"
						+ "resultat = resultat * o.nombreVoyageur(dossier)"));
		
		em.persist(indemniteBagageAgent = new RegleCalcul(Code.INDEMNITE_BAGGAGE_AGENT,"Indemnité bagage agent","var resultat = (33.5*o.distance(dossier)*o.poidsAgent(groupe))/1000;"));
		em.persist(indemniteBagageConjoint = new RegleCalcul(Code.INDEMNITE_BAGGAGE_CONJOINT,"Indemnité bagage conjoint","var resultat = (33.5*o.distance(dossier)*(masculin?o.poidsConjoint(groupe):0)*o.nombreConjoint(dossier))/1000;"));
		em.persist(indemniteBagageEnfant = new RegleCalcul(Code.INDEMNITE_BAGGAGE_ENFANT,"Indemnité bagage enfant","var resultat = (33.5*o.distance(dossier)*(masculin?o.poidsEnfant(groupe):0)*o.nombreEnfant(dossier))/1000;"));
		
		em.persist(indemniteJournaliereAgent = new RegleCalcul(Code.INDEMNITE_JOURNALIERE_AGENT,"Indemnité journaliere agent","var resultat = o.montantAgent(groupe)*2;"));
		em.persist(indemniteJournaliereConjoint = new RegleCalcul(Code.INDEMNITE_JOURNALIERE_CONJOINT,"Indemnité journaliere conjoint","var resultat = (masculin?o.montantConjoint(groupe):0)*o.nombreConjoint(dossier)*2;"));
		em.persist(indemniteJournaliereEnfant = new RegleCalcul(Code.INDEMNITE_JOURNALIERE_ENFANT,"Indemnité journaliere enfant","var resultat = (masculin?o.montantEnfant(groupe):0)*2*o.nombreEnfant(dossier);"));
		
		em.persist(indemniteMissionSejour = new RegleCalcul(Code.INDEMNITE_MISSION_SEJOUR,"Indemnité Mission Séjour",
				"var resultat = o.montantMissionSejour(dossier,groupe)*o.dureeJourDeplacement(dossier)"));
		em.persist(indemniteMissionDivers = new RegleCalcul(Code.INDEMNITE_MISSION_DIVERS,"Indemnité Mission Divers","var resultat = o.montantMissionDivers(dossier,groupe)"));
		
		em.persist(indemniteDistanceObseque = new RegleCalcul(Code.INDEMNITE_DISTANCE_OBSEQUE,"Indemnité Distance",
				"var resultat;"
						+ "if( o.codeLocaliteDepart(dossier)=='ABJ' && o.codeLocaliteArrivee(dossier)=='ABJ' ){"
						+ "  resultat = 30000;"
						+ "} else if( o.codeLocaliteArrivee(dossier)!='ABJ' ){"
						+ "  var d = o.distance(dossier);"
						+ "  if(d<=30){"
						+ "  resultat = 49000;"
						+ "  }else{"
						+ "  resultat = d*690;"
						+ "  }"
						+ "}"));
		em.persist(indemniteCercueil = new RegleCalcul(Code.INDEMNITE_CERCUEIL,"Indemnité cercueil","var resultat = o.montantCercueil(o.indice(dossier))"));
		
		for(NatureDeplacement natureDeplacement : new NatureDeplacement[]{natureDeplacementAffectation,natureDeplacementDepartRetraite,natureDeplacementMutation}){
			natureDeplacement.getIndemnites().add(indemniteKP);
			natureDeplacement.getIndemnites().add(indemniteBagageAgent);
			natureDeplacement.getIndemnites().add(indemniteBagageConjoint); 
			natureDeplacement.getIndemnites().add(indemniteBagageEnfant);
			natureDeplacement.getIndemnites().add(indemniteJournaliereAgent);
			natureDeplacement.getIndemnites().add(indemniteJournaliereConjoint);
			natureDeplacement.getIndemnites().add(indemniteJournaliereEnfant);
		}
		
		for(NatureDeplacement natureDeplacement : new NatureDeplacement[]{natureDeplacementMhci}){
			natureDeplacement.getIndemnites().add(indemniteMissionSejour);
			natureDeplacement.getIndemnites().add(indemniteMissionDivers);
		}
		
		for(NatureDeplacement natureDeplacement : new NatureDeplacement[]{natureDeplacementFo}){
			natureDeplacement.getIndemnites().add(indemniteDistanceObseque);
			natureDeplacement.getIndemnites().add(indemniteCercueil);
		}
	}
	
	private void parametrageOperation(){
		
		NatureOperation noOrganisationDeplacement,noAnnulationDeplacement,noTransmissionSaisieBen,noTransmissionSaisieOrg,noSoumissionDeplacment,
			noCreationDemande,noSaisie,noSoumission,noValidationRecevabilite,noValidationConformite,noRetourFD,noReceptionFacture,noDepotDossier,noEtablissementBL,noValidationEtablissementBL/*,noTransmissionBLPourVisa*/,
			noVisaBL,noProductionBT,noModificationBT,noValidationProductionBT,noTransmissionBTPriseEnCharge,noRetourBT,noAnnulerDemande,nodemandeCotationBA,norepondreCotationBA,nogenBCTT,novisaBCTT,notransBCTT,
			noPriseEnChargeBT,noMiseEnReglementBT,noReglerBulletinLiquidation,annulationCS,demandeCS,retraitCS,annulationLCS,ouvertureLCS,clotureLCS,validationLCS,retraitCarteLCS,generationCCS,distributionCCSSotra,
			distributionCCSDelegue,noEnregistrerFactureDefinitive,noEtablissementPREMB,noValidationPREMB,noTransmissionPREMB,noReceptionPREMB,nogenBCFO,novisaBCFO,notransBCFO;
		
		em.persist(statutAccepte = new Statut(Code.STATUT_ACCEPTE, "Accepte"));
		em.persist(statutRejete = new Statut(Code.STATUT_REJETE, "Rejete"));
		em.persist(statutDiffere = new Statut(Code.STATUT_DIFFERE, "Differe"));
		/*------------------------------------------- Creation des Natures d'Opération possible ----------------------------------------------*/
		//Par l'organisateur d'un deplacement
		em.persist(noOrganisationDeplacement = new NatureOperation(Code.NATURE_OPERATION_ORGANISATION_DEPLACEMENT, "Organisation d'un déplacement","Mission en cours d'organisation"));
		em.persist(noTransmissionSaisieBen = new NatureOperation(Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE, "Transimission de la saisie au bénéficiaire","Demande à complèter"));
		em.persist(noSoumissionDeplacment = new NatureOperation(Code.NATURE_OPERATION_SOUMISSION_DEPLACEMENT, "Soumission d'un déplacment","Mission soumise"));
		
		//Par l'usager
		//Carte sotra
		em.persist(annulationCS = new NatureOperation(Code.NATURE_OPERATION_ANNULATION_CS, "Annulation de la demande de carte sotra","Demande de carte sotra annulée"));
		em.persist(demandeCS = new NatureOperation(Code.NATURE_OPERATION_DEMANDE_CS, "Demande de carte sotra","Demande de carte sotra éffectuée"));
		em.persist(retraitCS = new NatureOperation(Code.NATURE_OPERATION_RETRAIT_CS, "Retrait de la carte sotra","Carte sotra retirée"));
		//Dossier
		em.persist(noCreationDemande = new NatureOperation(Code.NATURE_OPERATION_CREATION_DEMANDE, "Création d'une demande","Demande créee"));
		em.persist(noTransmissionSaisieOrg = new NatureOperation(Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_ORGANISATEUR, "Transimission de la saisie à l'organisateur","Mission en cours d'organisation"));
		em.persist(noAnnulerDemande = new NatureOperation(Code.NATURE_OPERATION_ANNULATION_DEMANDE, "Annulation de la demande","Demande annulée"));
		em.persist(noSaisie = new NatureOperation(Code.NATURE_OPERATION_SAISIE, "Saisie de la demande","Demande initiée"));
		em.persist(noSoumission = new NatureOperation(Code.NATURE_OPERATION_SOUMISSION, "Soumission de la demande","Demande soumise"));
		em.persist(noDepotDossier = new NatureOperation(Code.NATURE_OPERATION_DEPOT, "Depot de dossier","Dossier deposé"));
		em.persist(noRetourFD = new NatureOperation(Code.NATURE_OPERATION_RETOUR_FD, "Retourner une feuille de déplacement","Feuille de déplacement retournée"));
		
		//Carte Sotra
		em.persist(annulationLCS = new NatureOperation(Code.NATURE_OPERATION_ANNULATION_LCS, "Annulation de la liste de carte sotra","Liste de carte sotra annulée"));
		em.persist(ouvertureLCS = new NatureOperation(Code.NATURE_OPERATION_OUVERTURE_LCS, "Ouverture de la liste de carte sotra","Liste de carte sotra ouverte"));
		em.persist(clotureLCS = new NatureOperation(Code.NATURE_OPERATION_CLOTURE_LCS, "Cloture de la liste de carte sotra","Liste de carte sotra clos"));
		em.persist(validationLCS = new NatureOperation(Code.NATURE_OPERATION_VALIDATION_LCS, "Validation de la liste de carte sotra","Liste de carte sotra validée"));
		em.persist(retraitCarteLCS = new NatureOperation(Code.NATURE_OPERATION_RETRAIT_CARTE_LCS, "Retrait des cartes sotra","Cartes sotra retirées"));
		
		em.persist(generationCCS = new NatureOperation(Code.NATURE_OPERATION_GENERATION_CCS, "Génération de bon de commande de carte sotra","Bon de commande de carte sotra généré"));
		em.persist(distributionCCSSotra = new NatureOperation(Code.NATURE_OPERATION_DISTRIBUTION_CCS_SOTRA, "Distribution de bon de commande de carte sotra à la sotra","Bon de commande de carte sotra distribué à la sotra"));
		em.persist(distributionCCSDelegue = new NatureOperation(Code.NATURE_OPERATION_DISTRIBUTION_CCS_DELEGUE, "Distribution de bon de commande de carte sotra au délégué","Bon de commande de carte sotra distribué au délégué"));
		
		em.persist(noValidationRecevabilite = new NatureOperation(Code.NATURE_OPERATION_RECEVABILITE, "Juger la recevabilité","Demande jugée recevable"));
		em.persist(noValidationConformite = new NatureOperation(Code.NATURE_OPERATION_CONFORMITE, "Juger la conformité","Dossier jugé conforme"));
		em.persist(noReceptionFacture = new NatureOperation(Code.NATURE_OPERATION_RECEPTION_FACTURE, "Receptionner une facture","Facture receptionnée"));
		
		//Liquidation
		em.persist(noEtablissementBL = new NatureOperation(Code.NATURE_OPERATION_ETABLISSEMENT_BL, "Etablissement de bulletin de liquidation","Bulletin de liquidation généré"));
		em.persist(noValidationEtablissementBL = new NatureOperation(Code.NATURE_OPERATION_VALIDATION_BL, "Validation de bulletin de liquidation","Bulletin de liquidation validé"));
		//em.persist(noTransmissionBLPourVisa = new NatureOperation(Code.NATURE_OPERATION_TRANSMISSION_BL_VISA, "Transmission de bulletin de liquidation pour visa","Bulletin de liquidation transmis pour visa"));
		em.persist(noVisaBL = new NatureOperation(Code.NATURE_OPERATION_VISA_BL, "Visa de bulletin de liquidation","Bulletin de liquidation visé"));
		
		//Remboursement
		em.persist(noEtablissementPREMB = new NatureOperation(Code.NATURE_OPERATION_ETABLISSEMENT_PROJDEC, "Etablissement de projet de décision","Projet de décision généré"));
		em.persist(noValidationPREMB = new NatureOperation(Code.NATURE_OPERATION_VALIDATION_PROJDEC, "Validation de projet de décision","Projet de décision validé"));
		em.persist(noTransmissionPREMB = new NatureOperation(Code.NATURE_OPERATION_TRANSMISSION_PROJDEC_CABINET, "Transmission de projet de décision au cabinet","Projet de décision transmis au cabinet"));
		em.persist(noReceptionPREMB = new NatureOperation(Code.NATURE_OPERATION_RECEPTION_PROJDEC, "Reception de décision de remboursement","Décision de remboursement réceptionnée"));
	
		
		//Transmission BL
		em.persist(noProductionBT = new NatureOperation(Code.NATURE_OPERATION_ETABLISSEMENT_BTBL, "Production de bordereaux de transmission","Bordereau de bulletin de liquidation généré"));
		em.persist(noModificationBT = new NatureOperation(Code.NATURE_OPERATION_MODIFICATION_BTBL, "Modification d'un bordereau","Bordereau de bulletin de liquidation modifié"));
		em.persist(noValidationProductionBT = new NatureOperation(Code.NATURE_OPERATION_VALIDATION_BTBL, "Validation de bordereaux de transmission","Bordereau de bulletin de liquidation validé"));
		em.persist(noTransmissionBTPriseEnCharge = new NatureOperation(Code.NATURE_OPERATION_TRANSMISSION_BTBL, "Transmission de bordereaux","Bordereau de bulletin de liquidation tranmis au payeur"));
		
		//Paiement
		em.persist(noPriseEnChargeBT = new NatureOperation(Code.NATURE_OPERATION_PRISE_EN_CHARGE, "Prise en charge de bordereaux","Bordereau de bulletin de liquidation pris en charge"));
		em.persist(noMiseEnReglementBT = new NatureOperation(Code.NATURE_OPERATION_MISE_EN_REGLEMENT, "Mise en reglement de bordereaux","Bordereau de bulletin de liquidation mis en règlement"));
		em.persist(noReglerBulletinLiquidation = new NatureOperation(Code.NATURE_OPERATION_REGLER_BL, "Reglement de bulletin de liquidation","Bulletin de liquidation reglé"));
		
		
		//Prestation
		em.persist(nodemandeCotationBA = new NatureOperation(Code.NATURE_OPERATION_DEMANDE_COTATION, "Demande de cotation","Demande de cotation emise"));
		em.persist(norepondreCotationBA = new NatureOperation(Code.NATURE_OPERATION_REPONDRE_COTATION, "Repondre à une demande de cotation","Demande de cotation traitée"));
		
		em.persist(nogenBCTT = new NatureOperation(Code.NATURE_OPERATION_GENERATION_BCTT, "Générer un bon de commande de titre de transport","Bon de commande de titre de transport généré"));
		em.persist(nogenBCFO = new NatureOperation(Code.NATURE_OPERATION_GENERATION_BCFO, "Générer un bon de commande de frais d'obsèque","Bon de commande de frais d'obsèques généré"));
		
		em.persist(novisaBCTT = new NatureOperation(Code.NATURE_OPERATION_VISA_BCTT, "Valider bon de commande de titre de transport","Bon de commande de titre de transport validé"));
		em.persist(novisaBCFO = new NatureOperation(Code.NATURE_OPERATION_VISA_BCFO, "Valider bon de commande de frais d'obsèque","Bon de commande de frais d'obsèque validé"));
		
		em.persist(notransBCTT = new NatureOperation(Code.NATURE_OPERATION_TRANSMISSION_BCTT, "Transmettre bon de commande de titre de transport","Bon de commande de titre de transport transmis"));
		em.persist(notransBCFO = new NatureOperation(Code.NATURE_OPERATION_TRANSMISSION_BCFO, "Transmettre bon de frais d'obsèque","Bon de commande de frais d'obsèque transmis"));
		/*-------------------------------------------- Configuration des opérations ------------------------------------------------------------*/
		
		personnelOperationConfig(noOrganisationDeplacement,null,noSoumission,false, rolePF);
		personnelOperationConfig(noSoumissionDeplacment,noOrganisationDeplacement,noValidationRecevabilite,false, rolePF);
		personnelOperationConfig(noTransmissionSaisieBen,null,noTransmissionSaisieOrg,false, rolePF);
		
		//Sotra
		usagerOperationConfig(annulationCS, null, null);
		usagerOperationConfig(demandeCS, null, retraitCS);
		usagerOperationConfig(retraitCS, demandeCS, null);
		
		usagerOperationConfig(noTransmissionSaisieOrg, noTransmissionSaisieBen, noSoumission);
		usagerOperationConfig(noAnnulerDemande, null, null);
		usagerOperationConfig(noSaisie, null, noSoumission);
		usagerOperationConfig(noSoumission, noSaisie, noValidationRecevabilite);
		
		personnelOperationConfig(noValidationRecevabilite,noSoumission,noDepotDossier,true,false, roleResponsable);
		usagerOperationConfig(noDepotDossier,noValidationRecevabilite,noValidationConformite);
		personnelOperationConfig(noValidationConformite,noDepotDossier,noEtablissementBL, roleResponsable);
		
		//Sotra
		personnelOperationConfig(annulationLCS,null,null,false, roleDelegueSotra);
		personnelOperationConfig(ouvertureLCS,null,clotureLCS,false, roleDelegueSotra);
		personnelOperationConfig(clotureLCS,ouvertureLCS,validationLCS,false, roleDelegueSotra);
		personnelOperationConfig(validationLCS,clotureLCS,retraitCarteLCS,false, roleDelegueSotra);
		personnelOperationConfig(retraitCarteLCS,validationLCS,null,false, roleDelegueSotra);
		
		personnelOperationConfig(generationCCS,null,distributionCCSSotra, roleGCS);
		personnelOperationConfig(distributionCCSSotra,generationCCS,distributionCCSDelegue, roleGCS);
		personnelOperationConfig(distributionCCSDelegue,distributionCCSSotra,null, roleGCS);
		
		personnelOperationConfig(noEtablissementBL,null,noValidationEtablissementBL, roleLiquidateur);
		personnelOperationConfig(noValidationEtablissementBL,noEtablissementBL,noVisaBL, roleResponsable);
		//personnelOperationConfig(noTransmissionBLPourVisa,noValidationEtablissementBL,noVisaBL,false, roleResponsable);
		//personnelOperationConfig(noVisaBL,noTransmissionBLPourVisa,noProductionBT, roleDirecteur);
		personnelOperationConfig(noVisaBL,noValidationEtablissementBL,noProductionBT, roleDirecteur);//
		
		personnelOperationConfig(noEtablissementPREMB,null,noValidationPREMB,false, roleLiquidateur);
		personnelOperationConfig(noValidationPREMB,noEtablissementPREMB,noTransmissionPREMB,false, roleResponsable);
		personnelOperationConfig(noTransmissionPREMB,noValidationPREMB,noReceptionPREMB,false, roleDirecteur);
		personnelOperationConfig(noReceptionPREMB,noTransmissionPREMB,null,false, roleLiquidateur);//
		
		personnelOperationConfig(noRetourFD,null,null,roleResponsable);
		personnelOperationConfig(noReceptionFacture,null,null,false, roleResponsable);
		
		
		personnelOperationConfig(noProductionBT,null,noValidationProductionBT,false, roleResponsable);
		personnelOperationConfig(noValidationProductionBT,noProductionBT,noTransmissionBTPriseEnCharge, roleDirecteur);
		personnelOperationConfig(noTransmissionBTPriseEnCharge,noValidationProductionBT,noPriseEnChargeBT,false, roleDirecteur);
		
		personnelOperationConfig(noPriseEnChargeBT, noTransmissionBTPriseEnCharge, noMiseEnReglementBT,rolePayeur);
		personnelOperationConfig(noMiseEnReglementBT, noPriseEnChargeBT, null,false,rolePayeur);
		personnelOperationConfig(noReglerBulletinLiquidation, noMiseEnReglementBT, null,false,rolePayeur);
		
		personnelOperationConfig(noModificationBT,null,null,false, roleDirecteur);
		
		personnelOperationConfig(nodemandeCotationBA, null, norepondreCotationBA,false,roleResponsable);
		personnelOperationConfig(norepondreCotationBA, nodemandeCotationBA, null,false,rolePrestataire);
		personnelOperationConfig(nogenBCTT, null, novisaBCTT,false,roleResponsable);
		personnelOperationConfig(novisaBCTT,nogenBCTT,notransBCTT,roleDirecteur);
		personnelOperationConfig(nogenBCFO, null, novisaBCFO,false,roleResponsable);
		personnelOperationConfig(novisaBCFO,nogenBCFO,notransBCFO,roleDirecteur);

		personnelOperationConfig(notransBCTT, novisaBCTT, null,false,roleResponsable);
		personnelOperationConfig(notransBCFO, novisaBCFO, null,false,roleResponsable);
	}
	
	private void creationUtilisateur(){
		AgentEtat agentEtat1,agentEtat2,agentEtat3,agentEtat4,agentEtat5,agentEtat6,agentEtat7,agentEtatTest1,agentEtatTest2,agentEtatTest3,agentEtatTest4,agentEtatTest5;
		//chargement des agents de l'etat
		creerAgentEtatReference("096000T", "Fiellou", "N'Dri", date(1,1,1960));
		creerAgentEtatReference("101000G", "Edoh", "Vincent", date(1,1,1960));
		creerAgentEtatReference("201000L", "Losseni", "Diarrassouba", date(1,1,1960));
		creerAgentEtatReference("175000H", "Thio", "Bekpancha", date(1,1,1960));
		creerAgentEtatReference("360257X", "Nadi", "Boua Eric", date(1,1,1960));
		
		//AgentEtatReference testAgentEtatReference0 = creerAgentEtatReference("900900Z", "Admin", "Admin", date(1,1,1960));
		
		AgentEtatReference testAgentEtatReference1 = creerAgentEtatReference("900900A", "Komenan", "Yao christian", date(1,1,1960));
		AgentEtatReference testAgentEtatReference2 = creerAgentEtatReference("900900B", "Amichia", "Martial", date(1,1,1960));
		AgentEtatReference testAgentEtatReference3 = creerAgentEtatReference("900900C", "Irie", "Henok Roland", date(1,1,1960));
		AgentEtatReference testAgentEtatReference4 = creerAgentEtatReference("900900D", "N'Dri", "Aime", date(1,1,1960));
		
		AgentEtatReference testAgentEtatReference10 = creerAgentEtatReference("900800A", "N'Goran", "Albertine", date(1,1,1960));
		
		//personnel de la solde
		Personnel personnelPointFocal = creerPersonnel(creerAgentEtatReference("900400D", "Alain", "Boua", date(1,1,1960)),sectionServiceLiquidation,"pointfocal",rolePF);
		
		Personnel personnelLiquidateur = creerPersonnel(creerAgentEtatReference("900500D", "Jack", "Yves", date(1,1,1960)),sectionServiceLiquidation,"liquidateur",roleLiquidateur,roleGCS);
		
		personnelResponsable = creerPersonnel(creerAgentEtatReference("900600D", "Adou", "Bernard", date(1,1,1960)),sectionServiceLiquidation,"responsable",roleResponsable);
		Personnel personnelResponsable1 = creerPersonnel(creerAgentEtatReference("900601D", "Madou", "Zike", date(1,1,1960)),sectionServiceLiquidation,"responsable1",roleLiquidateur,roleResponsable);
		
		//Personnel personnelResponsableFemme = creerPersonnel(creerAgentEtatReference("900800D", "Adou", "Bernanrd", date(1,1,1960)),"responsablef",roleResponsable);
		
		Personnel personnelPayeur = creerPersonnel(creerAgentEtatReference("900700D", "N'Goran", "Albertine", date(1,1,1960)),sectionServicePaiement,"payeur",rolePayeur);
		personnelPayeur.getAgentEtat().setSexe(Sexe.FEMININ);
		
		creerPersonnel(creerAgentEtatReference("900900Z", "Admin", "Admin", date(1,1,1960)),sectionSousDirectionDCP,"admin",roleAdministrateur);
		
		personnelDirecteur = creerPersonnel(creerAgentEtatReference("900801D", "Yao", "Constant", date(1,1,1960)),sectionSousDirectionDCP,"directeur",roleDirecteur);
		personnelDirecteur1 = creerPersonnel(creerAgentEtatReference("900802D", "Ali", "Bamba", date(1,1,1960)),sectionSousDirectionDCP,"directeur1",roleDirecteur,roleLiquidateur);
		personnelDirecteur2 = creerPersonnel(creerAgentEtatReference("900803D", "Zadi", "Kacou", date(1,1,1960)),sectionSousDirectionDCP,"directeur2",roleDirecteur,roleResponsable);
		personnelDirecteur3 = creerPersonnel(creerAgentEtatReference("900804D", "Molle", "Jules", date(1,1,1960)),sectionSousDirectionDCP,"directeur3",roleDirecteur,roleResponsable,roleLiquidateur,rolePF,rolePayeur,
				roleGCS,roleDelegueSotra,rolePrestataire);
		
		
		/*
		agentEtat1 = creerAgentEtatReference(fonctionnaire,"096000T", "Fiellou", "N'Dri", date(1,1,1960), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		agentEtat2 = creerAgentEtatReference(fonctionnaire,"101000G", "Edoh", "Vincent", date(1,1,1960), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		agentEtat3 = creerAgentEtatReference(fonctionnaire,"201000L", "Losseni", "Diarrassouba", date(1,1,1960), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		agentEtat4 = creerAgentEtatReference(fonctionnaire,"175000H", "Thio", "Bekpancha", date(1,1,1960), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		agentEtat5 = creerAgentEtatReference(fonctionnaire,"360257X", "Nadi", "Boua Eric", date(1,1,1960), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		*/
		//agentEtat5 = creerAgentEtat(fonctionnaire,"360257X", "Nadi", "Boua Eric", date(1,1,1960), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		//agentEtat7 = creerAgentEtat(fonctionnaire,"360257X", "Nadi", "Boua Eric", date(1,1,1960), Sexe.MASCULIN,situationMatrimoniale1, coteDivoire, null,null,null,null,null,null,null);
		
		
		creerCompteUtilisateur(agentEtatTest1 = creerAgentEtat(testAgentEtatReference1), "christian.komenan@budget.gouv.ci", "12345678", new Object[][]{ {questionSecrete3,"Dgbf"} });
		creerCompteUtilisateur(agentEtatTest2 = creerAgentEtat(testAgentEtatReference2), "marcial.amichia@budget.gouv.ci", "12345678", new Object[][]{ {questionSecrete3,"Dgbf"} });
		creerCompteUtilisateur(agentEtatTest3 = creerAgentEtat(testAgentEtatReference3), "irie.serge@budget.gouv.ci", "12345678", new Object[][]{ {questionSecrete3,"Dgbf"} });
		creerCompteUtilisateur(agentEtatTest4 = creerAgentEtat(testAgentEtatReference4), "stephane.ndri@budget.gouv.ci", "12345678", new Object[][]{ {questionSecrete3,"Dgbf"} });
		
		Personne agentMission = new Personne("Habib", "Bey", date(1, 1, 1960), contact(), Sexe.MASCULIN, situationMatrimonialeMarie, coteDivoire, professionIngInfo);
		em.persist(agentMission);
		creerCompteUtilisateur(agentMission, "agentmission", "12345678",new Object[][]{ {questionSecrete3,"Dgbf"} },roleAgentMission);
		
		Personne personne = new Personne("Jules", "Ferry", date(1, 1, 1960), contact(), Sexe.MASCULIN, situationMatrimonialeMarie, coteDivoire, professionIngInfo);
		em.persist(personne);
		creerCompteUtilisateur(personne, "ayantdroit", "12345678",new Object[][]{ {questionSecrete3,"Dgbf"} },roleAD);
		
		DelegueSotra delegueSotra1 = new DelegueSotra(personnelDirecteur3.getAgentEtat(),personnelDirecteur3.getAgentEtat(),sectionServiceIdentification);
		em.persist(delegueSotra1);
		DelegueSotra delegueSotra2 = new DelegueSotra(personnelDirecteur2.getAgentEtat(),personnelDirecteur3.getAgentEtat(),sectionServiceLiquidation);
		em.persist(delegueSotra2);
		
		BeneficiaireCarteSotra adherentCarteSotra1 = new BeneficiaireCarteSotra(delegueSotra1,agentEtatTest3,date());
		em.persist(adherentCarteSotra1);
		BeneficiaireCarteSotra adherentCarteSotra2 = new BeneficiaireCarteSotra(delegueSotra1,agentEtatTest4,date());
		em.persist(adherentCarteSotra2);
		
		
		creerCompteUtilisateur( new Prestataire(pompesFunebres,"IVOSEP", contact()) , "ivosep", "12345678",new Object[][]{ {questionSecrete3,"Dgbf"} },rolePrestataire);
		creerCompteUtilisateur(  new Prestataire(agenceVoyages,"Elohim Voyage", contact()) , "elohim", "12345678",new Object[][]{ {questionSecrete3,"Dgbf"} },rolePrestataire);
		creerCompteUtilisateur(  new Prestataire(agenceVoyages,"Mistral Voyage", contact()) , "mistral", "12345678",new Object[][]{ {questionSecrete3,"Dgbf"} },rolePrestataire);
		creerCompteUtilisateur( new Prestataire(transitaire,"TRANSIT", contact()) , "transit", "12345678",new Object[][]{ {questionSecrete3,"Dgbf"} },rolePrestataire);
		
	}

	
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	private AgentEtatReference creerAgentEtatReference(String matricule, String nom, String prenoms, Date dateNaissance){
		AgentEtatReference agentEtatReference = new AgentEtatReference(matricule, nom+" "+prenoms, dateNaissance, null, null,null,null,null,null,null,null);
		em.persist(agentEtatReference);
		return agentEtatReference;
	}
	
	private Contact contact(){
		return new Contact("christian.komenan@budget.gouv.ci", "11223344", "01 BP Abidjan 01", null, abidjan);
	}
	
	private static Date date(){
		return new Date();
	}
	
	private static Date date(int j,int m,int a){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, j);
		calendar.set(Calendar.MONTH, m);
		calendar.set(Calendar.YEAR, a);
		return calendar.getTime();
	}
	
	static String numero(){
		return UUID.randomUUID().toString();
	}
	
	private PieceJustificativeAFournir pjaf(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne,
			TypePieceJustificative typePieceJustificative,Integer quantite,Boolean original,Integer periodeValiditeEnMois,Boolean conditionnee,String description){
		PieceJustificativeAFournir p = new PieceJustificativeAFournir(typePieceJustificative,natureDeplacement,typePersonne, typeDepense,original,periodeValiditeEnMois, quantite,description);
		p.getConfig().setConditionnee(conditionnee);
		return p;
	}
	
	private PieceJustificativeAFournir pjaf(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne,TypePieceJustificative typePieceJustificative,Boolean conditionnee){
		return pjaf(natureDeplacement, typeDepense, typePersonne, typePieceJustificative, 1,Boolean.TRUE, -6,conditionnee,null);
	}
	
	private PieceJustificativeAFournir pjaf(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne,TypePieceJustificative typePieceJustificative){
		return pjaf(natureDeplacement, typeDepense, typePersonne, typePieceJustificative, 1,Boolean.TRUE, -6,Boolean.FALSE,null);
	}
	
	private void communPieceJustificativeAFournir(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne){
		//em.persist(pjaf(natureDeplacement, typeDepense, typePersonne, cni));
	}
	
	private void communDDPieceJustificativeAFournir(NatureDeplacement natureDeplacement){
		em.persist(pjaf(natureDeplacement, remboursement, fonctionnaire, extraitNaissance,Boolean.TRUE));
		em.persist(pjaf(natureDeplacement, remboursement, fonctionnaire, extraitMariage,Boolean.TRUE));
		PieceJustificativeAFournir p;
		em.persist(p = pjaf(natureDeplacement, remboursement, fonctionnaire, feuilleDep));
		p.getConfig().setDerivee(Boolean.TRUE);
		em.persist(p = pjaf(natureDeplacement, remboursement, fonctionnaire, bonTransport));
		p.getConfig().setDerivee(Boolean.TRUE);
		
		em.persist(p = pjaf(natureDeplacement, remboursement, fonctionnaire, cni));
		em.persist(p = pjaf(natureDeplacement, remboursement, fonctionnaire, attestationTransport));
	}
	
	private void communTRPieceJustificativeAFournir(NatureDeplacement natureDeplacement){
		em.persist(pjaf(natureDeplacement,priseEnCharge,null, factprof));
		em.persist(pjaf(natureDeplacement,remboursement,null, factdef));
		//PieceJustificativeAFournir p;
		//em.persist(p = pjaf(natureDeplacement,priseEnCharge,null, attpec));
		//p.getConfig().setDerivee(true);
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
	
	private void creerCompteUtilisateur(Party utilisateur,String username,String password,Object[][] rs,Role...roles){
		Set<Role> r = new HashSet<Role>();
		if(utilisateur instanceof AgentEtat)
			r.add(roleAgentEtat);
		if(utilisateur.getId()==null)
			em.persist(utilisateur);
		if(roles!=null)
			r.addAll(Arrays.asList(roles));
		CompteUtilisateur compteUtilisateur = new CompteUtilisateur(new Credentials(username, password),utilisateur,r);
		for(Object[] o : rs)
			compteUtilisateur.getReponseSecretes().add(new ReponseSecrete((QuestionSecrete)o[0], (String)o[1]));
		compteUtilisateur.setDateCreation(new Date());
		em.persist(compteUtilisateur);
	}
	
	private AgentEtat creerAgentEtat(AgentEtatReference agentEtatReference){
		AgentEtat agentEtat = new AgentEtat(fonctionnaire, agentEtatReference.getMatricule(), agentEtatReference.getNomPrenoms(), null, date(1, 1, 1960), contact(), Sexe.MASCULIN, null, coteDivoire, date(), null, null, null, null, null, null, null, null);
		em.persist(agentEtat);
		return agentEtat;
	}
	
	private Personnel creerPersonnel(AgentEtatReference agentEtatReference,Section section,String username,Role...roles){
		Personnel personnel = new Personnel(creerAgentEtat(agentEtatReference));
		personnel.getAgentEtat().setSection(section);
		em.persist(personnel);
		creerCompteUtilisateur(personnel.getAgentEtat(), username, "12345678", new Object[][]{ {questionSecrete3,"Dgbf"}},ArrayUtils.addAll(roles, roleAgentSolde));
		return personnel;
	}
	
	private GroupeDD groupeDD(String code,String ma,String pa,String me,String pe,String mef,String pef,Grade...grades){
		GroupeDD g = new GroupeDD(code, code, new BigDecimal(ma),  new BigDecimal(pa),  new BigDecimal(me),  new BigDecimal(pe),  new BigDecimal(mef),  new BigDecimal(pef));
		for(Grade grade : grades)
			g.getGrades().add(grade);
		em.persist(g);
		return g;
	}
	
	private GroupeMission groupeMission(String code,String libelle,TypeClasseVoyage typeClasseVoyage,Grade...grades){
		GroupeMission g = new GroupeMission(code, libelle);
		g.setClasseVoyage(typeClasseVoyage);
		for(Grade grade : grades)
			g.getGrades().add(grade);
		em.persist(g);
		return g;
	}
		
	private void personnelOperationConfig(NatureOperation natureOperation,NatureOperation precedent,NatureOperation suivant,boolean choix,boolean differer,Role role){
		em.persist(new OperationValidationConfig(natureOperation, ValidationType.ACCEPTER, statutAccepte, role));
		if(choix){
			em.persist(new OperationValidationConfig(natureOperation, ValidationType.REJETER, statutRejete,role));
			if(differer)
				em.persist(new OperationValidationConfig(natureOperation, ValidationType.DIFFERER, statutDiffere,role));
		}
		natureOperation.setPrecedent(precedent);
		natureOperation.setSuivant(suivant);
	}
	
	private void personnelOperationConfig(NatureOperation natureOperation,NatureOperation precedent,NatureOperation suivant,boolean choix,Role role){
		personnelOperationConfig(natureOperation, precedent, suivant, choix, true, role);
	}
	
	private void personnelOperationConfig(NatureOperation natureOperation,NatureOperation precedent,NatureOperation suivant,Role role){
		personnelOperationConfig(natureOperation, precedent, suivant, true,true, role);
	}
	
	private void usagerOperationConfig(NatureOperation natureOperation,NatureOperation precedent,NatureOperation suivant){
		em.persist(new OperationValidationConfig(natureOperation, ValidationType.ACCEPTER, statutAccepte,roleAgentEtat));
		natureOperation.setPrecedent(precedent);
		natureOperation.setSuivant(suivant);
	}
	

	
	private Grade creerGrade(Categorie categorie,int index){
		Grade g;
		em.persist(g = new Grade(categorie, categorie.getCode()+index+"", categorie.getCode()+index+""));
		grades.add(g);
		return g;
	}
	

	
	private void indemniteMission(GroupeMission groupe,String montantAfrique,String montantResteMonde){
		LocaliteGroupeMission lgm = new LocaliteGroupeMission(new LocaliteGroupeMissionId(zoneAfrique.getCode(),groupe.getCode()),new BigDecimal(montantAfrique));
		em.persist(lgm);
		lgm = new LocaliteGroupeMission(new LocaliteGroupeMissionId(zoneResteMonde.getCode(),groupe.getCode()),new BigDecimal(montantResteMonde));
		em.persist(lgm);
	}
	
	private int professionId=0;
	private Profession profession(String libelle,Grade grade){
		Profession profession = new Profession("PROF"+(professionId++), libelle, grade);
		em.persist(profession);
		return profession;
	}
	
	private int fonctionId=0;
	private Fonction fonction(String libelle/*,Boolean horsGroupe*/){
		Fonction fonction = new Fonction("FONCTION"+(fonctionId++), libelle/*, horsGroupe*/);
		em.persist(fonction);
		return fonction;
	}


}
