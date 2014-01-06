package ci.gouv.budget.solde.sigdcp.service;

import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.CauseDeces;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.GroupeTypePiece;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Operation;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.geographie.TypeLocalite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Echelon;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.InfosInscriptionPersonne;
import ci.gouv.budget.solde.sigdcp.model.identification.Inscription;
import ci.gouv.budget.solde.sigdcp.model.identification.Position;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.SituationMatrimoniale;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.TypePersonne;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeSection;

@Stateless//(mappedName="SampleDataService") @Remote
public class SampleDataServiceImpl implements SampleDataService {

	@PersistenceContext
	private EntityManager em ;
	private TypeDepense priseEnCharge,remboursement;
	private TypeAgentEtat fonctionnaire,contractuel,policier,gendarme;
	private TypePersonne ayantDroit;
	private TypePieceJustificative extraitNaissance,extraitMariage,cni,feuilleDep,bonTransport;
	
	@Override 
	public void create() {
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
		
		NatureDeplacement affectation=new NatureDeplacement(deplacementDefinitif, Code.NATURE_DEPLACEMENT_AFFECTATION,"Affectation", 2);
		em.persist(affectation);
		em.persist(pjaf(affectation,remboursement,fonctionnaire, decisionAffectation));
		communPieceJustificativeAFournir(affectation, remboursement, fonctionnaire);
		communDDPieceJustificativeAFournir(affectation);
		
		NatureDeplacement mutation = new NatureDeplacement(deplacementDefinitif, Code.NATURE_DEPLACEMENT_MUTATION,"Mutation", 2);
		em.persist(mutation);
		em.persist(pjaf(mutation,remboursement,fonctionnaire,avisMutation));
		communPieceJustificativeAFournir(mutation, remboursement, fonctionnaire);
		communDDPieceJustificativeAFournir(mutation);
			
		NatureDeplacement retraite = new NatureDeplacement(deplacementDefinitif, Code.NATURE_DEPLACEMENT_RETRAITE,"Retraite", 2);
		em.persist(retraite);
		em.persist(pjaf(retraite,remboursement,fonctionnaire,avisMutation));
		communPieceJustificativeAFournir(retraite, remboursement, fonctionnaire);
		communDDPieceJustificativeAFournir(retraite);
		
		NatureDeplacement mhci = new NatureDeplacement(mission, Code.NATURE_DEPLACEMENT_MISSION_HCI,"Mission Hors Côte d'Ivoire", 2);
		em.persist(mhci);
		em.persist(pjaf(mhci,priseEnCharge,null, com));
		em.persist(pjaf(mhci,priseEnCharge,null, att));
		em.persist(pjaf(mhci,priseEnCharge,null, om));
		communPieceJustificativeAFournir(mhci, priseEnCharge, null);
		
		NatureDeplacement fo = new NatureDeplacement(obseque, Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS,"Frais d'obsèques", 2);
		em.persist(fo);
		em.persist(pjaf(fo,priseEnCharge,fonctionnaire,certdeces));
		em.persist(pjaf(fo,priseEnCharge,fonctionnaire, extdeces));
		em.persist(pjaf(fo,priseEnCharge,fonctionnaire, bullsal));
		em.persist(pjaf(fo,priseEnCharge,fonctionnaire, lettmin));
		em.persist(pjaf(fo,priseEnCharge,ayantDroit, cni));
		communPieceJustificativeAFournir(fo, priseEnCharge, fonctionnaire);
		
		
		NatureDeplacement tr = new NatureDeplacement(transit, Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES,"Transit de Bagages", 2);
		em.persist(tr);
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, attms,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, attfs,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, decisrappel,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, cps,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, ccs,Boolean.TRUE));
		em.persist(pjaf(tr,priseEnCharge,fonctionnaire, attmae,Boolean.TRUE));
		communPieceJustificativeAFournir(tr, priseEnCharge, fonctionnaire);
		
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
		
		Localite abj = new Localite("ABJ", "Abidjan", null,ville);
		em.persist(abj);
		Localite bk = new Localite("BK", "Bouake", null,ville);
		em.persist(bk);
		
		Localite coteDivoire = new Localite(Code.LOCALITE_COTE_DIVOIRE, "Côte d'Ivoire", null,pays);
		em.persist(coteDivoire);
		
		
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
		
		Section mef = new Section(null,Code.SECTION_MIN_MEF, "Economie et finances", ministere);
		em.persist(mef);
		Section bud = new Section(null,Code.SECTION_MIN_MB, "Budget", ministere);
		em.persist(bud);
		Section sexp = new Section(bud,Code.SECTION_SERV_EXP, "Exploitation", service);
		em.persist(sexp);
		Section set = new Section(bud,Code.SECTION_SERV_ET, "Etude et développement", service);
		em.persist(set);
		
		Date dateNaiss = new Date();
		AgentEtat agentEtat1 = new AgentEtat("AE1","A99", "Tata", "Pion", dateNaiss, new Contact("tatmail@yahoo.com", "123456", "02 BP Abidjan", "Rue des masques", null), Sexe.MASCULIN, situationMatrimoniale1, 
				coteDivoire, new Date(),  a1, echelon1, position1, 2000, fonction1, bud, profession1, null);
		em.persist(agentEtat1);
		
		inscrireAgentEtat("DZ12", "Zadi", "Alain", new Date(), new Contact("mail@yahoo.com", "123456", "01 BP Abidjan", "Rue des jardins", null), Sexe.MASCULIN, 
				situationMatrimoniale1, coteDivoire, null, null, null, null, null, bud, profession1,gendarme);
		
		inscrireAgentEtat("DZ44", "Tata", "Mole", new Date(), new Contact("mail@yahoo.com", "123456", "01 BP Abidjan", "Rue des jardins", null), Sexe.MASCULIN, 
				situationMatrimoniale1, coteDivoire, null, null, null, null, null, bud, profession1,gendarme);
		
		inscrireAgentEtat("DZ100", "Kadi", "mariam", new Date(), new Contact("mail@yahoo.com", "123456", "01 BP Abidjan", "Rue des jardins", null), Sexe.FEMININ, 
				situationMatrimoniale1, coteDivoire, null, null, null, null, null, bud, profession1,policier);
		
		Deplacement deplacementAffectation = new Deplacement(date(), date(), date(), null, affectation, sexp, set, abj, bk);
		em.persist(deplacementAffectation);
		Deplacement deplacementRetraite = new Deplacement(date(), date(), date(), null, retraite, sexp, set, bk, abj);
		em.persist(deplacementRetraite);
		
		DossierDD dossierDD1 = new DossierDD(numero(), date(), numero(), date(), deplacementAffectation, a2, agentEtat1, 500, 500, date(), numero(), abj, date(), date());
		em.persist(dossierDD1);
		
		DossierDD dossierDD2 = new DossierDD(numero(), date(), numero(), date(), deplacementRetraite, a2, agentEtat1, 300, 150, date(), numero(), abj, date(), date());
		em.persist(dossierDD2);
		
		Statut soumis = new Statut("SOUMIS", "Soumis", 0);
		em.persist(soumis);
		Statut recevable = new Statut("VR", "Reçevable", 0);
		em.persist(recevable);
		Statut conforme = new Statut("VC", "Conforme", 0);
		em.persist(conforme);
		Statut liquide = new Statut("L", "Liquide", 0);
		em.persist(liquide);
		Statut paye = new Statut("P", "Paye", 0);
		em.persist(paye);
		
		NatureOperation valRecevabilite = new NatureOperation("VAL_REC", "Validation Reçevabilité");
		em.persist(valRecevabilite);
		NatureOperation valConformite = new NatureOperation("VAL_CON", "Validation Conformité");
		em.persist(valConformite);
		NatureOperation liquidation = new NatureOperation("LIQ", "Liquidation");
		em.persist(liquidation);
		NatureOperation reglement = new NatureOperation("PAIE", "Reglement");
		em.persist(reglement);
		
		Operation validerRecevabilite1 = new Operation(date(), valRecevabilite);em.persist(validerRecevabilite1);
		em.persist(new Traitement(validerRecevabilite1,null,dossierDD1,recevable));
		
		Operation validerConformite1 = new Operation(date(), valRecevabilite);em.persist(validerConformite1);
		em.persist(new Traitement(validerConformite1,null,dossierDD1,conforme));
		
		Operation liquider = new Operation(date(), liquidation);em.persist(liquider);
		em.persist(new Traitement(liquider,null,dossierDD1,liquide));
		
		Operation payer = new Operation(date(), reglement);em.persist(payer);
		em.persist(new Traitement(payer,null,dossierDD1,paye));
		
		
		Operation validerRecevabilite2 = new Operation(date(), valRecevabilite);em.persist(validerRecevabilite2);
		em.persist(new Traitement(validerRecevabilite2,null,dossierDD2,recevable));
		
		Operation validerConformite2 = new Operation(date(), valRecevabilite);em.persist(validerConformite2);
		em.persist(new Traitement(validerConformite1,null,dossierDD2,conforme));
		
	}
	
	public AgentEtat creerAgentEtat(String matricule, String nom, String prenoms, Date dateNaissance, Contact contact, Sexe sexe, SituationMatrimoniale situationMatrimoniale, 
			Localite nationalite, Grade grade, Echelon echelon, Position position, Integer indice, Fonction fonction, Section ministere, Profession profession){
		AgentEtat agentEtat = new AgentEtat(nextIdString(),matricule, nom, prenoms, dateNaissance, contact, sexe, situationMatrimoniale, 
				nationalite, new Date(),  grade, echelon, position, indice, fonction, ministere, profession, null);
		em.persist(agentEtat);
		return agentEtat;
	}
	
	public InfosInscriptionPersonne creerPersonneInscription(String matricule, String nom, String prenoms, Date dateNaissance, Contact contact, Sexe sexe,  Localite nationalite, Profession profession,
			TypeAgentEtat typeAgentEtat,PieceJustificative pieceIdentite){
		InfosInscriptionPersonne infosInscriptionPersonne = new InfosInscriptionPersonne(null,nom,prenoms,dateNaissance,contact,sexe,nationalite,typeAgentEtat,matricule,profession,pieceIdentite);
		return infosInscriptionPersonne;
	}
	
	public Inscription inscrireAgentEtat(String matricule, String nom, String prenoms, Date dateNaissance, Contact contact, Sexe sexe, SituationMatrimoniale situationMatrimoniale, 
			Localite nationalite, Grade grade, Echelon echelon, Position position, Integer indice, Fonction fonction, Section ministere, Profession profession, TypeAgentEtat typeAgentEtat){
		Inscription inscription = new Inscription(nextIdString(),creerPersonneInscription(matricule, nom, prenoms, dateNaissance, contact, sexe, nationalite, profession, 
				typeAgentEtat, null),null,null,new Date(),null,null);
		em.persist(inscription);
		return inscription;
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

}
