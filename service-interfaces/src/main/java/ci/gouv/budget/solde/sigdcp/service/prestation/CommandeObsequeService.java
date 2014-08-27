package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeObseque;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationObseque;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractPieceProduiteService;

public interface CommandeObsequeService extends AbstractPieceProduiteService<CommandeObseque> {
	CommandeObseque nouveau(PrestataireDemandeCotationObseque cotation, String natureOperationCode);
	
	Collection<CommandeObseque> findByPrestataire(Long prestataire);
}
