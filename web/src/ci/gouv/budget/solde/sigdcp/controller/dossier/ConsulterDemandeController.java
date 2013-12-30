package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.flow.AbstractUIFlowController;
import ci.gouv.budget.solde.sigdcp.controller.flow.FlowDefinitions;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;
import ci.gouv.budget.solde.sigdcp.service.dossier.TraitementService;

@Named @FlowScoped(value=FlowDefinitions.FLOW_DEMANDE_CONSULTATION_ID)
public class ConsulterDemandeController extends AbstractUIFlowController implements Serializable {

	private static final long serialVersionUID = 1027310826427829321L;
	
	@Inject private DossierDDService dossierService;
	@Inject private TraitementService traitementService;
	
	@Getter private List<DossierDD> dossiers;
	@Getter private Dossier dossierSelectionne;
	
	/*
	 * Infos sur le dossier selectionné
	 */
	@Getter private Statut statut;
	@Getter private Date dateSoumission = new Date(),dateValidation=new Date();
	@Getter private List<Traitement> traitements;
	
	private String niveauCode;
	
	@PostConstruct
	public void postConstructConsulterDemandeController() {
		title = "Consultation des demandes";
		niveauCode = Faces.getRequestParameter("nc");
		dossiers = dossierService.findAll();
	}
	
	@Override
	protected void action() {
		super.action();
	}
	
	public String details(Dossier dossier) {
		//if(niveauCode==null || niveauCode.isEmpty() || "1".equals(niveauCode)){
			dossierSelectionne = dossier;
			traitements = new LinkedList<>(traitementService.findByDossier(dossierSelectionne));
			if(!traitements.isEmpty())
				statut = traitements.get(0).getStatut();
			return "demandeconsultationDetails";
		//}
		//return super.succes();
	}

}
