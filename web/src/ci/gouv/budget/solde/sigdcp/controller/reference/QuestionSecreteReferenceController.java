package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.identification.QuestionSecrete;

@Named @ViewScoped
public class QuestionSecreteReferenceController extends AbstractEntiteReferenceController<QuestionSecrete, Long> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Questions secrètes";
	}

	@Override
	protected Class<QuestionSecrete> clazz() {
		return QuestionSecrete.class;
	}

	@Override
	public Long identifiant(QuestionSecrete entity) {
		// TODO Auto-generated method stub
		return entity.getId();
	}

}
