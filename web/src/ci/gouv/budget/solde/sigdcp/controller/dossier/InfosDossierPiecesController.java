package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.dao.dossier.NatureDeplacementDao;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;

@Named
public class InfosDossierPiecesController implements Serializable {

	private static final long serialVersionUID = -1219965991037432385L;

	@Inject
	private NatureDeplacementDao natureDeplacementDao;
	
	@Getter
	private List<NatureDeplacement> natureDeplacements;
	
	@PostConstruct
	private void postConstruct(){
		//natureDeplacements = (List<NatureDeplacement>) natureDeplacementDao.findAllWithPieceJustificativeAFournir();
		//System.out.println("Found : "+natureDeplacements.size());
	}
	
}
