package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

public abstract class AbstractDynaEnumReferenceController<ENTITY extends DynamicEnumeration> extends AbstractEntiteReferenceController<ENTITY,String> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	public String identifiant(ENTITY entity) {
		return entity.getCode();
	}
	
}
