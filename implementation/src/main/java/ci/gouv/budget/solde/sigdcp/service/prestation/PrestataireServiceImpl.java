package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.StatistiqueDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.PrestataireDao;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiquesResultats;

public class PrestataireServiceImpl extends AbstractPrestataireServiceImpl<Prestataire> implements PrestataireService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	@Inject private StatistiqueDao statDao;
	
	@Inject
	public PrestataireServiceImpl(PrestataireDao dao) {
		super(dao);
	}
	 
	@Override
	public Collection<Prestataire> findByType(String typePrestataire) {
		return ((PrestataireDao)dao).readByType(typePrestataire);
	}

	@Override
	public DeplacementStatistiques<Prestataire> findConsommationBilletAvionStatistique(Collection<Prestataire> prestataires) {
		DeplacementStatistiques<Prestataire> statistiques = new DeplacementStatistiques<Prestataire>();
		
		for(Prestataire prestataire : prestataires){
			DeplacementStatistiquesResultats details = new DeplacementStatistiquesResultats();
			details.setNombreVoyageurs(statDao.countConsommationBilletAvionByPrestataire(prestataire));
			details.setFraisTransport(statDao.sumConsommationBilletAvionFraisByPrestataire(prestataire));

			statistiques.getGroupes().add(prestataire);
			statistiques.getResultatParGroupe().put(prestataire, details);
			
			
			statistiques.getTotal().setNombreVoyageurs(statistiques.getTotal().getNombreVoyageurs()+details.getNombreVoyageurs());
			statistiques.getTotal().setFraisTransport(statistiques.getTotal().getFraisTransport()+details.getFraisTransport());
		}
		
		
		
		return statistiques;
	}
}

