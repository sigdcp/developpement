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
	;
	
	private String libelle;

	
	
}