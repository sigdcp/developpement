package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
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
	private AgentEtat defunt;
	@Getter private IdentitePersonneDTO declarantDto,defuntDto,agentConstatataireDto;
	
	@Override
	protected void initialisation() {
		defunt = new AgentEtat();
		super.initialisation();
		if(!StringUtils.isEmpty(entity.getNumero())){
			defunt = entity.getBeneficiaire();
		}
		
		AgentEtat declarant = (AgentEtat) (userSessionManager.getCompteUtilisateur()==null?new AgentEtat():userSessionManager.getUser());//peut etre un agent de l'état
		
		defunt.setAyantDroit(declarant);
		entity.setBeneficiaire(defunt);
		
		declarantDto = new IdentitePersonneDTO(declarant,null,isEditable());
		declarantDto.setShowQuestionAgentEtat(Boolean.TRUE);
		
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
	
	@Override
	protected AgentEtat beneficiaire(DossierObseques dossierObseques) {
		return dossierObseques==null?defunt:dossierObseques.getBeneficiaire();
	}
	
	@Override
	protected Personne creerPar(DossierObseques dossierObseques) {
		return userSessionManager.getCompteUtilisateur()==null?new AgentEtat():userSessionManager.getUser();
	}
	
	
}
		
