package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.prestation.CommandeDao;
import ci.gouv.budget.solde.sigdcp.dao.prestation.FactureDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.prestation.Commande;
import ci.gouv.budget.solde.sigdcp.model.prestation.Facture;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementFacture;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.sotra.TraitableServiceImpl;

@Stateless
public class FactureServiceImpl extends TraitableServiceImpl<Facture, Long,TraitementFacture> implements FactureService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private CommandeDao commandeDao;
	
	@Inject
	public FactureServiceImpl(FactureDao dao) {
		super(dao);
	} 
	
	@Override
	protected Collection<Facture> firstFlow(NatureOperation natureOperation) {
		Collection<Facture> liste = new ArrayList<>();
		for(Commande commande : commandeDao.readByNatureOperationIdByStatutId(Code.NATURE_OPERATION_GENERATION_BCTT, Code.STATUT_ACCEPTE))
			liste.add(new Facture(commande));
		return liste;
	}

	@Override
	public Facture find(Long id, String natureOperationCode)throws ServiceException {
		return null;
	}

	@Override
	public Collection<Facture> findDemandes() {
		return null;
	}

	@Override
	protected void associerTraitement(Facture facture) {
		facture.getTraitable().getTraitement().setFacture(facture);
	}

	@Override
	protected Object idValue(Facture facture) {
		return facture.getId();
	}

	@Override
	protected Collection<TraitementFacture> historiqueTraitements(Facture facture) {
		return null;
	}

	@Override
	protected TraitementFacture traitementInstance() {
		return new TraitementFacture();
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Facture nouveau(Commande commande) {
		Facture f=new Facture(commande);
		f.getTraitable().setNatureOperation(genericDao.readByClass(NatureOperation.class, Code.NATURE_OPERATION_RECEPTION_FACTURE));
		f.getTraitable().setTraitement(new TraitementFacture());
		f.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
		return f;
	}

	@Override
	public Collection<Facture> findDemandesSolde() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

