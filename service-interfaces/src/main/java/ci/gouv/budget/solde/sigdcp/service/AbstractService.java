package ci.gouv.budget.solde.sigdcp.service;

import java.util.List;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;


public interface AbstractService<TYPE_MODEL extends AbstractModel<TYPE_IDENTIFIANT>,TYPE_IDENTIFIANT> {

	/**
	 * Les services de lectures de donnees
	 */
	
	/**
	 * Retourne l'objet avec l'identifiant donné
	 * @param identifiant
	 * @return
	 */
	public TYPE_MODEL findById(TYPE_IDENTIFIANT identifiant);
	
	/**
	 * Retourne la liste de tous les objets du type @MODEL
	 * @return
	 */
	public List<TYPE_MODEL> findAll();
	
	public Boolean exist(TYPE_IDENTIFIANT identifiant);
	
}
