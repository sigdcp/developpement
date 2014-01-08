package ci.gouv.budget.solde.sigdcp.controller.ui;
import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;



/**
 * Acceder à un enregistrement
 * @author christian
 *
 */
@Getter @Setter
public abstract class AbstractEntityUIController<ENTITY extends AbstractModel<?>> extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = 6482420670667872715L;
	
	/**
	 * pour recuperer ou présenter les informations coté client
	 * Pourra être obtenu à partir du paramètre de requête Identifiant <code>ConstantController.getInstance().getRECORD_ID()</code>
	 */
	protected ENTITY entity;
	

}
