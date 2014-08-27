package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

@Getter
public enum ValidationExceptionType {
	
	_DEFAULT(),
	MATRICULE_FORMAT("matricule"),
	MATRICULE_NON_REFERENCE("matricule"),
	NATIONALITE("nationalite"),
	DATE_NAISSANCE_MAJEUR("date.naissance.majorite"),
	PASSWORD("motpasse"),
	PASSWORD_CONFIRMATION("motpasse.confirmation"),
	USERNAME_UNIQUE("nomutilisateur.unicite"),
	DATE_PRISE_SERVICE("date.priseservice"),
	DATE_FIN_SERVICE("date.finservice"),
	DATE_MISE_STAGE("date.misestage"),
	EMAIL_OCCUPE("email.unicite"),
	EMAIL_REFERENCE("email.reference"),
	TELEPHONE("telephone"),
	LOCALITE_DEPART("localite.depart"),
	LOCALITE_ARRIVEE("localite.arrivee"),
	DATE_DEPART("date.depart"),
	DATE_ARRIVEE("date.arrivee"),
	DATE_DISTRIBUTION("date.distribution"),
	DATE_DEPOT_DOSSIER("date.depotdossier"),
	DATE_RETOUR("date.retour"),
	DATE_DECES("date.deces"),
	DATE_MARIAGE("date.mariage"),
	DATE_RETRAITE("date.retraite"),
	DATE_ETABLISSEMENT_PIECE("date.etablissement.piece"),
	
	DEPART_RETRAITE_ENCOURS("departretraite.encours"),
	DEPART_RETRAITE_TRAITE("departretraite.valide"),
	
	FICHIER_OBLIGATOIRE("fichier.obligatoire"),
	FICHIER_EXTENSION("fichier.extension"),
	FICHIER_TAILLE("fichier.taille"),
	
	
	/*---------------------------------------------------------------------------------------*/
	;
	
	private String id;

	private ValidationExceptionType() {
		this(null);
	}
	
	private ValidationExceptionType(String id) {
		this.id = "error.validation."+(StringUtils.isEmpty(id)?"_default":id);
	}
	
	@Override
	public String toString() {
		return id;
	}
}
