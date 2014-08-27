package ci.gouv.budget.solde.sigdcp.service.resources;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.dao.GenericDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou.Cause;
import ci.gouv.budget.solde.sigdcp.service.utils.TextService;

/**
 * Les constantes du systeme
 * @author christian
 *
 */
@Singleton @Named("constantResources") @Log
public class ServiceConstantResources implements Serializable{
	
	private static final long serialVersionUID = 1583754563831914427L;

	@Inject private TextService textService;
	@Inject private GenericDao genericDao;
	
	@Getter private final String fullDateTimePattern = "EEEE , dd/MM/yyyy HH:mm";
	@Getter private final String dateTimePattern = "dd/MM/yyyy HH:mm";
	@Getter private final String fullDatePattern = "EEEE , dd/MM/yyyy";
	@Getter private final String datePattern = "dd/MM/yyyy";
	
	@Getter private TypeAgentEtat fonctionnaire;
	
	@Getter private final String webRequestParamVerrouCode = "codever";
	@Getter private final String webRequestParamVerrouCause = "causever";
	@Getter private final String webRequestParamVerrouCauseAccessMultiple = Cause.ACCESS_MULTIPLE.name().toLowerCase();
	@Getter private final String webRequestParamVerrouCauseReinitialiserPassword = Cause.REINITIALISATION_PASSWORD.name().toLowerCase();
	
	@PostConstruct
	private void postConstruct(){
		fonctionnaire = genericDao.readByClass(TypeAgentEtat.class, Code.TYPE_AGENT_ETAT_FONCTIONNAIRE);
	}
	
	public String getWebRequestParamVerrouCause(Cause cause){
		if(Cause.ACCESS_MULTIPLE.equals(cause))
			return webRequestParamVerrouCauseAccessMultiple;
		if(Cause.REINITIALISATION_PASSWORD.equals(cause))
			return webRequestParamVerrouCauseReinitialiserPassword;
		log.info("Service Cannot Map "+cause+" to a web request param");
		return null;
	}
	
	public String text(String id){
		return textService.find(id);
	}
	
	public String dateAsString(Date date){
		if(date==null)
			return "";
		return new SimpleDateFormat(datePattern).format(date);
	}
	
}