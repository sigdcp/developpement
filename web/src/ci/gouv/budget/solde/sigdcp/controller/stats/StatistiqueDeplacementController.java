package ci.gouv.budget.solde.sigdcp.controller.stats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.model.chart.PieChartModel;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiquesResultats;

public abstract class StatistiqueDeplacementController<ENTITY extends AbstractModel<?>> extends AbstractEntityListUIController<ENTITY> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Getter @Setter protected Date dateDebut;
	@Getter @Setter protected Date dateFin;
	
	@Getter @Setter protected Boolean showMontantColumn=false,showZoneCritere=true,showOperationCritere=false;
	@Getter protected DeplacementStatistiques<ENTITY> statistiques;
	@Getter protected PieChartModel pieChartModelDepense,pieChartModelFraisTransport,pieChartModelNombreDossier,pieChartModelNombreVoyageurs;
	
	
	@Getter @Setter protected int pcolCritere;
	
	@Override
	protected void initialisation() {
		
		super.initialisation();
		title="Statistique des déplacements";		
		defaultSubmitCommand.setValue("Editer");
		selectLabel="bouton.details";
		defaultSubmitCommand.setRendered(false);
		pcolCritere=6;
	}
	@Override
	protected List<ENTITY> load() {	
		statistiques = calculeSatistique();
		if(statistiques==null)
			return null;
		
		pieChartModelDepense = new PieChartModel(); 
		pieChartModelFraisTransport = new PieChartModel();
		pieChartModelNombreDossier = new PieChartModel();
		pieChartModelNombreVoyageurs = new PieChartModel();
		for(Entry<ENTITY, DeplacementStatistiquesResultats> entry : statistiques.getResultatParGroupe().entrySet()){
			pieChartModelDepense.set(camembertLibelle(entry.getKey()), entry.getValue().getDepense());
			pieChartModelFraisTransport.set(camembertLibelle(entry.getKey()), entry.getValue().getFraisTransport());
			pieChartModelNombreDossier.set(camembertLibelle(entry.getKey()), entry.getValue().getNombreDossier());
			pieChartModelNombreVoyageurs.set(camembertLibelle(entry.getKey()), entry.getValue().getNombreVoyageurs());
		}
		
		return new ArrayList<ENTITY>(statistiques.getGroupes());
			
	}
	
	protected abstract DeplacementStatistiques<ENTITY> calculeSatistique();
	protected abstract String camembertLibelle(ENTITY entity);
	
	public DeplacementStatistiquesResultats details(ENTITY entity){
		if(entity==null)
			return null;
		return statistiques.getResultatParGroupe().get(entity);
	}


	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
	}
	
	@Override
	protected void onSearchCommandAction() {
		super.onSearchCommandAction();
	}
	

	public void doSearch(){
		onSearchCommandAction();
	}
	
}
