package ci.gouv.budget.solde.sigdcp.controller.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;
import ci.gouv.budget.solde.sigdcp.model.calendrier.Exercice;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtatReference;
import ci.gouv.budget.solde.sigdcp.model.identification.QuestionSecrete;

/**
 * Converter Using View Map to store and retrieve object.
 * No connection to data store (DB) needed
 * @author christian
 *
 */
@Named @Log
public class ObjectConverter implements Converter {
	
	private static final String OBJECT_MAP_KEY = ObjectConverter.class.getSimpleName();
	private static final String NULL_STRING_VALUE = "";

	
	private Map<String, Object> getObjectMap(FacesContext context) {
		Map<String, Object> viewMap = context.getViewRoot().getViewMap();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> objectMap = (Map) viewMap.get(OBJECT_MAP_KEY);
		if (objectMap == null) {
			objectMap = new HashMap<String, Object>();
			viewMap.put(OBJECT_MAP_KEY, objectMap);
		}
		return objectMap;
	}
	
	private String getIdentifier(Object object){
		if(object == null)
			return null;
		if(object instanceof DynamicEnumeration){
			return ((DynamicEnumeration) object).getCode();
		}else if(object instanceof Exercice){
			return ((Exercice) object).getAnnee().toString();
		}else if(object instanceof QuestionSecrete){
			return ((QuestionSecrete) object).getId().toString();
		}else if(object instanceof AgentEtat){
			return ((AgentEtat) object).getMatricule();
		}else if(object instanceof DossierMission){
			return ((DossierMission) object).getBeneficiaire().getMatricule();
		}else if(object instanceof Dossier){
			return ((Dossier) object).getId().toString();
		}else if(object instanceof AgentEtatReference){
			return ((AgentEtatReference) object).getMatricule();
		}else if(!(object instanceof String))
			log.warning("Cannot find identitifer of this object type : "+object.getClass());
		return null;
	}
	
	
	private String createNewIdentifierFrom(String identifier){
		return identifier+System.currentTimeMillis();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String identifier) {
		if (identifier==null || identifier.isEmpty())
			return null;
		return getObjectMap(context).get(identifier);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object object) {
		if (object == null) 
			return NULL_STRING_VALUE;
		String identifier = getIdentifier(object);
		if(identifier==null){
			//log.warning("Identifier value of <"+object+"> is null");
			return NULL_STRING_VALUE;
		}
		// handle duplicate id (two object of different type can have the same id value)
		Map<String, Object> objectMap = getObjectMap(context);
		if(objectMap.containsKey(identifier))
			identifier = createNewIdentifierFrom(identifier);
		objectMap.put(identifier, object);
		return identifier;
	}
}