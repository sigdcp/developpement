package ci.gouv.budget.solde.sigdcp.dao;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Groupe;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;

public interface StatistiqueDao {

	Long countDossierByLocalite(Boolean depart,Localite localite,Collection<NatureDeplacement> natureDeplacements);	
	Long countVoyageurByLocalite(Boolean depart,Localite localite,Collection<NatureDeplacement> natureDeplacements);	
	Long sumDepenseByLocalite(Boolean depart,Localite localite,Collection<NatureDeplacement> natureDeplacements);	
	Long sumFraisByLocalite(Boolean depart,Localite localite,Collection<NatureDeplacement> natureDeplacements);
	
	Long countConsommationBilletAvionByLocalite(Localite localite);	
	Long sumConsommationBilletAvionFraisByLocalite(Localite localite);	
	
	Long countConsommationBilletAvionByPrestataire(Prestataire prestataire);	
	Long sumConsommationBilletAvionFraisByPrestataire(Prestataire prestataire);

	//Long countConsommationBilletAvionByTypeClasseVoyage(TypeClasseVoyage typeClasseVoyage);
	Long sumConsommationBilletAvionDepenseByTypeClasseVoyage(TypeClasseVoyage typeClasseVoyage);
	Long sumConsommationBilletAvionFraisByTypeClasseVoyage(TypeClasseVoyage typeClasseVoyage);

	//Long countConsommationBilletAvionByGroupe(Groupe groupe);	
	Long sumConsommationBilletAvionDepenseByGroupe(Groupe groupe); 
	Long sumConsommationBilletAvionFraisByGroupe(Groupe groupe);
	

	Long countLettreAvanceBySection(Section section);
	Long sumFraisLettreAvanceBySection(Section section);
	
	
	Long countSituationDossierBySection(Section section,	Collection<NatureDeplacement> natureDeplacements);
	Long countSituationVoyageurBySection(Section section, Collection<NatureDeplacement> natureDeplacements);
	Long sumSituationDepenseBySection(Section section, Collection<NatureDeplacement> natureDeplacements);
	Long sumSituationFraisBySection(Section section,	Collection<NatureDeplacement> natureDeplacements);
	
	
	Long countDossierByNatureOperationByStatut(NatureDeplacement nd, NatureOperation nop, Statut statut);
	Long countVoyageurByNatureOperationByStatut(NatureDeplacement nd, NatureOperation nop, Statut statut);
	Long sumDepenseByNatureOperationByStatut(NatureDeplacement nd, NatureOperation nop, Statut statut);
	Long sumFraisByNatureOperationByStatut(NatureDeplacement nd, NatureOperation nop, Statut statut);
}
