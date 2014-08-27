package ci.gouv.budget.solde.sigdcp.service.sotra;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.DelegueSotraDao;
import ci.gouv.budget.solde.sigdcp.dao.sotra.BeneficiaireCarteSotraDao;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.model.sotra.BeneficiaireCarteSotra;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

@Stateless
public class BeneficiaireCarteSotraServiceImpl extends DefaultServiceImpl<BeneficiaireCarteSotra, Long> implements BeneficiaireCarteSotraService {
	
	private static final long serialVersionUID = 8509906777706320841L;

	@Inject private DelegueSotraDao delegueSotraDao;
	
	@Inject
	public BeneficiaireCarteSotraServiceImpl(BeneficiaireCarteSotraDao dao) {
		super(dao);
	}

	@Override
	public void adherer(DelegueSotra delegueSotra) throws ServiceException {
		BeneficiaireCarteSotra adherent = ((BeneficiaireCarteSotraDao)dao).readByAgent((AgentEtat) utilisateur());
		if(adherent!=null){
			if(Boolean.TRUE.equals(adherent.getValide()))
				serviceException("Vous êtes déja inscrit sur une liste de base");
			else if(adherent.getValide()==null)
				serviceException("Vous avez déja une inscription en cours de validation");
		}
		adherent = new BeneficiaireCarteSotra(delegueSotra, (AgentEtat) utilisateur(), tempsCourant());
		((BeneficiaireCarteSotraDao)dao).create(adherent);
	}

	@Override
	public void annulerAdhesion() throws ServiceException {
		
	}

	@Override
	public void validerInscription(Collection<BeneficiaireCarteSotra> adherentCarteSotras) throws ServiceException {
		for(BeneficiaireCarteSotra adherent : adherentCarteSotras){
			adherent.setDateValidation(tempsCourant());
			((BeneficiaireCarteSotraDao)dao).update(adherent);
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean estAdherent() throws ServiceException {
		return delegueSotraDao.readByBeneficiaire((AgentEtat) utilisateur())!=null;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<BeneficiaireCarteSotra> findAdherents() {
		return ((BeneficiaireCarteSotraDao)dao).readValideByDelegue((AgentEtat) utilisateur());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<BeneficiaireCarteSotra> findByValide(Boolean valide) {
		if(valide==null)
			return ((BeneficiaireCarteSotraDao)dao).readValideIsNull();
		return ((BeneficiaireCarteSotraDao)dao).readByValide(valide);
	}

}
