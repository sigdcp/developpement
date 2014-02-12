package ci.gouv.budget.solde.sigdcp.controller.infos;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.WebConstantResources;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import lombok.Getter;
import lombok.Setter;

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
		
		if(Code.CATEGORIE_DEPLACEMENT_DEFINITIF.equals(natureDeplacement.getCategorie().getCode()))
			outcome = "demande_dd";
		else if(Code.CATEGORIE_DEPLACEMENT_MISSION.equals(natureDeplacement.getCategorie().getCode()))
			outcome = "demande_m";
		else if(Code.CATEGORIE_DEPLACEMENT_OBSEQUE.equals(natureDeplacement.getCategorie().getCode()))
			outcome = "demande_o";
		else if(Code.CATEGORIE_DEPLACEMENT_TRANSIT.equals(natureDeplacement.getCategorie().getCode()))
			outcome = "demande_t";
		else if(Code.CATEGORIE_DEPLACEMENT_TRANSPORT_URBAIN.equals(natureDeplacement.getCategorie().getCode()))
			outcome = "demande_dd";
		
		if(Code.NATURE_DEPLACEMENT_OBSEQUE_FRAIS.equals(natureDeplacement.getCode())){
			showDepotDossierButton = Boolean.FALSE;
			showSouscrireButton=Boolean.FALSE;
		}else if(Code.NATURE_DEPLACEMENT_TRANSPORT_CARTE_SOTRA.equals(natureDeplacement.getCode())){
			showDepotDossierButton = Boolean.FALSE;
			//showSouscrireButton=Boolean.FALSE;
		}
	}
	
	
	
}
