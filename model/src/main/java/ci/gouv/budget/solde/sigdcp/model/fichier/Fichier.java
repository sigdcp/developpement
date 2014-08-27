package ci.gouv.budget.solde.sigdcp.model.fichier;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

/**
 * Contenu d'un fichier
 * @author christian
 *
 */
@Entity
@Getter @Setter
public class Fichier extends AbstractModel<Long> implements Serializable{

	private static final long serialVersionUID = 129506142716551683L;
	
	@Id @GeneratedValue
	protected Long id;
	
	/**
	 * .jpg,.bmp,.png,...
	 */
	private String extension;//in case we do not have the URI we need to know which kind of data we have
	/**
	 * image/png , ...
	 */
	private String contentType;
	/**
	 * L'url du fichier hors base de donn�es
	 * example : http://notre_serveur/chemin/nom_image
	 */
	private URI uri;//in case we need to point to a file outside the database (File System Storage)
	/**
	 * Le contenu du fichier a stocker dans la base de donn�es
	 */
	private byte[] bytes;//in case we need to point a file inside the database (DB STORAGE)
	
	public Fichier() {}
	
	/*
	 public Fichier(File file) throws FileNotFoundException, IOException{
		init(new FileInputStream(file),FilenameUtils.getExtension(file.getName()));
	} 
	
	public Fichier(InputStream inputStream,String extension) throws IOException{
		init(inputStream,extension);
	}
	
	private void init(InputStream inputStream,String extension) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for(int i=inputStream.read();i!=-1;i=inputStream.read())
			baos.write(i);
		bytes=baos.toByteArray();
		if(extension!=null){
			this.extension = extension.toLowerCase();//better use lower case because of mime type lookup
		}
	}
	*/
	
	public Fichier(Fichier fichier) {
		extension = fichier.extension;
		contentType = fichier.contentType;
		uri = fichier.uri;
		bytes = fichier.bytes;
	}
	
	public InputStream getInputStream(){
		return new ByteArrayInputStream(bytes);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Fichier && ((Fichier)obj).id==id;
	}

}
