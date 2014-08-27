package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;

@Named @ViewScoped
public class TypePieceProduiteReferenceController extends AbstractDynaEnumReferenceController<TypePieceProduite> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Type Pièce Produite";
	}

	@Override
	protected Class<TypePieceProduite> clazz() {
		return TypePieceProduite.class;
	}

}
