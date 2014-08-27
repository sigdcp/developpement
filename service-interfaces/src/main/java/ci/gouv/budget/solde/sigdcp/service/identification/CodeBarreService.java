package ci.gouv.budget.solde.sigdcp.service.identification;

public interface CodeBarreService {

	/**
	 * Encode une chaine de caractere sous un format image
	 * @param value
	 * @return
	 */
	byte[] encoder(String value);
	
	/**
	 * Decode une image en une chaine de caractères
	 * @param codeBarre
	 * @return
	 */
	String decoder(byte[] codeBarre);
}
