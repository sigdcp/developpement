package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;

@Entity @Getter @Setter 
@AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of="compteContribuable",callSuper=false)
@Table(name="PRESTATAIREREF")
public class PrestataireReference extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = 4242485379623860202L;

	@Id private Long compteContribuable;
	
	private String sigle;
	private String siege;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateHabilitation;	
	private Boolean habilitationValide;
	
	@ManyToOne
	@JoinColumn(name="prestataire_type")
	private TypePrestataire type;
	
}
