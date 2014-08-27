/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@Embeddable @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of={"commune","principale","derivee","conditionnee","administrative"})
public class PieceJustificativeAFournirConfig  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Cette piece commune a tous les dossiers d'un déplacement
	 */
	@NotNull @Column(nullable=false)
	private Boolean commune = Boolean.FALSE;
	
	/**
	 * Cette piece est obligatoire pour la creation et la mise à jour d'un dossier
	 */
	@NotNull @Column(nullable=false)
	private Boolean principale = Boolean.FALSE;
		
	/**
	 * Cette piece est fournie par le système
	 */
	@NotNull @Column(nullable=false)
	private Boolean derivee = Boolean.FALSE;
	
	@ManyToOne
	private Role derivablePar;
	
	/**
	 * Cette piece est demandée en fonction d'autres informations fournis par le client
	 */
	@NotNull @Column(nullable=false)
	private Boolean conditionnee = Boolean.FALSE;
	
	@NotNull @Column(nullable=false)
	private Boolean administrative = Boolean.FALSE;
	
	/**
	 * Les operations permettant la saisie
	 */
	/*
	private Collection<NatureOperation> natureOperations = new HashSet<>();
	*/
	/*
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commune == null) ? 0 : commune.hashCode());
		result = prime * result + ((conditionnee == null) ? 0 : conditionnee.hashCode());
		result = prime * result + ((derivee == null) ? 0 : derivee.hashCode());
		result = prime * result + ((principale == null) ? 0 : principale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PieceJustificativeAFournirConfig other = (PieceJustificativeAFournirConfig) obj;
		if (commune == null) {
			if (other.commune != null)
				return false;
		} else if (!commune.equals(other.commune))
			return false;
		if (conditionnee == null) {
			if (other.conditionnee != null)
				return false;
		} else if (!conditionnee.equals(other.conditionnee))
			return false;
		if (derivee == null) {
			if (other.derivee != null)
				return false;
		} else if (!derivee.equals(other.derivee))
			return false;
		if (principale == null) {
			if (other.principale != null)
				return false;
		} else if (!principale.equals(other.principale))
			return false;
		return true;
	}*/
	
	
	
}