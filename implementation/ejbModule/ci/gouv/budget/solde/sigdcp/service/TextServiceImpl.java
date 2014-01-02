package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.inject.Singleton;

/**
 * Provided localized text
 * @author Komenan Y .Christian
 *
 */
@Singleton 
public class TextServiceImpl implements TextService, Serializable {
	
	private static final long serialVersionUID = -2096649010369789825L;
	
	private static final ResourceBundle I18N = ResourceBundle.getBundle("ci.gouv.budget.solde.sigdcp.service.resources.i18n.message",Locale.FRENCH);
	
	private static final String NOT_YET_DEFINED_ID_FORMAT = "## %s ##";
	
	@Override
	public String find(String id){
		try {
			return I18N.getString(id);
		} catch (MissingResourceException e) {
			return String.format(NOT_YET_DEFINED_ID_FORMAT, id);
		}
	}
	

	
	

}
