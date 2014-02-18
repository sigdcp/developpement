package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;

@Getter @Setter
public class DossierMissionDto implements Serializable {

	private static final long serialVersionUID = -4146491673047255659L;

	private DossierMission dossierMission;
	
	@Inject
	private PieceJustificativeUploader pieceJustificativeUploader;

	private String libelle;

	public DossierMissionDto(DossierMission dossierMission) {
		super();
		this.dossierMission = dossierMission;
		libelle = dossierMission.toString();
	}
	
	
	
}
