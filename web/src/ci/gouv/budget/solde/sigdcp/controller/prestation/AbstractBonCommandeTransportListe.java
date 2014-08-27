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
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.service.prestation.CommandeTitreTransportService;

public abstract class AbstractBonCommandeTransportListe extends AbstractEffectuerOperationPersonnelController<CommandeTitreTransport> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;
	
	@Inject protected CommandeTitreTransportService commandeTitreTransportService;
	
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
	public Traitement dernierTraitement(CommandeTitreTransport commandeTitreTransport) {
		return commandeTitreTransport.getTraitable().getDernierTraitement();
	}

	@Override
	public BigDecimal depense(CommandeTitreTransport commandeTitreTransport) {
		return BigDecimal.ZERO;
	}

	@Override
	public String numero(CommandeTitreTransport commandeTitreTransport) {
		return commandeTitreTransport.getNumero();
	}

	@Override
	public Date dateCreation(CommandeTitreTransport commandeTitreTransport) {
		return null;
	}

	@Override
	protected void valider(Collection<CommandeTitreTransport> datas) {
		commandeTitreTransportService.valider(datas);
	}
	
	@Override
	protected Collection<CommandeTitreTransport> data(Collection<NatureDeplacement> natureDeplacements) {
		return commandeTitreTransportService.findATraiter(natureOperationCode());
	}
	
	
	
}
