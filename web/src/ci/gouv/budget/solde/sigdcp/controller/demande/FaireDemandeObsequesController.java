package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierObsequesService;

@Named @ViewScoped
@Getter @Setter
public class FaireDemandeObsequesController extends AbstractFaireDemandeController<DossierObseques,DossierObsequesService> implements Serializable {
	
	private static final long serialVersionUID = 6615049982603373278L;
	
	/*
	 * Services
	 */
	@Inject private DossierObsequesService dossierObsequesService;
	
	/*
	 * DTOs
	 */

	@Getter private IdentitePersonneDTO declarantDto,defuntDto,agentConstatataireDto;
	
	@Override
	protected void initialisation() {
		entity = new DossierObseques();
		super.initialisation();
		
		
		declarantDto = new IdentitePersonneDTO(entity.getBeneficiaire().getAyantDroit(),null,isEditable());
		declarantDto.setShowQuestionAgentEtat(Boolean.TRUE);
		
		defuntDto = new IdentitePersonneDTO(entity.getBeneficiaire(), Boolean.TRUE,isEditable());
		defuntDto.setShowQuestionAgentEtat(Boolean.FALSE);
		defuntDto.setReadNationalite(Boolean.FALSE);
		defuntDto.setReadContact(Boolean.FALSE);
		
		agentConstatataireDto = new IdentitePersonneDTO(new AgentEtat(),Boolean.FALSE,isEditable());
	}

	@Override
	protected DossierObsequesService getDossierService() {
		return dossierObsequesService;
	}
	
	public AgentEtat getDefunt(){
		return entity.getBeneficiaire();
	}
	
	
}
		
