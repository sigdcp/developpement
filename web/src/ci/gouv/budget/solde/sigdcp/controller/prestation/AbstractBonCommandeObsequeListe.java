package ci.gouv.budget.solde.sigdcp.controller.prestation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEffectuerOperationPersonnelController;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeObseque;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeObsequeService;

public abstract class AbstractBonCommandeObsequeListe extends AbstractEffectuerOperationPersonnelController<CommandeObseque> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;
	
	@Inject protected CommandeObsequeService commandeObsequeService;
	
	@Getter @Setter
	protected Boolean showValide = false,showDateRetrait=false;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		rechercherCommande=null;
		natureDeplacement.getCategorie().setDisponible(null);
	}
	
	@Override
	protected String identifierFieldName() {
		return "id";
	}

	@Override
	public Traitement dernierTraitement(CommandeObseque commandeObseque) {
		return commandeObseque.getTraitable().getDernierTraitement();
	}

	@Override
	public BigDecimal depense(CommandeObseque commandeObseque) {
		return BigDecimal.ZERO;
	}

	@Override
	public String numero(CommandeObseque commandeObseque) {
		return commandeObseque.getNumero();
	}

	@Override
	public Date dateCreation(CommandeObseque commandeObseque) {
		return null;
	}

	@Override
	protected void valider(Collection<CommandeObseque> datas) {
		commandeObsequeService.valider(datas);
	}
	
	@Override
	protected Collection<CommandeObseque> data(Collection<NatureDeplacement> natureDeplacements) {
		return commandeObsequeService.findATraiter(natureOperationCode());
	}
	
	
	
}
