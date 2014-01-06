package ci.gouv.budget.solde.sigdcp.model;


/**
 * Ensembles des constantes définies dans la base de données
 * @author christian
 *
 */
public interface Code {
	
	/*
	 * Catégorie de déplacement
	 * 
	 */
	
	String CATEGORIE_DEPLACEMENT_DEFINITIF = "Definitif";
	String CATEGORIE_DEPLACEMENT_TRANSIT = "Transit";
	String CATEGORIE_DEPLACEMENT_MISSION = "Mission";
	String CATEGORIE_DEPLACEMENT_OBSEQUE = "Obseques";
	String CATEGORIE_DEPLACEMENT_TRANSPORT_URBAIN = "TRANSPORT_URBAIN";
	
	/*
	 * Nature de déplacement
	 */
	String NATURE_DEPLACEMENT_AFFECTATION = "AFF";
	String NATURE_DEPLACEMENT_MUTATION = "MUT";
	String NATURE_DEPLACEMENT_RETRAITE = "RET";
	String NATURE_DEPLACEMENT_MISSION_HCI = "MHCI";
	String NATURE_DEPLACEMENT_TRANSIT_BAGAGGES = "TB";
	String NATURE_DEPLACEMENT_OBSEQUE_FRAIS = "FO";
	String NATURE_DEPLACEMENT_TRANSPORT_CARTE_SOTRA = "CARTE SOTRA";
	
	/*
	 * Groupe Type Piece
	 */
	
	String GROUPE_TYPE_PIECE_IDENTITE = "IDENTITE";
	
	/*
	 * Type de pieces
	 */
	
	String TYPE_PIECE_CNI = "CNI";
	String TYPE_PIECE_PASSPORT = "PASSPORT";
	String TYPE_PIECE_ATTESTATION_SG = "ATTSG";
	String TYPE_PIECE_COMMUNICATION = "COMMUNICATION";
	String TYPE_PIECE_DECISION_AFFECTATION = "DAFF";
	String TYPE_PIECE_CERTIFICAT_PREMIERE_PRISE_SERVICE = "CPPS";
	String TYPE_PIECE_EXTRAIT_MARIAGE= "EXTMAR";
	String TYPE_PIECE_EXTRAIT_NAISSANCE = "EXTNAISS";
	String TYPE_PIECE_ATTESTATION_TRANSPORT = "ATTRANS";
	String TYPE_PIECE_ARRETE_MUTATION = "ARRMUT";
	String TYPE_PIECE_CARTE_PROFESSIONNELLE = "CARTPROF";
	String TYPE_PIECE_AVIS_MUTATION = "AVISMUT";
	String TYPE_PIECE_CERTIFICAT_PRESENCE_CORPS = "CPC";
	String TYPE_PIECE_ARRETE_RADIATION = "ARRADIATION";
	String TYPE_PIECE_ARRETE_MISE_RETRAITE = "ARRMR";
	String TYPE_PIECE_CERTIFICAT_CESSATION_SERVICE = "CCS";
	String TYPE_PIECE_ORDRE_MISSION = "ORDMISS";
	String TYPE_PIECE_ATTESTATION_MISE_STAGE = "ATTMS";
	String TYPE_PIECE_ATTESTATION_FIN_STAGE = "ATTFS";
	String TYPE_PIECE_ATTESTATION_MAE = "ATTMAE";
	String TYPE_PIECE_DECISION_RAPPEL = "DECISRAPPEL";
	String TYPE_PIECE_CERTIFICAT_PRISE_SERVICE = "CPS";
	String TYPE_PIECE_FACTURE_PROFORMA = "FACTPROF";
	String TYPE_PIECE_FACTURE_DEFINITIVE = "FACTDEF";
	String TYPE_PIECE_EXTRAIT_DECES = "EXTDECES";
	String TYPE_PIECE_CERTIFICAT_DECES = "CERTDECES";
	String TYPE_PIECE_BULLETIN_SALAIRE = "BULLSAL";
	String TYPE_PIECE_LETTRE_MINISTERIELLE = "LETTMIN";
	String TYPE_PIECE_ARRETE_PREFECTORAL ="ARRPREF";
	String TYPE_PIECE_FEUILLE_DEPLACEMENT ="FEUILLEDEP";
	String TYPE_PIECE_BON_TRANSPORT ="BONTRANS";
	
	
	/*
	 * Type de agent de l'état
	 */
	String TYPE_AGENT_ETAT_FONCTIONNAIRE = "F";
	String TYPE_AGENT_ETAT_CONTRACTUEL = "C";
	String TYPE_AGENT_ETAT_POLICIER = "P";
	String TYPE_AGENT_ETAT_GENDARME = "G";
	
	/*
	 * Type de personne
	 */
	String TYPE_PERSONNE_AYANT_DROIT = "AD";
	
	/*
	 * Type de localite
	 */
	String TYPE_LOCALITE_PAYS = "PAYS";
	String TYPE_LOCALITE_VILLE = "VILLE";
	String TYPE_LOCALITE_ZONE = "ZONE";
	
	/*
	 * Localite
	 */
	String LOCALITE_COTE_DIVOIRE = "CI";
	
	/*
	 * Situation Matrimoniale
	 */
	String SITUATION_MATRIMONIALE_MARIE = "MARIE";
	String SITUATION_MATRIMONIALE_CELIBATAIRE = "CELIB";
	String SITUATION_MATRIMONIALE_VEUF = "VEUF";
	
	/*
	 * Type de section
	 */
	String TYPE_SECTION_MINISTERE = "MIN";
	String TYPE_SECTION_SERVICE = "SERV";
	String TYPE_LOCALITE_DIRECTION = "DIR";
	
	/*
	 * section
	 */
	String SECTION_MIN_MEF = "MEF";
	String SECTION_MIN_MB = "SERV";
	String SECTION_SERV_EXP = "SEXP";
	String SECTION_SERV_ET = "SET";
	
	/*
	 * Type de dépense
	 */
	String TYPE_DEPENSE_PRISE_EN_CHARGE = "PEC";
	String TYPE_DEPENSE_RMBOURSEMENT = "REMB";
	
}
