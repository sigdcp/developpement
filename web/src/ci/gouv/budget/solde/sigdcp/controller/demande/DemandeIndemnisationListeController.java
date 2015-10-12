package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEntityListUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;

@Named @ViewScoped //@Log
public class DemandeIndemnisationListeController extends AbstractEntityListUIController<Dossier> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject protected DossierService dossierService;
	
	@Setter @Getter private String solde=null;
 
	@Override
	protected void initialisation() {
		
		try {
			solde = Faces.getRequestParameter(webConstantResources.getRequestParamSolde());					
		} catch (NumberFormatException e) {}
		
		super.initialisation();
		
		title = text((solde==null)?"menu.consulter.demande":"menu.consulter.demande.solde");
		internalCode = "FS_DEM_01_Ecran_01";
		defaultSubmitCommand.setRendered(Boolean.FALSE);
		closeCommand.setRendered(Boolean.FALSE);
		listTitle="Liste des demandes";
		//nextViewOutcome = "demande";
		removeDetailsCommand.setRendered(true);
		removeDetailsCommand.setValue(text("bouton.annuler"));
		selectLabel="bouton.afficher";
		
		selectLabel="bouton.consulter";
		
	}
	
	@Override
	protected ProcessingType getProcessingType() {
		return ProcessingType.SINGLE;
	}
		
	@Override
	protected String identifierFieldName() {
		return "numero";
	}
	
	@Override
	protected List<Dossier> load() {
		LinkedList<Dossier> dossiers = new LinkedList<>();			
		if(solde!=null)dossiers.addAll(dossierService.findDemandesSolde());
		else dossiers.addAll(dossierService.findDemandes());
		return dossiers; 
	}
	
	@Override
	protected void onRemoveDetailsCommandAction(Dossier dossier) throws Exception {
		Faces.redirect(Faces.getServletContext().getContextPath()+"/"+_href(dossier,webConstantResources.getRequestParamCrudDelete()));
	}
	
	@Override
	public Boolean canRemove(Dossier dossier) {
		return !(dossier instanceof DossierMission) && actionable(dossier);
	}
	
	@Override
	public Boolean actionable(Dossier dossier){
		if(dossier.getTraitable().getNatureOperation()==null)
			return false;
		
		if(dossier instanceof DossierMission){
			if(((DossierMission)dossier).getDeplacement().getNature().getCode().equals(Code.NATURE_DEPLACEMENT_MISSION_HCI))
				return Code.NATURE_OPERATION_SAISIE.equals(dossier.getTraitable().getNatureOperation().getCode()) ||  
					Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_ORGANISATEUR.equals(dossier.getTraitable().getNatureOperation().getCode()) ||  
					Code.NATURE_OPERATION_RETOUR_FD.equals(dossier.getTraitable().getNatureOperation().getCode());
			else
				return Code.NATURE_OPERATION_SAISIE.equals(dossier.getTraitable().getNatureOperation().getCode()) || 
						Code.NATURE_OPERATION_SOUMISSION.equals(dossier.getTraitable().getNatureOperation().getCode())
					|| Code.NATURE_OPERATION_DEPOT.equals(dossier.getTraitable().getNatureOperation().getCode());	
		}else{
			return Code.NATURE_OPERATION_SAISIE.equals(dossier.getTraitable().getNatureOperation().getCode()) || 
					Code.NATURE_OPERATION_SOUMISSION.equals(dossier.getTraitable().getNatureOperation().getCode())
				|| Code.NATURE_OPERATION_DEPOT.equals(dossier.getTraitable().getNatureOperation().getCode());	
		}
	}
	
	@Override
	public String actionLabel(Dossier dossier){
		switch(dossier.getTraitable().getNatureOperation().getCode()){
		case Code.NATURE_OPERATION_SAISIE:case Code.NATURE_OPERATION_SOUMISSION:return "Modifier";
		case Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_ORGANISATEUR: return "Completer";
		case Code.NATURE_OPERATION_DEPOT: return "Déposer";
		case Code.NATURE_OPERATION_RETOUR_FD: return "Retourner feuille déplacement";
		}
		return null;
	}
	
	@Override
	public String actionHref(Dossier dossier){
		return _href(dossier, webConstantResources.getRequestParamCrudUpdate());
	}
	
	@Override
	protected void hrefParameters(Map<String, Object> parameters, Dossier dossier) {
		super.hrefParameters(parameters, dossier);
		parameters.put(webConstantResources.getRequestParamDossier(),dossier.getId());
	}
	
	@Override
	protected Object hrefObjectOutcome(Dossier entity) {
		return entity;
	}
		
}
