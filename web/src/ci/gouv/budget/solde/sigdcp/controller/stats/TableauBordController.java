package ci.gouv.budget.solde.sigdcp.controller.stats;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.stats.TableauBord;
import ci.gouv.budget.solde.sigdcp.model.stats.TableauBordStatutDetails;
import ci.gouv.budget.solde.sigdcp.service.stats.TableauBordService;

@Named @ViewScoped
public class TableauBordController extends AbstractFormUIController<TableauBord> implements Serializable {

	private static final long serialVersionUID = 5374456603691536348L;

	/*
	 * Services
	 */
	@Inject
	private TableauBordService tableauBordService;
	
	/*
	 * Dtos
	 */
	@Getter private TableauBord tableauBord;
	// critères de recherche
	@Getter @Setter private NatureDeplacement natureDeplacement;
	@Getter @Setter private Date dateDebut,dateFin;
	
	@Getter
	private CartesianChartModel categoryModel;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Tableau de bord";
		internalCode="FS_DD_107_Ecran_01";
		tableauBord = tableauBordService.findByNatureDeplacementByPeriode(null, null, null);
		
		categoryModel = new CartesianChartModel();  
		for(TableauBordStatutDetails details : tableauBord.getStatutDetails()){  
			ChartSeries serie = new ChartSeries();  
			serie.setLabel(details.getStatut().getLibelle());  
			serie.set("Tableau de bord", details.getCount());
			categoryModel.addSeries(serie);
		} 
	}
	
	@Override
	public boolean isRead() {
		return true;
	}
	
}
