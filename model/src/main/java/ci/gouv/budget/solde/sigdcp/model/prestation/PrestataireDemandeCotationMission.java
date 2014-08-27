/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.fichier.Fichier;

@Getter @Setter 
@Entity
@Table(name="PRESTDEMCOTM")
public class PrestataireDemandeCotationMission extends AbstractModel<PrestataireDemandeCotationMissionId> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private PrestataireDemandeCotationMissionId id;
	
	@Transient private MissionExecutee missionExecutee;
	@Transient private Prestataire prestataire;
	@Transient private Collection<Object[]> classeVoyages=new ArrayList<>();
	
	/**
	 * Réponse
	 */
	@OneToOne(cascade=CascadeType.ALL)
	private Fichier fichier;
	@Temporal(TemporalType.DATE)
	@Column(name="DAT")
	private Date date;
	
	public PrestataireDemandeCotationMission() {}

	public PrestataireDemandeCotationMission(PrestataireDemandeCotationMissionId id, MissionExecutee missionExecutee, Prestataire prestataire) {
		super();
		this.id = id;
		this.missionExecutee = missionExecutee;
		this.prestataire = prestataire;
	}

	public PrestataireDemandeCotationMission(PrestataireDemandeCotationMissionId id, Fichier fichier, Date date) {
		super();
		this.id = id;
		this.fichier = fichier;
		this.date = date;
	}
	
	
	
}