/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Getter @Setter 
@Entity
public class PieceJustificativeAFournir  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	private Boolean original;	
	
	/**
	 * exemple : -3 -> - de 3 mois
	 */

	private Integer periodeValiditeEnMois;
	
	private Integer quantite;
	
	@ManyToOne private NatureDeplacement natureDeplacement;
	
	@ManyToOne
	private TypePieceJustificative typePieceJustificative;
	
	@Column(length=1 * 1024)
	private String description;
	
	public PieceJustificativeAFournir() {}

	public PieceJustificativeAFournir(NatureDeplacement natureDeplacement,Boolean original,
			Integer periodeValiditeEnMois, Integer quantite,
			TypePieceJustificative typePieceJustificative,String description) {
		super();
		this.natureDeplacement = natureDeplacement;
		this.original = original;
		this.periodeValiditeEnMois = periodeValiditeEnMois;
		this.quantite = quantite;
		this.typePieceJustificative = typePieceJustificative;
		this.description = description;
	}
	
	public PieceJustificativeAFournir(NatureDeplacement natureDeplacement,Boolean original,
			Integer periodeValiditeEnMois, Integer quantite,
			TypePieceJustificative typePieceJustificative) {
		this(natureDeplacement,original,periodeValiditeEnMois,quantite,typePieceJustificative,null);
	}
	
	@Override
	public String toString() {
		if(description!=null && !description.isEmpty())
			return typePieceJustificative.toString()+"("+description+")";
		return typePieceJustificative.toString();
	}
	
}