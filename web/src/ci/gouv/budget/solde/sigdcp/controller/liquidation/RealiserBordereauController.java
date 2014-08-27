package ci.gouv.budget.solde.sigdcp.controller.liquidation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.service.dossier.BordereauTransmissionService;
import ci.gouv.budget.solde.sigdcp.service.dossier.BulletinLiquidationService;
import ci.gouv.budget.solde.sigdcp.service.resources.CRUDType;

@Named @ViewScoped
public class RealiserBordereauController extends AbstractValidationLiquidationController implements Serializable {

	private static final long serialVersionUID = -2412073347414420827L;
	
	@Inject private BordereauTransmissionService bordereauTransmissionService;
	@Inject private BulletinLiquidationService bulletinLiquidationService;
	
	private BordereauTransmission bt;
		
	@Override
	protected void initialisation() {
		calculerDisponible=true;
		super.initialisation();
		showCheckBox = false;
		showMontant = true;
		showTotalDepense = Boolean.TRUE;
		numeroLibelle=text("bulletin.liquidation.numero");
		addDetailsCommand.setRendered(Faces.isUserInRole(Code.ROLE_DIRECTEUR));
		removeDetailsCommand.setValue("Retirer");
		removeDetailsCommand.setRendered(isEditable() && Faces.isUserInRole(Code.ROLE_DIRECTEUR));
		
		if(Faces.isUserInRole(Code.ROLE_DIRECTEUR)){
			defaultSubmitCommand.setRendered(true);
		}
		defaultSubmitCommand.setRendered(isEditable());
		if(CRUDType.UPDATE.equals(crudType)){
			NatureOperation op = natureOperationService.findById(Code.NATURE_OPERATION_MODIFICATION_BTBL);
			title = op.getLibelle()+" - N° "+bt.getNumero();
		}
	}
	
	@Override
	protected String natureOperationCode() {
		return Code.NATURE_OPERATION_ETABLISSEMENT_BTBL;
	}
		
	@Override
	protected void onDefaultSubmitAction() throws Exception {
		bordereauTransmissionService.valider(Arrays.asList(bt));
		/*
		switch(crudType){
		case CREATE:bordereauTransmissionService.creer(natureDeplacement,list);break;
		case UPDATE:
			bordereauTransmissionService.modifier(bordereauTransmissionDto.getPiece(), list);
			break;
		default:break;
		}*/
	}
			
	@Override
	protected Collection<BulletinLiquidation> data(Collection<NatureDeplacement> natureDeplacements) {
		if(StringUtils.isEmpty(Faces.getRequestParameter(webConstantResources.getRequestParamBordereau()))){
			Collection<BordereauTransmission> bts = bordereauTransmissionService.findATraiter(natureDeplacements, natureOperationCode());
			bt = (bts==null||bts.isEmpty())?null:bts.iterator().next();
		}else{
			bt = bordereauTransmissionService.find(Long.parseLong(Faces.getRequestParameter(webConstantResources.getRequestParamBordereau())),natureOperationCode());
		}
		return bt==null?null:bt.getBulletinLiquidations();
	}
	
	@Override
	protected void onAddDetailsCommandAction() throws Exception {
		//System.out.println("RealiserBordereauController.onAddDetailsCommandAction() : "+numeroLigne);
		for(BulletinLiquidation dto : list)
			if(dto.getNumero().equals(numeroLigne))
				return;
	
		BulletinLiquidation bl = bulletinLiquidationService.findByNumero(numeroLigne,natureOperationCode());
		//System.out.println("Dossier : "+dossier);
		//System.out.println("Dto : "+dossierService.findDtoByDossier(dossier));
		if(disponible.subtract(bl.getMontant()).compareTo(BigDecimal.ZERO)>=0){
			list.add(bl);
			bt.getBulletinLiquidations().add(bl);
			numeroLigne = null;
			disponible = disponible.subtract(bl.getMontant());
			calculerTotalDepense();
		}else{
			/*
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Impossible d'ajouter ce bulletin de liquidation", "Le disponible courant ne vous permet d'ajouter ce bulletin de liquidation."
					+ "Montant du bulletin de liquidation : "+dto.getPiece().getMontant()+". Disponible courant : "+disponible);  
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
	        RequestContext.getCurrentInstance().execute("alert('Yes');");*/
		}
	}
	
	@Override
	protected void onRemoveDetailsCommandAction(BulletinLiquidation bulletinLiquidation) throws Exception {
		super.onRemoveDetailsCommandAction(bulletinLiquidation);
		disponible = disponible.add(bulletinLiquidation.getMontant());
		bt.getBulletinLiquidations().remove(bulletinLiquidation);
		calculerTotalDepense();
	}
	
	@Override
	public Boolean actionable(BulletinLiquidation entity) {
		return true;
	}
				
}
