package ci.gouv.budget.solde.sigdcp.service.resources;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.service.TextService;
import lombok.Getter;

/**
 * Les constantes du systeme
 * @author christian
 *
 */
@Singleton @Named("constantResources")
public class ServiceConstantResources implements Serializable{
	
	private static final long serialVersionUID = 1583754563831914427L;

	@Inject private TextService textService;
	
	/**/
	@Getter private final String statutSoumis = Code.STATUT_SOUMIS;
	@Getter private final String statutRecevable = Code.STATUT_RECEVABLE;
	@Getter private final String statutConforme = Code.STATUT_CONFORME;
	@Getter private final String statutLiquide = Code.STATUT_LIQUIDE;
	@Getter private final String statutRegle = Code.STATUT_REGLE;
	/**/
	
	@Getter private String fullDateTimePattern = "EEEE , dd/MM/yyyy HH:mm";
	@Getter private String dateTimePattern = "dd/MM/yyyy HH:mm";
	@Getter private String fullDatePattern = "EEEE , dd/MM/yyyy";
	@Getter private String datePattern = "dd/MM/yyyy";
	
	@Getter private String matriculePattern = "\\d\\d\\d\\d\\d\\d[a-zA-Z]";
	
	@Getter private Integer AgeMinimumAns = 19;
	
	public Date getDateNaissanceMinimum(){
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.YEAR, -AgeMinimumAns);
		return calendar.getTime();
	}
	
	public Date getDateMaximum(){
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.YEAR, -100);
		return calendar.getTime();
	}
	
	public String text(String id){
		return textService.find(id);
	}
	
}