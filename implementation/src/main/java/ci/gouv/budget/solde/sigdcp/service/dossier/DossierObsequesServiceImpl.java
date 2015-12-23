package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierObsequesDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierObsequesValidator;

@Stateless
public class DossierObsequesServiceImpl extends AbstractDossierServiceImpl<DossierObseques> implements DossierObsequesService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject private DossierObsequesValidator dossierObsequesValidator;
	@Inject private AgentEtatDao agentEtatDao;
	
	@Inject
	public DossierObsequesServiceImpl(DossierObsequesDao dao) {
		super(dao);
	}
	
	@Override
	protected void validationSaisie(DossierObseques dossier, Boolean soumission) throws ServiceException {
		dossierObsequesValidator.setSoumission(soumission);
		if(!dossierObsequesValidator.validate(dossier).isSucces())
			throw new ServiceException(dossierObsequesValidator.getMessagesAsString());
	}
	
	@Override
	protected void onDaoCreate(DossierObseques dossier) {
		//est ce que le defunt est connu ?
		AgentEtat agentEtat = agentEtatDao.readByMatricule(dossier.getBeneficiaire().getMatricule());
		if(agentEtat==null)
			agentEtatDao.create(dossier.getBeneficiaire());
		else{
			agentEtat.setAyantDroit(dossier.getBeneficiaire().getAyantDroit());
			dossier.setBeneficiaire(agentEtat);
		}
			
		super.onDaoCreate(dossier);
	}
	
	@Override
	protected DossierObseques createDossier(NatureDeplacement natureDeplacement) {
		DossierObseques dossierObseque=new DossierObseques(new Deplacement(genericDao.readByClass(TypeDepense.class, String.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE)));
		if(natureDeplacement!=null && natureDeplacement.getSceSolde()){
			dossierObseque.getDeplacement().setAddUser(utilisateur());
		}
		return dossierObseque;
	}
	
	@Override
	protected void beneficiaire(DossierObseques dossier) {
		AgentEtat agentEtat = new AgentEtat();
		agentEtat.setType(genericDao.readByClass(TypeAgentEtat.class, Code.TYPE_AGENT_ETAT_FONCTIONNAIRE));
		agentEtat.setAyantDroit(utilisateur());
		dossier.setBeneficiaire(agentEtat);
	}
}
