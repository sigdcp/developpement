package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.Document;

public class AbstractDocumentDaoImpl<DOCUMENT extends Document> extends JpaDaoImpl<DOCUMENT, Long> implements AbstractDocumentDao<DOCUMENT>, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	

	

}
 