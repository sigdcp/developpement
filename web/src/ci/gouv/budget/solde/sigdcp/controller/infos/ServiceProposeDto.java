package ci.gouv.budget.solde.sigdcp.controller.infos;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.WebConstantResources;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

@Getter @Setter
public class ServiceProposeDto implements Serializable  {

	private static final long serialVersionUID = -6544273335558518398L;

	private NatureDeplacement natureDeplacement;
	private String descriptionApercu,title,lirePlusRequestParamName,lirePlusRequestParamValue,outcome;
	private Boolean showDepotDossierButton=Boolean.TRUE,showSouscrireButton=Boolean.TRUE,showSolliciterButton=Boolean.TRUE;
	
	public ServiceProposeDto(NatureDeplacement natureDeplacement,WebConstantResources webConstantResources) {
		super();
		this.natureDeplacement = natureDeplacement;
		descriptionApercu = StringUtils.abbreviate(natureDeplacement.getDescription(), 255);
		title = natureDeplacement.getLibelle();
		lirePlusRequestParamName=webConstantResources.getRequestParamNatureDeplacement();
		lirePlusRequestParamValue = natureDeplacement.getCode();
		outcome="naturedepinfos";
	}

	public ServiceProposeDto(String title,String descriptionApercu, String lirePlusRequestParamName, String lirePlusRequestParamValue,
			String outcome, Boolean showDepotDossierButton,Boolean showSouscrireButton, Boolean showSolliciterButton) {
		super();
		this.descriptionApercu = descriptionApercu;
		this.title = title;
		this.lirePlusRequestParamName = lirePlusRequestParamName;
		this.lirePlusRequestParamValue = lirePlusRequestParamValue;
		this.outcome = outcome;
		this.showDepotDossierButton = showDepotDossierButton;
		this.showSouscrireButton = showSouscrireButton;
		this.showSolliciterButton = showSolliciterButton;
	}
	
	
	
}
