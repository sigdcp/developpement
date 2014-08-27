package ci.gouv.budget.solde.sigdcp.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;

import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractUIController;

@Named @ViewScoped @Getter @Log
public class ActionMessageController extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = -4718035679410781214L;

	private String title,message,href,template;
	@Inject private NavigationManager navigationManager;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title = Faces.getRequestParameter(webConstantResources.getRequestParamMessageTitle());
		template = webConstantResources.getPageTemplate();
		if(StringUtils.isEmpty(title))
			title = "Résultat de l'opération";
		href = decodeParam(webConstantResources.getRequestParamUrl(),navigationManager.url("espacePrivee", null, false, false));
		String parametres=decodeParam(webConstantResources.getRequestParamMessageParameters(),"");
		
		String messageId = Faces.getRequestParameter(webConstantResources.getRequestParamRuntimeMessageId());
		if(StringUtils.isEmpty(messageId)){
			messageId = Faces.getRequestParameter(webConstantResources.getRequestParamMessageId());
			if(StringUtils.isEmpty(messageId))
				message="Opération bien effectuée"; //"Votre opération a été réalisée avec succès.";
			else
				try {
					message = text(messageId,StringUtils.isEmpty(parametres)?null:new Object[]{parametres});
				} catch (Exception e) {
					message = messageId;
				}
		}else
			message = runtimeMessage(messageId);
		
		//it overrides all messages
		//message = "Opération bien effectuée";
	}
	
	private String decodeParam(String id,String _default){
		id = Faces.getRequestParameter(id);
		if(StringUtils.isNotEmpty(id))
			try {
				return URLDecoder.decode(id,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.log(Level.SEVERE,e.toString(),e);
			}
		return _default;
	}
	
	public void close(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
}
