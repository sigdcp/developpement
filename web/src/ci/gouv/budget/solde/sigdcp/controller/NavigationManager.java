package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.model.prestation.DemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.service.utils.NavigationHelper;

@Log
public class NavigationManager implements Serializable {
	
	private static final long serialVersionUID = 4432678991321751425L;
	
	/**
	 * 
	 */
	private static final String OUTCOME_NOT_FOUND = "outcomenotfound";
	/**
	 * We stay on the same view after action
	 */
	public static final String OUTCOME_CURRENT_VIEW = null;
	/**
	 * We move to the success view after action
	 */
	public static final String OUTCOME_SUCCESS_VIEW = "succes";
	
	private static final String QUERY_PARAMETER_FACES_REDIRECT_NAME = "faces-redirect";
	private static final String FILE_STATIC_EXTENSION = ".xhtml";
	private static final String FILE_PROCESSING_EXTENSION = ".jsf";
	
	@Inject private NavigationHelper navigationHelper;
		
	public String url(String id,Object[] parameters,Boolean actionOutcome,Boolean partial){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationCase navigationCase = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler()).getNavigationCase(facesContext, null, id);
		//System.out.println(id+" / "+navigationCase);
		if(navigationCase==null){
			log.severe("No Navigation Case found for "+id);
			return url(OUTCOME_NOT_FOUND, new Object[]{"oc",id});
		}
		String s = navigationCase.getToViewId(facesContext);
		StringBuilder url;
		if(Boolean.TRUE.equals(actionOutcome))
			url = new StringBuilder(s);
		else
			url = new StringBuilder(StringUtils.replace(s, FILE_STATIC_EXTENSION, FILE_PROCESSING_EXTENSION));
	    
		if(Boolean.TRUE.equals(actionOutcome))
	    	navigationHelper.addParameter(url, QUERY_PARAMETER_FACES_REDIRECT_NAME, navigationCase.isRedirect());
	    if(parameters!=null && parameters.length>0){
	    	for(int i=0;i<parameters.length-1;i=i+2)
				navigationHelper.addParameter(url, (String) parameters[i], parameters[i+1]);
	    }
	    if(Boolean.TRUE.equals(partial))
	    	return url.toString();
	    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath()+url;
	}
	
	public String url(String id,Object[] parameters,Boolean actionOutcome){
		return url(id, parameters, actionOutcome, Boolean.TRUE);
	}
	
	public String url(/*FacesContext facesContext,*/String id,Object[] parameters){
		return url(/*facesContext,*/id, parameters, Boolean.TRUE);
	}
	
	public String url(/*FacesContext facesContext,*/String id,Boolean actionOutcome){
		return url(/*facesContext,*/id, null,actionOutcome);
	}
	
	public String url(String id){
		return url(id, Boolean.TRUE);
	}
	

	
	public String getRequestUrl(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//System.out.println(request.getQueryString());
		String url = request.getRequestURL().toString();
		if(StringUtils.isNotEmpty(request.getQueryString()))
			url += NavigationHelper.QUERY_START+request.getQueryString();
		return url;
	}
	
	public String outcome(Object object){
		if(object==null)
			throw new IllegalArgumentException();
		if(object instanceof Dossier){
			String id = "demande_";
			if(object instanceof DossierDD)
				return id+"dd";
			if(object instanceof DossierTransit)
				return id+"t";
			if(object instanceof DossierObseques)
				return id+"o";
			if(object instanceof DossierMission)
				if(((DossierMission)object).getDeplacement().getNature().getCode().equals(Code.NATURE_DEPLACEMENT_MISSION_HCI))
					return id+"m";
				else
					return id+"tt";
		}
		if(object instanceof MissionExecutee)
			return "demande_mission_pointfocal";
		if(object instanceof CompteUtilisateur)
			return "compteutilisateur_form";
		if(object instanceof DemandeCotationMission)
			return "demande_cotation_mhci_form";
		if(object instanceof CommandeCarteSotra)
			return "consulterlisteacheteurs";
		if(object instanceof DemandeCotationMission)
			return "demande_cotation_mhci_form";
		if(object instanceof DemandeCotationObseque)
			return "demande_cotation_fo_form";
		if(object instanceof CommandeTitreTransport)
			return "generer_commande_mhci_form";
		log.severe("No outcome can be derived from object of type <<"+object.getClass()+">>");
		return null;	
	}
	
	
	


}
