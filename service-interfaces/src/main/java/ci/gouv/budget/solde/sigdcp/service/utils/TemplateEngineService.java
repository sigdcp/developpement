package ci.gouv.budget.solde.sigdcp.service.utils;

import java.util.Map;

public interface TemplateEngineService {
	
	String find(String templateId,Map<String,Object> parameters);

}
