package ci.gouv.budget.solde.sigdcp.controller.infos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractUIController;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.service.dossier.NatureDeplacementService;

@Named @RequestScoped
public class PublicHomeController extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = 7404320093212948431L;

	/*
	 * Services
	 */
	@Inject private NatureDeplacementService natureDeplacementService;
	
	@Getter @Setter private List<ServiceProposeDto> serviceProposeDtos = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		for(NatureDeplacement natureDeplacement : natureDeplacementService.findAll())
			serviceProposeDtos.add(new ServiceProposeDto(natureDeplacement,webConstantResources));
		serviceProposeDtos.add(new ServiceProposeDto("Sotra","Description","lpus","lpv","lireplussotra",false,false,true));
	}
	
}
