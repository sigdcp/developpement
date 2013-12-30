/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/

package ci.gouv.budget.solde.sigdcp.model.identification;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ci.gouv.budget.solde.sigdcp.model.AbstractModel;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.geographie.Contact;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;

@Getter @Setter 
@Entity @AllArgsConstructor
public class InfosInscriptionPersonne  extends AbstractModel<Long>  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	
	/*
	 * Toutes personnes
	 */
	
	private String nom;
	
	private String prenoms;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateNaissance;
	
	@Embedded
	private Contact contact = new Contact();
	
	@Enumerated(EnumType.ORDINAL)
	private Sexe sexe;
	
	@ManyToOne
	private Localite nationalite;
	
	/*
	 * Agents de l'état
	 */
	@ManyToOne
	private TypeAgentEtat type;
	
	private String matricule;
	
	@ManyToOne
	private Profession profession;
	
	/*
	 * Non Agent de l'état
	 */
	
	@OneToOne(cascade=CascadeType.ALL)
	private PieceJustificative pieceIdentite = new PieceJustificative();
	
	public InfosInscriptionPersonne() {}
	
	public String getNomPrenoms(){
		return nom+" "+prenoms;
	}
}