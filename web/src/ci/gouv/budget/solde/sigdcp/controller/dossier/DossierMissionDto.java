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
	private Boolean editable = Boolean.FALSE,pieceEditable;
	private String matricule;

	public DossierMissionDto(MissionExecutee missionExecutee,DossierMission dossierMission,Collection<PieceJustificative> pieceJustificatives,PieceJustificativeService pieceJustificativeService,FichierService fichierService,Boolean peditable) {
		this(missionExecutee,dossierMission.getBeneficiaire().getMatricule(),dossierMission.getBeneficiaire(),dossierMission,pieceJustificativeService,fichierService,peditable);
		//for(PieceJustificative pieceJustificative : pieceJustificatives)
		//	pieceJustificativeUploader.addPieceJustificative(pieceJustificative, peditable);
	}
	
	public DossierMissionDto(MissionExecutee missionExecutee,String matricule,AgentEtat agentEtat,PieceJustificativeService pieceJustificativeService,FichierService fichierService,Boolean peditable) {
		this(missionExecutee,matricule,agentEtat,new DossierMission(missionExecutee.getDeplacement()),pieceJustificativeService,fichierService,peditable);
	}
	
	public DossierMissionDto(MissionExecutee missionExecutee,String matricule,AgentEtat agentEtat,DossierMission dossierMission ,PieceJustificativeService pieceJustificativeService,FichierService fichierService,Boolean peditable) {
		super();
		this.matricule=matricule;
		this.dossierMission = dossierMission;
		if(this.dossierMission==null)
			this.dossierMission = new DossierMission();
		
		if(this.dossierMission.getDeplacement()==null)
			this.dossierMission.setDeplacement(missionExecutee.getDeplacement());
		
		this.pieceEditable = peditable;
		this.editable = agentEtat==null;
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
			pieceJustificativeUploader.addPieceJustificative(pieceJustificative,pieceEditable);
		pieceJustificativeUploader.update();
		
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
