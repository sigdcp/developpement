package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.StatistiqueDao;
import ci.gouv.budget.solde.sigdcp.dao.indemnite.TypeClasseVoyageDao;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiquesResultats;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class TypeClasseVoyageServiceImpl extends DefaultServiceImpl<TypeClasseVoyage,String> implements TypeClasseVoyageService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;

	@Inject private StatistiqueDao statDao;
	
	@Inject
	public TypeClasseVoyageServiceImpl(TypeClasseVoyageDao dao) {
		super(dao);  
	}

	@Override
	public DeplacementStatistiques<TypeClasseVoyage> findStatistiqueByTypeClasseVoyage(Collection<TypeClasseVoyage> typeClasseVoyages) {
DeplacementStatistiques<TypeClasseVoyage> statistiques = new DeplacementStatistiques<TypeClasseVoyage>();
		
		for(TypeClasseVoyage typeClasseVoyage : typeClasseVoyages){
			DeplacementStatistiquesResultats details = new DeplacementStatistiquesResultats();
			details.setDepense(statDao.sumConsommationBilletAvionDepenseByTypeClasseVoyage(typeClasseVoyage));
			details.setFraisTransport(statDao.sumConsommationBilletAvionFraisByTypeClasseVoyage(typeClasseVoyage));

			statistiques.getGroupes().add(typeClasseVoyage);
			statistiques.getResultatParGroupe().put(typeClasseVoyage, details);
			
			
			statistiques.getTotal().setDepense(statistiques.getTotal().getDepense()+details.getDepense());
			statistiques.getTotal().setFraisTransport(statistiques.getTotal().getFraisTransport()+details.getFraisTransport());
		}
		
		
		
		return statistiques;
	}

	@Override
	public DeplacementStatistiques<GroupeMission> findStatistiqueByGroupe(Collection<GroupeMission> groupes) {
		DeplacementStatistiques<GroupeMission> statistiques = new DeplacementStatistiques<GroupeMission>();

		for(GroupeMission groupe : groupes){
			DeplacementStatistiquesResultats details = new DeplacementStatistiquesResultats();
			details.setDepense(statDao.sumConsommationBilletAvionDepenseByGroupe(groupe));
			details.setFraisTransport(statDao.sumConsommationBilletAvionFraisByGroupe(groupe));

			statistiques.getGroupes().add(groupe);
			statistiques.getResultatParGroupe().put(groupe, details);
			
			
			statistiques.getTotal().setDepense(statistiques.getTotal().getDepense()+details.getDepense());
			statistiques.getTotal().setFraisTransport(statistiques.getTotal().getFraisTransport()+details.getFraisTransport());
		}
		
		
		
		return statistiques;
	}
	

}
