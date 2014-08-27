/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementPieceProduite;

@Getter @Setter 
@Entity @AllArgsConstructor
//@Inheritance(strategy=InheritanceType.JOINED)
public class PieceProduite  extends Document  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private TypePieceProduite type;
	
	@Embedded private Traitable<TraitementPieceProduite> traitable = new Traitable<>();
	
	public PieceProduite() {}

	public PieceProduite(String numero, TypePieceProduite type, Date dateEtablissement) {
		super(numero);
		this.type = type;
		this.dateEtablissement=dateEtablissement;
	}
	
	@Override
	public String getNumero() {
		return Code.NUMERO_PREFIX+type.getCode()+"/"+getId();
	}
	
}