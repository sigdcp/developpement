package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDDDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierDDValidator;

@Stateless
public class DossierDDServiceImpl extends AbstractDossierServiceImpl<DossierDD> implements DossierDDService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject private DossierDDValidator dossierDDValidator;
	
	@Inject
	public DossierDDServiceImpl(DossierDDDao dao) {
		super(dao);
	}
		
	@Override
	protected void validationSaisie(DossierDD dossier,Collection<PieceJustificative> pieceJustificatives,Personne personne,Boolean soumission)throws ServiceException {
		dossierDDValidator.setPieceJustificatives(pieceJustificatives);
		dossierDDValidator.setSoumission(soumission);
		if(!dossierDDValidator.validate(dossier).isSucces())
			throw new ServiceException(dossierDDValidator.getMessagesAsString());
	}
	


}
