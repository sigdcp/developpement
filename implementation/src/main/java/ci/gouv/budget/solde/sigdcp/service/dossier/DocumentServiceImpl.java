package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DocumentDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.Document;

@Stateless
public class DocumentServiceImpl extends AbstractDocumentServiceImpl<Document> implements DocumentService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public DocumentServiceImpl(DocumentDao dao) {
		super(dao);
	}

}
