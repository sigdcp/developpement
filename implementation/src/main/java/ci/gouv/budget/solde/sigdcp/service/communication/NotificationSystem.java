package ci.gouv.budget.solde.sigdcp.service.communication;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import ci.gouv.budget.solde.sigdcp.model.identification.Personne;

/**
 * Les constantes du systeme
 * @author christian
 *
 */
@Singleton @Named
public class NotificationSystem implements Serializable{
	
	private static final long serialVersionUID = 1583754563831914427L;

	public void notify(Personne personne){
		//notification par mail , sms , etc.
	}
 
}