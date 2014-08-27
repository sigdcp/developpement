package ci.gouv.budget.solde.sigdcp.dao;


public interface CrudDao {
	
	Boolean existe(Class<?> clazz,Object id);
	void create(Object object);
	Object update(Object object);
	void delete(Object object);
 
}
