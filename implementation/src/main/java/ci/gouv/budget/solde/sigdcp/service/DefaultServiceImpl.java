package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public class DefaultServiceImpl<TYPE_MODEL extends AbstractModel<TYPE_IDENTIFIANT>,TYPE_IDENTIFIANT> implements AbstractService<TYPE_MODEL,TYPE_IDENTIFIANT> , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;

	protected DataAccessObject<TYPE_MODEL, TYPE_IDENTIFIANT> dao;
	
	public DefaultServiceImpl(DataAccessObject<TYPE_MODEL, TYPE_IDENTIFIANT> dao) {
		super();
		this.dao = dao;
		//System.out.println("DefaultServiceImpl.DefaultServiceImpl() : "+dao);
	}

	@Override
	public TYPE_MODEL findById(TYPE_IDENTIFIANT identifiant) {
		return dao.read(identifiant);
	}

	@Override
	public List<TYPE_MODEL> findAll() {
		return new LinkedList<>(dao.readAll());
	}
	
	/*------------------------------------------------------------------------------*/
	
	protected static void serviceException(ServiceExceptionType type){
		serviceException(type.getLibelle());
	}
	
	protected static void serviceException(String message){
		throw new ServiceException(message);
	}

}
