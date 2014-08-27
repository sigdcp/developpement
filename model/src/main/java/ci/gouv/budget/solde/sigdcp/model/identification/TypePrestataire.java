package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity 
public class TypePrestataire  extends DynamicEnumeration implements Serializable{

	private static final long serialVersionUID = 1L;

	public TypePrestataire() {}

	public TypePrestataire(String code, String libelle) {
		super(code, libelle);
	}
	
}