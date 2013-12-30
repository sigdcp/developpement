package ci.gouv.budget.solde.sigdcp.service.resources;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.LocaliteDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.NatureDeplacementDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.TypePieceDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.CauseDeces;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePiece;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;

/**
 * Fournit les listes des données de types paramètres
 * @author christian
 *
 */
@Singleton
public class ListeResources {
 
	@Inject private DynamicEnumerationDao dynamicEnumerationDao;
	
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
    
    @Produces @Named
    public Sexe[] getSexes(){
    	return Sexe.values();
    }
    
    

}