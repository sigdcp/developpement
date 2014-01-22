package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import lombok.Getter;

import org.apache.commons.beanutils.BeanUtils;

import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;

@Getter
public class TraitementDto extends Traitement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3571075679263115724L;
	
	public TraitementDto(Traitement traitement) {
		try {
			BeanUtils.copyProperties(this, traitement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
