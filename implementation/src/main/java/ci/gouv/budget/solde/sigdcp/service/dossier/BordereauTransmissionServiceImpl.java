package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.BordereauTransmissionDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.BulletinLiquidationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DocumentDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.OperationValidationConfig;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitementPieceProduiteService;

@Stateless
public class BordereauTransmissionServiceImpl extends AbstractPieceProuiteServiceImpl<BordereauTransmission> implements BordereauTransmissionService, Serializable {

	private static final long				serialVersionUID	= -7601857525393731774L;
	
	@Inject private DynamicEnumerationDao			dynamicEnumerationDao;
	@Inject private DocumentDao						documentDao;
	@Inject private TraitementPieceProduiteService	traitementPieceProduiteService;
	@Inject private BulletinLiquidationDao			bulletinLiquidationDao;
	@Inject private CategorieDeplacementService		categorieDeplacementService;
	
	@Inject
	public BordereauTransmissionServiceImpl(BordereauTransmissionDao dao) {
		super(dao);
	}
		
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<BordereauTransmission> findATraiter(Collection<NatureDeplacement> natureDeplacements, String natureOperationCode) {
		NatureOperation natureOperation = genericDao.readByClass(NatureOperation.class, natureOperationCode);
		Collection<BordereauTransmission> liste;
		if(Code.NATURE_OPERATION_ETABLISSEMENT_BTBL.equals(natureOperationCode)){
			BordereauTransmission bt = new BordereauTransmission();
			bt.getBulletinLiquidations().addAll(bulletinLiquidationDao.readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,Code.NATURE_OPERATION_VISA_BL,Code.STATUT_ACCEPTE));
			bt.getBulletinLiquidations().addAll(bulletinLiquidationDao.readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,natureOperationCode,Code.STATUT_DIFFERE));
			if(bt.getBulletinLiquidations().isEmpty())
				return null;
			liste = Arrays.asList(bt);
			for(BordereauTransmission piece : liste)
				init(piece, natureOperationCode);
		}else{
			String codeOpPrec = natureOperation.getPrecedent().getCode();
			String statutPrecAcc = operationValidationConfigDao.readByNatureOperationIdByValidationType(codeOpPrec, ValidationType.ACCEPTER).getStatutResultat().getCode();
			OperationValidationConfig differerConfig = operationValidationConfigDao.readByNatureOperationIdByValidationType(natureOperationCode, ValidationType.DIFFERER);
		
			liste = ((BordereauTransmissionDao) dao).readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements, codeOpPrec, statutPrecAcc);
			if (differerConfig != null)
				liste.addAll(((BordereauTransmissionDao) dao).readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements, natureOperationCode, differerConfig.getStatutResultat()
						.getCode()));
			
			if (Code.NATURE_OPERATION_VALIDATION_BTBL.equals(natureOperationCode)) {
				liste.addAll(((BordereauTransmissionDao) dao).readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements, Code.NATURE_OPERATION_MODIFICATION_BTBL,
						Code.STATUT_ACCEPTE));
			}

			Long configCount = operationValidationConfigDao.countByNatureOperationId(natureOperationCode);

			for (BordereauTransmission bordereauTransmission : liste) {
				init(bordereauTransmission, natureOperationCode);
				if (configCount == 1)
					bordereauTransmission.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
			}
		}
		return liste;
	}

	private void ajouterBl(BordereauTransmission bt, BulletinLiquidation bl) {
		bt.setMontant(bt.getMontant().add(bl.getMontant()));
		bl.setBordereauTransmission(bt);
		traitementPieceProduiteService.creer(bt.getTraitable().getTraitement().getOperation(), bl, null, Code.STATUT_ACCEPTE);
		documentDao.update(bl);
	}

	/*
	@Override
	public void modifier(BordereauTransmission bordereauTransmission, Collection<BordereauTransmission> nouveauDtos) throws ServiceException {
		Collection<BulletinLiquidation> nouveaux = new ArrayList<>();
		
		for (BordereauTransmission bulletinLiquidationDto : nouveauDtos)
			nouveaux.add(bulletinLiquidationDto.getPiece());
		Collection<BulletinLiquidation> anciens = bulletinLiquidationDao.readByBordereau(bordereauTransmission);

		if (CollectionUtils.isEqualCollection(nouveaux, anciens))
			return;

		BigDecimal montant = bordereauTransmission.getMontant();

		Operation operation = operationService.creer(Code.NATURE_OPERATION_MODIFICATION_BTBL);
		CategorieDeplacement categorieDeplacement = nouveauDtos.iterator().next().getDossierDto().getDossier().getDeplacement().getNature().getCategorie();

		for (BulletinLiquidationDto bulletinLiquidationDto : nouveauDtos) {
			if (anciens.contains(bulletinLiquidationDto.getPiece()))
				anciens.remove(bulletinLiquidationDto.getPiece());// le bl na
																	// pas ete
																	// supprimer
			else
				ajouterBl(operation, bordereauTransmission, bulletinLiquidationDto);// nouveau
																					// venu
		}*/

		/*
		for (BulletinLiquidation bulletinLiquidation : anciens) {
			traitementPieceProduiteService.creer(operation, bulletinLiquidation, null, Code.STATUT_REJETE);
			bulletinLiquidation.setBordereauTransmission(null);
			bordereauTransmission.setMontant(bordereauTransmission.getMontant().subtract(bulletinLiquidation.getMontant()));
		}
		traitementPieceProduiteService.creer(operation, bordereauTransmission, null, bordereauTransmission.getTraitable().getDernierTraitement().getStatut().getCode());
		*/
		// mis a jour du disponible
		/*
		 * System.out.println("Disponible : "+categorieDeplacement.getDisponible(
		 * )); System.out.println("Initial : "+montant);
		 * System.out.println("Actuel : "+bordereauTransmission.getMontant());
		 * System
		 * .out.println("Diff : "+bordereauTransmission.getMontant().subtract
		 * (montant));
		 */
		/*
		montant = bordereauTransmission.getMontant().subtract(montant);
		switch (montant.compareTo(BigDecimal.ZERO)) {
			case -1:
				categorieDeplacementService.crediterDisponible(categorieDeplacement, montant.abs());
				break;
			case 1:
				categorieDeplacementService.debiterDisponible(categorieDeplacement, montant);
				break;
		}
		
		// System.out.println("Disponible MAJ : "+categorieDeplacement.getDisponible());
	}
	*/
	
	@Override
	protected void initFirstFlow(BordereauTransmission bt) {
		super.initFirstFlow(bt);
		bt.setNatureDeplacement(bt.getBulletinLiquidations().iterator().next().getDossier().getDeplacement().getNature());
		bt.setNumero(RandomStringUtils.randomAlphabetic(6));
		bt.setDateEtablissement(null);
		bt.setType(dynamicEnumerationDao.readByClass(TypePieceProduite.class, Code.TYPE_PIECE_PRODUITE_BT));
		bt.setMontant(BigDecimal.ZERO);	
		
	}
	
	@Override
	protected void afterCreation(BordereauTransmission bt) {
		super.afterCreation(bt);
		for (BulletinLiquidation dto : bt.getBulletinLiquidations())
			ajouterBl(bt, dto);
		
		// mis a jour du disponible
		categorieDeplacementService.debiterDisponible(bt.getNatureDeplacement().getCategorie(), bt.getMontant());
	}
	
	@Override
	protected void initMiddleFlow(BordereauTransmission bt) {
		super.initMiddleFlow(bt);
		OperationValidationConfig creerOpConfig = operationValidationConfigDao.readByNatureOperationIdByValidationType(Code.NATURE_OPERATION_ETABLISSEMENT_BTBL, ValidationType.ACCEPTER);
		bt.setDateCreation(traitementPieceProduiteDao.readByPieceProduiteByNatureOperationIdByStatutId(bt, Code.NATURE_OPERATION_ETABLISSEMENT_BTBL, 
			creerOpConfig.getStatutResultat().getCode()).iterator().next().getOperation().getDate());
		bt.setBulletinLiquidations(bulletinLiquidationDao.readByBordereau(bt));
	}
	
	@Override
	protected void firstAvantCreation(BordereauTransmission objetTraite) {
		super.firstAvantCreation(objetTraite);
	}
	
	@Override
	protected void afterValidation(BordereauTransmission bt) {
		super.afterValidation(bt);
		switch (bt.getTraitable().getTraitement().getValidationType()) {
		case ACCEPTER:
			if (Code.NATURE_OPERATION_VALIDATION_BTBL.equals(bt.getTraitable().getTraitement().getOperation().getNature().getCode()))
				categorieDeplacementService.mettreAJourDisponibleTresor(bt.getNatureDeplacement().getCategorie(), bt);
			break;
		default:
			break;
		}
	}

	/*
	@Override
	public Collection<BordereauTransmission> findByNatureDeplacement(NatureDeplacement natureDeplacement) {
		return null;
	}

	@Override
	public Collection<BordereauTransmission> findByStatutId(String statutId) {
		return ((BordereauTransmissionDao) dao).readByStatutId(statutId);
	}*/
	
	@Override
	public BordereauTransmission find(Long id, String natureOperationCode) throws ServiceException {
		BordereauTransmission bordereauTransmission = dao.read(id);
		init(bordereauTransmission, natureOperationCode);
		return bordereauTransmission;
	}

}
