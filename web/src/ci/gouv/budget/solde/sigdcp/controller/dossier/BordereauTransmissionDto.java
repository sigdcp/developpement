package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;

@Getter @Setter
public class BordereauTransmissionDto extends TraitementDto implements Serializable {

	private static final long serialVersionUID = -1928307348790827137L;

	private Integer nombreTransmission,montant;
	private String nature;
	
	public BordereauTransmissionDto(Traitement traitement,Integer nombreTransmission,String nature,Integer montant){
		super(traitement);
		this.nombreTransmission = nombreTransmission;
		this.nature=nature;
		this.montant = montant;
	}
	
	
}
