package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.NatureDeplacementDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Stateless
public class NatureDeplacementServiceImpl extends DefaultServiceImpl<NatureDeplacement, String> implements NatureDeplacementService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject
	public NatureDeplacementServiceImpl(NatureDeplacementDao dao) {
		super(dao);
	}
	
	/*
	@Override
	public NatureDeplacement findByIdWithPieceJustificativeAFournir(String identifiant) {
		return ((NatureDeplacementDao)dao).readWithPieceJustificativeAFournir(identifiant);
	}
	*/
}
