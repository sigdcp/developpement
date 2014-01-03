package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.AbstractFormSubmitAction;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierFO;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierObsequesService;

@Named @ViewScoped
@Getter @Setter
public class EnregistrerDemandeFOFormController extends AbstractDossierEnregistrementUIController<DossierFO,DossierObsequesService> implements Serializable {
	
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
		defaultSubmitAction = new AbstractFormSubmitAction<DossierFO>(entity,messageManager,"boutton.soumettre","ui-icon-check","notification.demande.soumise",Boolean.FALSE,Boolean.TRUE) {
			private static final long serialVersionUID = 2975854506689357563L;
			@Override
			protected void action() {
				
			}
		};
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
		return null;
	}
	
}
		
