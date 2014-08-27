package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.ProjetDecisionDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitementDossierDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.ProjetDecision;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.OperationValidationConfig;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDossier;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;

@Stateless
public class ProjetDecisionServiceImpl extends AbstractPieceProuiteServiceImpl<ProjetDecision> implements ProjetDecisionService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	@Inject private TraitementDossierDao traitementDossierDao;
	@Inject private DossierDao dossierDao;
	@Inject private DossierService dossierService;
	
	@Inject
	public ProjetDecisionServiceImpl(ProjetDecisionDao dao) {
		super(dao); 
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public ProjetDecision find(Long id, String natureOperationCode)throws ServiceException {
		// dto(((BulletinLiquidationDao)dao).readByNumero(numero), null);
		return super.findDemande(id, natureOperationCode);
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<ProjetDecision> findATraiter(Collection<NatureDeplacement> natureDeplacements, String natureOperationCode) {
		NatureOperation natureOperation = genericDao.readByClass(NatureOperation.class, natureOperationCode);
		Collection<ProjetDecision>  liste;
		if(Code.NATURE_OPERATION_ETABLISSEMENT_PROJDEC.equals(natureOperationCode)){
			//dossiers conforme
			Collection<Dossier> dossiers = dossierDao.readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,Code.NATURE_OPERATION_CONFORMITE,Code.STATUT_ACCEPTE);
			//dossier differe
			dossiers.addAll(dossierDao.readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,natureOperationCode,Code.STATUT_DIFFERE));
			liste = new ArrayList<>();
			for(Dossier dossier : dossiers){
				dossierService.init(dossier, natureOperationCode);
				liste.add(new ProjetDecision(dossier, null, genericDao.readByClass(TypePieceProduite.class,Code.TYPE_PIECE_PRODUITE_DECISION_REMB), null));
			}
			
		}else{
			String codeOpPrec = natureOperation.getPrecedent().getCode();
			String statutPrecAcc = operationValidationConfigDao.readByNatureOperationIdByValidationType(codeOpPrec, ValidationType.ACCEPTER).getStatutResultat().getCode();
			OperationValidationConfig differerConfig = operationValidationConfigDao.readByNatureOperationIdByValidationType(natureOperationCode, ValidationType.DIFFERER);
			
			liste = ((ProjetDecisionDao)dao).readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,codeOpPrec,statutPrecAcc);
			if(differerConfig!=null)
				liste.addAll(((ProjetDecisionDao)dao).readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,natureOperationCode,differerConfig.getStatutResultat().getCode() ));
			
			//if(Code.NATURE_OPERATION_VALIDATION_BL.equals(natureOperationCode)){
			//	liste.addAll(((BulletinLiquidationDao)dao).readByDernierTraitementIsNull(natureDeplacements));
			//}
		}
			
		for(ProjetDecision piece : liste)
			init(piece, natureOperationCode);
		
		return liste;
	}

	@Override
	protected void firstAvantCreation(ProjetDecision bl) {
		super.firstAvantCreation(bl);
		TraitementDossier td = new TraitementDossier(bl.getTraitable().getTraitement().getOperation(), bl, bl.getTraitable().getTraitement().getStatut(), bl.getDossier());
		td.setValidationType(bl.getTraitable().getTraitement().getValidationType());
		traitementDossierDao.create(td);
		bl.getDossier().getTraitable().setDernierTraitement(td);
		dossierDao.update(bl.getDossier());
	}
}
