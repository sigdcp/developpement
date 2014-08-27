package ci.gouv.budget.solde.sigdcp.controller.stats;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.LettreAvance;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.service.calendrier.LettreAvanceService;
import ci.gouv.budget.solde.sigdcp.service.identification.SectionService;

@Named @ViewScoped
public class StatistiqueLettreAvanceController  extends StatistiqueDeplacementController<Section> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Inject private SectionService sectionService;
	@Inject private LettreAvanceService lettreAvanceService;
	
	@Getter @Setter private Collection<Section> sectionSelectionnees;
	@Getter @Setter private Collection<LettreAvance> detailObjets;
	@Getter @Setter private String libelleDetailObjet;

	@Getter @Setter private BigDecimal montantTotal=new BigDecimal(0);
	
	@Override
	protected void initialisation() {
		
		super.initialisation();
		title="Bilan des lettres d'avance";
		libelleDetailObjet="Lettres d'avance"; 
		defaultSubmitCommand.setRendered(false);
	}

	public void detailAction(){  
		detailObjets = lettreAvanceService.findBySection(sectionSelectionnees);
		for(LettreAvance l : detailObjets)montantTotal=montantTotal.add(l.getMontant());
}
	@Override
	protected DeplacementStatistiques<Section> calculeSatistique() {
		if(sectionSelectionnees==null)
			return null;
			return sectionService.findLettreAvanceStatistique(sectionSelectionnees);
	}

	@Override
	protected String camembertLibelle(Section entity) {
		return entity.getLibelle();
	}
	
	public void total(BigDecimal montant){
		
		//return montantTotal;
}
	
}