package ci.gouv.budget.solde.sigdcp.dao;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

// TODO renommer en Persistence
public interface DataAccessObject<TYPE_MODEL extends AbstractModel<TYPE_IDENTIFIANT>,TYPE_IDENTIFIANT> {
	
	/**
	 * CRUD
	 * 
	 */
	
	public void create(TYPE_MODEL object);
	
	public TYPE_MODEL read(TYPE_IDENTIFIANT identifiant);
	
	public TYPE_MODEL update(TYPE_MODEL object);
	
	public void delete(TYPE_MODEL object);
	
	/**
	 * 
	 * useful methods
	 */
	
	public Collection<TYPE_MODEL> readAll();

	public Boolean exist(TYPE_IDENTIFIANT identifiant);
}
