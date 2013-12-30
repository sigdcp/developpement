package ci.gouv.budget.solde.sigdcp.model.fichier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Image extends Fichier implements Serializable {

	private static final long serialVersionUID = -7734285609148000846L;

	public Image() {}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Image && ((Image)obj).id==id;
	}
	
}
