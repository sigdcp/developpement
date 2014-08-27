package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.StatistiqueDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.NatureDeplacementDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiquesResultats;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class NatureDeplacementServiceImpl extends DefaultServiceImpl<NatureDeplacement, String> implements NatureDeplacementService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private NatureDeplacementDao natureDeplacementDao;
	//@Inject private DossierDao dossierDao;
	@Inject private StatistiqueDao statDao;
	
	@Inject
	public NatureDeplacementServiceImpl(NatureDeplacementDao dao) {
		super(dao);
	}
	
	/*@Override
	public Collection<NatureDeplacement> findStatistiqueByCategorieDeplacementIdByNatureOperationIdByStatutId(String code, String nopId, String statutId) {
		Collection<NatureDeplacement> natureDeplacements = natureDeplacementDao.readByCategorieId(code);
		for(NatureDeplacement nd : natureDeplacements){
			nd.setNombreDossier(dossierDao.countByNatureDeplacementIdByNatureOperationIdByStatutId(nd.getCode(), nopId, statutId));
		}
		return natureDeplacements;
	}*/

	@Override
	public DeplacementStatistiques<NatureDeplacement> findStatistiqueByNatureOperationByStatut(Collection<NatureDeplacement> natureDeplacements, Date debut,Date fin, NatureOperation nop, Statut statut) {
		DeplacementStatistiques<NatureDeplacement> statistiques = new DeplacementStatistiques<NatureDeplacement>();

		for(NatureDeplacement nd : natureDeplacements){
			DeplacementStatistiquesResultats details = new DeplacementStatistiquesResultats(
					statDao.countDossierByNatureOperationByStatut(nd, nop, statut),
					statDao.countVoyageurByNatureOperationByStatut(nd, nop, statut),
					statDao.sumDepenseByNatureOperationByStatut(nd, nop, statut),
					statDao.sumFraisByNatureOperationByStatut(nd, nop, statut));
			
			statistiques.getGroupes().add(nd);
			statistiques.getResultatParGroupe().put(nd, details);
			
			statistiques.getTotal().setNombreDossier(statistiques.getTotal().getNombreDossier()+details.getNombreDossier());
			statistiques.getTotal().setNombreVoyageurs(statistiques.getTotal().getNombreVoyageurs()+details.getNombreVoyageurs());
			statistiques.getTotal().setDepense(statistiques.getTotal().getDepense()+details.getDepense());
			statistiques.getTotal().setFraisTransport(statistiques.getTotal().getFraisTransport()+details.getFraisTransport());
		}
		
		
		
		return statistiques;
	}

	@Override
	public Collection<NatureDeplacement> findByCategorieId(String code) {
		return natureDeplacementDao.readByCategorieId(code);
	}
}
