package ci.gouv.budget.solde.sigdcp.service.identification;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.CompteUtilisateurDao;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

@Stateless
public class CompteUtilisateurServiceImpl extends DefaultServiceImpl<CompteUtilisateur, String> implements CompteUtilisateurService {

	private static final long serialVersionUID = 159214876975685747L;

	@Inject
	public CompteUtilisateurServiceImpl(CompteUtilisateurDao dao) {
		super(dao);
	}

	@Override
	public void activer(String username, String password) throws ServiceException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public CompteUtilisateur authentifier(String username, String password) throws ServiceException {
		if("sigdcp".equals(username) && "sigdcp".equals(password)){
			CompteUtilisateur c =new CompteUtilisateur();
			c.setLogin(username);
			c.setMotPasse(password);
			return c;
		}
		throw new ServiceException("Nom d'utilisateur ou mot de passe incorrect");
	}
	
	@Override
	public void deconnecter(CompteUtilisateur compteUtilisateur) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

}
