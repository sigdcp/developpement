package ci.gouv.budget.solde.sigdcp.service.resources;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.calendrier.ExerciceDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.NatureDeplacementDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.TypePieceDao;
import ci.gouv.budget.solde.sigdcp.dao.geographie.LocaliteDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.SectionDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.model.dossier.CauseDeces;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePiece;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.identification.QuestionSecrete;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne.PieceIdentiteType;
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
	
	@Produces @Named
    public PieceIdentiteType[] getPieceIdentiteTypes(){
    	return PieceIdentiteType.values(); 
    }
	
	@Produces @Named
    public List<Fonction> getFonctions(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(Fonction.class)); 
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
    public List<Localite> getLocalites(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(Localite.class)); 
    }
    
    @Inject private LocaliteDao localiteDao;
    @Produces @Named
    public List<Localite> getVilles(){ 
    	return new LinkedList<>(localiteDao.findByTypeId(Code.TYPE_LOCALITE_VILLE));
    }
    
    @Produces @Named
    public List<Localite> getPays(){ 
    	return new LinkedList<>(localiteDao.findByTypeId(Code.TYPE_LOCALITE_PAYS));
    }
    
    @Inject private NatureDeplacementDao natureDeplacementDao;
    @Produces @Named
    public List<NatureDeplacement> getNatureDeplacements(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(NatureDeplacement.class)); 
    }
    
    @Produces @Named
    public List<NatureDeplacement> getNatureDeplacementsDD(){
    	return (List<NatureDeplacement>) natureDeplacementDao.findByCategorieId(Code.CATEGORIE_DEPLACEMENT_DEFINITIF);
    }
    
    @Produces @Named
    public List<TypeAgentEtat> getTypeAgentEtats(){ 
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(TypeAgentEtat.class)); 
    }
    
    @Inject private TypePieceDao typePieceDao;
    @Produces @Named
    public List<TypePiece> getTypePieceIdentites(){
    	return (List<TypePiece>) typePieceDao.readByGroupeId(Code.GROUPE_TYPE_PIECE_IDENTITE);
    }
    
    @Produces @Named
    public List<CauseDeces> getCauseDeces(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(CauseDeces.class)); 
    }
    
    @Produces @Named
    public List<TypeDepense> getTypeDepenes(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(TypeDepense.class)); 
    }
    
    @Inject private SectionDao sectionDao;
    @Produces @Named
    public List<Section> getServices(){
    	return new LinkedList<>(sectionDao.readBySectionTypeId(Code.TYPE_SECTION_SERVICE));
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
    public List<GroupeMission> getGroupeMissions(){
    	return new LinkedList<>(dynamicEnumerationDao.readAllByClass(GroupeMission.class)); 
    }
    
    @Produces @Named
    public Sexe[] getSexes(){
    	return Sexe.values();
    }
   
    @Produces @Named
    public List<Section> getMinisteres(){
    	return new LinkedList<>(sectionDao.readBySectionTypeId(Code.TYPE_SECTION_MINISTERE));
    }
    

    @Inject private ExerciceDao exerciceDao;
    @Produces @Named
    public List<Exercice> getExercices(){
    	return new LinkedList<>(exerciceDao.readAll());
    }
    
    

}