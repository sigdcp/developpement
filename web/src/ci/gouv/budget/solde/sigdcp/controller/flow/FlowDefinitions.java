/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.gouv.budget.solde.sigdcp.controller.flow;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.faces.flow.builder.ViewBuilder;

public class FlowDefinitions implements Serializable {
    
	private static final long serialVersionUID = -3203191428293179877L;
	
	/* Identifiers = nom de repertoire*/
	public static final String FLOW_VALIDATION_INSCRIPTION_ID = "validationinscription";
	public static final String FLOW_VALIDATION_INSCRIPTION_CONFIRMATION_ID = "validationinscriptionConfirmation";
	
	public static final String FLOW_INSCRIPTION_PERSONNE_ID = "inscriptionpersonne";
	
	public static final String FLOW_DEMANDE_DD_ID = "demandedd";
	public static final String FLOW_DEMANDE_FO_ID = "demandefo";
	public static final String FLOW_DEMANDE_MHCI_ID = "demandemhci";
	public static final String FLOW_DEMANDE_TR_ID = "demandetr";
	
	public static final String FLOW_DEMANDE_CONSULTATION_ID = "demandeconsultation";
	
	private static final String FLOWS_FOLDER = "/flows/";
	/*
	@Produces @FlowDefinition
    public Flow inscriptionPersonne(@FlowBuilderParameter FlowBuilder flowBuilder) {
		return define(flowBuilder, FLOW_INSCRIPTION_PERSONNE_ID,"/identification/flows/", new String[]{"","NatDepInfos","Confirmation"}, "inscriptionPersonne");
    }
	
	@Produces @FlowDefinition
    public Flow validationInscription(@FlowBuilderParameter FlowBuilder flowBuilder) {
		return define(flowBuilder, FLOW_VALIDATION_INSCRIPTION_ID, "/identification/flows",new String[]{"","Confirmation"}, "validationInscription");
    }
	
	
	@Produces @FlowDefinition
    public Flow demandeDD(@FlowBuilderParameter FlowBuilder flowBuilder) {
		return define(flowBuilder, FLOW_DEMANDE_DD_ID, "/dossier/flows/",new String[]{"","PiecesJustificative","EditerFD","EditerBT"}, "enregistrerDemandeDD");
    }
	
	@Produces @FlowDefinition
    public Flow demandeTR(@FlowBuilderParameter FlowBuilder flowBuilder) {
		return define(flowBuilder, FLOW_DEMANDE_TR_ID, "/dossier/flows/",new String[]{"","PiecesJustificative"}, "enregistrerDemandeTRForm");
    }
	
	@Produces @FlowDefinition
    public Flow demandeFO(@FlowBuilderParameter FlowBuilder flowBuilder) {
		return define(flowBuilder, FLOW_DEMANDE_FO_ID, "/dossier/flows/",new String[]{"","Defunt","Deces","Pieces","Confirmation"}, "enregistrerDemandeFOForm");
    }
	
	@Produces @FlowDefinition
    public Flow demandeMHCI(@FlowBuilderParameter FlowBuilder flowBuilder) {
		return define(flowBuilder, FLOW_DEMANDE_MHCI_ID, "/dossier/flows/",new String[]{"","PiecesJustificative"}, "enregistrerDemandeMHCIForm");
    }
	
	@Produces @FlowDefinition
    public Flow demandeConsultation(@FlowBuilderParameter FlowBuilder flowBuilder) {
		return define(flowBuilder, FLOW_DEMANDE_CONSULTATION_ID, "/dossier/flows/",new String[]{"","Details"}, "consulterDemande");
    }
	*/
	private Flow define(FlowBuilder flowBuilder,String aFlowId,String viewFolder,String[] viewSuffixes,String returnNodeId,String fromOutcome) {
        flowBuilder.id("", aFlowId);
        boolean startNode = false;
        ViewBuilder viewBuilder;
        for(String nodeId : viewSuffixes){
        	viewBuilder =	addViewNode(flowBuilder,aFlowId+nodeId,viewFolder + aFlowId + "/" + aFlowId+nodeId+".xhtml");
        	if(!startNode){
        		viewBuilder.markAsStartNode();	
        		startNode=true;
        	}
        }
        addViewNode(flowBuilder,"succes",viewFolder + aFlowId + "/" + aFlowId+"Succes"+".xhtml");
        //addViewNode(flowBuilder, "succes", "/succes.xhtml");
        flowBuilder.returnNode(returnNodeId).fromOutcome(fromOutcome);
        return flowBuilder.getFlow();
    }
	
	private ViewBuilder addViewNode(FlowBuilder flowBuilder, String aFlowId,String vdlDocumentId){
		return flowBuilder.viewNode(aFlowId,vdlDocumentId);
	}
	
	private Flow define(FlowBuilder flowBuilder,String aFlowId,String viewFolder,String[] viewSuffixes,String aControllerPrefixName) {
		return define(flowBuilder, aFlowId, viewFolder,viewSuffixes, aFlowId+"ReturnFromFlow", 
				"#{"+aControllerPrefixName+"Controller.returnValue}");
	}
	
}
