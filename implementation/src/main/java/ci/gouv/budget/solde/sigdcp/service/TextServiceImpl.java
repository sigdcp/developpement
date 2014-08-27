package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.inject.Singleton;

import ci.gouv.budget.solde.sigdcp.service.utils.TextService;

/**
 * Provided localized text
 * @author Komenan Y .Christian
 *
 */
@Singleton 
public class TextServiceImpl implements TextService, Serializable {
	
	private static final long serialVersionUID = -2096649010369789825L;
	
	private static final ResourceBundle[] I18N_BUNDLES = { 
		ResourceBundle.getBundle("ci.gouv.budget.solde.sigdcp.service.resources.i18n.message",Locale.FRENCH),
		ResourceBundle.getBundle("ci.gouv.budget.solde.sigdcp.service.resources.i18n.error",Locale.FRENCH),
		ResourceBundle.getBundle("ci.gouv.budget.solde.sigdcp.service.resources.i18n.ui",Locale.FRENCH)
	};
	
	private static final String NOT_YET_DEFINED_ID_FORMAT = "## %s ##";
	
	@Override
	public String find(String id){
		return find(id, null);
	}
	
	@Override
	public String find(String id, Object[] parameters) {
			//if(parameters==null)
			//	parameters = new Object[]{};
			
			for(ResourceBundle resourceBundle : I18N_BUNDLES)
				try {return MessageFormat.format(resourceBundle.getString(id), parameters);} catch (MissingResourceException e) {}
			
			return String.format(NOT_YET_DEFINED_ID_FORMAT, id);
		
	}

}
