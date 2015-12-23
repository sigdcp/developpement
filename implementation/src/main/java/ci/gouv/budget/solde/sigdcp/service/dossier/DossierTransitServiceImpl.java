package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierTransitDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierTransitValidator;

@Stateless
public class DossierTransitServiceImpl extends AbstractDossierServiceImpl<DossierTransit> implements DossierTransitService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject private DossierTransitValidator dossierTransitValidator;
	
	@Inject
	public DossierTransitServiceImpl(DossierTransitDao dao) {
		super(dao);
	} 
	
	@Override
	protected void validationSaisie(DossierTransit dossier,Boolean soumission) throws ServiceException {
		dossierTransitValidator.setSoumission(soumission);
		if(!dossierTransitValidator.validate(dossier).isSucces())
			throw new ServiceException(dossierTransitValidator.getMessagesAsString());
	}
	
	@Override
	protected DossierTransit createDossier(NatureDeplacement natureDeplacement) {
		DossierTransit dossierTransit = new DossierTransit(new Deplacement(genericDao.readByClass(TypeDepense.class, String.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE)));
		if(natureDeplacement!=null && natureDeplacement.getSceSolde()){
			dossierTransit.getDeplacement().setAddUser(utilisateur());
		}
		return dossierTransit;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<DossierTransit> findEditerAttestationPriseEnCharge() {
		Collection<DossierTransit> liste =  ((DossierTransitDao)dao).readByNatureDeplacementsByTypeDepenseIdByNatureOperationIdByStatutId(
				Arrays.asList(genericDao.readByClass(NatureDeplacement.class,Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_MAE),
						genericDao.readByClass(NatureDeplacement.class,Code.NATURE_DEPLACEMENT_TRANSIT_BAGAGGES_STAGIAIRE)),Code.TYPE_DEPENSE_PRISE_EN_CHARGE
				, Code.NATURE_OPERATION_CONFORMITE, Code.STATUT_ACCEPTE);

		for(DossierTransit dossier : liste)
			init(dossier, null);
				
		return liste;
	}


}
