package ci.gouv.budget.solde.sigdcp.dao.prestation;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;

public abstract class AbstractPrestataireDaoImpl<PRESTATAIRE extends Prestataire> extends JpaDaoImpl<PRESTATAIRE, Long> implements AbstractPrestataireDao<PRESTATAIRE> , Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
	
}

