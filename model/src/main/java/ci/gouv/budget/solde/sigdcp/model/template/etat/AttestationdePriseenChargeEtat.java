package ci.gouv.budget.solde.sigdcp.model.template.etat;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.SituationMatrimoniale;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AttestationdePriseenChargeEtat {
	
	private PieceProduite piece;
	private DossierTransit dossier;
	private String transitaire;
	private String chapitreimputation;
	private String articleimputation;
	private String paragrapheimputation;
	
	public static Collection<AttestationdePriseenChargeEtat> test(){
		Collection<AttestationdePriseenChargeEtat> collection = new LinkedList<>();
		collection.add(build());
		return collection;
	}
	
	private static AttestationdePriseenChargeEtat build(){
		NatureDeplacement nd = new NatureDeplacement(new CategorieDeplacement("TR", "Transit de Bagages"), "Transit", "Transit de bagages", 0);
		Deplacement deplacement = new Deplacement(null, new Date(), new Date(), null, nd, new Localite(null, "Abidjan", null, null), new Localite(null, "Bouake", null, null));
		AgentEtat agentEtat = new AgentEtat(null, "500500A", "Tata", "pion", null, null, Sexe.MASCULIN, new SituationMatrimoniale("M", "Marié"), null, null, null, null, null, 1035, null, new Section(null, "MAE", "Ministère des Affaires étrangères", null), null, null);
		
		DossierTransit DossierTransit = new DossierTransit("TR-2014/04/0123", null, new Date(), deplacement, new Grade(null, null, "A1"), agentEtat, null, null, 5096587F, 12000000F);
		PieceProduite piece = new PieceProduite("123", null, new Date());
		return new AttestationdePriseenChargeEtat(piece,DossierTransit, "STA Transit-Douane", "332", "410101", "6283");
	
	}
	
	public AgentEtat getBeneficiaire(){
		return dossier.getBeneficiaire();
	}
	
	public Deplacement getDeplacement(){
		return dossier.getDeplacement();
	}

}
