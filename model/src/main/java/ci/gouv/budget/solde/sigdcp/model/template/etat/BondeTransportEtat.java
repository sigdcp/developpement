package ci.gouv.budget.solde.sigdcp.model.template.etat;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class BondeTransportEtat implements Serializable {
	
	private static final long serialVersionUID = 5852801302145957039L;
	
	private PieceJustificative piece;
	
	private String libdoc;
	private String statutdoc;
	private String ordreservice;
	private String numeroordreservice;
	private String dateordreservice;
	private String ordonateurservice;
	
	private String transporteur;
	private String proposantservice;
	private String recepteurservice;
	private String daterecepteurservice;
	private String dateremistransporteur;
	private String datesignature;
	private String lieusignature;
	private String imputationbudgetaire;
	private String autorisationengagement;
	private String engagementanterieur;
	private String annulationanterieur;
	private String restedisponible;
	private String codeservice;
	private String date;
	private String numerode;
	private String chapitreimputation;
	private String articleimputation;
	private String paragrapheimputation;
	private String lieucertification;
	private String datecertification;
	private String certificateur;
	
	private String materieltransporte;
	private String montant;

}
