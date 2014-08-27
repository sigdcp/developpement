package ci.gouv.budget.solde.sigdcp.model.communication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum NotificationMessageType{
	_TEST_("SIGDCP - Test","test"),
	
	AVIS_SOUSCRIPTION_COMPTE_PERSONNE_FONCTIONNAIRE("Avis de souscription à un compte SIGDCP","avisSouscriptionAgentEtat"),
	AVIS_SOUSCRIPTION_COMPTE_PRESTATAIRE("Avis de souscription à un compte SIGDCP","avisSouscriptionPrestataire"),
	AVIS_SOUSCRIPTION_COMPTE_PERSONNE_ENREGISTREE("Avis de souscription à un compte SIGDCP","avisSouscriptionGendarme"),
	AVIS_SOUSCRIPTION_COMPTE_PERSONNE_ACCEPTEE("Avis de création de compte SIGDCP","avisSouscriptionGendarmeAcceptee"),
	AVIS_SOUSCRIPTION_COMPTE_PERSONNE_REFUSEE("Avis de refus de compte SIGDCP","avisSouscriptionGendarmeRefusee"),
	
	AVIS_COMPTE_UTILISATEUR_VERROUILLE_ACCES_MULTIPLE("Avis de verrouillage de compte SIGDCP","avisVerrouillageCompteAccessMultiple"),
	AVIS_COMPTE_UTILISATEUR_DEVERROUILLE_ACCES_MULTIPLE("Avis de déverrouillage de compte SIGDCP","avisDeverrouillageCompteAccesMultiple"),
	
	AVIS_COMPTE_UTILISATEUR_VERROUILLE_REINITIALISATION_PASSWORD("Avis de verrouillage de compte SIGDCP","avisVerrouillageCompteReinitialisationMotPasse"),
	AVIS_COMPTE_UTILISATEUR_DEVERROUILLE_REINITIALISATION_PASSWORD("Avis de réinitialisation du mot de passe du compte SIGDCP","avisDeverrouillageCompteReinitialisationMotPasse"),
	
	AVIS_COMPTE_UTILISATEUR_ETAT_SESSION("Avis de connexion et déconnexion à SIGDCP","avisEtatSession"),
	
	AVIS_COMPTE_UTILISATEUR_DESACTIVATION("Avis de désactivation de votre compte SIGDCP","avisDesactivationCompte"),
	
	;
	private String subject;
	private String templateId;
	
	public String getEmailTemplateId(){
		return templateId+"_mail";
	}
}