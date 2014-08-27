package ci.gouv.budget.solde.sigdcp.controller.reference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocalite;
import ci.gouv.budget.solde.sigdcp.model.geographie.DistanceEntreLocaliteId;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.service.geographie.DistanceEntreLocaliteService;
import ci.gouv.budget.solde.sigdcp.service.resources.ListeResources;

@Named @ViewScoped
public class DistanceLocaliteReferenceController extends AbstractEntiteReferenceController<DistanceEntreLocalite, DistanceEntreLocaliteId> implements Serializable {

	private static final long serialVersionUID = -1061481987684469949L;
	@Inject private DistanceEntreLocaliteService distanceEntreLocaliteService;
	@Inject transient private ListeResources listeResources;
	@Getter
	private List<SelectItem> localites = new ArrayList<>();
	@Override
	protected void initialisation() {
		// TODO Auto-generated method stub
		super.initialisation();
		for(Localite localite : listeResources.getVillesCoteDIvoire()){
			localites.add(new SelectItem(localite.getCode(), localite.getLibelle()));
			
		}
	}
	@Override
	protected String nomEntite() {
		return "Distance entre deux localités";
	}

	@Override
	protected Class<DistanceEntreLocalite> clazz() {
		return DistanceEntreLocalite.class;
	}
	
	@Override
	protected List<DistanceEntreLocalite> load() {
		return (List<DistanceEntreLocalite>) distanceEntreLocaliteService.findDistLocLibelle();
	}
	@Override
	public void ajouter() {
		super.ajouter();
		enAjout.get(0).setId(new DistanceEntreLocaliteId());
	}
	
	@Override
	public void onRowEdit(RowEditEvent event) {
		DistanceEntreLocalite d = (DistanceEntreLocalite) event.getObject();
		super.onRowEdit(event);
		d.setLocalite1(genericService.findByClass(Localite.class, d.getId().getLocalite1()));
		d.setLocalite2(genericService.findByClass(Localite.class, d.getId().getLocalite2()));
	}
	
	@Override
	public DistanceEntreLocaliteId identifiant(DistanceEntreLocalite entity) {
		// TODO Auto-generated method stub
		return entity.getId();
	}
	

}
