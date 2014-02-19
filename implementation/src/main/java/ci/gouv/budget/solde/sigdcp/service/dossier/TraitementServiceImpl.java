package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.TraitementDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Operation;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class TraitementServiceImpl extends DefaultServiceImpl<Traitement, Long> implements TraitementService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private DynamicEnumerationDao dynamicEnumerationDao;
	@Inject private DossierDao dossierDao;
	
	@Inject
	public TraitementServiceImpl(TraitementDao dao) {
		super(dao);
	}
	
	@Override
	public Traitement create(Operation operation, Dossier dossier, Personne personne, String statutId) {
		Traitement traitement = new Traitement(operation, null, dossier, dynamicEnumerationDao.readByClass(Statut.class, statutId));
		((TraitementDao)dao).create(traitement);
		dossier.setDernierTraitement(traitement);
		dossierDao.update(dossier);
		return traitement;
	}

	@Override
	public Collection<Traitement> findByDossier(Dossier dossier) {
		return ((TraitementDao)dao).readAllByDossier(dossier);
	}
	
	@Override
	public Collection<Traitement> findByAgentEtat(AgentEtat agentEtat) {
		return ((TraitementDao)dao).readByAgentEtat(agentEtat);
	}
	
	@Override
	public Traitement findByPieceProduite(PieceProduite pieceProduite) {
		return ((TraitementDao)dao).readByPieceProduite(pieceProduite);
	}
	
	@Override
	public Collection<Traitement> findByPieceProduiteTypeId(String typePieceProduiteId) {
		return ((TraitementDao)dao).readByPieceProduiteTypeId(typePieceProduiteId);
	}
	
	@Override
	public Collection<Traitement> findByNatureDeplacementByStatut(NatureDeplacement natureDeplacement, Statut statut) {
		return ((TraitementDao)dao).readByNatureDeplacementByStatut(natureDeplacement, statut);
	}

}
