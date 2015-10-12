package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.dao.dossier.AbstractDossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DeplacementDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceProduiteDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.AgentEtatDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.OperationValidationConfigDao;
import ci.gouv.budget.solde.sigdcp.dao.traitement.TraitementDossierDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation.AspectLiquide;
import ci.gouv.budget.solde.sigdcp.model.dossier.Courrier;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.traitement.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.traitement.Operation;
import ci.gouv.budget.solde.sigdcp.model.traitement.OperationValidationConfig;
import ci.gouv.budget.solde.sigdcp.model.traitement.Statut;
import ci.gouv.budget.solde.sigdcp.model.traitement.TraitementDossier;
import ci.gouv.budget.solde.sigdcp.service.ActionType;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatReferenceService;
import ci.gouv.budget.solde.sigdcp.service.identification.AgentEtatService;
import ci.gouv.budget.solde.sigdcp.service.sotra.TraitableServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.traitement.TraitementDossierService;


public abstract class AbstractDossierServiceImpl<DOSSIER extends Dossier> extends TraitableServiceImpl<DOSSIER, Long,TraitementDossier> implements AbstractDossierService<DOSSIER>,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	private static final String[] OPERATION_CORRIGER = {Code.NATURE_OPERATION_RECEVABILITE,Code.NATURE_OPERATION_CONFORMITE,Code.NATURE_OPERATION_ETABLISSEMENT_BL};
	
	@Inject private DeplacementService deplacementService;

	@Inject private DossierDao dossierDao;
	@Inject protected PieceJustificativeDao pieceJustificativeDao;
	@Inject protected DeplacementDao deplacementDao;
	@Inject private PieceProduiteDao pieceProduiteDao;
	@Inject protected OperationService operationService;
	@Inject protected TraitementDossierService traitementService;
	@Inject protected PieceJustificativeAFournirDao pieceJustificativeAFournirDao;
	@Inject private TraitementDossierDao traitementDossierDao;
	@Inject protected OperationValidationConfigDao operationValidationConfigDao;
	@Inject protected BulletinLiquidationService bulletinLiquidationService;
	@Inject private AgentEtatService agentEtatService;
	@Inject private AgentEtatReferenceService agentEtatReferenceService;
	@Inject private AgentEtatDao agentEtatDao;
	
	public AbstractDossierServiceImpl(AbstractDossierDao<DOSSIER> dao) {
		super(dao); 
	}
	 
	/**/
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<DOSSIER> findATraiter(Collection<NatureDeplacement> natureDeplacements,String natureOperationId) {
		NatureOperation natureOperation = genericDao.readByClass(NatureOperation.class, natureOperationId);
		String codeOpPrec = null;
		String codeStatutAcc = null;
		if(natureOperation.getPrecedent()!=null){
			codeOpPrec = natureOperation.getPrecedent().getCode();
			codeStatutAcc = operationValidationConfigDao.readByNatureOperationIdByValidationType(codeOpPrec, ValidationType.ACCEPTER).getStatutResultat().getCode();
		}
		AbstractDossierDao<DOSSIER> dossierDao = ((AbstractDossierDao<DOSSIER>)dao);

		Collection<DOSSIER>  dossiers = null;
		switch(natureOperationId){
		case Code.NATURE_OPERATION_RETOUR_FD: dossiers =  dossierDao.readBulletinLiquidationExisteLiquidableByNatureDeplacements(natureDeplacements, AspectLiquide.DEMANDE);break;
		case Code.NATURE_OPERATION_CONFORMITE: dossiers =  dossierDao.readCourrierNonNullByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,codeOpPrec,codeStatutAcc);break;
		//case Code.NATURE_OPERATION_ETABLISSEMENT_BL: dossiers =  dossierDao.readCourrierNonNullByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,codeOpPrec,codeStatutAcc);break;
		default: dossiers = dossierDao.readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,codeOpPrec,codeStatutAcc );break;
		}
		
		OperationValidationConfig differeConfig = operationValidationConfigDao.readByNatureOperationIdByValidationType(natureOperationId, ValidationType.DIFFERER);
		if(differeConfig!=null)
			dossiers.addAll(((AbstractDossierDao<DOSSIER>)dao).readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements,natureOperationId,differeConfig.getStatutResultat().getCode()));
		
		for(DOSSIER dossier : dossiers)
			init(dossier,natureOperationId);
		
		return dossiers;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public DOSSIER find(Long id, String natureOperationCode)throws ServiceException {
		DOSSIER dossier = ((AbstractDossierDao<DOSSIER>)dao).read(id);
		init(dossier, natureOperationCode);
		return dossier;
	}
		
	protected void validationAccepter(DOSSIER dossier){
		super.validationAccepter(dossier);
		if(Code.TYPE_DEPENSE_PRISE_EN_CHARGE.equals(dossier.getDeplacement().getTypeDepense().getCode())){// Prise en charge
			if(dossier instanceof DossierTransit && Code.NATURE_OPERATION_CONFORMITE.equals(dossier.getTraitable().getNatureOperation().getCode())){
				PieceProduite attestationPec = new PieceProduite(null,genericDao.readByClass(TypePieceProduite.class,Code.TYPE_PIECE_PRODUITE_APEC), tempsCourant());
				attestationPec.setNumero(numero(attestationPec));
				pieceProduiteDao.create(attestationPec);
				dossier.getTraitable().getTraitement().setPieceProduite(attestationPec);
			}/*else if(dossier instanceof DossierObseques && Code.NATURE_OPERATION_CONFORMITE.equals(dossier.getTraitable().getNatureOperation().getCode())){
					DossierObseques d = ((DossierObseques)dossier);
					int indice=indemniteOperandeService.indice(d);
					Cercueil cercueil=cercueilDao.readByIndice(new BigDecimal(indice));
					int distance= indemniteOperandeService.distance(dossier);				
					int montantDistance=indemniteOperandeService.montantDistance(dossier,distance);
					
					d.setIndice(indice);
					d.setCoutTransport(new BigDecimal(montantDistance));
					d.setCercueil(cercueil);
				}*/
		}else{// Remboursement
			/*
			if(Code.NATURE_OPERATION_CONFORMITE.equals(dossier.getTraitable().getNatureOperation().getCode()) && !(dossier instanceof DossierDD)){
				PieceProduite projRemb = new PieceProduite(null,genericDao.readByClass(TypePieceProduite.class,Code.TYPE_PIECE_PRODUITE_DECISION_REMB), tempsCourant());
				projRemb.setNumero(numero(projRemb));
				pieceProduiteDao.create(projRemb);
				dossier.getTraitable().getTraitement().setPieceProduite(projRemb);
			}
			*/
		}
		
		
	}

	protected void validationRejeter(DOSSIER dossier){
		
	}
	
	protected void validationDifferer(DOSSIER dossier){
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public DOSSIER findDemande(NatureDeplacement natureDeplacement,Long numero,String codeNatureOperation) {
		DOSSIER dossier = null;
		if(numero!=null){
			dossier = ((AbstractDossierDao<DOSSIER>)dao).read(numero);
			if(dossier==null)
				serviceException(ServiceExceptionType.RESOURCE_NOT_FOUND);
		}else{
			if(!natureDeplacement.getSceSolde())
			dossier = ((AbstractDossierDao<DOSSIER>)dao).readSaisieByPersonneByNatureDeplacement(utilisateur(), natureDeplacement);
			
			if(dossier==null){// Nouveau dossier
				
				dossier = createDossier(natureDeplacement);
				if(dossier!=null){
					dossier.getDeplacement().setNature(natureDeplacement);
					codeNatureOperation = dossier.getTraitable().getDernierTraitement()==null?Code.NATURE_OPERATION_SAISIE:dossier.getTraitable().getDernierTraitement().getOperation().getNature().getSuivant().getCode();
				}else{
					//dossierNull(dossier);//exception
					serviceException("Vous n'avez aucune demande");
				}
			}
		}
		
		init(dossier, codeNatureOperation);
		return dossier;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<DOSSIER> findDemandes() {
		Collection<DOSSIER> demandes = ((AbstractDossierDao<DOSSIER>)dao).readByAgentEtatAndAyantDroit(utilisateur());
		for(DOSSIER demande : demandes)
			init(demande, null);
		return demandes;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<DOSSIER> findDemandesSolde() {
		Collection<DOSSIER> demandes = ((AbstractDossierDao<DOSSIER>)dao).readBySolde(utilisateur());
		for(DOSSIER demande : demandes)
			init(demande, null);
		return demandes;
	}

	@Override
	protected void associerTraitement(DOSSIER dossier) {
		dossier.getTraitable().getTraitement().setDossier(dossier);
	}

	@Override
	protected Object idValue(DOSSIER dossier) {
		return dossier.getId();
	}

	@Override
	protected Collection<TraitementDossier> historiqueTraitements(DOSSIER dossier) {
		return traitementDossierDao.readByDossier(dossier);
	}

	@Override
	protected TraitementDossier traitementInstance() {
		return new TraitementDossier();
	}
	
	/**/
	
	protected void validationSaisie(DOSSIER dossier,Boolean soumission) throws ServiceException{
		
	}
	
	public void saisir(Operation operation,DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives){
		
		//debug(operation);
		Date dateCourante = new Date();
		if(operation==null){//mise a jour
			deplacementDao.update(dossier.getDeplacement());
			//debug(dossier);
			dossierDao.update(dossier);
			updatePieceJustificatives(dossier, pieceJustificatives);
		}else{//creation
			if(!deplacementService.exist(dossier.getDeplacement().getId()))
				deplacementService.creer(dossier.getDeplacement());
			else
				deplacementDao.update(dossier.getDeplacement());
			
			if(agentEtatService.findByMatricule(dossier.getBeneficiaire().getMatricule()) == null){
				if(agentEtatReferenceService.findById(dossier.getBeneficiaire().getMatricule()) == null)
					serviceException("Matricule inconnu !");
				else
					agentEtatDao.create(dossier.getBeneficiaire());
			}
			
			
			//dossier.setNumero(numero(dossier, pieceJustificatives));
			
			onDaoCreate(dossier);
			updatePieceJustificatives(dossier, pieceJustificatives);//on enregistre les pieces justificatives
			//on cree les pieces derivees
			entityManager.flush();
			for(PieceJustificativeAFournir pieceAImprimer : pieceJustificativeAFournirDao.readDeriveeByNatureDeplacementIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(),
					dossier.getDeplacement().getTypeDepense().getCode())){
				PieceJustificative p = new PieceJustificative(dossier,Code.NUMERO_PREFIX+pieceAImprimer.getTypePieceJustificative().getCode()+"/"+dossier.getId(), pieceAImprimer,dateCourante);
				//if(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT.equals(pieceAImprimer.getTypePieceJustificative().getCode()))
				//	p.setNumero(dossier.getNumero());
				pieceJustificativeDao.create(p);
			}
			TraitementDossier td = new TraitementDossier();
			td.setValidationType(ValidationType.ACCEPTER);
			traitementService.creer(operation, dossier,td, Code.STATUT_ACCEPTE);
			
			//debug(dossier.getDernierTraitement());
		}
	}
	
	private void updatePieceJustificatives(DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives){
		if(pieceJustificatives==null)
			return;
		for(PieceJustificative pieceJustificative : pieceJustificatives){
			pieceJustificative.setDossier(dossier);
			if(pieceJustificativeDao.exist(pieceJustificative.getId())){
				pieceJustificativeDao.update(pieceJustificative);
			}else if(StringUtils.isNotEmpty(pieceJustificative.getNumero()))
				pieceJustificativeDao.create(pieceJustificative);
				
		}
	}
	
	protected String numero(DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives){
		String p = Code.NUMERO_PREFIX+dossier.getDeplacement().getTypeDepense().getCode()+"/";
		//if(dossier.getFeuilleDeplacement()==null)
			return p+dossier.getDeplacement().getNature().getCode()+"/"+RandomStringUtils.randomAlphanumeric(4).toUpperCase();
		//else
			//return p+dossier. objetTraite.getType().getCode()+"/"+RandomStringUtils.randomAlphanumeric(4).toUpperCase();
	}
	
	protected String numero(PieceProduite piece){
		return Code.NUMERO_PREFIX+piece.getType().getCode()+"/"+RandomStringUtils.randomAlphanumeric(4).toUpperCase();
	}
	
	@Override
	public void enregistrer(ActionType actionType,DOSSIER dossier) throws ServiceException {
		switch(actionType){
			case ENREGISTRER:
				enregistrer(dossier);
				break;
			case SOUMETTRE:
				soumettre(dossier);
				break;
		}
	}
	
	protected void onDaoCreate(DOSSIER dossier){
		dao.create(dossier);
	}
	
/*	private void preparationSaisieSolde(DOSSIER dossier){
		//AKM - Enregistrement agent solde		
			String matricule = dossier.getBeneficiaire().getMatricule();
			AgentEtat agentEtat = agentEtatDao.readByMatricule(matricule);
			if(agentEtat==null){ 
				AgentEtatReference agentEtatReference=agentEtatReferenceDao.read(matricule);
				if(agentEtatReference==null)
					serviceException("Matricule inconnu !");
				dossier.getBeneficiaire().setDateNaissance(agentEtatReference.getDateNaissance());
				agentEtatDao.create(dossier.getBeneficiaire());
				//dossier.setBeneficiaire(agentEtatDao.readByMatricule(matricule));
			}else dossier.getBeneficiaire().setDateNaissance(agentEtat.getDateNaissance());
			
	
	}
	*/
	private void enregistrer(DOSSIER dossier){
		
		validationSaisie(dossier,false);
		
		
		if(!dao.exist(dossier.getId())){
			Operation operation = operationService.creer(Code.NATURE_OPERATION_SAISIE);
			saisir(operation, dossier, dossier.getPieceJustificatives());
		}else{
			//Statut statutCourant = dossier.getDernierTraitement().getStatut();
			//if(statutCourant!=null && !Code.STATUT_ACCEPTE.equals(statutCourant.getCode()))
			//	serviceException(ServiceExceptionType.DOSSIER_STATUT_ILLELGAL);
			saisir(null, dossier, dossier.getPieceJustificatives());
		}
		
		if(Boolean.TRUE.equals(dossier.getTransmettreBeneficiaire())){ 
			Operation operation = operationService.creer(Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_BENEFICIAIRE);
			TraitementDossier td = new TraitementDossier();
			td.setValidationType(ValidationType.ACCEPTER);
			traitementService.creer(operation, dossier, td,Code.STATUT_ACCEPTE);
			dao.update(dossier);
		}
		
		if(Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_ORGANISATEUR.equals(dossier.getTraitable().getNatureOperation().getCode())){
			Operation operation = operationService.creer(dossier.getTraitable().getNatureOperation().getCode());
			TraitementDossier td = new TraitementDossier();
			td.setValidationType(ValidationType.ACCEPTER);
			traitementService.creer(operation, dossier, td,Code.STATUT_ACCEPTE);
			dao.update(dossier);
		}
	}
	

	private void soumettre(DOSSIER dossier){
		enregistrer(dossier);
		validationSaisie(dossier,true);

		Operation operation = operationService.creer(Code.NATURE_OPERATION_SOUMISSION);
		TraitementDossier td = new TraitementDossier();
		td.setValidationType(ValidationType.ACCEPTER);
		traitementService.creer(operation, dossier, td,Code.STATUT_ACCEPTE);
		
		if(dossier.getDeplacement().getAddUser()!=null){
			
			dossier.getTraitable().setTraitement(new TraitementDossier());
			dossier.getTraitable().setNatureOperation(genericDao.readByClass(NatureOperation.class, Code.NATURE_OPERATION_RECEVABILITE));
			dossier.getTraitable().getTraitement().setValidationType(ValidationType.ACCEPTER);
			//dossierService.valider(Arrays.asList((Dossier)dossier));
			dossier.setCourrier(new Courrier("999", new Date()));
			//dossier.getCourrier().setNumero("999");
			deposer(Arrays.asList(dossier));
			
			
			
		}
		
		
	}

	@Override
	public void deposer(Collection<DOSSIER> dossiers) throws ServiceException {
		Operation operation = operationService.creer(Code.NATURE_OPERATION_DEPOT);
		for(DOSSIER dossier : dossiers){
			TraitementDossier td = new TraitementDossier();
			td.setValidationType(ValidationType.ACCEPTER);
			traitementService.creer(operation, dossier, td,Code.STATUT_ACCEPTE);
			dao.update(dossier);
		}
	}
	
	@Override
	public void annuler(Collection<DOSSIER> dossiers) throws ServiceException {
		Operation operation = operationService.creer(Code.NATURE_OPERATION_ANNULATION_DEMANDE);
		for(DOSSIER dossier : dossiers){
			TraitementDossier td = new TraitementDossier();
			td.setValidationType(ValidationType.ACCEPTER);
			traitementService.creer(operation, dossier, td,Code.STATUT_ACCEPTE);
			dao.update(dossier);
		}
	}
		
	/* Fonctions techniques  */
	
	protected abstract DOSSIER createDossier(NatureDeplacement natureDeplacement);
	
	protected void dossierNull(DOSSIER dossier){}
	
	protected void chargerPiecesJustificatives(DOSSIER dossier){
		if(dossier==null || dossier.getDeplacement()==null || dossier.getDeplacement().getTypeDepense()==null)
			return;
		dossier.setPieceJustificatives(new ArrayList<PieceJustificative>());
		
		/*---------- Chargement des pieces de base et ...*/
		//quelles sont les pieces a fournir
		Collection<PieceJustificativeAFournir> pieceJustificativeAFournirs = pieceJustificativeAFournirDao.readBaseByNatureDeplacementIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(),
				dossier.getDeplacement().getTypeDepense().getCode());
		
		//quelles sont les pieces déja fournis
		if(dao.exist(dossier.getId()))
			dossier.getPieceJustificatives().addAll(pieceJustificativeDao.readByDossier(dossier));
		
		//croisement
		for(PieceJustificativeAFournir pieceJustificativeAFournir : pieceJustificativeAFournirs){
			boolean trouve = false;
			for(PieceJustificative pieceJustificative : dossier.getPieceJustificatives())
				if(pieceJustificative.getModel().equals(pieceJustificativeAFournir)){
					trouve = true;
					break;
				}
			if(!trouve)
				dossier.getPieceJustificatives().add(new PieceJustificative(dossier,pieceJustificativeAFournir));
		}
		
		for(PieceJustificative pieceJustificative : dossier.getPieceJustificatives())
			if(pieceJustificative.getModel().getTypePieceJustificative().getCode().equals(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT)){
				dossier.setFeuilleDeplacement(pieceJustificative);
				break;
			}
		
	}
	
	@Override
	public void mettreAJourPiecesJustificatives(Collection<DOSSIER> dossiers) {
		if(dossiers.isEmpty())
			return;
		TypeDepense temp = dossiers.iterator().next().getTypeDepense();
		if(temp==null)
			return;
		for(DOSSIER dossier : dossiers)
			if(!dossier.getDeplacement().getTypeDepense().equals(dossier.getTypeDepense())){
				temp = dossier.getDeplacement().getTypeDepense();
				if(dossier.getTypeDepense()!=null)
					dossier.getDeplacement().setTypeDepense(dossier.getTypeDepense());
				
				chargerPiecesJustificatives(dossier);
				dossier.getDeplacement().setTypeDepense(temp);
			}
		for(DOSSIER dossier : dossiers)
			if(!dossier.getDeplacement().getTypeDepense().equals(dossier.getTypeDepense()))
				if(dossier.getTypeDepense()!=null)
					dossier.getDeplacement().setTypeDepense(dossier.getTypeDepense());
			
	}
		
	protected void initSaisie(Dossier source,DOSSIER destination){
		destination.setGrade(source.getGrade());
		destination.setDatePriseService(source.getDatePriseService());
		destination.setService(source.getService());
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Collection<DOSSIER> findByNatureDeplacementAndStatut(NatureDeplacement natureDeplacement, Statut statut) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByNatureDeplacementAndStatut(natureDeplacement, statut);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Collection<DOSSIER> findByNatureDeplacementsByStatut(Collection<NatureDeplacement> natureDeplacements, Statut statut) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByNatureDeplacementsByStatut(natureDeplacements, statut);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Collection<DOSSIER> findByNatureDeplacement(NatureDeplacement natureDeplacement) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByNatureDeplacement(natureDeplacement);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Collection<DOSSIER> findByNatureDeplacementsByNatureOperationIdByStatutId(Collection<NatureDeplacement> natureDeplacements,String natureOperationId,String statutId){
		return ((AbstractDossierDao<DOSSIER>)dao).readByNatureDeplacementsByNatureOperationIdByStatutId(natureDeplacements, natureOperationId, statutId);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Collection<DOSSIER> findByDeplacement(Deplacement deplacement) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByDeplacement(deplacement);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public DOSSIER findDernierCreeByAgentEtat(AgentEtat agentEtat) {
		return ((AbstractDossierDao<DOSSIER>)dao).readDernierCreeByAgentEtat(agentEtat);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Collection<DOSSIER> findByBordereauId(Long bordereauTransmissionId) {
		//System.out.println(bordereauTransmissionId);
		//System.out.println(((AbstractDossierDao<DOSSIER>)dao).readByBordereauId(bordereauTransmissionId));
		return null;//dtos(null,((AbstractDossierDao<DOSSIER>)dao).readByBordereauId(bordereauTransmissionId));
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<DOSSIER> findALiquiderByNatureDeplacementsByAspectLiquide(Collection<NatureDeplacement> natureDeplacements,AspectLiquide aspectLiquide){
		return findATraiter(natureDeplacements, Code.NATURE_OPERATION_ETABLISSEMENT_BL);
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Collection<DOSSIER> findEditerProjetRemboursement() {
		List<NatureDeplacement> natureDeplacements = new ArrayList<>(genericDao.readAllByClass(NatureDeplacement.class));
		String[] exclure = {Code.NATURE_DEPLACEMENT_AFFECTATION,Code.NATURE_DEPLACEMENT_MUTATION,Code.NATURE_DEPLACEMENT_RETRAITE};
		for(int i=0;i<natureDeplacements.size();)
			if(ArrayUtils.contains(exclure, natureDeplacements.get(i).getCode()))
				natureDeplacements.remove(i);
			else
				i++;
		Collection<DOSSIER> liste = ((AbstractDossierDao<DOSSIER>)dao).readByNatureDeplacementsByTypeDepenseIdByNatureOperationIdByStatutId(
				natureDeplacements, Code.TYPE_DEPENSE_REMBOURSEMENT, Code.NATURE_OPERATION_ETABLISSEMENT_PROJDEC, Code.STATUT_ACCEPTE);
		for(DOSSIER dossier : liste)
			init(dossier, null);
		return liste;
	}
		
	/* fonction aidantes */
	
	private boolean corrigeable(DOSSIER dossier){
		if(ArrayUtils.contains(OPERATION_CORRIGER, dossier.getTraitable().getDernierTraitement().getOperation().getNature().getCode())){
			OperationValidationConfig config = operationValidationConfigDao.readByNatureOperationIdByValidationType(
					dossier.getTraitable().getDernierTraitement().getOperation().getNature().getCode(), ValidationType.REJETER);
			return config.getStatutResultat().getCode().equals(dossier.getTraitable().getDernierTraitement().getStatut().getCode());
		}
		return false;
	}
	
	protected void natureOperationIsNull(DOSSIER dossier){
		super.natureOperationIsNull(dossier);
		if(corrigeable(dossier))
			dossier.getTraitable().setNatureOperation(genericDao.readByClass(NatureOperation.class,Code.NATURE_OPERATION_SOUMISSION));//l'utilisateur peut soumettre
		else if(Code.NATURE_OPERATION_RETOUR_FD.equals(dossier.getTraitable().getDernierTraitement().getOperation().getNature().getCode()))
			dossier.getTraitable().setNatureOperation(genericDao.readByClass(NatureOperation.class,Code.NATURE_OPERATION_ETABLISSEMENT_BL));
		
	}
	
	protected void initFirstFlow(DOSSIER dossier){
		super.initFirstFlow(dossier);
		beneficiaire(dossier);
		if(utilisateur() instanceof AgentEtat){
			DOSSIER dernierCree = ((AbstractDossierDao<DOSSIER>)dao).readDernierCreeByAgentEtat((AgentEtat) utilisateur());
			if(dernierCree!=null && !dossier.getDeplacement().getNature().getSceSolde())
				initSaisie(dernierCree, dossier);
		}
		dossier.setPieceAdministrative(new PieceJustificative(dossier, null, 
				pieceJustificativeAFournirDao.readAdministrativeByNatureDeplacementIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), dossier.getDeplacement().getTypeDepense().getCode())
				, null));
		dossier.setTypeDepense(dossier.getDeplacement().getTypeDepense());
		
	}
	
	protected void initMiddleFlow(DOSSIER dossier){
		super.initMiddleFlow(dossier);
		dossier.setNumero(Code.NUMERO_PREFIX+dossier.getDeplacement().getNature().getCode()+"/"+dossier.getId());
		//dossier.getPieceJustificatives().clear();
		
		String noc = dossier.getTraitable().getNatureOperation()==null?null:dossier.getTraitable().getNatureOperation().getCode();
		OperationValidationConfig creerDossierOpConfig = operationValidationConfigDao.readByNatureOperationIdByValidationType(Code.NATURE_OPERATION_SAISIE, ValidationType.ACCEPTER);
		dossier.setDateCreation(traitementDossierDao.readByDossierByNatureOperationIdByStatutId(dossier, Code.NATURE_OPERATION_SAISIE, 
				creerDossierOpConfig.getStatutResultat().getCode()).iterator().next().getOperation().getDate());
		
		OperationValidationConfig visaOpConfig = operationValidationConfigDao.readByNatureOperationIdByValidationType(Code.NATURE_OPERATION_VISA_BL, ValidationType.ACCEPTER);
		
		
		Boolean blVise = !traitementDossierDao.readByDossierByNatureOperationIdByStatutId(dossier, Code.NATURE_OPERATION_VISA_BL, visaOpConfig.getStatutResultat().getCode()).isEmpty();
		if(blVise || StringUtils.isNotEmpty(noc) && (Code.NATURE_OPERATION_VALIDATION_BL.equals(noc) || 
				Code.NATURE_OPERATION_TRANSMISSION_BL_VISA.equals(noc) ||
				Code.NATURE_OPERATION_VISA_BL.equals(noc))) {
			//dossier.setBulletinLiquidation((BulletinLiquidation) creationBlTraitement.getPieceProduite());
		}
		
		dossier.getDeplacement().setUiLibelle(dossier.getDeplacement().getNature().getLibelle());
		if(!Code.CATEGORIE_DEPLACEMENT_DEFINITIF.equals(dossier.getDeplacement().getNature().getCategorie().getCode()))
			dossier.getDeplacement().setUiLibelle(dossier.getDeplacement().getTypeDepense().getLibelle()+" "+dossier.getDeplacement().getUiLibelle());
		
		/*
		if(dossier instanceof DossierTransit){
			if(operationEffectuee(dossier, Code.NATURE_OPERATION_CONFORMITE)){
				if(hasRole(Code.ROLE_LIQUIDATEUR))
					piecesJustificativeModifiable(dossier, true,true, Code.TYPE_PIECE_ATTESTATION_PEC);
				else
					supprimerPiecesJustificative(dossier, Code.TYPE_PIECE_ATTESTATION_PEC);
			}else
				supprimerPiecesJustificative(dossier, Code.TYPE_PIECE_ATTESTATION_PEC);
		}*/
		
	}
	
	protected void natureOperationAExecuter(DOSSIER dossier,String natureOperationCode){
		super.natureOperationAExecuter(dossier, natureOperationCode);
		String noc = dossier.getTraitable().getNatureOperation().getCode();
		//les bl
		OperationValidationConfig creationOpConfig = operationValidationConfigDao.readByNatureOperationIdByValidationType(Code.NATURE_OPERATION_ETABLISSEMENT_BL, ValidationType.ACCEPTER);
		Collection<TraitementDossier> blTraitements = traitementDossierDao.readByDossierByNatureOperationIdByStatutId(dossier, Code.NATURE_OPERATION_ETABLISSEMENT_BL, 
				creationOpConfig.getStatutResultat().getCode());
		if(!blTraitements.isEmpty()){
			for(TraitementDossier traitementDossier : blTraitements){
				BulletinLiquidation bulletinLiquidation = (BulletinLiquidation) traitementDossier.getPieceProduite();
				bulletinLiquidationService.init(bulletinLiquidation, natureOperationCode); //indemniteCalculateurService.calculer(bulletinLiquidation);
				dossier.getBulletinLiquidations().add(bulletinLiquidation);
			}
		}
		
		dossier.setPieceAdministrative(pieceJustificativeDao.readAdministrativeByDossier(dossier));	
				
		if(Code.NATURE_OPERATION_SOUMISSION.equals(noc)){
			if(dossier instanceof DossierMission)
				piecesJustificativeModifiable(dossier, false,Code.TYPE_PIECE_FEUILLE_DEPLACEMENT);
		}else if(Code.NATURE_OPERATION_DEPOT.equals(noc))
			dossier.setCourrier(new Courrier());
		else if(Code.NATURE_OPERATION_TRANSMISSION_SAISIE_A_ORGANISATEUR.equals(noc)){
			piecesJustificativeModifiable(dossier, false,Code.TYPE_PIECE_COMMUNICATION,Code.TYPE_PIECE_FEUILLE_DEPLACEMENT);
		}else if(Code.NATURE_OPERATION_ETABLISSEMENT_BL.equals(noc)){
			

		}else if(Code.NATURE_OPERATION_RETOUR_FD.equals(noc)){
			piecesJustificativeModifiable(dossier, true,Code.TYPE_PIECE_FEUILLE_DEPLACEMENT);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public void init(DOSSIER dossier, String natureOperationCode) {
		chargerPiecesJustificatives(dossier);//TODO a deplacer aux lieux adequoi
		dossier.getBulletinLiquidations().clear();
		super.init(dossier, natureOperationCode);
	}
				
	protected String formatTraitementUiMessage(TraitementDossier td){
		NatureOperation no = td.getOperation().getNature();
		//Collection<OperationValidationConfig> configs = operationValidationConfigDao.readByNatureOperationId(no.getCode());
		
		switch(td.getStatut().getCode()){
			case Code.STATUT_ACCEPTE:return no.getMessage();
			case Code.STATUT_DIFFERE:return "Différé pour le motif suivant : "+td.getObservation();
			case Code.STATUT_REJETE:return "Rejeté pour le motif suivant : "+td.getObservation();
		}
		return null;
	}
	
	protected void beneficiaire(DOSSIER dossier){
		if(dossier.getBeneficiaire()==null){			
			if(dossier.getDeplacement().getNature().getSceSolde())
				dossier.setBeneficiaire(new AgentEtat());
			else 
				dossier.setBeneficiaire((AgentEtat) utilisateur());
		}
		
	}

	
	/**/
	
	protected Integer index(DOSSIER dossier,PieceJustificativeAFournir model){
		int index = -1;
		for(PieceJustificative pieceJustificative : dossier.getPieceJustificatives()){
			index++;
			if(pieceJustificative.getModel().equals(model))
				return index;
		}
		return -1;
	}
	
	protected Integer count(DOSSIER dossier,PieceJustificativeAFournir model){
		int n = 0;
		for(PieceJustificative pieceJustificative : dossier.getPieceJustificatives()){
			if(pieceJustificative.getModel().equals(model))
				n++;
		}
		return n;
	}
	
	protected void piecesJustificativeModifiable(DOSSIER dossier,Boolean yes,Boolean editable,String...typePieces){
		for(PieceJustificative pieceJustificative : dossier.getPieceJustificatives())
			for(String typePiece : typePieces)
				if(pieceJustificative.getModel().getTypePieceJustificative().getCode().equals(typePiece)){
					pieceJustificative.setModifiable(yes);
					pieceJustificative.setEditable(editable);
				}
	}
	
	protected void piecesJustificativeModifiable(DOSSIER dossier,Boolean yes,String...typePieces){
		piecesJustificativeModifiable(dossier, yes, null, typePieces);
	}
	
	protected void supprimerPiecesJustificative(DOSSIER dossier,String...typePieces){
		List<PieceJustificative> pieces = (List<PieceJustificative>) dossier.getPieceJustificatives();
		for(int i=0;i<pieces.size();)
			for(String typePiece : typePieces)
				if(pieces.get(i).getModel().getTypePieceJustificative().getCode().equals(typePiece))
					pieces.remove(i);
				else
					i++;
	}


	
	
}
