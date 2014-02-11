package ci.gouv.budget.solde.sigdcp.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum ServiceExceptionType{
	IDENTIFICATION_SOUSCRIPTION_COMPTE_ENCOURS("Vous avez une souscription déja en cours de validation"),
	IDENTIFICATION_SOUSCRIPTION_COMPTE_ACCEPTE("Vous avez déja souscrit"),
	IDENTIFICATION_SOUSCRIPTION_COMPTE_MATRCULE_INCONNU("Votre matricule est inconnu"),
	IDENTIFICATION_SOUSCRIPTION_COMPTE_EXISTE("Vous avez deja un compte"),
	IDENTIFICATION_SOUSCRIPTION_COMPTE_MAIL_EXISTE("Ce mail est déja utilisé"),
	IDENTIFICATION_SOUSCRIPTION_COMPTE_INCOHERENT("Les informations saisies ne sont pas cohérentes"),
	
	IDENTIFICATION_COMPTE_UTILISATEUR_CONNECTE("Vous êtes déja connecté"),
	IDENTIFICATION_COMPTE_UTILISATEUR_VEROUILLE("Votre compte est vérouillé. Veuillez consulter votre email et suivre les instructions pour le dévérouiller."),
	//IDENTIFICATION_COMPTE_UTILISATEUR_DEVEROUILLAGE_MAUVAIS_ENDROIT("Votre compte est vérouillé. Veuillez consulter votre email et suivre les instructions pour le dévérouiller"),
	IDENTIFICATION_COMPTE_UTILISATEUR_ACTIF("Votre compte est actif"),
	IDENTIFICATION_COMPTE_UTILISATEUR_INCONNU("Nom d'utilisateur ou mot de passe incorrect"),
	IDENTIFICATION_COMPTE_UTILISATEUR_INEXISTANT("Vous n'avez pas de compte dans notre système"),
	IDENTIFICATION_COMPTE_UTILISATEUR_CODE_DEVEROUILLAGE_INCONNU("Les paramètres de dévérouillage sont incorrects"),
	IDENTIFICATION_COMPTE_UTILISATEUR_JETON_DEVEROUILLAGE_INCONNU("Le code de dévérouillage n'est pas valide"),
	IDENTIFICATION_COMPTE_UTILISATEUR_REPONSE_INCORRECT("Une ou plusieurs des réponses sont incorrectes"),
	
	DOSSIER_STATUT_ILLELGAL("Cette operation ne peut pas etre réalisée."),
	DOSSIER_PIECE_JUSTIFICATIVE_MANQUANTE("Toutes les pieces justificatives doivent être fournies."),
	
	SAISIE_INCOHERENTE("Les informations saisies sont incohérentes"),
	
	
	
	;
	
	private String libelle;

	
	
}