package ci.gouv.budget.solde.sigdcp.model.template.etat;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RemboursementEtat implements Serializable {

	private static final long serialVersionUID = -5973562425199996115L;
	
	private String natureDeplacement;
	private String noms;	
	private String montant;	
	private String quIlApaye;
	private String chapitre;
	private String libelleChapitre;
	private String dateDemande;

	private List<PieceJustificative> pieces = new LinkedList<>();
	
	public static Collection<RemboursementEtat> test() {
		Collection<RemboursementEtat> collection = new LinkedList<>();
		collection.add(build());
		return collection;
	}
	
	public static  RemboursementEtat build(){	
		RemboursementEtat etat = new RemboursementEtat();
		etat.setNatureDeplacement("DE TRANSPORT");
		etat.setNoms("MONSIEUR KOUHON SERGE PACOME");
		etat.setMontant("DEUX CENT QUATRE VINGT QUATORZE MILLE CENT (294 100) FCFA");
		etat.setQuIlApaye("qu'il a payés");
		etat.setChapitre("841 410201 6283232");
		etat.setLibelleChapitre("<<frais de transport des agents en mission à l'étranger>>");
		etat.setDateDemande("09 septembre 2013");
		etat.getPieces().add(new PieceJustificative(null, "123", new PieceJustificativeAFournir(new TypePieceJustificative(null, "la communication en consel des ministres"), null, null, null, null, null, null, null), new Date()));
		etat.getPieces().add(new PieceJustificative(null, "124", new PieceJustificativeAFournir(new TypePieceJustificative(null, "l'attestation du secretariat Général du gouvernement"), null, null, null, null, null, null, null), new Date()));
		etat.getPieces().add(new PieceJustificative(null, "125", new PieceJustificativeAFournir(new TypePieceJustificative(null, "l'odre de mission"), null, null, null, null, null, null, null), new Date()));

		return etat;
	}
			


}
