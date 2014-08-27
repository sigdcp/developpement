package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.sf.jasperreports.engine.type.VerticalAlignEnum;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.AbstractEntityFormUIController;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.Action;
import ci.gouv.budget.solde.sigdcp.controller.ui.form.command.FormCommand;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.CompteUtilisateur;
import ci.gouv.budget.solde.sigdcp.model.identification.DelegueSotra;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.identification.Role;
import ci.gouv.budget.solde.sigdcp.model.identification.Verrou.Cause;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;
import ci.gouv.budget.solde.sigdcp.service.identification.CompteUtilisateurService;
import ci.gouv.budget.solde.sigdcp.service.identification.DelegueSotraService;

@Named @ViewScoped
public class CompteUtilisateurController extends AbstractEntityFormUIController<CompteUtilisateur> implements Serializable {

	private static final long serialVersionUID = 4000008808410499878L;
	
	@Inject private CompteUtilisateurService compteUtilisateurService;
	@Inject private DelegueSotraService delegueSotraService;
	@Inject private AgentEtatService agentEtatService;
	@Getter @Setter private List<Role> roles;
	@Getter @Setter private List<Role> nouveauxRoles;
	@Getter @Setter private List<AgentEtat> agentEtats;
	@Getter @Setter private Boolean modifierRoles=false,modifierDelegueSotra=false, desactiverCompte=true;
	@Getter private FormCommand<CompteUtilisateur> enregistrerRolesCommand,desactiverCompteCommand;
	@Getter private DelegueSotra delegueSotra;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title="Compte utilisateur";
		roles = new ArrayList<>(entity.getRoles());
		nouveauxRoles = new ArrayList<>(roles);
		agentEtats = agentEtatService.findAll();
		if(getAgentEtat()!=null){
			delegueSotra = delegueSotraService.findByAgentEtat(getAgentEtat());
			if(delegueSotra==null)
				delegueSotra = new DelegueSotra(getAgentEtat(), null, null);
		}
		for(Role role : entity.getRoles())
			if(role.getCode().equals(Code.ROLE_DELEGUE_SOTRA)){
				modifierDelegueSotra=true;
				break;
			}
		enregistrerRolesCommand = createCommand().init("bouton.enregistrer", "ui-icon-check", null, new Action() {
			private static final long serialVersionUID = -8645979884804534081L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				compteUtilisateurService.modifierRoles(entity, nouveauxRoles, delegueSotra);
				return null;
			}
		});
		
		desactiverCompteCommand = createCommand().init("bouton.desactiver", "ui-icon-close", null, new Action() {
			private static final long serialVersionUID = -8645979884804534081L;
			@Override
			protected Object __execute__(Object object) throws Exception {
				compteUtilisateurService.verouiller(entity, Cause.DESACTIVATION_COMPTE);
				return null;
			}
		});
		

		enregistrerRolesCommand.onSuccessGoTo(navigationManager.url("profilesagentetat",new Object[]{},false), "", null);
		desactiverCompteCommand.onSuccessGoTo(navigationManager.url("profilesagentetat",new Object[]{},false), "", null);
		
	}
		
	public AgentEtat getAgentEtat(){
		if(entity.getUtilisateur() instanceof AgentEtat)
			return (AgentEtat)entity.getUtilisateur();
		return null;
	}
	
	public Personne getPersonne(){
		if(entity.getUtilisateur() instanceof Personne)
			return (Personne)entity.getUtilisateur();
		return null;
	}
	
	public Prestataire getPrestataire(){
		if(entity.getUtilisateur() instanceof Prestataire)
			return (Prestataire)entity.getUtilisateur();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void roleValueChangeListener(ValueChangeEvent valueChangeEvent){
		modifierDelegueSotra = false;
		for(Role role : ((List<Role>)valueChangeEvent.getNewValue()))
			if(role.getCode().equals(Code.ROLE_DELEGUE_SOTRA)){
				modifierDelegueSotra=true;
				break;
			}
	}
	

}
