package ci.gouv.budget.solde.sigdcp.controller.stats;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.service.geographie.LocaliteService;

@Named @ViewScoped
public class ConsomationBilletAvionLocaliteController extends StatistiqueDeplacementController<Localite> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	@Inject private LocaliteService localiteService;
	
	@Getter @Setter private Collection<Localite> localiteSelectionnees,detailObjets;
	@Getter @Setter private Collection<Prestataire> compagnieSelectionnees;
	@Getter @Setter private String libelleDetailObjet;
	@Getter  @Setter private String typeLocalite;
	
	@Override
	protected void initialisation() {
		
		super.initialisation();
		title="Statistique des consommations des billets d'avion - Localité";	
		showOperationCritere=false;
		pcolCritere=6;
		
		libelleDetailObjet="pays"; 
		typeLocalite=Code.TYPE_LOCALITE_ZONE;
	}

	public void detailAction(){ 
		typeLocalite=Code.TYPE_LOCALITE_PAYS;
		localiteSelectionnees=localiteService.findByTypeIdByParent(typeLocalite, localiteSelectionnees);
		detailObjets = load();
}


	@Override
	protected DeplacementStatistiques<Localite> calculeSatistique() {
		if(localiteSelectionnees==null)
		return null;
		return localiteService.findConsommationBilletAvionStatistique(localiteSelectionnees);
	}
	

	@Override
	protected String camembertLibelle(Localite entity) {
		return entity.getLibelle();
	}
	
}
