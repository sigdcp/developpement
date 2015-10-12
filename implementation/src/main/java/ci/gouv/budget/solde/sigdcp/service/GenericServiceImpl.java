package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.GenericDao;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

public class GenericServiceImpl implements GenericService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private GenericDao dao;
	
	@Override
	public <TYPE, TYPE_ID> TYPE findByClass(Class<TYPE> aClass,Class<TYPE_ID> aIdClass,String identifier) {
		return dao.readByClass(aClass, aIdClass,identifier);
	}
	
	@Override
	public final <TYPE> TYPE findByClass(Class<TYPE> aClass, String identifier) {
		return dao.readByClass(aClass, identifier);
	}

	@Override
	public final <TYPE> Collection<TYPE> findAllByClass(Class<TYPE> aClass) {
		return dao.readAllByClass(aClass);
	}

	@Override
	public final AbstractModel<Object> findById(Object identifiant) {
		throw new RuntimeException("Must not be called");
	}

	@Override
	public final List<AbstractModel<Object>> findAll() {
		throw new RuntimeException("Must not be called");
	}
	
	@Override
	public Boolean exist(Object identifiant) {
		throw new RuntimeException("Must not be called");
	}

	@Override
	public <TYPE> Collection<TYPE> findAllByClass(Class<TYPE> aClass, Integer debut, Integer max) {
		return dao.readAllByClass(aClass, debut, max);
	}
	@Override
	public <TYPE> Collection<TYPE> findAllByClass(Class<TYPE> aClass, String critere) {
		return dao.readAllByClass(aClass, critere);
	}

}

