/**
 *    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
 *
 *    Mod�le M�tier
 *
 **/

package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.identification.TypePersonne;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@Table(name="PIECEJAF")
public class PieceJustificativeAFournir extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private TypePieceJustificative typePieceJustificative;

	/*---  Une piece justificative à fournir dépend de plusieurs paramètres qui sont les suivants :  ---*/

	/**
	 * La nature du déplacement. ex : Affectation
	 */
	@ManyToOne
	private NatureDeplacement natureDeplacement;

	/**
	 * La personne qui fait la demande. Null signifie tout type de personne
	 */
	@ManyToOne
	private TypePersonne typePersonne;

	/**
	 * Le type de dépense. ex : Prise en charge , remboursement
	 */
	@ManyToOne
	private TypeDepense typeDepense;

	@Embedded
	private PieceJustificativeAFournirConfig config = new PieceJustificativeAFournirConfig();

	private Boolean original = Boolean.FALSE;

	/**
	 * La période de validité. ex : -3 -> moins de 3 mois
	 */

	private Integer periodeValiditeEnMois;

	private Integer quantite = 1;

	@Column(length = 1 * 1024)
	private String description;

	public PieceJustificativeAFournir() {
	}

	public PieceJustificativeAFournir(TypePieceJustificative typePieceJustificative, NatureDeplacement natureDeplacement, TypePersonne typePersonne, TypeDepense typeDepense, 
			Boolean original, Integer periodeValiditeEnMois, Integer quantite, String description) {
		super();
		this.typePieceJustificative = typePieceJustificative;
		this.natureDeplacement = natureDeplacement;
		this.typePersonne = typePersonne;
		this.typeDepense = typeDepense;
		this.original = original;
		this.periodeValiditeEnMois = periodeValiditeEnMois;
		this.quantite = quantite;
		this.description = description;
	}

	@Override
	public String toString() {
		if (description != null && !description.isEmpty())
			return typePieceJustificative.toString() + "(" + description + ")";
		return typePieceJustificative.toString();
	}

}