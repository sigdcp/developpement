/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.calendrier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.indemnite.FraisMission;

@Getter @Setter 
@Entity
//@Table(name="MISSIONEX")
public class MissionExecutee  extends Mission implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne private Deplacement deplacement = new Deplacement();
	
	@OneToOne @NotNull
	private DossierMission dossierDuResponsable;
	
	@ManyToOne
	private MissionProgrammee programmee;
	
	@Embedded
	private FraisMission indemnite = new FraisMission();
	
	@ManyToOne
	private AgentEtat organisateur;
	
	@Transient private PieceJustificative communication;
	@Transient private String natureOperationCode;
	@Transient private Boolean tousPresent=true;
	@Transient private TypeDepense typeDepense;
	@Transient private Collection<DossierMission> dossiers = new ArrayList<>();
	
	public MissionExecutee() {}

	public MissionExecutee(String designation, DossierMission dossierDuResponsable,AgentEtat organisateur) {
		super(designation);
		this.dossierDuResponsable = dossierDuResponsable;
		this.organisateur = organisateur;
	}
	
	
}