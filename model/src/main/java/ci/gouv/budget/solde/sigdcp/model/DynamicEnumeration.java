package ci.gouv.budget.solde.sigdcp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

/**
 * Decrit les types d'objets
 * @author christian
 *
 */
@Getter @Setter 
//@Entity 
@MappedSuperclass
@AllArgsConstructor
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) -- Mis en commentaire à cause d'Hibernate
//@Table(name="enumeration_dynamique")
public class DynamicEnumeration  extends AbstractModel<String>  implements Serializable {

	private static final long serialVersionUID = -8639942019354737162L;
	
	@Id
	@NotNull(groups=Client.class)
	protected String code;
	
	protected String libelle;
	
	@Column(length=10 * 1024)
	@Lob
	private String description;
	
	//private Integer shortDescriptionBenginIndex,shortDescriptionEndIndex;
	
	public DynamicEnumeration() {}
/*
	public DynamicEnumeration(String code, String libelle, String description) {
		super();
		this.code = code;
		this.libelle = libelle;
		this.description = description;
	}
	*/
	public DynamicEnumeration(String code, String libelle) {
		this(code,libelle,null);
	} 
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		DynamicEnumeration other = (DynamicEnumeration) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return libelle;
	}







	
}
