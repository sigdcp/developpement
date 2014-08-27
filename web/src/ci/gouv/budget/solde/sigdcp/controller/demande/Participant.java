package ci.gouv.budget.solde.sigdcp.controller.demande;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.controller.fichier.PieceJustificativeUploader;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.service.dossier.DossierMissionService;
import ci.gouv.budget.solde.sigdcp.service.dossier.PieceJustificativeService;
import ci.gouv.budget.solde.sigdcp.service.fichier.FichierService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.ValidationPolicy;

@Getter @Setter
public class Participant implements Serializable {

	private static final long serialVersionUID = -4146491673047255659L;

	private DossierMission dossier;
	
	private PieceJustificativeUploader pieceJustificativeUploader = new PieceJustificativeUploader();
	private PieceJustificativeService pieceJustificativeService;
	private DossierMissionService dossierMissionService;
	
	private String libelle;
	private Boolean editable = Boolean.FALSE,pieceEditable,transmis;
	private String matricule;
	private CourrierDto courrierDto;
	
	public Participant(DossierMissionService dossierMissionService,MissionExecutee missionExecutee,DossierMission dossier ,PieceJustificativeService pieceJustificativeService,FichierService fichierService,ValidationPolicy validationPolicy,Boolean peditable) {
		super();
		this.dossierMissionService = dossierMissionService;
		this.matricule=dossier.getBeneficiaire().getMatricule();
		this.dossier = dossier;
		
		if(this.dossier.getDeplacement()==null)
			this.dossier.setDeplacement(missionExecutee.getDeplacement());
		
		this.pieceEditable = peditable;
		this.editable = dossier.getBeneficiaire().getId()==null;
		libelle=matricule;
		if(dossier.getBeneficiaire().getId()==null){
			
		}else{
			
			libelle += " "+dossier.getBeneficiaire().getNomPrenoms();
		}
		if(dossier.getTraitable().getDernierTraitement()!=null)
			transmis = Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE.equals(dossier.getTraitable().getDernierTraitement().getOperation().getNature().getCode());
		pieceJustificativeUploader.setFichierService(fichierService);
		pieceJustificativeUploader.setEditable(pieceEditable);
		pieceJustificativeUploader.setAllowedFileTypes(StringUtils.join(validationPolicy.getPieceExtensions(),"|"));
		this.pieceJustificativeService = pieceJustificativeService;
		updatePieceJustificatives();
	}
	
	protected void updatePieceJustificatives(){
		dossierMissionService.mettreAJourPiecesJustificatives(Arrays.asList(dossier));
		pieceJustificativeUploader.clear();
		for(PieceJustificative pieceJustificative : dossier.getPieceJustificatives()){
			Boolean imprimable = Boolean.TRUE.equals(pieceJustificative.getModel().getConfig().getDerivee());
			if(!pieceJustificative.getModel().getTypePieceJustificative().getCode().equals(Code.TYPE_PIECE_COMMUNICATION)){
				
				if(pieceJustificative.getModifiable()==null)
					pieceJustificativeUploader.addPieceJustificative(pieceJustificative, pieceEditable ,imprimable);
				else
					pieceJustificativeUploader.addPieceJustificative(pieceJustificative,Boolean.TRUE.equals(pieceJustificative.getModifiable()) || !Boolean.FALSE.equals(pieceJustificative.getModifiable()),
							imprimable);
			}
		}
		pieceJustificativeUploader.update();
		
	}
	
	public AgentEtat getBeneficiaire() {
		return dossier.getBeneficiaire();
	}
	
	public DossierMission getDossierMission(){
		return (DossierMission) dossier;
	}
	
	public Boolean getNouveau(){
		return dossier.getId()==null;
	}
	
	@Override
	public int hashCode() {
		return matricule.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return matricule.equalsIgnoreCase(((Participant)obj).getMatricule());
	}
	
}
