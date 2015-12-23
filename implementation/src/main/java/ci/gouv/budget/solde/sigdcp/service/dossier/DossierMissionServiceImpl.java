package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierMissionDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.calendrier.MissionExecutee;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation.AspectLiquide;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.traitement.Operation;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDossier;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierMissionValidator;

@Stateless
public class DossierMissionServiceImpl extends AbstractDossierServiceImpl<DossierMission> implements DossierMissionService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject private AgentEtatService agentEtatService;
	@Inject private DossierMissionValidator dossierMissionValidator;
	@Inject private AgentEtatDao agentEtatDao;
	
	@Inject
	public DossierMissionServiceImpl(DossierMissionDao dao) {
		super(dao);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public DossierMission nouveau(String matricule,MissionExecutee missionExecutee) {
		DossierMission dossier = new DossierMission(missionExecutee.getDeplacement());
		AgentEtat agentEtat = agentEtatService.findByMatricule(matricule);
		if(agentEtat==null){
			dossier.setBeneficiaire(new AgentEtat());
			dossier.getBeneficiaire().setMatricule(matricule);
		}else
			dossier.setBeneficiaire(agentEtat);
		
		init(dossier, Code.NATURE_OPERATION_SAISIE);
		return dossier;
	}
	
	@Override
	protected void onDaoCreate(DossierMission dossier) {
		//est ce que le participant est connu ?
		AgentEtat agentEtat = agentEtatDao.readByMatricule(dossier.getBeneficiaire().getMatricule());
		if(agentEtat==null)
			agentEtatDao.create(dossier.getBeneficiaire());
		
		super.onDaoCreate(dossier);
	}
	
	@Override
	public void retournerFeuilleDeplacment(Collection<PieceJustificative> pieceJustificatives) throws ServiceException {
		Operation operation = operationService.creer(Code.NATURE_OPERATION_RETOUR_FD);
		for(PieceJustificative pieceJustificative : pieceJustificatives){
			TraitementDossier td = new TraitementDossier();
			td.setValidationType(ValidationType.ACCEPTER);
			traitementService.creer(operation, pieceJustificative.getDossier(), td,Code.STATUT_ACCEPTE);
			pieceJustificativeDao.update(pieceJustificative);
		}
		
	}
	
	@Override
	protected void validationSaisie(DossierMission dossier,Boolean soumission) throws ServiceException {
		dossierMissionValidator.setSoumission(soumission);
		if(!dossierMissionValidator.validate(dossier).isSucces())
			throw new ServiceException(dossierMissionValidator.getMessagesAsString());
	}
	
	@Override
	protected DossierMission createDossier(NatureDeplacement natureDeplacement) {
		if(!Code.NATURE_DEPLACEMENT_MISSION_HCI.equals(natureDeplacement.getCode())){
			DossierMission dossierMission= new DossierMission(new Deplacement(genericDao.readByClass(TypeDepense.class, String.class, Code.TYPE_DEPENSE_PRISE_EN_CHARGE)));
			if(natureDeplacement!=null && natureDeplacement.getSceSolde()){
				dossierMission.getDeplacement().setAddUser(utilisateur());
			}
			return dossierMission;
		}else{
			Collection<DossierMission> dossierMissions = ((DossierMissionDao)dao).readByNatureDeplacementsByNatureOperationIdByStatutId(Arrays.asList(genericDao.readByClass(NatureDeplacement.class, Code.NATURE_DEPLACEMENT_MISSION_HCI)), 
					Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE, Code.STATUT_ACCEPTE);
			return dossierMissions.isEmpty()?null:dossierMissions.iterator().next();	
		}
		
	}
	
	@Override
	protected void dossierNull(DossierMission dossier) {
		super.dossierNull(dossier);
		//serviceException("Vous n'avez aucune demande");
		//dossier.setMessageId("notification.aucunedemandeacompleter");
	}
			
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<DossierMission> findALiquiderByNatureDeplacementsByAspectLiquide(Collection<NatureDeplacement> natureDeplacements, AspectLiquide aspectLiquide) {
		/*
		String natureOperationId = Code.NATURE_OPERATION_ETABLISSEMENT_BL;
		if(AspectLiquide.DEMANDE.equals(aspectLiquide))
			return super.findATraiterByNatureDeplacementsByNatureOperationId(natureDeplacements, natureOperationId );
		
		//les bons de transport à liquider
		
		DossierMissionDao dossierDao = ((DossierMissionDao)dao);
		Collection<DossierMission>  dossiers = dossierDao.readByNatureDeplacementsByAspectLiquideNotExist(natureDeplacements,AspectLiquide.TITRE_TRANSPORT); 
		Collection<DossierMission> dtos = dtos(natureOperationId,dossiers);
		
		for(DossierMission dto : dtos){
			BulletinLiquidation bl = dto.getBulletinLiquidationSaisie();
			bl.setAspect(AspectLiquide.TITRE_TRANSPORT);
			bl.setPourcentage(new BigDecimal("1"));
			bl.setMontant( ((DossierMission)dto.getDossier()).getFrais().getTransport() );
		}*/
		return null;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void mettreAJourPiecesJustificatives(Collection<DossierMission> dossiers) {
		super.mettreAJourPiecesJustificatives(dossiers);
		DossierMission dossier = dossiers.iterator().next();
		PieceJustificativeAFournir modelPiece = null;
		int pieceJustificativeExistanteIndex;
		//Extrait de mariage
		
		modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_MARIAGE,
				dossier.getDeplacement().getTypeDepense().getCode());
		if(modelPiece!=null){
			pieceJustificativeExistanteIndex = index(dossier, modelPiece);
			if(Boolean.TRUE.equals(dossier.getMarie())){//ajouter un extrait de mariage si il n'en existe pas
				if(pieceJustificativeExistanteIndex==-1)
					dossier.getPieceJustificatives().add(new PieceJustificative(dossier, modelPiece));
			}else{//supprimer l'extrait de mariage si il en existe
				if(pieceJustificativeExistanteIndex!=-1)
					((List<PieceJustificative>)dossier.getPieceJustificatives()).remove(pieceJustificativeExistanteIndex);
			}
		}
		
		//Extrait de naissances des enfants
		modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_NAISSANCE,
				dossier.getDeplacement().getTypeDepense().getCode());
		if(modelPiece!=null){
			int n = dossier.getNombreEnfant() - count(dossier, modelPiece);
			if(n>0){// ajouter 
				for(int i=0;i<n;i++)
					dossier.getPieceJustificatives().add(new PieceJustificative(dossier, modelPiece));
			}else{//supprimer
				List<PieceJustificative> list = (List<PieceJustificative>) dossier.getPieceJustificatives();
				for(int j=0;n<0 && j<list.size();){
					if(list.get(j).getModel().equals(modelPiece)){
						((List<PieceJustificative>)dossier.getPieceJustificatives()).remove(j);
						n++;
					}else
						j++;
				}
			}
		}
	}
	 
}
 