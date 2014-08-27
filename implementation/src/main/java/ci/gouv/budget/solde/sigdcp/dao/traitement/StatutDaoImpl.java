package ci.gouv.budget.solde.sigdcp.dao.traitement;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.dao.traitement.StatutDao;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;

public class StatutDaoImpl extends JpaDaoImpl<Statut, String> implements StatutDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;
	
}
