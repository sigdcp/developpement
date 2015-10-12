package ci.gouv.budget.solde.sigdcp.service.resources;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.calendrier.ExerciceDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.NatureDeplacementDao;
import ci.gouv.budget.solde.sigdcp.dao.geographie.LocaliteDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.FonctionDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.SectionDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.TypeSectionDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.model.dossier.CauseDeces;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.geographie.TypeLocalite;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne.PieceIdentiteType;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.identification.QuestionSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.TypePrestataire;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeSection;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;

/**
 * Fournit les listes des données de types paramètres
 * @author christian
 *
 */
@Singleton
public class ListeResources {
 
	@Inject private DynamicEnumerationDao dynamicEnumerationDao;
	@Inject private PrestataireDao prestataireDao;
	@Inject private TypeSectionDao typeSectionDao;
	@Inject private SectionDao sectionDao;
	
	@Produces @Named
    public List<TypeSection> getTypeSections(){
    	return new LinkedList<>(typeSectionDao.readAll());
    }
	
	@Produces @Named
    public List<Section> getSections(){
    	return new LinkedList<>(sectionDao.readAll());
    }
	
	@Produces @Named
    public PieceIdentiteType[] getPieceIdentiteTypes(){
    	return PieceIdentiteType.values(); 
    }
	
	@Produces @Named
    public Collection<TypeAgentEtat> getTypeAgentEtatsObseques(){
		return Arrays.asList(dynamicEnumerationDao.readByClass(TypeAgentEtat.class, Code.TYPE_AGENT_ETAT_FONCTIONNAIRE),
				dynamicEnumerationDao.readByClass(TypeAgentEtat.class, Code.TYPE_AGENT_ETAT_POLICIER)); 
    }
	
	@Produces @Named
    public Collection<TypeAgentEtat> getTypeAgentEtatsFonctionnaire(){
		return Arrays.asList(dynamicEnumerationDao.readByClass(TypeAgentEtat.class, Code.TYPE_AGENT_ETAT_FONCTIONNAIRE),
				dynamicEnumerationDao.readByClass(TypeAgentEtat.class, Code.TYPE_AGENT_ETAT_POLICIER)); 
    }
	
	@Inject private FonctionDao fonctionDao;
	@Produces @Named
    public List<Fonction> getFonctions(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(Fonction.class)); 
    }
	@Produces @Named
	public List<Fonction> getFonctionsGroupeMission(){
    	return new LinkedList<>(fonctionDao.readInGroupeMissions()); 
    }
	
	@Produces @Named
    public List<Grade> getGrades(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(Grade.class)); 
    }
	
	@Produces @Named
    public List<Role> getRoles(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(Role.class)); 
    }
	
	@Produces @Named
    public List<QuestionSecrete> getQuestionSecretes(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(QuestionSecrete.class)); 
    }
	
    @Produces @Named
    public List<Categorie> getCategories(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(Categorie.class)); 
    }
     
    @Produces @Named
    public List<Profession> getProfessions(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(Profession.class)); 
    }
    
    @Produces @Named
    public List<TypeLocalite> getTypeLocalites(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(TypeLocalite.class)); 
    }
    
    @Produces @Named
    public List<Localite> getLocalites(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(Localite.class)); 
    }
    
    @Inject private LocaliteDao localiteDao;
    @Produces @Named
    public List<Localite> getVilles(){ 
    	return new LinkedList<>(localiteDao.readByTypeId(Code.TYPE_LOCALITE_VILLE));
    }
    
    @Produces @Named
    public List<Localite> getVillesHorsCoteDIvoire(){
    	List<Localite> villes = getVilles();
    	Localite coteDivoire = localiteDao.read(Code.LOCALITE_COTE_DIVOIRE);
    	for(int i=0;i<villes.size();)
    		if(coteDivoire.equals(villes.get(i).getParent()))
    			villes.remove(i);
    		else
    			i++;
    	return villes;
    }
    
    @Produces @Named
    public List<Localite> getVillesCoteDIvoire(){ 
    	List<Localite> villes = getVilles();
    	Localite coteDivoire = localiteDao.read(Code.LOCALITE_COTE_DIVOIRE);
    	for(int i=0;i<villes.size();)
    		if(!coteDivoire.equals(villes.get(i).getParent()))
    			villes.remove(i);
    		else
    			i++;
    	return villes;
    }
    
    @Produces @Named
    public Collection<Localite> getPortsCoteDIvoire(){
		return Arrays.asList(dynamicEnumerationDao.readByClass(Localite.class, Code.LOCALITE_ABIDJAN),
				dynamicEnumerationDao.readByClass(Localite.class, Code.LOCALITE_SAN_PEDRO)); 
    }
    
    @Produces @Named
    public List<Localite> getMairies(){ 
    	return new LinkedList<>(localiteDao.readByTypeId(Code.TYPE_LOCALITE_MAIRIE));
    }
    
    @Produces @Named
    public List<Localite> getPays(){ 
    	return new LinkedList<>(localiteDao.readByTypeId(Code.TYPE_LOCALITE_PAYS));
    }
    
    @Produces @Named
    public List<Localite> getZones(){ 
    	return new LinkedList<>(localiteDao.readByTypeId(Code.TYPE_LOCALITE_ZONE));
    }
    
    @Inject private NatureDeplacementDao natureDeplacementDao;
    @Produces @Named
    public List<NatureDeplacement> getNatureDeplacements(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(NatureDeplacement.class)); 
    }
    
    @Produces @Named
    public List<NatureDeplacement> getNatureDeplacementsDD(){
    	return (List<NatureDeplacement>) natureDeplacementDao.readByCategorieId(Code.CATEGORIE_DEPLACEMENT_DEFINITIF);
    }
    
    @Produces @Named
    public List<TypeAgentEtat> getTypeAgentEtats(){ 
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(TypeAgentEtat.class)); 
    }
     
    @Produces @Named
    public List<CauseDeces> getCauseDeces(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(CauseDeces.class)); 
    }
    
    @Produces @Named
    public List<TypeDepense> getTypeDepenes(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(TypeDepense.class)); 
    }
    
   
    @Produces @Named
    public List<Section> getServices(){
    	return new LinkedList<>(sectionDao.readBySectionTypeId(Code.TYPE_SECTION_SERVICE));
    }
    
    @Produces @Named
    public List<Section> getDirections(){
    	return new LinkedList<>(sectionDao.readBySectionTypeId(Code.TYPE_SECTION_DIRECTION));
    }
    
    @Produces @Named
    public List<Section> getMinisteres(){
    	return new LinkedList<>(sectionDao.readBySectionTypeId(Code.TYPE_SECTION_MINISTERE));
    }
    
    @Produces @Named
    public List<TypeClasseVoyage> getTypeClasseVoyages(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(TypeClasseVoyage.class)); 
    }
    
    @Produces @Named
    public List<Prestataire> getAgenceVoyages(){
    	return new LinkedList<>(prestataireDao.readAll()); 
    }
    
    @Produces @Named
    public List<Prestataire> getCompagnies(){
    	return new LinkedList<>(prestataireDao.readByType(Code.TYPE_PRESTATAIRE_CG)); 
    }
    
    @Produces @Named
    public List<TypePrestataire> getTypePrestataires(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(TypePrestataire.class)); 
    }
    
    @Produces @Named
    public List<GroupeMission> getGroupeMissions(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(GroupeMission.class)); 
    }
    
    @Produces @Named
    public Sexe[] getSexes(){
    	return Sexe.values();
    }
    
    @Produces @Named
    public ValidationType[] getValidationTypes(){
    	return ValidationType.values();
    }
   
    
    

    @Inject private ExerciceDao exerciceDao;
    @Produces @Named
    public List<Exercice> getExercices(){
    	return new LinkedList<>(exerciceDao.readAll());
    }
    
    

}