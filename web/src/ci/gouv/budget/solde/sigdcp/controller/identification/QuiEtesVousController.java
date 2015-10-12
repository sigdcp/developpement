package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractUIController;
import ci.gouv.budget.solde.sigdcp.model.Code;

@Named @RequestScoped @Getter
public class QuiEtesVousController extends AbstractUIController implements Serializable {

	private static final long serialVersionUID = -3714190672770031871L;

	/*private Collection<CategorieRequerant> categorieRequerants = new ArrayList<>();*/
	private CategorieRequerant requerant;
	private String typeRequerant;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		/*categorieRequerants.add(new CategorieRequerant("Fonctionnaire",text("inscription.fonctionnaireagent.description") , "inscriptionfonctionnaireagent"));
		categorieRequerants.add(new CategorieRequerant("Gendarme", text("inscription.inscriptiongendarme.description"), "inscriptiongendarme"));
		categorieRequerants.add(new CategorieRequerant("Contractuel", text("inscription.inscriptioncontractuel.description"), "inscriptioncontractuel"));
		categorieRequerants.add(new CategorieRequerant("Ayant droit d'un parent défunt", text("inscription.inscriptiondeclarantdeces.description"), "inscriptiondeclarantdeces"));
		
		categorieRequerants.add(new CategorieRequerant("Prestataire", text("inscription.inscriptionprestataire.description"), "inscriptionprestataire"));
		*/
		try {
			typeRequerant = Faces.getRequestParameter(webConstantResources.getRequestParamCategorieRequerant()); 
		} catch (NumberFormatException e) {return;}
		
		if(Code.TYPE_AGENT_ETAT_FONCTIONNAIRE.equals(typeRequerant))
			requerant=new CategorieRequerant("Fonctionnaires",text("inscription.fonctionnaireagent.description") , "inscriptionfonctionnaireagent");
		else if(Code.TYPE_AGENT_ETAT_GENDARME.equals(typeRequerant))
			requerant=new CategorieRequerant("Gendarmes et militaires", text("inscription.inscriptiongendarme.description"), "inscriptiongendarme");
		else if(Code.TYPE_AGENT_ETAT_CONTRACTUEL.equals(typeRequerant))
			requerant=new CategorieRequerant("Contractuels", text("inscription.inscriptioncontractuel.description"), "inscriptioncontractuel");
		else if(Code.TYPE_AGENT_ETAT_MISSION.equals(typeRequerant))
			requerant=new CategorieRequerant("Agents de mission", text("inscription.inscriptionagentmission.description"), "inscriptionagentmission");
		else if(Code.TYPE_PERSONNE_AYANT_DROIT.equals(typeRequerant))
			requerant=new CategorieRequerant("Ayant droit d'un parent défunt", text("inscription.inscriptiondeclarantdeces.description"), "inscriptiondeclarantdeces");
		else if(Code.TYPE_PRESTATAIRE.equals(typeRequerant))
			requerant=new CategorieRequerant("Prestataires", text("inscription.inscriptionprestataire.description"), "inscriptionprestataire");
		
	}
		
	/**/
	
	@Getter @Setter @AllArgsConstructor
	public static class CategorieRequerant{
		
		private String nom;
		private String description;
		private String outcome;
		
	}
	
}
