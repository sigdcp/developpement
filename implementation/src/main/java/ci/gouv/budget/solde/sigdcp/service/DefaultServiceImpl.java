package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessageType;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.service.resources.ServiceConstantResources;
import ci.gouv.budget.solde.sigdcp.service.utils.NavigationHelper;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.NotificationService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;

public class DefaultServiceImpl<TYPE_MODEL extends AbstractModel<TYPE_IDENTIFIANT>,TYPE_IDENTIFIANT> implements AbstractService<TYPE_MODEL,TYPE_IDENTIFIANT> , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;

	@Inject protected ServiceConstantResources constantResources;
	@Inject protected NotificationService notificationService;
	@Inject protected NavigationHelper navigationHelper;
	
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
	
	@Override
	public Boolean exist(TYPE_IDENTIFIANT identifiant) {
		return dao.exist(identifiant);
	}
	
	/*------------------------------------------------------------------------------*/
	
	protected void notifier(NotificationMessageType messageType,Object[] theParameters, Party receiver) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		for(int i=0;i<theParameters.length-1;i=i+2)
			parameters.put((String)theParameters[i], theParameters[i+1]);
		notificationService.send(messageType, parameters, receiver);
	}
	
	protected void notifier(NotificationMessageType messageType,Object[] theParameters, CompteUtilisateur compteUtilisateur) {
		notifier(messageType, theParameters, compteUtilisateur.getUtilisateur());
	}
	
	/*------------------------------------------------------------------------------*/
	
	protected static void serviceException(ServiceExceptionType type,Boolean rollback){
		serviceException(type.getLibelle(),rollback);
	}

	protected static void serviceException(ServiceExceptionType type){
		serviceException(type, Boolean.TRUE);
	}
	
	protected static void serviceException(String message,Boolean rollback){
		//System.out.println("Rollback : "+rollback);
		if(rollback)
			throw new ServiceException(message);
		//System.out.println("DefaultServiceImpl.serviceException()");
		throw new ServiceExceptionNoRollBack(message);
	}
	protected static void serviceException(String message){
		serviceException(message, Boolean.TRUE);
	}
	
	protected String formatDate(Date date){
		return date.toString();
	}
	
	protected <T> void validate(AbstractValidator<T> validator,T object){
		if(!validator.validate(object).isSucces())
			serviceException(validator.getMessagesAsString());
	}

}
