package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import ci.gouv.budget.solde.sigdcp.dao.DataAccessObject;
import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.GenericDao;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessageType;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.service.identification.AuthentificationInfos;
import ci.gouv.budget.solde.sigdcp.service.resources.ServiceConstantResources;
import ci.gouv.budget.solde.sigdcp.service.utils.NavigationHelper;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.NotificationService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.AbstractValidator;

public class DefaultServiceImpl<TYPE_MODEL extends AbstractModel<TYPE_IDENTIFIANT>,TYPE_IDENTIFIANT> implements AbstractService<TYPE_MODEL,TYPE_IDENTIFIANT> , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;

	
	
	@Inject protected AuthentificationInfos authentificationInfos;
	@Inject protected ServiceConstantResources constantResources;
	@Inject protected NotificationService notificationService;
	@Inject protected NavigationHelper navigationHelper;
	@Inject protected GenericDao genericDao;
	@Inject protected DynamicEnumerationDao dynamicEnumerationDao;
	
	protected DataAccessObject<TYPE_MODEL, TYPE_IDENTIFIANT> dao;
	
	public DefaultServiceImpl(DataAccessObject<TYPE_MODEL, TYPE_IDENTIFIANT> dao) {
		super();
		this.dao = dao;
	}

	@Override
	public TYPE_MODEL findById(TYPE_IDENTIFIANT identifiant) {
		return dao.read(identifiant);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public List<TYPE_MODEL> findAll() {
		return new LinkedList<>(dao.readAll());
	}
	
	@Override
	public Boolean exist(TYPE_IDENTIFIANT identifiant) {
		return dao.exist(identifiant);
	}
	
	/*------------------------------------------------------------------------------*/
	
	protected Personne utilisateur(){
		return (Personne) authentificationInfos.getUtilisateur();
	}
	
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
		return constantResources.dateAsString(date);
	}
	
	protected <T> void validate(AbstractValidator<T> validator,T object){
		if(!validator.validate(object).isSucces())
			serviceException(validator.getMessagesAsString());
	}
	
	protected Date tempsCourant(){
		return new DateTime().withZone(DateTimeZone.UTC)/*.withTime(0, 0, 0, 0)*/.toDate();
	}
	
	protected Boolean hasRole(String roleCode){
		for(Role role : authentificationInfos.getCompteUtilisateur().getRoles())
			if(role.getCode().equals(roleCode))
				return true;
		return false;
	}
	
	protected void debug(Object object){
		System.out.println("**********************************************************************");
		System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
	}
	
}
