package ci.gouv.budget.solde.sigdcp.service.stats;

import java.util.Date;

import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.stats.TableauBord;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

public interface TableauBordService  {

	TableauBord findByNatureDeplacementByPeriode(NatureDeplacement natureDeplacement,Date dateDebut,Date dateFin) throws ServiceException ;
	
}
