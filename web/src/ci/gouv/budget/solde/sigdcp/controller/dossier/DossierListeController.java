package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;

@Named @ViewScoped
public class DossierListeController extends AbstractFormUIController<Object> implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;

	/*
	 * Services
	 */
	@Inject private DossierService dossierService;
	
	/*
	 * Dto
	 */
	@Getter private List<Dossier> list;
	//recherches
	@Getter @Setter Date dateDebut,dateFin;
	@Getter @Setter NatureDeplacement natureDeplacement;
	/*
	 * Paramètres url
	 */
	@Getter @Setter private Statut statut;
	@Getter @Setter private String nextViewOutcome;
	
	
	@Getter private FormCommand<Object> rechercherCommande;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = text("dossierliste");
		internalCode = "FS_DEM_01_Ecran_01";
		defaultSubmitCommand.setRendered(Boolean.FALSE);
		closeCommand.setRendered(Boolean.FALSE);
		rechercherCommande = createCommand();
		rechercherCommande.setValue(text("bouton.rechercher"));
		rechercherCommande.setIcon("ui-icon-search");
		rechercherCommande.set_action( new Action() {
			private static final long serialVersionUID = -5307893903678626614L;
			@Override
			protected void __execute__() throws Exception {
				// TODO Auto-generated method stub
			}
		} );
		list = new LinkedList<>(dossierService.findByStatut(statut));
	}
	
	public String href(Dossier dossier){
		String outcome = null;
		if(dossier instanceof DossierDD)
			outcome = nextViewOutcome+"dd";
		outcome = navigationManager.url(outcome);
		
		//System.out.println(navigationManager.addQueryParameters(outcome, 
		//		new String[]{webConstantResources.getRequestParamDossier(),dossier.getNumero(),webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudUpdate()}));
		return navigationManager.addQueryParameters(outcome, 
				new String[]{webConstantResources.getRequestParamDossier(),dossier.getNumero(),webConstantResources.getRequestParamCrudType(),webConstantResources.getRequestParamCrudRead()});
	}
	
	
}
