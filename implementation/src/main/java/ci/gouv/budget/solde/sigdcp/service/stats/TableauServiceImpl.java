package ci.gouv.budget.solde.sigdcp.service.stats;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.stats.TableauBord;
import ci.gouv.budget.solde.sigdcp.model.stats.TableauBordStatutDetails;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

@Stateless
public class TableauServiceImpl implements TableauBordService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Override
	public TableauBord findByNatureDeplacementByPeriode(NatureDeplacement natureDeplacement,Date dateDebut,Date dateFin) throws ServiceException {
		TableauBord tableauBord = new TableauBord();
		tableauBord.getStatutDetails().add(new TableauBordStatutDetails(new Statut(null, Code.STATUT_SOUMIS, null), 100l));
		tableauBord.getStatutDetails().add(new TableauBordStatutDetails(new Statut(null, Code.STATUT_RECEVABLE, null), 98l));
		tableauBord.getStatutDetails().add(new TableauBordStatutDetails(new Statut(null, Code.STATUT_CONFORME, null), 90l));
		tableauBord.getStatutDetails().add(new TableauBordStatutDetails(new Statut(null, Code.STATUT_LIQUIDE, null), 50l));
		tableauBord.getStatutDetails().add(new TableauBordStatutDetails(new Statut(null, Code.STATUT_REGLE, null), 30l));
		return tableauBord;
	}
	
	
}
