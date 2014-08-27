package ci.gouv.budget.solde.sigdcp.model.template.etat;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.SituationMatrimoniale;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BulletinLiquidationDDEtat implements Serializable {

	private static final long serialVersionUID = -5973562425199996115L;
	
	private PieceJustificative decisionAffectation;
	private PieceJustificative feuilleDeplacement;
	private PieceProduite piece;
	private String libdoc;
	private String statutdoc;
	private Integer nbrEnfant;
	private Integer distance;
	private String groupe;
	private Integer nbrjrIndemnitekm;
	private Integer nbrjrIndemnitejournaliere;
	private String formuleIKP;
	private String resultatIKP;
	private String formuleIBA;
	private String resultatIBA;
	private String formuleIBC;
	private String resultatIBC;
	private String formuleIBE;
	private String resultatIBE;
	private String formuleIJA;
	private String resultatIJA;
	private String formuleIJC;
	private String resultatIJC;
	private String formuleIJE;
	private String resultatIJE;
	private String montantchiffre;
	private String montantlettre;
		
	public AgentEtat getBeneficiaire(){
		return getDossier().getBeneficiaire();
	}
	
	public Deplacement getDeplacement(){
		return decisionAffectation.getDossier().getDeplacement();
	}
	
	public DossierDD getDossier(){
		return (DossierDD) decisionAffectation.getDossier();
	}
	
	public static Collection<BulletinLiquidationDDEtat> test(){
		Collection<BulletinLiquidationDDEtat> collection = new LinkedList<>();
		collection.add(build());
		return collection;
	}
	
	private static BulletinLiquidationDDEtat build(){
		NatureDeplacement nd = new NatureDeplacement(new CategorieDeplacement("D", "Déplacement définitif"), "DD", "Affectation", 0);
		
		Deplacement deplacement = new Deplacement(null, new Date(), new Date(), null, nd, new Localite(null, "Abidjan", null, null), new Localite(null, "Bouake", null, null));
		AgentEtat agentEtat = new AgentEtat(null, "500500A", "Tata", "pion", null, null, Sexe.MASCULIN, new SituationMatrimoniale("M", "Marié"), null, null, null, null, null, 1035, null, new Section(null, "MAE", "Ministère des Affaires étrangères", null), null, null);
		DossierDD dossier = new DossierDD(null, null, deplacement, new Grade(null, null, "A1"), agentEtat, 500, null, null, null, null, null);
		PieceJustificative decisionAffectation = new PieceJustificative(dossier, "52654", null, new Date());
		PieceJustificative feuilleDeplacement = new PieceJustificative(dossier, "123", null, new Date());
		PieceProduite piece = new PieceProduite("23654725", null, null);
		return new BulletinLiquidationDDEtat(decisionAffectation, feuilleDeplacement, piece, "BULLETIN DE LIQUIDATION DES INDEMNITES DE DEPLACEMENT DEFINITIF", "DUPLICATA", 5, 653, "Groupe I", 2, 10, "formuleIKP", "resultatIKP", "formuleIBA", "resultatIBA", "formuleIBC", "resultatIBC", "formuleIBE", "resultatIBE", "formuleIJA", "resultatIJA", "formuleIJC", "resultatIJC", "formuleIJE", "resultatIJE", "montantchiffre", "montantlettre");
			}
	}


