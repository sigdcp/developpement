package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierDDService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeService;
import ci.gouv.budget.solde.sigdcp.service.dossier.TypePieceJustificativeService;

//@ManagedBean @ViewScoped
public class SoumettreFeuilleDeplacementController implements Serializable {

	private static final long serialVersionUID = 572544943898480486L;

	//@EJB private PieceJustificativeService pieceJustificativeService;
	//@EJB private TypePieceJustificativeService typePieceJustificativeService;
	//@EJB private DossierDDService dossierDDService;
	
	//@Inject @Getter
	private EnregistrerDemandeDDController enregistrerDemandeDDController;
	
	@Getter
	private PieceJustificative feuilleDeplacement;
	
	@PostConstruct
	private void postConstruct(){
		/*Collection<PieceJustificative> pieces = pieceJustificativeService.findByDossierByType(enregistrerDemandeDDController.getDossierDD(),
				typePieceJustificativeService.findById(TypePieceJustificativeEnum.FD.name()));
		feuilleDeplacement = pieces.iterator().next();*/
	}
	
	public String soumettre(){
		//dossierDDService.soumettreFeuilleDeplacement(enregistrerDemandeDDController.getDossierDD(), feuilleDeplacement);
		return "succes";
	}
	
}
