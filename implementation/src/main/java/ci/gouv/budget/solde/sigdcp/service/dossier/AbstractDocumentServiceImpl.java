package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import ci.gouv.budget.solde.sigdcp.dao.dossier.AbstractDocumentDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Document;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public abstract class AbstractDocumentServiceImpl<DOCUMENT extends Document> extends DefaultServiceImpl<DOCUMENT, Long> implements AbstractDocumentService<DOCUMENT> , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	public AbstractDocumentServiceImpl(AbstractDocumentDao<DOCUMENT> dao) {
		super(dao);
	}
	

}
