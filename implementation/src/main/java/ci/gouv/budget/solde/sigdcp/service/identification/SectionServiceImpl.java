package ci.gouv.budget.solde.sigdcp.service.identification;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.StatistiqueDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.SectionDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiquesResultats;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class SectionServiceImpl extends DefaultServiceImpl<Section, String> implements SectionService {
	
	private static final long serialVersionUID = 1170771216036513138L;
	@Inject private StatistiqueDao statDao;

	@Inject
	public SectionServiceImpl(SectionDao dao) {
		super(dao);
	}
	
	@Override
	public DeplacementStatistiques<Section> findLettreAvanceStatistique(Collection<Section> sections) {
		DeplacementStatistiques<Section> statistiques = new DeplacementStatistiques<Section>();
		
		for(Section sec : sections){
			DeplacementStatistiquesResultats details = new DeplacementStatistiquesResultats(
					statDao.countLettreAvanceBySection(sec),
					null,null,
					statDao.sumFraisLettreAvanceBySection(sec));

			statistiques.getGroupes().add(sec);
			statistiques.getResultatParGroupe().put(sec, details);
			
			statistiques.getTotal().setNombreDossier(statistiques.getTotal().getNombreDossier()+details.getNombreDossier());
			statistiques.getTotal().setFraisTransport(statistiques.getTotal().getFraisTransport()+details.getFraisTransport());
		}
		
		
		
		return statistiques;
	}
	

	
	@Override
	public DeplacementStatistiques<Section> findStatistique(Collection<Section> sections) {
		DeplacementStatistiques<Section> statistiques = new DeplacementStatistiques<Section>();
		Collection<NatureDeplacement> natureDeplacements = Arrays.asList(genericDao.readByClass(NatureDeplacement.class, Code.NATURE_DEPLACEMENT_MISSION_HCI));
		
		for(Section sec : sections){
			DeplacementStatistiquesResultats details = new DeplacementStatistiquesResultats(
					statDao.countSituationDossierBySection(sec,natureDeplacements),
					statDao.countSituationVoyageurBySection(sec, natureDeplacements),
					statDao.sumSituationDepenseBySection(sec, natureDeplacements),
					statDao.sumSituationFraisBySection(sec, natureDeplacements));

			statistiques.getGroupes().add(sec);
			statistiques.getResultatParGroupe().put(sec, details);
			
			statistiques.getTotal().setNombreDossier(statistiques.getTotal().getNombreDossier()+details.getNombreDossier());
			//statistiques.getTotal().setNombreVoyageurs(statistiques.getTotal().getNombreVoyageurs()+details.getNombreVoyageurs());
			statistiques.getTotal().setDepense(statistiques.getTotal().getDepense()+details.getDepense());
			statistiques.getTotal().setFraisTransport(statistiques.getTotal().getFraisTransport()+details.getFraisTransport());
		}
		
		
		
		return statistiques;
	}
}
