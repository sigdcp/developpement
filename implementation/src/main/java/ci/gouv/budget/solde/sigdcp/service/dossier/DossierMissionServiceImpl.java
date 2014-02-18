package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierMissionDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierMissionValidator;

@Stateless
public class DossierMissionServiceImpl extends AbstractDossierServiceImpl<DossierMission> implements DossierMissionService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject private DossierMissionValidator dossierMissionValidator;
	
	@Inject
	public DossierMissionServiceImpl(DossierMissionDao dao) {
		super(dao);
	}
	
	@Override
	protected void validationSaisie(DossierMission dossier,Collection<PieceJustificative> pieceJustificatives,Personne personne, Boolean soumission) throws ServiceException {
		dossierMissionValidator.setPieceJustificatives(pieceJustificatives);
		dossierMissionValidator.setSoumission(soumission);
		if(!dossierMissionValidator.validate(dossier).isSucces())
			throw new ServiceException(dossierMissionValidator.getMessagesAsString());
	}
 
}
 