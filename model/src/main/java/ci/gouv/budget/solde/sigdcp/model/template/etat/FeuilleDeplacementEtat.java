package ci.gouv.budget.solde.sigdcp.model.template.etat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FeuilleDeplacementEtat implements Serializable {

	private static final long serialVersionUID = -5973562425199996115L;

	private PieceJustificative piece;
	
	private String ordreservice;
	private String dateodreservice;
	
	private String groupe;
	
	private String indice;
	private String compagnons;
	
	private InputStream codeBarre;
	
	public AgentEtat getBeneficiaire(){
		return piece.getDossier().getBeneficiaire();
	}
	
	public Deplacement getDeplacement(){
		return piece.getDossier().getDeplacement();
	}
	
	public Dossier getDossier(){
		return piece.getDossier();
	}
	
	public DossierDD getDossierDD(){
		if(piece.getDossier() instanceof DossierDD)
			return (DossierDD) piece.getDossier();
		return null;
	}
	
	public DossierMission getDossierMission(){
		if(piece.getDossier() instanceof DossierMission)
			return (DossierMission) piece.getDossier();
		return null;
	}
	
	public static Collection<FeuilleDeplacementEtat> test(){
		Collection<FeuilleDeplacementEtat> collection = new LinkedList<>();
		collection.add(build());
		return collection;
	}
	
	private static FeuilleDeplacementEtat build(){
		NatureDeplacement nd = new NatureDeplacement(new CategorieDeplacement("D", "Déplacement définitif"), "DD", "Affectation", 0);
		
		Deplacement deplacement = new Deplacement(null, new Date(), new Date(), null, nd, new Localite(null, "Abidjan", null, null), new Localite(null, "Bouake", null, null));
		AgentEtat agentEtat = new AgentEtat(null,"500500A", "Tata", "pion", null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		
		DossierDD dossier = new DossierDD(null, null, deplacement, new Grade(null, null, "A1"), agentEtat, 500, null, null, null, null, null);
		PieceJustificative piece = new PieceJustificative(dossier, "123", null, new Date());
		try {
			return new FeuilleDeplacementEtat(piece, "Décision", new Date().toString(), "Groupe 1", "795", "Femme et deux(02) enfants",new FileInputStream("C:/Developpement/qrcode/qrcode-01.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
