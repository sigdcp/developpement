package ci.gouv.budget.solde.sigdcp.service.utils.communication;

import java.util.Map;

import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessage;
import ci.gouv.budget.solde.sigdcp.model.communication.NotificationMessageType;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;

public interface NotificationService {
	
	public NotificationMessage build(NotificationMessageType messageType,Map<String,Object> parameters);
	
	public NotificationMessage send(NotificationMessageType messageType,Map<String,Object> parameters,Party receiver);

	public NotificationMessage send(NotificationMessageType messageType,Map<String,Object> parameters,String receiver);
	
}
