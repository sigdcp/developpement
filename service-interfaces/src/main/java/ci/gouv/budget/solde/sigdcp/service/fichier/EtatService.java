package ci.gouv.budget.solde.sigdcp.service.fichier;

import java.io.InputStream;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;


public interface EtatService {

	<T> byte[] build(Class<T> aClass,InputStream templateInputStream,Collection<T> dataSource);
	
	byte[] build(PieceJustificative pieceJustificative);
	
}
