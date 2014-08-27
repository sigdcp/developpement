package ci.gouv.budget.solde.sigdcp.model.template.etat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.identification.SituationMatrimoniale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BordereauEnvoiBLEtat implements Serializable {

	private static final long serialVersionUID = -5973562425199996115L;

	private List<BordereauEnvoiBLDetailEtat> details = new LinkedList<>();
	private BordereauTransmission bordereau;

	public static Collection<BordereauEnvoiBLEtat> test() {
		Collection<BordereauEnvoiBLEtat> collection = new LinkedList<>();
		collection.add(build());
		return collection;
	}

	public static BordereauEnvoiBLEtat build() {
		BordereauEnvoiBLEtat etat = new BordereauEnvoiBLEtat();
		etat.setBordereau(new BordereauTransmission("BT2546256", null, null));
		for (int i = 0; i < 9; i++) {
			AgentEtat agentEtat = new AgentEtat(null, "500500A", "Tata", "pion", null, null, Sexe.MASCULIN, new SituationMatrimoniale("M", "Marié"), null, null, null, null, null, 1035, null, new Section(null, "MAE", "Ministère des Affaires étrangères", null), null, null);
			
			DossierDD d = new DossierDD(null, null, null, null, agentEtat, null, null, null, null, null, null);
			BulletinLiquidation bulletin = new BulletinLiquidation(null,"BL1024512", null, null, new BigDecimal(17214587));
			etat.getDetails().add(new BordereauEnvoiBLDetailEtat(d, bulletin));
		}
		return etat;
	}
	
	public static void main(String[] args) {
		System.out.println(test());
		System.out.println("Done!");
	}

}
