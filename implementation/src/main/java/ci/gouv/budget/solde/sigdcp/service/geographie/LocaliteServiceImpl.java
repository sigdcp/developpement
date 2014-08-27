package ci.gouv.budget.solde.sigdcp.service.geographie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.StatistiqueDao;
import ci.gouv.budget.solde.sigdcp.dao.geographie.LocaliteDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiquesResultats;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class LocaliteServiceImpl extends DefaultServiceImpl<Localite,String> implements LocaliteService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private LocaliteDao localiteDao; 
	@Inject private StatistiqueDao statDao;
	
	@Inject
	public LocaliteServiceImpl(LocaliteDao dao) {
		super(dao);
	}

	@Override
	public Collection<Localite> findByTypeId(String typeLocalite) {
		return localiteDao.readByTypeId(typeLocalite);
	}

	@Override
	public Collection<Localite> findByTypeIdByParent(String typeLocalite,Collection<Localite> parents) {
		Collection<Localite> localites=new ArrayList<>();
		for(Localite p : parents)
			localites= localiteDao.readByTypeIdByParent(typeLocalite,p);
		return localites;
	}

	@Override
	public DeplacementStatistiques<Localite> findStatistiqueByTypeId(Collection<Localite> localites,String typeLocalite) {
		DeplacementStatistiques<Localite> statistiques = new DeplacementStatistiques<Localite>();
		Collection<NatureDeplacement> natureDeplacements = Arrays.asList(genericDao.readByClass(NatureDeplacement.class, Code.NATURE_DEPLACEMENT_MISSION_HCI));
		Boolean depart = false;
		for(Localite loc : localites){
			DeplacementStatistiquesResultats details = new DeplacementStatistiquesResultats(
					statDao.countDossierByLocalite(depart, loc,natureDeplacements),
					statDao.countVoyageurByLocalite(depart, loc, natureDeplacements),
					statDao.sumDepenseByLocalite(depart, loc, natureDeplacements),
					statDao.sumFraisByLocalite(depart, loc, natureDeplacements));

			statistiques.getGroupes().add(loc);
			statistiques.getResultatParGroupe().put(loc, details);
			
			statistiques.getTotal().setNombreDossier(statistiques.getTotal().getNombreDossier()+details.getNombreDossier());
			statistiques.getTotal().setNombreVoyageurs(statistiques.getTotal().getNombreVoyageurs()+details.getNombreVoyageurs());
			statistiques.getTotal().setDepense(statistiques.getTotal().getDepense()+details.getDepense());
			statistiques.getTotal().setFraisTransport(statistiques.getTotal().getFraisTransport()+details.getFraisTransport());
		}
		
		
		
		return statistiques;
	}
	

	@Override
	public DeplacementStatistiques<Localite> findConsommationBilletAvionStatistique(Collection<Localite> localites) {
		DeplacementStatistiques<Localite> statistiques = new DeplacementStatistiques<Localite>();
		
		for(Localite loc : localites){
			DeplacementStatistiquesResultats details = new DeplacementStatistiquesResultats();
			details.setNombreVoyageurs(statDao.countConsommationBilletAvionByLocalite(loc));
			details.setFraisTransport(statDao.sumConsommationBilletAvionFraisByLocalite(loc));

			statistiques.getGroupes().add(loc);
			statistiques.getResultatParGroupe().put(loc, details);
			
			
			statistiques.getTotal().setNombreVoyageurs(statistiques.getTotal().getNombreVoyageurs()+details.getNombreVoyageurs());
			statistiques.getTotal().setFraisTransport(statistiques.getTotal().getFraisTransport()+details.getFraisTransport());
		}
		
		
		
		return statistiques;
	}
	

}
