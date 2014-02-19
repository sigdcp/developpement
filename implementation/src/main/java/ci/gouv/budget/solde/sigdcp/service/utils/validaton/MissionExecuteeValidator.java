package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.AssertTrue;

import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;

@Setter
public class MissionExecuteeValidator extends AbstractValidator<MissionExecutee> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	private Collection<DossierMission> dossiers;
	private Collection<Collection<PieceJustificative>> pieceJustificatives;
	
	@AssertTrue(message="La date d'arrivée n'est pas valide",groups=Client.class)
	public boolean isValidDateFin() {
		try{
			validationPolicy.validateDateArrivee(object.getDeplacement().getDateDepart(),object.getDeplacement().getDateArrivee());
			return true;
		}catch(Exception exception){
			return false;
		}
	}
	

	
}
