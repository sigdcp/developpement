package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;

public class CategorieDaoImpl extends JpaDaoImpl<Categorie, String> implements CategorieDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
}

/*
public class MinistereDaoImpl implements MinistereDao , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Inject
	protected EntityManager entityManager;
	
	@Override
	public void create(Ministere ministere) {
		entityManager.persist(ministere);
	}

	@Override
	public Ministere read(String identifiant) {
		return entityManager.find(Ministere.class, identifiant);
	}

	@Override
	public Ministere update(Ministere object) {
		return entityManager.merge(object);
	}

	@Override
	public void delete(String object) {
		entityManager.remove(entityManager.merge(object));
	}

	@Override
	public List<Ministere> findAll() {
		return entityManager.createQuery("SELECT m FROM Ministere m", Ministere.class).getResultList();
	}
	
	

}
*/
