package ci.gouv.budget.solde.sigdcp.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;

/**
 * 
 * @author christian
 *
 */
@Log
public class ViewParamDynamicEnumerationConverter implements Converter {
	
	private DynamicEnumerationDao dynamicEnumerationDao;
	private Class<?> clazz;
	
	public ViewParamDynamicEnumerationConverter(DynamicEnumerationDao dynamicEnumerationDao, Class<?> clazz) {
		super();
		this.dynamicEnumerationDao = dynamicEnumerationDao;
		this.clazz = clazz;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String identifier) {
		if (identifier==null || identifier.isEmpty())
			return null;
		return dynamicEnumerationDao.readByClass(clazz,identifier);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object object) {
		log.warning("Should Not Be Called!!!");
		return null;
	}
}