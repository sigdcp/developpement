package ci.gouv.budget.solde.sigdcp.service.indemnite;

import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeDD;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;


public interface IndemniteOperandeService {

	int distance(Dossier dossier);
	
	int indice(DossierObseques dossier);
	
	int montantDistance(Dossier dossier,int distance); 
	
	int montantCercueil(int indice); 
	
	int nombreVoyageur(Dossier dossier);
	
	int nombreConjoint(Dossier dossier);
	
	int nombreEnfant(Dossier dossier);
	
	int poidsAgent(GroupeDD groupeDD);
	
	int poidsConjoint(GroupeDD groupeDD);
	
	int poidsEnfant(GroupeDD groupeDD);
	
	int montantAgent(GroupeDD groupeDD);
	
	int montantConjoint(GroupeDD groupeDD);
	
	int montantEnfant(GroupeDD groupeDD);
	
	int montantMissionSejour(DossierMission dossier,GroupeMission groupe);
	
	int montantMissionTransport(DossierMission dossier,GroupeMission groupe);
	
	int montantMissionDivers(DossierMission dossier,GroupeMission groupe);
	
	int dureeJourDeplacement(Dossier dossier);
	
	String codeLocaliteDepart(Dossier dossier);
	
	String codeLocaliteArrivee(Dossier dossier);
}
