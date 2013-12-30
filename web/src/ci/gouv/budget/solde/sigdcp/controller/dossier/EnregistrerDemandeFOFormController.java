package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.flow.FlowDefinitions;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierFO;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;

@Named @FlowScoped(value=FlowDefinitions.FLOW_DEMANDE_FO_ID) 
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
	

	@PostConstruct
	protected void postConstruct2() {
		flowId = FlowDefinitions.FLOW_DEMANDE_FO_ID;
		title = "Formulaire de Demande  prise en charge des frais d'obsèques";
		for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirService.findByNatureDeplacementId(natureDaplacementCode))
			pieceJustificativeUploader.addPieceJustificative(new PieceJustificative(pieceJustificativeAFournir, dossier));
		
		AgentEtat declarant = new AgentEtat();//peut etre un agent de l'état
		AgentEtat defunt = new AgentEtat();
		defunt.setAyantDroit(declarant);
		dossier.setBeneficiaire(defunt);
		
		declarantDto = new IdentitePersonneDTO(declarant);
		declarantDto.setShowQuestionAgentEtat(Boolean.TRUE);
		
		defuntDto = new IdentitePersonneDTO(defunt, Boolean.TRUE);
		
		agentConstatataireDto = new IdentitePersonneDTO(new AgentEtat());
	}
	
	@Override
	protected DossierFO createDossierInstance() {
		DossierFO dossierFO = new DossierFO();
		dossierFO.setDeplacement(new Deplacement());
		return dossierFO;
	}
	
}
		
