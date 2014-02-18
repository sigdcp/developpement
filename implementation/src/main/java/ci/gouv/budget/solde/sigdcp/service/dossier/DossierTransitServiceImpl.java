package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierTransitDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
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
	protected void validationSaisie(DossierTransit dossier,Collection<PieceJustificative> pieceJustificatives,Personne personne, Boolean soumission) throws ServiceException {
		dossierTransitValidator.setPieceJustificatives(pieceJustificatives);
		dossierTransitValidator.setSoumission(soumission);
		if(!dossierTransitValidator.validate(dossier).isSucces())
			throw new ServiceException(dossierTransitValidator.getMessagesAsString());
	}

}
