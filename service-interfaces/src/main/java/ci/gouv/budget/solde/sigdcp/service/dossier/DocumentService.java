package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.InputStream;

import ci.gouv.budget.solde.sigdcp.model.dossier.Document;
import ci.gouv.budget.solde.sigdcp.model.signature.SignatureDocumentGenereDossier;

public interface DocumentService extends AbstractDocumentService<Document> {

	/**
	 * Produit une signature unique et chiffrée en fonction du document
	 * @param document
	 * @return
	 */
	String signature(Document document);
	
	/**
	 * Ramene en clair la signature d'un  QR Code
	 * @param codeBarre
	 * @return
	 */
	String decoderSignature(String signature);
	
	SignatureDocumentGenereDossier formatterSignature(String signature);
	
	InputStream genererCodeBarre(Document document);
	
	/**
	 * Ramene le document correspondant a la signature
	 * @param signature
	 * @return
	 */
	Document findBySignature(String signature);
}
