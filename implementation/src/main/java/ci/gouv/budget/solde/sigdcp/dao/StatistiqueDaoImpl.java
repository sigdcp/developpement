package ci.gouv.budget.solde.sigdcp.dao;

import java.math.BigDecimal;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.Section;
import ci.gouv.budget.solde.sigdcp.model.indemnite.Groupe;
import ci.gouv.budget.solde.sigdcp.model.indemnite.TypeClasseVoyage;
import ci.gouv.budget.solde.sigdcp.model.prestation.Prestataire;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;

public class StatistiqueDaoImpl implements StatistiqueDao {

	@Inject
	protected EntityManager entityManager;
	
	@Override
	public Long countDossierByLocalite(Boolean depart,Localite localite,Collection<NatureDeplacement> natureDeplacements) {
		return entityManager.createQuery(
				"SELECT COUNT(deplacement) "
				+ "FROM deplacement deplacement "
				+ "WHERE deplacement.localite"+(Boolean.TRUE.equals(depart)?"Depart":"Arrivee")+" = :localite "
						+ "AND deplacement.nature IN :natures",
					Long.class).setParameter("localite", localite).setParameter("natures", natureDeplacements).getSingleResult();
	}

	@Override
	public Long countVoyageurByLocalite(Boolean depart, Localite localite, Collection<NatureDeplacement> natureDeplacements) {
		return entityManager.createQuery(
				"SELECT COUNT(dossier) "
				+ "FROM Dossier dossier "
				+ "WHERE dossier.deplacement.localite"+(Boolean.TRUE.equals(depart)?"Depart":"Arrivee")+" = :localite "
						+ "AND dossier.deplacement.nature IN :natures",
					Long.class).setParameter("localite", localite).setParameter("natures", natureDeplacements).getSingleResult();
	}

	@Override
	public Long sumDepenseByLocalite(Boolean depart, Localite localite, Collection<NatureDeplacement> natureDeplacements) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.montantIndemnisation) "
				+ "FROM Dossier dossier "
				+ "WHERE dossier.deplacement.localite"+(Boolean.TRUE.equals(depart)?"Depart":"Arrivee")+" = :localite "
						+ "AND dossier.deplacement.nature IN :natures",
					BigDecimal.class).setParameter("localite", localite).setParameter("natures", natureDeplacements).getSingleResult();
		return r==null?0:r.longValue();
	}

	@Override
	public Long sumFraisByLocalite(Boolean depart, Localite localite, Collection<NatureDeplacement> natureDeplacements) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.frais.transport) "
				+ "FROM DossierMission dossier "
				+ "WHERE dossier.deplacement.localite"+(Boolean.TRUE.equals(depart)?"Depart":"Arrivee")+" = :localite "
						+ "AND dossier.deplacement.nature IN :natures",
					BigDecimal.class).setParameter("localite", localite).setParameter("natures", natureDeplacements).getSingleResult();
		return r==null?0:r.longValue();
	}
	
/*
 * Lettre d'avance
 * 
 */


	@Override
	public Long countLettreAvanceBySection(Section section) {
		return entityManager.createQuery(
				"SELECT COUNT(lettre) "
				+ "FROM LettreAvance lettre "
				+ "WHERE lettre.beneficiaire = :section ",Long.class).setParameter("section", section).getSingleResult();
	}


	@Override
	public Long sumFraisLettreAvanceBySection(Section section) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(lettre.montant) "
				+ "FROM LettreAvance lettre "
				+ "WHERE lettre.beneficiaire = :section ",BigDecimal.class).setParameter("section", section).getSingleResult();
		
		return r==null?0:r.longValue();
	}

	
	/*
	 * Section
	 * 
	 */

	@Override
	public Long countSituationDossierBySection(Section section, Collection<NatureDeplacement> natureDeplacements) {
		return entityManager.createQuery(
				"SELECT COUNT(d) "
				+ "FROM Deplacement d "
				+ "WHERE EXISTS (SELECT td FROM TraitementDossier td WHERE td.dossier.deplacement = d AND td.dossier.service = :section "
				+ "AND td.dossier.deplacement.nature IN :natures AND td.operation.nature.code = :nop AND td.statut.code = :statut)",Long.class)
				.setParameter("section", section)
				.setParameter("natures", natureDeplacements)
				.setParameter("nop", Code.NATURE_OPERATION_VALIDATION_BL)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getSingleResult();
		
	}

	@Override
	public Long countSituationVoyageurBySection(Section section, Collection<NatureDeplacement> natureDeplacements) {
		return entityManager.createQuery(
				"SELECT COUNT(dossier) "
				+ "FROM Dossier dossier "
				+ "WHERE EXISTS (SELECT td FROM TraitementDossier td WHERE td.dossier = dossier AND td.dossier.service = :section "
				+ "AND td.dossier.deplacement.nature IN :natures AND td.operation.nature.code = :nop AND td.statut.code = :statut)",Long.class)
				.setParameter("section", section)
				.setParameter("natures", natureDeplacements)
				.setParameter("nop", Code.NATURE_OPERATION_VALIDATION_BL)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getSingleResult();
	}

	@Override
	public Long sumSituationDepenseBySection(Section section, Collection<NatureDeplacement> natureDeplacements) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.montantIndemnisation) "
				+ "FROM Dossier dossier "
				+ "WHERE EXISTS (SELECT td FROM TraitementDossier td WHERE td.dossier = dossier AND td.dossier.service = :section "
				+ "AND td.dossier.deplacement.nature IN :natures AND td.operation.nature.code = :nop AND td.statut.code = :statut)",BigDecimal.class)
				.setParameter("section", section)
				.setParameter("natures", natureDeplacements)
				.setParameter("nop", Code.NATURE_OPERATION_VALIDATION_BL)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getSingleResult();
		
		return r==null?0:r.longValue();
	}

	@Override
	public Long sumSituationFraisBySection(Section section, Collection<NatureDeplacement> natureDeplacements) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.frais.transport) "
				+ "FROM DossierMission dossier "
				+ "WHERE EXISTS (SELECT td FROM TraitementDossier td WHERE td.dossier = dossier AND td.dossier.service = :section "
				+ "AND td.dossier.deplacement.nature IN :natures AND td.operation.nature.code = :nop AND td.statut.code = :statut)",BigDecimal.class)
				.setParameter("section", section)
				.setParameter("natures", natureDeplacements)
				.setParameter("nop", Code.NATURE_OPERATION_VALIDATION_BL)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getSingleResult();
		
		return r==null?0:r.longValue();
	}
	
	/**
	 * Nature de deplacement
	 */

	@Override
	public Long countDossierByNatureOperationByStatut(NatureDeplacement nd, NatureOperation nop, Statut statut) {
		return entityManager.createQuery(
				"SELECT COUNT(d) FROM Deplacement d WHERE d.nature = :nature "
				+ "AND EXISTS (SELECT td FROM TraitementDossier td WHERE td.dossier.deplacement=d AND td.operation.nature = :nop AND td.statut = :statut)",
					Long.class).setParameter("nature", nd).setParameter("nop", nop).setParameter("statut", statut).getSingleResult();
	}

	@Override
	public Long countVoyageurByNatureOperationByStatut(NatureDeplacement nd, NatureOperation nop, Statut statut) {
		return entityManager.createQuery(
				"SELECT COUNT(td) "
				+ "FROM TraitementDossier td WHERE td.dossier.deplacement.nature = :nature AND td.operation.nature = :nop AND td.statut = :statut",
					Long.class).setParameter("nature", nd).setParameter("nop", nop).setParameter("statut", statut).getSingleResult();
	}

	@Override
	public Long sumDepenseByNatureOperationByStatut(NatureDeplacement nd, NatureOperation nop, Statut statut) {

		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(td.dossier.montantIndemnisation) "
						+ "FROM TraitementDossier td WHERE td.dossier.deplacement.nature = :nature AND td.operation.nature = :nop AND td.statut = :statut",
						BigDecimal.class).setParameter("nature", nd).setParameter("nop", nop).setParameter("statut", statut).getSingleResult();
		
		return r==null?0:r.longValue();
	}

	@Override
	public Long sumFraisByNatureOperationByStatut(NatureDeplacement nd, NatureOperation nop, Statut statut) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.frais.transport) "
						+ "FROM DossierMission dossier WHERE dossier.deplacement.nature = :nature "
						+ "AND  EXISTS (SELECT td FROM TraitementDossier td WHERE td.dossier=dossier AND td.operation.nature = :nop AND td.statut = :statut)",
						BigDecimal.class).setParameter("nature", nd).setParameter("nop", nop).setParameter("statut", statut).getSingleResult();
		
		return r==null?0:r.longValue();
	}

	
	
	@Override
	public Long countConsommationBilletAvionByLocalite(Localite localite) {
		return entityManager.createQuery(
				"SELECT COUNT(dossier) "
				+ "FROM DossierMission dossier "
				+ "WHERE EXISTS (SELECT dc FROM DemandeCotationMission dc WHERE dc.mission.deplacement=dossier.deplacement "
				+ "AND dc.mission.deplacement.localiteArrivee = :localite "
				+ "AND EXISTS (SELECT pdc FROM PrestataireDemandeCotationMission pdc WHERE pdc.id.demandeCotationMissionId=dc.id  "
				+ "AND EXISTS (SELECT cmd FROM CommandeTitreTransport cmd WHERE cmd.prestataireDemandeCotationMission=pdc)))",
					Long.class).setParameter("localite", localite).getSingleResult();
	}

	@Override
	public Long sumConsommationBilletAvionFraisByLocalite(Localite localite) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.frais.transport) "
				+ "FROM DossierMission dossier "
				+ "WHERE EXISTS (SELECT dc FROM DemandeCotationMission dc WHERE dc.mission.deplacement=dossier.deplacement "
				+ "AND dc.mission.deplacement.localiteArrivee = :localite "
				+ "AND EXISTS (SELECT pdc FROM PrestataireDemandeCotationMission pdc WHERE pdc.id.demandeCotationMissionId=dc.id  "
				+ "AND EXISTS (SELECT cmd FROM CommandeTitreTransport cmd WHERE cmd.prestataireDemandeCotationMission=pdc)))",
					BigDecimal.class).setParameter("localite", localite).getSingleResult();
		
		return r==null?0:r.longValue();
	}

	@Override
	public Long countConsommationBilletAvionByPrestataire(Prestataire prestataire) {
		return entityManager.createQuery(
				"SELECT COUNT(dossier) "
				+ "FROM DossierMission dossier "
				+ "WHERE EXISTS (SELECT dc FROM DemandeCotationMission dc WHERE dc.mission.deplacement=dossier.deplacement "
				+ "AND EXISTS (SELECT pdc FROM PrestataireDemandeCotationMission pdc WHERE pdc.id.demandeCotationMissionId=dc.id AND  pdc.id.prestataireId = :prestataire "
				+ " AND EXISTS (SELECT cmd FROM CommandeTitreTransport cmd WHERE cmd.prestataireDemandeCotationMission=pdc)))",
					Long.class).setParameter("prestataire", prestataire.getId()).getSingleResult();
	}

	@Override
	public Long sumConsommationBilletAvionFraisByPrestataire(Prestataire prestataire) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.frais.transport) "
				+ "FROM DossierMission dossier "
				+ "WHERE EXISTS (SELECT dc FROM DemandeCotationMission dc WHERE dc.mission.deplacement=dossier.deplacement "
				+ "AND EXISTS (SELECT pdc FROM PrestataireDemandeCotationMission pdc WHERE pdc.id.demandeCotationMissionId=dc.id AND  pdc.id.prestataireId = :prestataire "
				+ " AND EXISTS (SELECT cmd FROM CommandeTitreTransport cmd WHERE cmd.prestataireDemandeCotationMission=pdc)))",
				BigDecimal.class).setParameter("prestataire", prestataire.getId()).getSingleResult();
		
		return r==null?0:r.longValue();
	}

	@Override
	public Long sumConsommationBilletAvionDepenseByTypeClasseVoyage(TypeClasseVoyage typeClasseVoyage) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.montantIndemnisation) "
				+ "FROM DossierMission dossier "
				+ "WHERE dossier.classeVoyage = :typeClasseVoyage AND EXISTS (SELECT bl FROM BulletinLiquidation bl WHERE bl.dossier=dossier AND "
				+ "EXISTS (SELECT tp FROM TraitementPieceProduite tp WHERE tp.pieceProduite=bl AND tp.operation.nature.code= :nop AND tp.statut.code= :statut ))",
				BigDecimal.class)
				.setParameter("typeClasseVoyage", typeClasseVoyage)
				.setParameter("nop", Code.NATURE_OPERATION_REGLER_BL)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getSingleResult();
		
		return r==null?0:r.longValue();
	}

	@Override
	public Long sumConsommationBilletAvionFraisByTypeClasseVoyage(TypeClasseVoyage typeClasseVoyage) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.frais.transport) "
				+ "FROM DossierMission dossier "
				+ "WHERE dossier.classeVoyage= :typeClasseVoyage AND EXISTS (SELECT bl FROM BulletinLiquidation bl WHERE bl.dossier=dossier AND "
				+ "EXISTS (SELECT tp FROM TraitementPieceProduite tp WHERE tp.pieceProduite=bl AND tp.operation.nature.code= :nop AND tp.statut.code= :statut ))",
				BigDecimal.class)
				.setParameter("typeClasseVoyage", typeClasseVoyage)
				.setParameter("nop", Code.NATURE_OPERATION_REGLER_BL)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getSingleResult();
		
		return r==null?0:r.longValue();
	}


	@Override
	public Long sumConsommationBilletAvionDepenseByGroupe(Groupe groupe) {
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.montantIndemnisation) "
						+ "FROM DossierMission dossier WHERE dossier.groupe= :groupe "
						+ "AND EXISTS (SELECT dc FROM DemandeCotationMission dc WHERE dc.mission.deplacement=dossier.deplacement "
						+ "AND EXISTS (SELECT pdc FROM PrestataireDemandeCotationMission pdc WHERE pdc.id.demandeCotationMissionId=dc.id "
						+ "AND EXISTS (SELECT cmd FROM CommandeTitreTransport cmd WHERE cmd.prestataireDemandeCotationMission=pdc)))",
						BigDecimal.class).setParameter("groupe", groupe).getSingleResult();
		
		return r==null?0:r.longValue();
	}

	@Override
	public Long sumConsommationBilletAvionFraisByGroupe(Groupe groupe) {
		/*
		 entityManager.createQuery(
				"SELECT SUM(dossier.frais.transport) "
				+ "FROM DossierMission dossier "
				+ "WHERE dossier.groupe= :groupe AND EXISTS (SELECT bl FROM BulletinLiquidation bl WHERE bl.dossier=dossier AND "
				+ "EXISTS (SELECT tp FROM TraitementPieceProduite tp WHERE tp.pieceProduite=bl AND tp.operation.nature.code= :nop AND tp.statut.code= :statut ))",
				BigDecimal.class)
				.setParameter("groupe", groupe)
				.setParameter("nop", Code.NATURE_OPERATION_REGLER_BL)
				.setParameter("statut", Code.STATUT_ACCEPTE)
				.getSingleResult();
		 */
		
		BigDecimal r = entityManager.createQuery(
				"SELECT SUM(dossier.frais.transport) "
				+ "FROM DossierMission dossier WHERE dossier.groupe= :groupe "
				+ "AND EXISTS (SELECT dc FROM DemandeCotationMission dc WHERE dc.mission.deplacement=dossier.deplacement "
				+ "AND EXISTS (SELECT pdc FROM PrestataireDemandeCotationMission pdc WHERE pdc.id.demandeCotationMissionId=dc.id "
				+ "AND EXISTS (SELECT cmd FROM CommandeTitreTransport cmd WHERE cmd.prestataireDemandeCotationMission=pdc)))",
				BigDecimal.class).setParameter("groupe", groupe).getSingleResult();
		
		return r==null?0:r.longValue();
	}

	/*@Override
	public Long countConsommationBilletAvionByTypeClasseVoyage(TypeClasseVoyage typeClasseVoyage) {
		
		return entityManager.createQuery(
				"SELECT COUNT(dossier) "
				+ "FROM DossierMission dossier "
				+ "WHERE dossier.classeVoyage = :typeClasseVoyage "
				+ "AND EXISTS (SELECT dc FROM DemandeCotationMission dc WHERE dc.mission.deplacement=dossier.deplacement "
				+ "AND EXISTS (SELECT pdc FROM PrestataireDemandeCotationMission pdc WHERE pdc.id.demandeCotationMissionId=dc.id "
				+ "AND EXISTS (SELECT cmd FROM CommandeTitreTransport cmd WHERE cmd.prestataireDemandeCotationMission=pdc)))",
				Long.class)
				.setParameter("typeClasseVoyage", typeClasseVoyage)
				.getSingleResult();
	}

	@Override
	public Long countConsommationBilletAvionByGroupe(Groupe groupe) {
		return entityManager.createQuery(
				"SELECT COUNT(dossier) "
				+ "FROM DossierMission dossier "
				+ "WHERE dossier.groupe = :groupe "
				+ "AND EXISTS (SELECT dc FROM DemandeCotationMission dc WHERE dc.mission.deplacement=dossier.deplacement "
				+ "AND EXISTS (SELECT pdc FROM PrestataireDemandeCotationMission pdc WHERE pdc.id.demandeCotationMissionId=dc.id "
				+ "AND EXISTS (SELECT cmd FROM CommandeTitreTransport cmd WHERE cmd.prestataireDemandeCotationMission=pdc)))",
				Long.class)
				.setParameter("groupe", groupe)
				.getSingleResult();
	}
	*/
}
