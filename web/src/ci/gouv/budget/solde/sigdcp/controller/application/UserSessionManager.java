package ci.gouv.budget.solde.sigdcp.controller.application;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.omnifaces.util.Faces;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.service.utils.TextService;

@Named @SessionScoped 
public class UserSessionManager implements Serializable{

	private static final long serialVersionUID = 258649685790992448L;

	/*
	 * Services
	 */
	@Inject transient private TextService textService;
	@Inject transient private RoleManager roleManager;
	@Getter @Setter private CompteUtilisateur compteUtilisateur; 
	@Getter private Boolean isIvoirien;
	/*------------------------------------- UI ----------------------------------------------------*/
	
	@Getter private Integer uiMenuTabLiquidationEtablirIndex=0;
	@Getter private Integer uiMenuTabLiquidationValiderIndex=0;
	//@Getter private Integer uiMenuTabLiquidationTransmettreIndex=0;
	@Getter private Integer uiMenuTabLiquidationViserIndex=0;
	@Getter private Integer uiMenuTabLiquidationProduireBTIndex=0;
	@Getter private Integer uiMenuTabLiquidationValiderBTIndex=0;
	@Getter private Integer uiMenuTabLiquidationTransmettreBTIndex=0;
	
	@Getter private Integer uiMenuTabRemboursementEtablirIndex=0;
	@Getter private Integer uiMenuTabRemboursementValiderIndex=0;
	@Getter private Integer uiMenuTabRemboursementTransmettreIndex=0;
	@Getter private Integer uiMenuTabRemboursementReceptionnerIndex=0;
	
	@Getter private Integer uiMenuTabReceptionRecIndex=0;
	@Getter private Integer uiMenuTabReceptionConformeIndex=0;
	@Getter private Integer uiMenuTabReceptionFdIndex=0;
	@Getter private Integer uiMenuTabReceptionFactureIndex=0;
		
	@Getter private Integer uiMenuTabPrestationCotationMhciIndex=0;
	@Getter private Integer uiMenuTabPrestationCotationFoIndex=0;
	@Getter private Integer uiMenuTabPrestationCommandeMhciIndex=0;
	@Getter private Integer uiMenuTabPrestationCommandeFoIndex=0;
	@Getter private Integer uiMenuTabPrestationValiderCommandeTTIndex=0;
	@Getter private Integer uiMenuTabPrestationValiderCommandeFOIndex=0;
	
	@PostConstruct
	private void postConstruct(){
		
		initUiTabMenuLiquidationIndex();
		initUiTabMenuRemboursementIndex();
		initUiTabMenuReceptionIndex();
		initUiTabMenuPrestationIndex();
	}
	
	public Boolean isLoggedIn(){
		return StringUtils.isNotEmpty(Faces.getRemoteUser());
	}
	
	/**
	 * The connected user
	 * @return
	 */
	@Named @SessionScoped @User
	public Personne getUser(){
		return compteUtilisateur==null?null:(Personne) compteUtilisateur.getUtilisateur();
	}
	
	public AgentEtat getUserAsAgentEtat(){
		return compteUtilisateur==null?null:compteUtilisateur.getUtilisateur() instanceof AgentEtat?
				(AgentEtat) compteUtilisateur.getUtilisateur():null;
	}
	
	public Prestataire getUserAsPrestataire(){
		return compteUtilisateur==null?null:compteUtilisateur.getUtilisateur() instanceof Prestataire?
			(Prestataire) compteUtilisateur.getUtilisateur():null;
	}
	
	public boolean isAgentEtat(Personne personne){
		return personne instanceof AgentEtat;
	}
	
	public String getUserInfosLine(){
		System.out.println(ToStringBuilder.reflectionToString(compteUtilisateur.getUtilisateur(), ToStringStyle.MULTI_LINE_STYLE));
		if(compteUtilisateur==null)
			return null;
		if(compteUtilisateur.getUtilisateur() instanceof Personne)
			isIvoirien = ((Personne)compteUtilisateur.getUtilisateur()).getNationalite().getCode().equals(Code.LOCALITE_COTE_DIVOIRE); 
		if(compteUtilisateur.getUtilisateur() instanceof AgentEtat){
			/*return textService.find("userinfos.top.agentetat",new Object[]{((AgentEtat)compteUtilisateur.getUtilisateur()).getMatricule(),
					((AgentEtat)compteUtilisateur.getUtilisateur()).getNomPrenoms()});*/
			return textService.find("userinfos.top.agentetat",new Object[]{((AgentEtat)compteUtilisateur.getUtilisateur()).getNomPrenoms()});
		}else if(compteUtilisateur.getUtilisateur() instanceof Personne){
			return textService.find("userinfos.top.personne",new Object[]{((Personne)compteUtilisateur.getUtilisateur()).getNomPrenoms()});
		}else if(compteUtilisateur.getUtilisateur() instanceof Prestataire){
			return textService.find("userinfos.top.prestataire",new Object[]{((Prestataire)compteUtilisateur.getUtilisateur()).getNom()});
		}
		return null;
	}
	
	public String getAccueil(){
		if(roleManager.isAdministrateur())
			return "espacePriveeAdmin";
		else if(roleManager.isDirecteur())
			return "viserbulletinliquidation";
		else if(roleManager.isResponsable())
			return "validationrecevabilite";
		else if(roleManager.isLiquidateur())
			return "mettreenliquidation";
		else if(roleManager.isPointFocal())
			return "missionexecuteeliste";
		else if(roleManager.isPayeur())
			return "priseenchargebordereau";
		else if(roleManager.isAgentEtat())
			return "espacePrivee"; 
		else if(roleManager.isAyantDroit())
			return "espacePrivee"; 
		else if(roleManager.isPrestataire())
			return "espacePriveePrestataire"; 
		else if(roleManager.isAgentMission())
			return "espacePriveeAgentMission"; 
		return "index";
	}
	
	public void onNodeExpand(NodeExpandEvent event) {  
        //System.out.println("UserSessionManager.onNodeExpand() : "+event.getTreeNode().isExpanded());
        event.getTreeNode().setExpanded(true);
    }  
  
    public void onNodeCollapse(NodeCollapseEvent event) {  
         //System.out.println("UserSessionManager.onNodeCollapse() : "+event.getTreeNode().isExpanded());
         event.getTreeNode().setExpanded(false);
    }  

	/**/
	
	private void initUiTabMenuLiquidationIndex(){
		int l=0;
		//tab 1
		if(roleManager.isLiquidateur())
			uiMenuTabLiquidationEtablirIndex=l++;
		//tab 2
		if(roleManager.isResponsable())
			uiMenuTabLiquidationValiderIndex=l++; 
		//tab 3
		//if(roleManager.isResponsable())
		//	uiMenuTabLiquidationTransmettreIndex=l++;
		//tab 4
		if(roleManager.isDirecteur())
			uiMenuTabLiquidationViserIndex=l++;
		//tab 5
		if(roleManager.isResponsable())
			uiMenuTabLiquidationProduireBTIndex=l++;
		//tab 6
		if(roleManager.isDirecteur())
			uiMenuTabLiquidationValiderBTIndex=l++;
		//tab 7
		if(roleManager.isDirecteur())
			uiMenuTabLiquidationTransmettreBTIndex=l++;
	}
	
	private void initUiTabMenuRemboursementIndex(){
		int l=0;
		//tab 1
		if(roleManager.isLiquidateur())
			uiMenuTabRemboursementEtablirIndex=l++;
		//tab 2
		if(roleManager.isResponsable())
			uiMenuTabRemboursementValiderIndex=l++; 
		//tab 3
		if(roleManager.isDirecteur())
			uiMenuTabRemboursementTransmettreIndex=l++;
		//tab 4
		if(roleManager.isLiquidateur())
			uiMenuTabRemboursementReceptionnerIndex=l++;
	}
	
	private void initUiTabMenuReceptionIndex(){
		int l=0;
		//tab 1
		//if(roleManager.isResponsable())
		uiMenuTabReceptionRecIndex=l++;
		//tab 2
		uiMenuTabPrestationCotationFoIndex=l++;
		//tab 3
		uiMenuTabReceptionFdIndex=l++;
		//tab 4
		uiMenuTabReceptionFactureIndex=l++;
		
	}
	
	private void initUiTabMenuPrestationIndex(){
		int l=0;
		//tab 1	
	//if(roleManager.isResponsable())
		uiMenuTabPrestationCotationMhciIndex=l++;
	//tab 2
		uiMenuTabPrestationCommandeMhciIndex=l++;
		//tab 3	
	//if(roleManager.isResponsable())
		uiMenuTabPrestationCotationFoIndex=l++;
	//tab 4
		uiMenuTabPrestationCommandeFoIndex=l++;
	//tab 5	
		uiMenuTabPrestationValiderCommandeTTIndex=l++;
	//tab 6	
		uiMenuTabPrestationValiderCommandeFOIndex=l++;
		
		
	}
	
}
