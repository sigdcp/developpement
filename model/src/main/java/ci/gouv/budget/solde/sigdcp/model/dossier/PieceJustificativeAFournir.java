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
import ci.gouv.budget.solde.sigdcp.model.identification.TypePersonne;

@Getter @Setter 
@Entity
public class PieceJustificativeAFournir  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private TypePieceJustificative typePieceJustificative;
	
	/*---  Une piece justificative à fournir dépend de plusieurs paramètres qui sont les suivants :  ---*/
	
	/**
	 * La nature du déplacement. ex : Affectation
	 */
	@ManyToOne private NatureDeplacement natureDeplacement;
	
	/**
	 * La personne qui fait la demande. Null signifie tout type de personne
	 */
	@ManyToOne private TypePersonne typePersonne;
	
	/**
	 * Le type de dépense. ex : Prise en charge , remboursement
	 */
	@ManyToOne private TypeDepense typeDepense;
	
	/*---------------------------------------------------------------------------------------------------*/
	
	private Boolean original;	
	
	/**
	 * La période de validité. ex : -3 -> moins de 3 mois
	 */

	private Integer periodeValiditeEnMois;
	
	private Integer quantite;
	
	/**
	 * Cette piece est fourniepar le système
	 */
	private Boolean derivee = Boolean.FALSE;
	
	/**
	 * Cette piece est demandée en fonction d'autres informations.
	 * Cette condition est évaluée au niveau métier
	 */
	private Boolean conditionnee = Boolean.FALSE;
	
	@Column(length=1 * 1024)
	private String description;
	
	public PieceJustificativeAFournir() {}

	public PieceJustificativeAFournir(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne,TypePieceJustificative typePieceJustificative,
			Integer quantite,Boolean original,Integer periodeValiditeEnMois,Boolean conditionnee,String description) {
		super();
		this.natureDeplacement = natureDeplacement;
		this.typeDepense = typeDepense;
		this.typePersonne = typePersonne;
		this.original = original;
		this.periodeValiditeEnMois = periodeValiditeEnMois;
		this.quantite = quantite;
		this.typePieceJustificative = typePieceJustificative;
		this.description = description;
		this.conditionnee = conditionnee;
	}
	public PieceJustificativeAFournir(NatureDeplacement natureDeplacement,TypeDepense typeDepense,TypePersonne typePersonne,TypePieceJustificative typePieceJustificative,
			 Integer quantite,Boolean original,Integer periodeValiditeEnMois,Boolean conditionnee){
		this(natureDeplacement,typeDepense,typePersonne,typePieceJustificative,quantite,original,periodeValiditeEnMois,conditionnee,null);
	}
	
	@Override
	public String toString() {
		if(description!=null && !description.isEmpty())
			return typePieceJustificative.toString()+"("+description+")";
		return typePieceJustificative.toString();
	}
	
}