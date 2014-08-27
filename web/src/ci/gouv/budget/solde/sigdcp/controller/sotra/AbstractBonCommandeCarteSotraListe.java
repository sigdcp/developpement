package ci.gouv.budget.solde.sigdcp.controller.sotra;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.ui.AbstractEffectuerOperationPersonnelController;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeCarteSotra;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitable;
import ci.gouv.budget.solde.sigdcp.model.traitement.Traitement;
import ci.gouv.budget.solde.sigdcp.service.sotra.CommandeCarteSotraService;

public abstract class AbstractBonCommandeCarteSotraListe extends AbstractEffectuerOperationPersonnelController<CommandeCarteSotra> implements Serializable  {

	private static final long serialVersionUID = -286293555482422433L;
	
	@Inject protected CommandeCarteSotraService commandeCarteSotraService;
	
	@Getter @Setter
	protected Boolean showValide = false,showDateDistributionSotra=false,showDateDistributionDelegue=false;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		rechercherCommande=null;
		natureDeplacement.getCategorie().setDisponible(null);
	}
	
	@Override
	protected String identifierFieldName() {
		return "achatId";
	}

	@Override
	public BigDecimal depense(CommandeCarteSotra commandeCarteSotra) {
		return BigDecimal.ZERO;
	}

	@Override
	public String numero(CommandeCarteSotra commandeCarteSotra) {
		return commandeCarteSotra.getNumero();
	}

	@Override
	public Date dateCreation(CommandeCarteSotra commandeCarteSotra) {
		return null;
	}
	
	@Override
	public Traitable<? extends Traitement> firstOperationTraitable(CommandeCarteSotra commandeCarteSotra) {
		return commandeCarteSotra.getAchat().getTraitable();
	}
	
	@Override
	protected void valider(Collection<CommandeCarteSotra> datas) {
		commandeCarteSotraService.valider(datas);
	}
	
	@Override
	protected Collection<CommandeCarteSotra> data(Collection<NatureDeplacement> natureDeplacements) {
		return commandeCarteSotraService.findATraiter(natureOperationCode());
	}
	
	@Override
	protected void detailsOutcomeParameters(Map<String, List<String>> parameters, CommandeCarteSotra commandeCarteSotra) {
		addParameters(parameters, webConstantResources.getRequestParamAchatCarteSotra(), commandeCarteSotra.getAchatId()+"");
	}
	
	
	
}
