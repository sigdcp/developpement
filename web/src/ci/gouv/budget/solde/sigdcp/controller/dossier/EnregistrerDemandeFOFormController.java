package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierFO;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractDossierService;

@Named @ViewScoped
@Getter @Setter
public class EnregistrerDemandeFOFormController extends AbstractDossierUIControllerController<DossierFO> implements Serializable {
	
	private static final long serialVersionUID = 6615049982603373278L;
	
	/*
	 * Services
	 */
	
	/*
	 * DTOs
	 */
	
	@Getter private IdentitePersonneDTO declarantDto,defuntDto,agentConstatataireDto;
	
	@Override
	public void __firstPreRenderView__() {
		super.__firstPreRenderView__();
		title = "Formulaire de Demande  prise en charge des frais d'obsèques";
		
		AgentEtat declarant = new AgentEtat();//peut etre un agent de l'état
		AgentEtat defunt = new AgentEtat();
		defunt.setAyantDroit(declarant);
		entity.setBeneficiaire(defunt);
		
		declarantDto = new IdentitePersonneDTO(declarant);
		declarantDto.setShowQuestionAgentEtat(Boolean.TRUE);
		
		defuntDto = new IdentitePersonneDTO(defunt, Boolean.TRUE);
		
		agentConstatataireDto = new IdentitePersonneDTO(new AgentEtat());
	}

	@Override
	protected AbstractDossierService<DossierFO> getDossierService() {
		return null;
	}
	
}
		
