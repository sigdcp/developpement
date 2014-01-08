package ci.gouv.budget.solde.sigdcp.controller.dossier;

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
public class EnregistrerDemandeFOFormController extends AbstractDossierUIController<DossierObseques,DossierObsequesService> implements Serializable {
	
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
		super.initialisation();
		AgentEtat declarant = new AgentEtat();//peut etre un agent de l'état
		AgentEtat defunt = new AgentEtat();
		defunt.setAyantDroit(declarant);
		entity.setBeneficiaire(defunt);
		
		declarantDto = new IdentitePersonneDTO(declarant,null,isEditable());
		declarantDto.setShowQuestionAgentEtat(Boolean.TRUE);
		
		System.out.println("Editable : "+declarantDto.getEditable());
		
		defuntDto = new IdentitePersonneDTO(defunt, Boolean.TRUE,isEditable());
		defuntDto.setShowQuestionAgentEtat(Boolean.FALSE);
		defuntDto.setReadNationalite(Boolean.FALSE);
		defuntDto.setReadContact(Boolean.FALSE);
		
		agentConstatataireDto = new IdentitePersonneDTO(new AgentEtat(),Boolean.FALSE,isEditable());
	}

	@Override
	protected DossierObsequesService getDossierService() {
		return dossierObsequesService;
	}
	
}
		
