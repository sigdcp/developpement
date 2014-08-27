package ci.gouv.budget.solde.sigdcp.service.indemnite;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Days;

import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.dao.geographie.DistanceEntreLocaliteDao;
import ci.gouv.budget.solde.sigdcp.dao.indemnite.IndemniteTrancheCercueilDao;
import ci.gouv.budget.solde.sigdcp.dao.indemnite.IndemniteTrancheDistanceDao;
import ci.gouv.budget.solde.sigdcp.dao.indemnite.LocaliteGroupeMissionDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeDD;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;

public class IndemniteOperandeServiceImpl implements IndemniteOperandeService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private PieceJustificativeDao pieceJustificativeDao;
	//@Inject private IndemniteOperandeDao indemniteOperandeDao; 
	@Inject private DistanceEntreLocaliteDao distanceEntreLocaliteDao;
	@Inject private IndemniteTrancheDistanceDao indemniteTrancheDistanceDao;
	@Inject private IndemniteTrancheCercueilDao indemniteTrancheCercueilDao;
	@Inject private LocaliteGroupeMissionDao localiteGroupeMissionDao;
	

	@Override
	public int distance(Dossier dossier) {
		return distanceEntreLocaliteDao.readByLocalite1ByLocalite2(dossier.getDeplacement().getLocaliteDepart(), dossier.getDeplacement().getLocaliteArrivee())
				.getDistanceKm().intValue();
	} 

	@Override
	public int montantDistance(Dossier dossier,int distance) {
		return indemniteTrancheDistanceDao.readByValeurByCategorieDeplacement(dossier.getDeplacement().getNature().getCategorie() , new BigDecimal(distance)).getMontant().intValue();
	}
	
	@Override
	public int montantCercueil(int indice) {
		return indemniteTrancheCercueilDao.readByIndice(new BigDecimal(indice)).getCercueil().getMontant().intValue() ;
	}

	@Override
	public int nombreVoyageur(Dossier dossier) {
		return 1+nombreConjoint(dossier)+nombreEnfant(dossier);
	}

	@Override
	public int nombreConjoint(Dossier dossier) {
		return pieceJustificativeDao.countByDossierByTypePieceJustificativeId(dossier, Code.TYPE_PIECE_EXTRAIT_MARIAGE).intValue();
	}
	
	@Override
	public int nombreEnfant(Dossier dossier) {
		return pieceJustificativeDao.countByDossierByTypePieceJustificativeId(dossier, Code.TYPE_PIECE_EXTRAIT_NAISSANCE).intValue();
	}
	
	@Override
	public int poidsAgent(GroupeDD groupeDD) {
		return groupeDD.getPoidsAutoriseAgent().intValue();
	}

	@Override
	public int poidsConjoint(GroupeDD groupeDD) {
		return groupeDD.getPoidsAutoriseEpouse().intValue();
	}

	@Override
	public int poidsEnfant(GroupeDD groupeDD) {
		return groupeDD.getPoidsAutoriseEnfant().intValue();
	}

	@Override
	public int montantAgent(GroupeDD groupeDD) {
		return groupeDD.getMontantAgent().intValue();
	}

	@Override
	public int montantConjoint(GroupeDD groupeDD) {
		return groupeDD.getMontantEpouse().intValue();
	}

	@Override
	public int montantEnfant(GroupeDD groupeDD) {
		return groupeDD.getMontantEnfant().intValue();
	}

	@Override
	public int montantMissionSejour(DossierMission dossier, GroupeMission groupe) {
		Localite zone = dossier.getDeplacement().getLocaliteArrivee();
		while(!zone.getType().getCode().equals(Code.TYPE_LOCALITE_ZONE))
			zone = zone.getParent();
		return localiteGroupeMissionDao.readByLocaliteByGroupe(zone, groupe).getIndemnite().intValue();
	}

	@Override
	public int montantMissionTransport(DossierMission dossier, GroupeMission groupe) {
		return dossier.getFrais().getTransport().intValue();
	}
	
	@Override
	public int montantMissionDivers(DossierMission dossier, GroupeMission groupe) {
		return dossier.getFrais().getDivers().intValue();
	}

	@Override
	public int dureeJourDeplacement(Dossier dossier) {
		return Days.daysBetween(new DateTime(dossier.getDeplacement().getDateDepart()), new DateTime(dossier.getDeplacement().getDateArrivee())).getDays()+1;
	}

	@Override
	public String codeLocaliteDepart(Dossier dossier) {
		return dossier.getDeplacement().getLocaliteDepart().getCode();
	}

	@Override
	public String codeLocaliteArrivee(Dossier dossier) {
		return dossier.getDeplacement().getLocaliteArrivee().getCode();
	}

	@Override
	public int indice(DossierObseques dossier) {
		return dossier.getIndice();
	}


	

}
