package ci.gouv.budget.solde.sigdcp.controller.stats;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map.Entry;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiquesResultats;
import ci.gouv.budget.solde.sigdcp.service.indemnite.GroupeMissionService;
import ci.gouv.budget.solde.sigdcp.service.indemnite.TypeClasseVoyageService;

@Named @ViewScoped
public class ConsomationIndemniteTransportController extends StatistiqueDeplacementController<TypeClasseVoyage> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	@Inject private TypeClasseVoyageService typeClasseVoyageService;
	@Inject private GroupeMissionService groupeMissionService;
	
	@Getter @Setter private Collection<TypeClasseVoyage> typeClasseVoyageSelectionnees;
	@Getter @Setter private DeplacementStatistiques<GroupeMission> statistiks;
	@Getter @Setter private String libelleDetailObjet;
	
	
	@Override
	protected void initialisation() {
		
		super.initialisation();
		title="Consommations des indemnités et transport";	
		showOperationCritere=false;
		pcolCritere=6;
		
		libelleDetailObjet="groupes"; 
		
	}

	public void detailAction(){ 
		statistiks = typeClasseVoyageService.findStatistiqueByGroupe(groupeMissionService.findByTypeClasseVoyage(typeClasseVoyageSelectionnees));
}

public Collection<GroupeMission> getDetailObjets(){
	if(statistiks==null)
		return null;
	return statistiks.getGroupes();
}

public Long depense(GroupeMission groupeMission){
	if(statistiks==null)
		return null;
	for(Entry<GroupeMission, DeplacementStatistiquesResultats> r : statistiks.getResultatParGroupe().entrySet())
		if(r.getKey().equals(groupeMission))
			return r.getValue().getDepense();
	return 0l;
}

public Long fraisTransport(GroupeMission groupeMission){
	if(statistiks==null)
		return null;
	for(Entry<GroupeMission, DeplacementStatistiquesResultats> r : statistiks.getResultatParGroupe().entrySet())
		if(r.getKey().equals(groupeMission))
			return r.getValue().getFraisTransport();
	return 0l;
}

	@Override
	protected DeplacementStatistiques<TypeClasseVoyage> calculeSatistique() {
		if(typeClasseVoyageSelectionnees==null)
		return null;
		return typeClasseVoyageService.findStatistiqueByTypeClasseVoyage(typeClasseVoyageSelectionnees);
	}
	

	@Override
	protected String camembertLibelle(TypeClasseVoyage entity) {
		return entity.getLibelle();
	}
	
}
