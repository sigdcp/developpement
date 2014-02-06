package ci.gouv.budget.solde.sigdcp.service.utils.communication;

import java.util.Map;

import ci.gouv.budget.solde.sigdcp.model.identification.Party;

public interface NotificationService {
	
	public void send(String subject,String messageTemplateId,Map<String,Object> parameters,Party receiver);

}
