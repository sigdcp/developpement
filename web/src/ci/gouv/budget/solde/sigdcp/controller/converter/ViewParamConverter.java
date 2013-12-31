package ci.gouv.budget.solde.sigdcp.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import ci.gouv.budget.solde.sigdcp.service.GenericService;

/**
 * 
 * @author christian
 *
 */
//@Log
public class ViewParamConverter implements Converter {
	
	private GenericService service;
	private Class<?> clazz;
	
	public ViewParamConverter(GenericService service, Class<?> clazz) {
		super();
		this.service = service;
		this.clazz = clazz;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String identifier) {
		System.out.println(clazz+" : "+identifier);
		if (identifier==null || identifier.isEmpty())
			return null;
		return service.findByClass(clazz,identifier);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object object) {
		//log.warning("Should Not Be Called!!!");
		return null;
	}
}