package ci.gouv.budget.solde.sigdcp.service;

import java.util.Collection;

public interface CrudService {

	Boolean existe(Class<?> clazz,Object id);
	Object enregistrer(Object object);
	void supprimer(Collection<?> objects);
	
}
