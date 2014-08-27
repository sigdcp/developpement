/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/

package ci.gouv.budget.solde.sigdcp.model.identification.souscription;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;

@Getter @Setter 
@Entity @AllArgsConstructor
@Table(name="INFOSSOUSCPE")
public class InfosSouscriptionComptePersonne  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue private Long id;
		
	@ManyToOne(cascade=CascadeType.PERSIST) @Valid
	private Personne personne = new Personne();
	
	/*
	 * Agents de l'état
	 */
	//@NotNull(groups=Client.class)
	@ManyToOne private TypeAgentEtat type;
	
	//@NotNull(groups=Client.class)
	private String matricule;
	
	public InfosSouscriptionComptePersonne() {}
	
}