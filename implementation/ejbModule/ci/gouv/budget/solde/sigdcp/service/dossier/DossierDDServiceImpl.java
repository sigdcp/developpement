package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDDDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

@Stateless
public class DossierDDServiceImpl extends AbstractDossierServiceImpl<DossierDD> implements DossierDDService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	//@Inject private TypePieceJustificativeService typePieceJustificativeService;
	//@Inject private PieceJustificativeService pieceJustificativeService;
	
	@Inject
	public DossierDDServiceImpl(DossierDDDao dao) {
		super(dao);
	}
	
	@Override
	public byte[] editerFeuilleDeplacement(DossierDD dossier) throws ServiceException {
		/*
		PieceJustificative feuilleDeplacement = new PieceJustificative(typePieceJustificativeService.findById(TypePieceJustificativeEnum.FD.name()),dossier);
		feuilleDeplacement.setNumero("FD/2013/"+System.currentTimeMillis());
		pieceJustificativeService.creer(feuilleDeplacement);
		//on genere l'état sous forme binaire
		byte[] etat = null;
		*/
		return null;//etat;
	}
	
	@Override
	public void soumettreFeuilleDeplacement(DossierDD dossier,PieceJustificative pieceJustificative) throws ServiceException {
		dao.update(dossier);
	}

}
