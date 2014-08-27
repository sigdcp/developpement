package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;

@Named @ViewScoped
public class TypePieceJustificativeReferenceController extends AbstractDynaEnumReferenceController<TypePieceJustificative> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Type Pièce Justificative";
	}

	@Override
	protected Class<TypePieceJustificative> clazz() {
		return TypePieceJustificative.class;
	}

}
