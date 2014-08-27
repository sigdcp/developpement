package ci.gouv.budget.solde.sigdcp.model.signature;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignatureDocumentGenereDossier implements Serializable {

	private static final long serialVersionUID = -7787198374386509441L;

	/*
	 * A propos du document
	 */
	private String documentType;
	private String documentNumero;
	private Date documentDateCreation;
	
	/*
	 * A propos du dossier
	 */
	private String dossierNumero;
	private Date dossierDateCreation;
	
	/*
	 * A propos du requerant
	 */
	private String requerantMatricule;
	private String requerantNomPrenoms;
	
	/*
	 * A propos du compte utilisateur
	 */
	private Date compteUtilisateurDateCreation;
}
