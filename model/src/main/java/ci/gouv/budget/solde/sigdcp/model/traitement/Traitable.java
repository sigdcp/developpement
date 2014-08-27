/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.traitement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Embeddable
public class Traitable<TRAITEMENT extends Traitement> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * -------------------------------------------------------------- Attributs persistés --------------------------------------------------------------------------
	 */
	/*
	 * Le traitement réalisé lors de la derniere operation
	 */
	@ManyToOne private Traitement dernierTraitement;
 
	/*
	 * ------------------------------------------------------ Attributs non persistés - Concept de vue -------------------------------------------------------------
	 */
	/*
	 * L'operation a éffectuer sur cette instance
	 */
	@Transient private NatureOperation natureOperation;
	/*
	 * Le traitement éffectué sur cette instance
	 */
	@Transient private TRAITEMENT traitement;
	/*
	 * Tous les traitements réalisé sur cet objet
	 */
	@Transient private Collection<TRAITEMENT> historiques = new LinkedList<>();
	
	/**
	 * Actions possibles
	 */
	@Transient private List<ValidationType> validationTypes = new ArrayList<>();
	
	
	//@Transient private String uiObservation;
	
	/* Fonctions racourcies */
	
	public Boolean isNatureOperationCode(String code){
		return code.equals(natureOperation.getCode());
	}
	
	
	
}