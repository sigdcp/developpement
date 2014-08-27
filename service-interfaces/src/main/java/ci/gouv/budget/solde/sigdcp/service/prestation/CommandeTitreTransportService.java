package ci.gouv.budget.solde.sigdcp.service.prestation;

import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.prestation.CommandeTitreTransport;
import ci.gouv.budget.solde.sigdcp.model.prestation.PrestataireDemandeCotationMission;
import ci.gouv.budget.solde.sigdcp.service.dossier.AbstractPieceProduiteService;

public interface CommandeTitreTransportService extends AbstractPieceProduiteService<CommandeTitreTransport> {
	CommandeTitreTransport nouveau(PrestataireDemandeCotationMission cotation, String natureOperationCode);

	Collection<CommandeTitreTransport> findByPrestataire(Long prestataire);
}
