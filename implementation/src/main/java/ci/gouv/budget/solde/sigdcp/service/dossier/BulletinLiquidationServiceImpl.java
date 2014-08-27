package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.BulletinLiquidationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.GroupeDDDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitementDossierDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation.AspectLiquide;
import ci.gouv.budget.solde.sigdcp.model.identification.Grade;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.OperationValidationConfig;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDossier;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.indemnite.GroupeMissionService;
import ci.gouv.budget.solde.sigdcp.service.indemnite.IndemniteCalculateurService;

@Stateless
public class BulletinLiquidationServiceImpl extends AbstractPieceProuiteServiceImpl<BulletinLiquidation> implements BulletinLiquidationService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private TraitementDossierDao traitementDossierDao;
	@Inject private DossierDao dossierDao;
	@Inject private GroupeMissionService groupeMissionService;
	@Inject private GroupeDDDao groupeDDDao;
	
	@Inject protected IndemniteCalculateurService indemniteCalculateurService;
	
	@Inject
	public BulletinLiquidationServiceImpl(BulletinLiquidationDao dao) {
		super(dao); 
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public BulletinLiquidation nouveau(Dossier dossier) {
		BulletinLiquidation bulletinLiquidation = new BulletinLiquidation(dossier, null, genericDao.readByClass(TypePieceProduite.class, Code.TYPE_PIECE_PRODUITE_BL), null, null);
		dossier.setDateCreation(traitementDossierDao.readByDossierByNatureOperationIdByStatutId(dossier, Code.NATURE_OPERATION_SAISIE, Code.STATUT_ACCEPTE).iterator().next().getOperation().getDate());
		init(bulletinLiquidation, Code.NATURE_OPERATION_ETABLISSEMENT_BL);
		return bulletinLiquidation;
	}
	
	@Override
	public BulletinLiquidation findByNumero(String numero,String natureoperationCode) {
	
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<BulletinLiquidation> findByBordereau(BordereauTransmission bordereauTransmission) {
		return ((BulletinLiquidationDao)dao).readByBordereau(bordereauTransmission);
	} 

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public BulletinLiquidation find(Long id, String natureOperationCode)throws ServiceException {
		// dto(((BulletinLiquidationDao)dao).readByNumero(numero), null);
		return super.findDemande(id, natureOperationCode);
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<BulletinLiquidation> findATraiter(Collection<NatureDeplacement> natureDeplacements, String natureOperationCode) {
		NatureOperation natureOperation = genericDao.readByClass(NatureOperation.class, natureOperationCode);
		Collection<BulletinLiquidation>  liste;
		if(Code.NATURE_OPERATION_ETABLISSEMENT_BL.equals(natureOperationCode)){
			//dossiers conforme
			Collection<Dossier> dossiers = dossierDao.readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,Code.NATURE_OPERATION_CONFORMITE,Code.STATUT_ACCEPTE);
			//dossier liquidation differe
			dossiers.addAll(dossierDao.readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,natureOperationCode,Code.STATUT_DIFFERE));
			//dossier mission avec feuille de dep retourne
			dossiers.addAll(dossierDao.readBLExisteAndFDFichierExisteByNatureDeplacements(natureDeplacements));
			liste = new ArrayList<>();
			for(Dossier dossier : dossiers){
				liste.add(nouveau(dossier));
			}
			
		}else{
			String codeOpPrec = natureOperation.getPrecedent().getCode();
			String statutPrecAcc = operationValidationConfigDao.readByNatureOperationIdByValidationType(codeOpPrec, ValidationType.ACCEPTER).getStatutResultat().getCode();
			OperationValidationConfig differerConfig = operationValidationConfigDao.readByNatureOperationIdByValidationType(natureOperationCode, ValidationType.DIFFERER);
			//if(Code.NATURE_OPERATION_PAIEMENT_PRISE_EN_CHARGE.equals(natureOperationId)){
				//bordereaux avec statut transmis et differe
				liste = ((BulletinLiquidationDao)dao).readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,codeOpPrec,statutPrecAcc);
				if(differerConfig!=null)
					liste.addAll(((BulletinLiquidationDao)dao).readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,natureOperationCode,differerConfig.getStatutResultat().getCode() ));
			//}else{
			//	bordereauTransmissions = ((BordereauTransmissionDao)dao).readByNatureOperationIdByStatutId(natureOperation.getOperationPrecedente().getCode(),natureOperation.getOperationPrecedente().getStatutResultatAccepte().getCode() );
			//}
			
			switch(natureOperationCode){
			case Code.NATURE_OPERATION_REGLER_BL: 
				liste = ((BulletinLiquidationDao)dao).readByNatureDeplacementsByBordereauNatureOperationIdByBordereauStatutId(natureDeplacements,Code.NATURE_OPERATION_MISE_EN_REGLEMENT,Code.STATUT_ACCEPTE);break;
			}
				
			if(Code.NATURE_OPERATION_VALIDATION_BL.equals(natureOperationCode)){
				liste.addAll(((BulletinLiquidationDao)dao).readByDernierTraitementIsNull(natureDeplacements));
			}
		}
			
		
		for(BulletinLiquidation piece : liste)
			init(piece, natureOperationCode);
		
		// on applique la notion de disponible disponible
		if(Code.NATURE_OPERATION_ETABLISSEMENT_BTBL.equals(natureOperationCode)){
			NatureDeplacement natureDeplacement = natureDeplacements.iterator().next();
			if(Code.CATEGORIE_DEPLACEMENT_DEFINITIF.equals(natureDeplacement.getCategorie().getCode())){
				BigDecimal disponible = natureDeplacement.getCategorie().getDisponible();
				Collection<BulletinLiquidation> blPayable = new ArrayList<>();
				if(disponible==null || disponible.equals(BigDecimal.ZERO))
					return blPayable;
				for(BulletinLiquidation bl : liste){
					disponible = disponible.subtract(bl.getMontant());
					if(disponible.compareTo(BigDecimal.ZERO)>=0){
						blPayable.add(bl);
					}else
						disponible = disponible.add(bl.getMontant());
				}
				liste = blPayable;
			}else if(Code.CATEGORIE_DEPLACEMENT_MISSION.equals(natureDeplacement.getCode())){
				return liste;
			}
		}
		
		return liste;
	}

	@Override
	protected void initFirstFlow(BulletinLiquidation bulletinLiquidation) {
		super.initFirstFlow(bulletinLiquidation);
		Dossier dossier = bulletinLiquidation.getDossier();
		if(dossier.getGroupe()==null){
			if(dossier instanceof DossierMission){
				Grade grade = ((DossierMission)dossier).getProfession()==null?null:((DossierMission)dossier).getProfession().getGrade();
				dossier.setGroupe(groupeMissionService.findByFonctionOrGrade(((DossierMission) dossier).getFonction(),grade));
			}else if(dossier instanceof DossierDD)
				dossier.setGroupe(groupeDDDao.readByGrade(((DossierDD)dossier).getGrade()));
		}
		
		bulletinLiquidation.setDossier(dossier);
		bulletinLiquidation.setType(genericDao.readByClass(TypePieceProduite.class, Code.TYPE_PIECE_PRODUITE_BL));
		bulletinLiquidation.setAspect(AspectLiquide.DEMANDE);
		
		if(Code.TYPE_DEPENSE_PRISE_EN_CHARGE.equals(dossier.getDeplacement().getTypeDepense().getCode()))
			if(dossier instanceof DossierMission){
				bulletinLiquidation.setPourcentage(new BigDecimal(dossier.getBulletinLiquidations().isEmpty()?"0.8":"0.2"));
			}else
				bulletinLiquidation.setPourcentage(new BigDecimal("1"));
		else
			bulletinLiquidation.setPourcentage(new BigDecimal("1"));
		indemniteCalculateurService.calculer(bulletinLiquidation);
	}
	
	@Override
	protected void initMiddleFlow(BulletinLiquidation bulletinLiquidation) {
		super.initMiddleFlow(bulletinLiquidation);
		OperationValidationConfig creerOpConfig = operationValidationConfigDao.readByNatureOperationIdByValidationType(Code.NATURE_OPERATION_ETABLISSEMENT_BL, ValidationType.ACCEPTER);
		bulletinLiquidation.setDateCreation(traitementDossierDao.readByDossierByNatureOperationIdByStatutId(bulletinLiquidation.getDossier(), Code.NATURE_OPERATION_ETABLISSEMENT_BL, 
			creerOpConfig.getStatutResultat().getCode()).iterator().next().getOperation().getDate());
		indemniteCalculateurService.calculer(bulletinLiquidation);
	}

	@Override
	protected void firstAvantCreation(BulletinLiquidation bl) {
		super.firstAvantCreation(bl);
		TraitementDossier td = new TraitementDossier(bl.getTraitable().getTraitement().getOperation(), bl, bl.getTraitable().getTraitement().getStatut(), bl.getDossier());
		td.setValidationType(bl.getTraitable().getTraitement().getValidationType());
		traitementDossierDao.create(td);
		bl.getDossier().getTraitable().setDernierTraitement(td);
		dossierDao.update(bl.getDossier());
	}
}
