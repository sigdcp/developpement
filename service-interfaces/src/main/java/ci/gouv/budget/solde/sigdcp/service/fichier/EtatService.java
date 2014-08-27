package ci.gouv.budget.solde.sigdcp.service.fichier;

import java.io.InputStream;
import java.util.Collection;

import ci.gouv.budget.solde.sigdcp.model.dossier.Document;


public interface EtatService {

	<T> byte[] build(Class<T> aClass,InputStream templateInputStream,Collection<T> dataSource);
	
	byte[] build(Document document);
	
	byte[] buildBulletinLiquidation(Long id);

}
