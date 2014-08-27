package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.TypePieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

public class TypePieceJustificativeServiceImpl extends DefaultServiceImpl<TypePieceJustificative, String> implements TypePieceJustificativeService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public TypePieceJustificativeServiceImpl(TypePieceJustificativeDao dao) {
		super(dao);
	}

}
