package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation.AspectLiquide;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;

@Stateless
public class DossierServiceImpl extends AbstractDossierServiceImpl<Dossier> implements DossierService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
		
	@Inject
	public DossierServiceImpl(DossierDao dao) {
		super(dao);
	}
		
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<Dossier> findALiquiderByNatureDeplacementsByAspectLiquide(Collection<NatureDeplacement> natureDeplacements, AspectLiquide aspectLiquide) {
		//if(AspectLiquide.TITRE_TRANSPORT.equals(aspectLiquide))
		//	return dossierMissionService.findALiquiderByNatureDeplacementsByAspectLiquide(natureDeplacements, aspectLiquide);
		return super.findALiquiderByNatureDeplacementsByAspectLiquide(natureDeplacements, aspectLiquide);
	}
	
	
	
	@Override
	protected Dossier createDossier(NatureDeplacement natureDeplacement) {
		return null;
	}


	@Override
	public Collection<Dossier> findStatistiqueDetailByNatureDeplacementsByNatureOperationByStatut(Collection<NatureDeplacement> natureDeplacements,NatureOperation natureOperation, Statut statut) {
		Collection<Dossier> dossiers = ((DossierDao)dao).readStatistiqueDetailByNatureDeplacementByNatureOperationByStatut(natureDeplacements,natureOperation, statut);
		for(Dossier d : dossiers)
			init(d, null);
		return dossiers;
	}

	@Override
	public Collection<Dossier> findStatiqueDetailBySection(Collection<Section> sections) {
		Collection<Dossier> dossiers = ((DossierDao)dao).readStatiqueDetailBySection(sections);
		for(Dossier d : dossiers)
			init(d, null);
		return dossiers;
	}
	
}
