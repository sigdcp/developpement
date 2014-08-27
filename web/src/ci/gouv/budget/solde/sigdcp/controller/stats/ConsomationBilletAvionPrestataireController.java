package ci.gouv.budget.solde.sigdcp.controller.stats;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.service.prestation.PrestataireService;

@Named @ViewScoped
public class ConsomationBilletAvionPrestataireController extends StatistiqueDeplacementController<Prestataire> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	@Inject private PrestataireService prestataireService;
	
	@Getter @Setter private Collection<Prestataire> prestataireSelectionnees,detailObjets;
	@Getter @Setter private String libelleDetailObjet;
	@Getter  @Setter private String typePrestataire;
	
	@Override
	protected void initialisation() {
		
		super.initialisation();
		title="Statistique des consommations des billets d'avion - Prestataire";	
		showOperationCritere=false;
		pcolCritere=6;
		
		libelleDetailObjet="agences"; 
		typePrestataire=Code.TYPE_PRESTATAIRE_CG;
	}

	public void detailAction(){ 
		/*typePrestataire=Code.TYPE_PRESTATAIRE_AV;
		prestataireSelectionnees=prestataireService.findByTypeIdByParent(typePrestataire, prestataireSelectionnees);
		detailObjets = load();*/
}


	@Override
	protected DeplacementStatistiques<Prestataire> calculeSatistique() {
		if(prestataireSelectionnees==null)
		return null;
		return prestataireService.findConsommationBilletAvionStatistique(prestataireSelectionnees);  
	}
	

	@Override
	protected String camembertLibelle(Prestataire entity) {
		return entity.getNom();
	}
	
}
