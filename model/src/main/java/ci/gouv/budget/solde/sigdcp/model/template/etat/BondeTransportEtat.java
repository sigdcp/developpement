package ci.gouv.budget.solde.sigdcp.model.template.etat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.SituationMatrimoniale;
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
	private String chapitreimputation;
	private String articleimputation;
	private String paragrapheimputation;
	private String lieucertification;
	private String datecertification;
	private String certificateur;
	private String materieltransporte;
	private String montant;
	private InputStream codeBarre;

	public AgentEtat getBeneficiaire(){
		return piece.getDossier().getBeneficiaire();
	}
	
	public Deplacement getDeplacement(){
		return piece.getDossier().getDeplacement();
	}
	
	public DossierDD getDossier(){
		return (DossierDD) piece.getDossier();
	}
	public static Collection<BondeTransportEtat> test(){
		Collection<BondeTransportEtat> collection = new LinkedList<>();
		collection.add(build());
		return collection;
	}
	private static BondeTransportEtat build(){
		NatureDeplacement nd = new NatureDeplacement(new CategorieDeplacement("D", "Déplacement définitif"), "DD", "Affectation", 0);
		Deplacement deplacement = new Deplacement(null, new Date(), new Date(), null, nd, new Localite(null, "Abidjan", null, null), new Localite(null, "Bouake", null, null));
		AgentEtat agentEtat = new AgentEtat(null, "500500A", "Tata", "pion", null, null, Sexe.MASCULIN, new SituationMatrimoniale("M", "Marié"), null, null, null, null, null, 1035, null, new Section(null, "MAE", "Ministère des Affaires étrangères", null), null, null);
		
		DossierDD dossier = new DossierDD(null, null, deplacement, new Grade(null, null, "A1"), agentEtat, 500, null, null, null, null, null);
		PieceJustificative piece = new PieceJustificative(dossier, "123", null, new Date());
		try {
			return new BondeTransportEtat(piece, "AVIS DE FACTURATION", "DUPLICATA", "ARRETE DE NOMMINATION", "MFP/2014/02/01/12345467", "01/02/2014", "DIRECTEUR DE CABINET", "MIDID Transport", "SDDCP", "", "", "05/02/2014", "07/02/2014", "", "Budget général", "", "", "", "", "", "", "232", "0101101", "621010", "VAVOUA", "05/02/2014", "Sous-Prefet", "objet personnel", "452 325 FCFA",
					new FileInputStream("C:/Developpement/qrcode/qrcode-01.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
