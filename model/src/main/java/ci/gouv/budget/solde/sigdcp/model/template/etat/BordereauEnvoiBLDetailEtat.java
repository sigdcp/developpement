package ci.gouv.budget.solde.sigdcp.model.template.etat;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BordereauEnvoiBLDetailEtat implements Serializable {

	private static final long serialVersionUID = -5973562425199996115L;
	
	private DossierDD dossier;
	private BulletinLiquidation bulletin;
		
	public AgentEtat getBeneficiaire(){
		return dossier.getBeneficiaire();
	}

	}


