package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ci.gouv.budget.solde.sigdcp.model.indemnite.ClasseMoyenTransport;
import ci.gouv.budget.solde.sigdcp.model.indemnite.ClasseMoyenTransportId;

@Named @ViewScoped
public class ClasseMoyenTransportReferenceController extends AbstractEntiteReferenceController<ClasseMoyenTransport, ClasseMoyenTransportId> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	
	@Override
	protected String nomEntite() {
		return "Classe Moyen de transport";
	}

	@Override
	protected Class<ClasseMoyenTransport> clazz() {
		return ClasseMoyenTransport.class;
	}

	@Override
	public ClasseMoyenTransportId identifiant(ClasseMoyenTransport entity) {
		// TODO Auto-generated method stub
		return entity.getId();
	}

}
