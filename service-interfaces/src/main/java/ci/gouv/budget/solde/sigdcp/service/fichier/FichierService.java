package ci.gouv.budget.solde.sigdcp.service.fichier;

import java.io.IOException;

import ci.gouv.budget.solde.sigdcp.model.fichier.Fichier;
import ci.gouv.budget.solde.sigdcp.service.AbstractService;

public interface FichierService extends AbstractService<Fichier,Long> {

	String findContentTypeByExtension(String extension);
	
	Fichier convertir(byte[] bytes, String nom) throws IOException;
	
}
