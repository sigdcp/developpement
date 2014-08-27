/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.prestation;

import java.io.Serializable;
import java.math.BigDecimal;
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
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.fichier.Fichier;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Cercueil;

@Getter @Setter 
@Entity
@Table(name="PRESTDEMCOTOBS")
public class PrestataireDemandeCotationObseque extends AbstractModel<PrestataireDemandeCotationObsequeId> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private PrestataireDemandeCotationObsequeId id;
	
	@Transient private DossierObseques dossier;
	@Transient private Prestataire prestataire;
	@Transient private Cercueil cercueil;
	
	/**
	 * Réponse
	 */
	private BigDecimal montantTransport;
	private BigDecimal montantCercueil;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Fichier fichier;
	@Temporal(TemporalType.DATE)
	@Column(name="DAT")
	private Date date;
	
	public PrestataireDemandeCotationObseque() {}

	public PrestataireDemandeCotationObseque(PrestataireDemandeCotationObsequeId id, DossierObseques dossier, Prestataire prestataire) {
		super();
		this.id = id;
		this.dossier = dossier;
		this.prestataire = prestataire;
	}

	public PrestataireDemandeCotationObseque(PrestataireDemandeCotationObsequeId id, Fichier fichier, Date date) {
		super();
		this.id = id;
		this.fichier = fichier;
		this.date = date;
	}
	
	
	
}