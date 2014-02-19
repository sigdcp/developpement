package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeService;
import ci.gouv.budget.solde.sigdcp.service.fichier.FichierService;

@Getter @Setter
public class DossierMissionDto implements Serializable {

	private static final long serialVersionUID = -4146491673047255659L;

	private DossierMission dossierMission;
	
	private PieceJustificativeUploader pieceJustificativeUploader = new PieceJustificativeUploader();
	private PieceJustificativeService pieceJustificativeService;
	
	private String libelle;
	private Boolean editable = Boolean.FALSE;
	private String matricule;

	public DossierMissionDto(MissionExecutee missionExecutee,String matricule,AgentEtat agentEtat,PieceJustificativeService pieceJustificativeService,FichierService fichierService) {
		super();
		this.matricule=matricule;
		dossierMission = new DossierMission();
		dossierMission.setDeplacement(missionExecutee.getDeplacement());
		editable = agentEtat==null;
		libelle=matricule;
		if(agentEtat==null){
			dossierMission.setBeneficiaire(new AgentEtat());
			dossierMission.getBeneficiaire().setMatricule(matricule);
		}else{
			dossierMission.setBeneficiaire(agentEtat);
			libelle += " "+agentEtat.getNomPrenoms();
		}
		pieceJustificativeUploader.setFichierService(fichierService);
		this.pieceJustificativeService = pieceJustificativeService;
		updatePieceJustificatives();
	}
	
	protected void updatePieceJustificatives(boolean first){
		
		Collection<PieceJustificative> pieceJustificatives;
		//if(first)
			pieceJustificatives = pieceJustificativeService.findByDossier(dossierMission, null, null);
		//else
		//	pieceJustificatives = pieceJustificativeService.findByDossier(dossierMission,pieceJustificativeUploader.getPieceJustificatives(), null);
		
		pieceJustificativeUploader.clear();
		for(PieceJustificative pieceJustificative : pieceJustificatives)
			pieceJustificativeUploader.addPieceJustificative(pieceJustificative);
		pieceJustificativeUploader.update();
		System.out.println(pieceJustificativeUploader.getCollection());
	}
	
	protected void updatePieceJustificatives(){
		updatePieceJustificatives(false);
	}

	public AgentEtat getBeneficiaire() {
		return dossierMission.getBeneficiaire();
	}
	
	@Override
	public int hashCode() {
		return matricule.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return matricule.equalsIgnoreCase(((DossierMissionDto)obj).getMatricule());
	}
	
}
