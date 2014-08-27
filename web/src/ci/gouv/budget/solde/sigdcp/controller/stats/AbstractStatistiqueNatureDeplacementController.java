package ci.gouv.budget.solde.sigdcp.controller.stats;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.stats.DeplacementStatistiques;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;
import ci.gouv.budget.solde.sigdcp.service.dossier.NatureDeplacementService;

public abstract class AbstractStatistiqueNatureDeplacementController extends StatistiqueDeplacementController<NatureDeplacement> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Inject private DossierService dossierService;
	@Inject private NatureDeplacementService natureDeplacementService;
	
	@Getter @Setter protected List<NatureDeplacement> natureDeplacements,natureDeplacementSelectionnees;
	@Getter @Setter protected List<NatureOperation> natureOperations;
	@Getter @Setter protected NatureOperation natureOperaitonSelectionnee;
	@Getter @Setter protected List<Statut> statuts;
	@Getter @Setter protected Statut statutSelectionne;
	@Getter private List<Dossier> /*dossiers,*/ detailObjets;
	
	@Getter @Setter protected String libelleDetailObjet;

	@Getter @Setter private BigDecimal montantTotalIndemnite=new BigDecimal(0),montantTotalTransport=new BigDecimal(0);
	
	@Getter @Setter protected boolean showTransport;
	
	@Override
	protected void initialisation() {
		
		super.initialisation();
		title="Statistique des déplacements définitifs";
		
		showOperationCritere=true;
		natureDeplacements = new ArrayList<>(genericService.findAllByClass(NatureDeplacement.class));
		
		//natureOperations = new ArrayList<>(genericService.findAllByClass(NatureOperation.class));
		natureOperations = Arrays.asList(
				genericService.findByClass(NatureOperation.class, Code.NATURE_OPERATION_RECEVABILITE),
				genericService.findByClass(NatureOperation.class, Code.NATURE_OPERATION_CONFORMITE)
				);
		
		statuts = new ArrayList<>(genericService.findAllByClass(Statut.class));
		
		pcolCritere=5;
		defaultSubmitCommand.setRendered(false);
		
		libelleDetailObjet="Dossiers";
	}
	
	public void detailAction(){
		
		if(natureDeplacementSelectionnees==null || natureDeplacementSelectionnees.isEmpty() || natureOperaitonSelectionnee==null || statutSelectionne==null)
		return ;
	
		detailObjets = new ArrayList<>(dossierService. findStatistiqueDetailByNatureDeplacementsByNatureOperationByStatut(natureDeplacementSelectionnees,  natureOperaitonSelectionnee, statutSelectionne));
		
		for(Dossier dossier : detailObjets){
			dossierService.init(dossier, null);
			if(dossier.getMontantIndemnisation()!=null)
			montantTotalIndemnite=dossier.getMontantIndemnisation().add(montantTotalIndemnite);
			
			if(dossier instanceof DossierMission)
				montantTotalTransport=((DossierMission)dossier).getFrais().getTransport().add(montantTotalTransport);
		}
		
	}


	@Override
	protected DeplacementStatistiques<NatureDeplacement> calculeSatistique() {
		
		if(natureDeplacementSelectionnees==null || natureDeplacementSelectionnees.isEmpty() || natureOperaitonSelectionnee==null || statutSelectionne==null)
			return null;
		return natureDeplacementService.findStatistiqueByNatureOperationByStatut(natureDeplacementSelectionnees, dateDebut, dateFin,  natureOperaitonSelectionnee, statutSelectionne);
		
	}


	@Override
	protected String camembertLibelle(NatureDeplacement entity) {
		return entity.getLibelle();
	}
	
}
