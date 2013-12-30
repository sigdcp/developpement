package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.AbstractDossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DeplacementDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;


public abstract class AbstractDossierServiceImpl<DOSSIER extends Dossier> extends DefaultServiceImpl<DOSSIER, String> implements AbstractDossierService<DOSSIER>,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject private DeplacementDao deplacementDao;
	@Inject private PieceJustificativeDao pieceJustificativeDao;
	@Inject private AgentEtatDao beneficiaireDao;//a supprimer
	
	@Inject
	public AbstractDossierServiceImpl(AbstractDossierDao<DOSSIER> dao) {
		super(dao);
	}
	
	/*--------- Fonctions métiers ----------*/
	
	@Override
	public void enregistrer(DOSSIER dossier) throws ServiceException {
		dossier.setNumero(System.currentTimeMillis()+"");
		
		/* a supprimer */
		dossier.setBeneficiaire(beneficiaireDao.readAll().iterator().next());
		/**             */
		deplacementDao.create(dossier.getDeplacement());
		dao.create(dossier);
	}
	
	@Override
	public void enregistrer(DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives) throws ServiceException {
		dossier.setNumero(System.currentTimeMillis()+"");
		
		/* a supprimer */
		dossier.setBeneficiaire(beneficiaireDao.readAll().iterator().next());
		/**             */
		deplacementDao.create(dossier.getDeplacement());
		dao.create(dossier);
		
		for(PieceJustificative pj : pieceJustificatives){
			pj.setDossier(dossier);
			pieceJustificativeDao.create(pj);
		}
		
	}

	@Override
	public void deposer(DOSSIER dossier) throws ServiceException {
		
	}

	@Override
	public void accepterRecevabilite(DOSSIER dossier) throws ServiceException {
		//Operation operation = new Operation(code, new Date(), nature);
	}
	
	@Override
	public void accepterRecevabilite(Collection<DOSSIER> dossiers) throws ServiceException {
		for(DOSSIER dossier : dossiers)
			accepterRecevabilite(dossier);
	}
	
	@Override
	public void rejeterRecevabilite(Collection<DOSSIER> dossiers, String motif)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void rejeterRecevabilite(DOSSIER dossier, String motif)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accepterConformite(DOSSIER dossier) throws ServiceException {
		
	} 
	
	@Override
	public void accepterConformite(Collection<DOSSIER> dossiers) throws ServiceException {
		for(DOSSIER dossier : dossiers)
			accepterConformite(dossier);
		
	}
	
	@Override
	public void rejeterConformite(Collection<DOSSIER> dossiers, String motif)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void rejeterConformite(DOSSIER dossier, String motif)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	/* Fonctions techniques  */
	/*
	@Override
	public DOSSIER findByIdWithPieceJustificative(String identifiant) {
		return (DOSSIER) ((AbstractDossierDao<DOSSIER>)dao).readWithPieceJustificative(identifiant);
	}
	*/
	@Override
	public Collection<DOSSIER> findAll(NatureDeplacement natureDeplacement, Statut statut) {
		return null;
	}

}
