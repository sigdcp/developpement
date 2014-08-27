/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.DynamicEnumeration;

@Getter @Setter 
@Entity
public class RegleCalcul  extends DynamicEnumeration  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public enum Type{INDEMNITE,PRESTATION}
	
	@Column(name="LE_SCRIPT",length=1024)
	private String script;
	
	@Enumerated
	private Type type;
	
	public RegleCalcul() {}
	
	public RegleCalcul(String code, String libelle,Type type, String script) {
		super(code, libelle);
		this.type=type;
		this.script = script;
	}
	
	public RegleCalcul(String code, String libelle, String script) {
		this(code,libelle,Type.INDEMNITE,script);
	}
	
}