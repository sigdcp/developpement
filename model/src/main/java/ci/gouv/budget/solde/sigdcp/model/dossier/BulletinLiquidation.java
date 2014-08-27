/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteCalculee;

@Getter @Setter 
@Entity 
//@Table(name="BULLETINLIQ")
public class BulletinLiquidation  extends PieceProduite  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public enum AspectLiquide{DEMANDE,TITRE_TRANSPORT}
	
	/**
	 * Null signifie pas porté sur bordereau
	 */
	@ManyToOne
	private BordereauTransmission bordereauTransmission;
	
	@Column(precision=10,scale=4)
	private BigDecimal pourcentage;
	
	@Column(precision=10,scale=2,name="BL_MONTANT")
	private BigDecimal montant;
	
	/**
	 * Le dossier concerné
	 */
	@ManyToOne
	protected Dossier dossier;
	
	@Enumerated(EnumType.STRING)
	private AspectLiquide aspect;
	
	@Transient private Collection<IndemniteCalculee> indemniteCalculees = new ArrayList<>();
	@Transient Date dateCreation;
	
	public BulletinLiquidation() {
		super();
	}

	public BulletinLiquidation(Dossier dossier,String numero, TypePieceProduite type, Date dateEtablissement,BigDecimal montant) {
		super(numero, type,dateEtablissement);
		this.dossier = dossier;
		this.montant=montant;
	}
	
	
	
}