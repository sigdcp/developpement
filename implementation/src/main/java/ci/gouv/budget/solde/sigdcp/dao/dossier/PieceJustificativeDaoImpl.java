package ci.gouv.budget.solde.sigdcp.dao.dossier;

import java.io.Serializable;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.dao.JpaDaoImpl;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;

public class PieceJustificativeDaoImpl extends JpaDaoImpl<PieceJustificative, Long> implements PieceJustificativeDao, Serializable {

	private static final long serialVersionUID = -2609724288199083806L;

	@Override
	public Collection<PieceJustificative> readByDossier(Dossier dossier) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificative pj WHERE pj.dossier=:dossier", clazz)
				.setParameter("dossier", dossier)
				.getResultList();
	}
	
	@Override
	public Collection<PieceJustificative> readByDossierByType(Dossier dossier,TypePieceJustificative type) {
		return entityManager.createQuery("SELECT pj FROM PieceJustificative pj WHERE pj.type= :tpj AND pj.dossier=:dossier", clazz)
				.setParameter("dossier", dossier).setParameter("tpj", type)
				.getResultList();
	}
	

}
 