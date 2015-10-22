package ci.gouv.budget.solde.sigdcp.controller.liquidation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.identification.Fonction;
import ci.gouv.budget.solde.sigdcp.model.identification.Profession;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Groupe;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeDD;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteCalculee;
import ci.gouv.budget.solde.sigdcp.service.dossier.BulletinLiquidationService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierService;
import ci.gouv.budget.solde.sigdcp.service.indemnite.IndemniteCalculateurService;

@Getter @Setter @Named @ViewScoped
public class LiquiderController extends AbstractEntityFormUIController<BulletinLiquidation> implements Serializable {

	private static final long serialVersionUID = -6419775515441198968L;

	@Inject private BulletinLiquidationService bulletinLiquidationService;
	@Inject private IndemniteCalculateurService indemniteCalculateurService;
	@Inject private DossierService dossierService;
	
	private FormCommand<BulletinLiquidation> calculerCommand;
	private BigDecimal totalAPayer=new BigDecimal(0);
	private Collection<Groupe> groupes = new ArrayList<>();
	private Fonction fonction;
	private Profession profession;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		entity = bulletinLiquidationService.nouveau(dossierService.find(requestParameterLong(webConstantResources.getRequestParamDossier()),Code.NATURE_OPERATION_ETABLISSEMENT_BL) );
		
		System.out.println("LiquiderController.initialisation() ZZZZZZ "+entity.getNumero());
		
		if(entity.getDossier().getBulletinLiquidations().isEmpty()){
			calculerCommand = createCommand();
			calculerCommand.setValue("Calculer");
			calculerCommand.set_action(new Action() {
				private static final long serialVersionUID = 4833259507611607789L;
				@Override
				protected Object __execute__(Object object) throws Exception {
					System.out
							.println("LiquiderController.initialisation().new Action() {...}.__execute__()");
					indemniteCalculateurService.calculer(entity);
					calculerTotalPaye();
					return null;
				}
			});
			calculerCommand.getObjectValidators().clear();
			calculerCommand.setUpdate("@form");
			
			calculerCommand.onSuccessStayOnCurrentView();
		}
		
		title=entity.getTraitable().getNatureOperation().getLibelle()+" - Demande N° "+entity.getDossier().getNumero();
		defaultSubmitCommand.setRendered(true);
		defaultSubmitCommand.setValue("Liquider");
		defaultSubmitCommand.onSuccessGoTo(navigationManager.url("mettreenliquidation",null,false), "", null);
		calculerTotalPaye();
		
		if(entity.getDossier() instanceof DossierDD){
			groupes.addAll(genericService.findAllByClass(GroupeDD.class));
		}else if(entity.getDossier() instanceof DossierMission){
			groupes.addAll(genericService.findAllByClass(GroupeMission.class));
			fonction = ((DossierMission)entity.getDossier()).getFonction();
			profession = ((DossierMission)entity.getDossier()).getProfession();
		}
		
		entity.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
		
	}
	
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		super.onDefaultSubmitAction();
		bulletinLiquidationService.valider(Arrays.asList(entity));
	}
	
	private void calculerTotalPaye(){
		totalAPayer=new BigDecimal(0);
		for(IndemniteCalculee indemniteCalculee : entity.getIndemniteCalculees())
			totalAPayer = totalAPayer.add(indemniteCalculee.getMontant());
	}
	
}
