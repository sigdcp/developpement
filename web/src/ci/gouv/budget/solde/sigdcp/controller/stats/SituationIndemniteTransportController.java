package ci.gouv.budget.solde.sigdcp.controller.stats;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;
import ci.gouv.budget.solde.sigdcp.service.identification.SectionService;

@Named @ViewScoped
public class SituationIndemniteTransportController  extends StatistiqueDeplacementController<Section> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Inject private SectionService sectionService;
	@Inject private DossierService dossierService;
	
	@Getter @Setter private Collection<Section> sectionSelectionnees;
	@Getter @Setter private Collection<Dossier> detailObjets;
	@Getter @Setter private String libelleDetailObjet;

	@Getter @Setter private BigDecimal montantTotalIndemnite=new BigDecimal(0),montantTotaltransport=new BigDecimal(0);
	
	@Override
	protected void initialisation() {
		
		super.initialisation();
		title="Situation des indemnités et transport";
		libelleDetailObjet="bénéficiaires"; 
		defaultSubmitCommand.setRendered(false);
	}

	public void detailAction(){  
		detailObjets = dossierService.findStatiqueDetailBySection(sectionSelectionnees);		
		for(Dossier dossier : detailObjets){
			montantTotalIndemnite=montantTotalIndemnite.add(dossier.getMontantIndemnisation());
			if(dossier instanceof DossierMission)
				montantTotaltransport=montantTotaltransport.add(((DossierMission)dossier).getFrais().getTransport());
			
		}
	}
	
	@Override
	protected DeplacementStatistiques<Section> calculeSatistique() {
		if(sectionSelectionnees==null)
			return null;
			return sectionService.findStatistique(sectionSelectionnees);
	}

	@Override
	protected String camembertLibelle(Section entity) {
		return entity.getLibelle();
	}
	
	
}