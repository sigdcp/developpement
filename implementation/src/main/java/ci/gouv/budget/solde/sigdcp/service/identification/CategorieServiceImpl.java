package ci.gouv.budget.solde.sigdcp.service.identification;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.identification.CategorieDao;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class CategorieServiceImpl extends DefaultServiceImpl<Categorie, String> implements CategorieService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public CategorieServiceImpl(CategorieDao categorieDao) {
		super(categorieDao);
	}

}
