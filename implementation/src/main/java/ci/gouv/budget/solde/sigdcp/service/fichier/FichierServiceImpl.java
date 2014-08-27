package ci.gouv.budget.solde.sigdcp.service.fichier;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;

import lombok.extern.java.Log;

import org.apache.commons.lang3.ArrayUtils;

import ci.gouv.budget.solde.sigdcp.dao.fichier.FichierDao;
import ci.gouv.budget.solde.sigdcp.model.fichier.Fichier;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;

@Log
public class FichierServiceImpl extends DefaultServiceImpl<Fichier, Long> implements FichierService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	private static final String[] IMAGE_EXTENSIONS = {"jpg","jpeg","gif","png"};
	
	@Inject
	public FichierServiceImpl(FichierDao dao) {
		super(dao);
	}
	
	@Override
	public String findContentTypeByExtension(String extension) {
		if(ArrayUtils.contains(IMAGE_EXTENSIONS,extension))
			return "image/"+extension;
		log.severe("No content type found for extension "+extension);
		return null;
	}
	
	@Override
	public Fichier convertir(byte[] bytes, String nom) throws IOException {
		if(bytes==null || bytes.length==0)
			return null;
		Fichier fichier = new Fichier();
		fichier.setBytes(bytes);
		int i = nom.lastIndexOf('.');
		if (i > 0) {
		    fichier.setExtension(nom.substring(i+1));
			if(fichier.getExtension()!=null){
				fichier.setExtension(fichier.getExtension().toLowerCase());//better use lower case because of mime type lookup
			}
			fichier.setContentType(findContentTypeByExtension(fichier.getExtension()));
		}
		return fichier;
	}
	
}
