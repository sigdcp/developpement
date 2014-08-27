/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.identification.souscription;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.identification.Souscription;

@Getter @Setter 
@Entity @DiscriminatorValue("GestionnaireCarteSotra")
//@Table(name="SOUSGE")
public class SouscriptionGestionnaireCarteSotra  extends Souscription  implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Identification de l'agent de l'état qui souscrit
	 */
	@ManyToOne private AgentEtat gestionnaire;
	
	/*
	 * Infos sur la section a gérer
	 */
	@ManyToOne private Section ministere;
	private String libelleSection;
	private String sigleSection;
	
	@OneToOne(cascade=CascadeType.ALL)
	private PieceJustificative decretCreationSection=new PieceJustificative();
	
	@OneToOne(cascade=CascadeType.ALL)
	private PieceJustificative noteService=new PieceJustificative();
	
	/**
	 * Identification de son intérimaire
	 */
	@ManyToOne private AgentEtat interimaire;
		
	public SouscriptionGestionnaireCarteSotra() {}

	public SouscriptionGestionnaireCarteSotra(String code, Date dateCreation,
			Date dateValidation, Boolean acceptee, AgentEtat gestionnaire,
			Section ministere, String libelleSection, String sigleSection,
			AgentEtat interimaire) {
		super(code, dateCreation, dateValidation, acceptee);
		this.gestionnaire = gestionnaire;
		this.ministere = ministere;
		this.libelleSection = libelleSection;
		this.sigleSection = sigleSection;
		this.interimaire = interimaire;
	}

	public String getNomSectionAGerer(){
		if(StringUtils.isEmpty(sigleSection))
			return libelleSection;
		return libelleSection+"("+sigleSection+")";
	}

	
	
	
}