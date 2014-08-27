package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ci.gouv.budget.solde.sigdcp.service.resources.ServiceConstantResources;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Signature implements Serializable {

	private static final long serialVersionUID = -4923781309028350477L;

	private ServiceConstantResources serviceConstantResources;
	
	private List<SignatureDetails> details = new ArrayList<SignatureDetails>(){
		private static final long serialVersionUID = 8504609613089121364L;
		@Override
		public boolean add(SignatureDetails e){
			boolean r;
			if(r = super.add(e)){
				if(Boolean.TRUE.equals(acceptable) && Boolean.FALSE.equals(e.acceptable))
					acceptable = Boolean.FALSE;
			}
			return r;
		}
	};
	private Boolean acceptable=Boolean.TRUE;
	
	public Signature(ServiceConstantResources serviceConstantResources) {
		super();
		this.serviceConstantResources = serviceConstantResources;
	}
	
	public Signature add(String libelle,Object vsig,Object vsys){
		String valeurSignature = valueToString(vsig);
		String valeurSysteme = valueToString(vsys);
		/*
		Boolean ok;
		if(valeurSignature==null)
			ok = Boolean.FALSE;
		else
			ok = valeurSignature.equals(valeurSysteme);
		*/
		details.add(new SignatureDetails(libelle,valeurSignature, valeurSysteme, valeurSignature==null?Boolean.FALSE:valeurSignature.equals(valeurSysteme)));
		return this;
	}
	
	private String valueToString(Object object){
		if(object==null)
			return null;
		if(object instanceof Date)
			return serviceConstantResources.dateAsString((Date) object);
		return object.toString();
	}
	
	/**/
	@Getter @Setter @AllArgsConstructor
	public static class SignatureDetails implements Serializable{

		private static final long serialVersionUID = -5493219863280060904L;
		
		private String libelle;
		private String valeurSignature;
		private String valeurSysteme;
		private Boolean acceptable;
	}
	
}
