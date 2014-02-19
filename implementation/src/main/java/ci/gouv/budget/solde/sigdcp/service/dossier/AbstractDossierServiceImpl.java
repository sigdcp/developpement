package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.AbstractDossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.OperationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Operation;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.ValidationType;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType;


public abstract class AbstractDossierServiceImpl<DOSSIER extends Dossier> extends DefaultServiceImpl<DOSSIER, String> implements AbstractDossierService<DOSSIER>,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject private DeplacementService deplacementService;
	@Inject private DossierDao dossierDao;
	@Inject private PieceJustificativeDao pieceJustificativeDao;
	@Inject private DynamicEnumerationDao dynamicEnumerationDao;
	@Inject private OperationDao operationDao;
	@Inject private TraitementService traitementService;
	@Inject protected PieceJustificativeAFournirDao pieceJustificativeAFournirDao;
	
	//@Inject private DaoCreateHelper daoCreateHelper;
	//private Action dossierDaoCreate;
	
	public AbstractDossierServiceImpl(AbstractDossierDao<DOSSIER> dao) {
		super(dao); 
	}
	 
	/*--------- Fonctions métiers ----------*/
	
	protected void validationSaisie(DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives,Personne personne,Boolean soumission) throws ServiceException{
		
	}
	
	public void saisir(Operation operation,DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives,Personne personne){
		Date dateCourante = new Date();
		if(operation==null){//mise a jour
			dossierDao.update(dossier);
			updatePieceJustificatives(dossier, pieceJustificatives);
		}else{//creation
			deplacementService.create(dossier.getDeplacement());
			dossier.setNumero(System.currentTimeMillis()+"");//TODO un numéro métier doit etre construit
			//dossierDaoCreate.execute(dossier);
			__createDossier__(dossier);
			updatePieceJustificatives(dossier, pieceJustificatives);//on enregistre les pieces justificatives
			//on cree les pieces derivees
			for(PieceJustificativeAFournir pieceAImprimer : pieceJustificativeAFournirDao.readDeriveeByNatureDeplacementIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(),
					dossier.getDeplacement().getTypeDepense().getCode()))
				pieceJustificativeDao.create(new PieceJustificative(dossier,RandomStringUtils.randomNumeric(4), pieceAImprimer,dateCourante));
			//traiter(operation, dossier, personne, Code.STATUT_SAISIE);	//on traite le dossier
			traitementService.create(operation, dossier, personne, Code.STATUT_SAISIE);
		}
	}
	
	private void updatePieceJustificatives(Dossier dossier,Collection<PieceJustificative> pieceJustificatives){
		for(PieceJustificative pieceJustificative : pieceJustificatives){
			pieceJustificative.setDossier(dossier);
			if(pieceJustificativeDao.exist(pieceJustificative.getId()))
				pieceJustificativeDao.update(pieceJustificative);
			else if(StringUtils.isNotEmpty(pieceJustificative.getNumero()))
				pieceJustificativeDao.create(pieceJustificative);
		}
	}
	/*
	private void traiter(Operation operation,Dossier dossier,Personne personne,String statutId){
		Traitement traitement = new Traitement(operation, null, dossier, dynamicEnumerationDao.readByClass(Statut.class, statutId));
		traitementDao.create(traitement);
		dossier.setDernierTraitement(traitement);
		dossierDao.update(dossier);
	}*/
	
	@Override
	public void enregistrer(DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives,Personne personne) throws ServiceException {
		
		validationSaisie(dossier, pieceJustificatives,personne,false);
		//est ce une creation ou une mise à jour
		//Date dateCourante = new Date();
		//DOSSIER dossierExistant = StringUtils.isNotEmpty(dossier.getNumero())?null:((AbstractDossierDao<DOSSIER>)dao).read(dossier.getNumero());

		if(!dao.exist(dossier.getNumero())){
			//creation
			//dossier.setNumero(System.currentTimeMillis()+"");
			//dossier.getDeplacement().setDateCreation(new Date());
			//deplacementDao.create(dossier.getDeplacement());
			//System.out.println(ToStringBuilder.reflectionToString(dossier.getBeneficiaire(), ToStringStyle.MULTI_LINE_STYLE));
			//dossier.getBeneficiaire().setAyantDroit(null);
			//__createDossier__(dossier);
			//on cree les pieces generee par le syteme
			/*
			for(PieceJustificativeAFournir pieceAImprimer : pieceJustificativeAFournirDao.readDeriveeByNatureDeplacementIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(),
					dossier.getDeplacement().getTypeDepense().getCode()))
				pieceJustificativeDao.create(new PieceJustificative(dossier,RandomStringUtils.randomNumeric(4), pieceAImprimer,new Date()));
			
			Operation operationSaisie = new Operation(dateCourante,dynamicEnumerationDao.readByClass(NatureOperation.class,Code.NATURE_OPERATION_SAISIE),personne);
			operationDao.create(operationSaisie);
			Traitement traitement = new Traitement(operationSaisie, null, dossier, dynamicEnumerationDao.readByClass(Statut.class, Code.STATUT_SAISIE));
			traitementDao.create(traitement);
			
			dossier.setDernierTraitement(traitement);
			//dossier.getBeneficiaire().setAyantDroit(null);
			dao.update(dossier);
			*/
			//daoCreateHelper.init();
			//daoCreateHelper.createDeplacement(dossier.getDeplacement());
			Operation operation = new Operation(new Date(),dynamicEnumerationDao.readByClass(NatureOperation.class,Code.NATURE_OPERATION_SAISIE),personne);
			operationDao.create(operation);
			//daoCreateHelper.saisir(operation,dossier,pieceJustificatives, personne);
			saisir(operation, dossier, pieceJustificatives, personne);
		}else{
			//mise a jour d'un dossier qui est en saisie uniquement
			//System.out.println(ToStringBuilder.reflectionToString(dossier,ToStringStyle.MULTI_LINE_STYLE));
			Statut statutCourant = dossier.getDernierTraitement().getStatut();
			if(statutCourant!=null && !Code.STATUT_SAISIE.equals(statutCourant.getCode()))
				serviceException(ServiceExceptionType.DOSSIER_STATUT_ILLELGAL);
			/*
			dao.update(dossier);//mise a jour du dossier
			*/
			saisir(null, dossier, pieceJustificatives, personne);
			
			//daoCreateHelper.saisir(null, dossier, pieceJustificatives,personne);
		}
		
		/*
		for(PieceJustificative pieceJustificative : pieceJustificatives){
			pieceJustificative.setDossier(dossier);
			if(pieceJustificativeDao.exist(pieceJustificative.getId()))
				pieceJustificativeDao.update(pieceJustificative);
			else if(StringUtils.isNotEmpty(pieceJustificative.getNumero()))
				pieceJustificativeDao.create(pieceJustificative);
		}*/
	}
	
	protected void __createDossier__(DOSSIER dossier){
		dao.create(dossier);
	}
	
	@Override
	public void soumettre(DOSSIER dossier,Collection<PieceJustificative> pieceJustificatives,Personne personne)throws ServiceException {
		enregistrer(dossier, pieceJustificatives,personne);
		validationSaisie(dossier, pieceJustificatives,personne,true);
		
		//Operation operation = new Operation(new Date(),dynamicEnumerationDao.readByClass(NatureOperation.class,Code.NATURE_OPERATION_SOUMISSION),personne);
		//operationDao.create(operation);
		
		Operation operation = new Operation(new Date(),dynamicEnumerationDao.readByClass(NatureOperation.class,Code.NATURE_OPERATION_SOUMISSION),personne);
		operationDao.create(operation);
		//traiter(operation, dossier, personne, Code.STATUT_SOUMIS);
		traitementService.create(operation, dossier, personne, Code.STATUT_SOUMIS);
		
		/*
		Operation operationSoumission = new Operation(new Date(),dynamicEnumerationDao.readByClass(NatureOperation.class,Code.NATURE_OPERATION_SOUMISSION),personne);
		operationDao.create(operationSoumission);
		Traitement traitement = new Traitement(operationSoumission, null, dossier, dynamicEnumerationDao.readByClass(Statut.class, Code.STATUT_SOUMIS));
		traitementDao.create(traitement);
		
		dossier.setDernierTraitement(traitement);
		dao.update(dossier);*/
	}

	@Override
	public void deposer(DOSSIER dossier) throws ServiceException {
		dao.update(dossier);
	}

	@Override
	public void accepterRecevabilite(DOSSIER dossier) throws ServiceException {
		//Operation operation = new Operation(code, new Date(), nature);
	}
	
	@Override
	public void accepterRecevabilite(Collection<DOSSIER> dossiers) throws ServiceException {
		for(DOSSIER dossier : dossiers)
			accepterRecevabilite(dossier);
	}
	
	@Override
	public void rejeterRecevabilite(Collection<DOSSIER> dossiers, String motif)
			throws ServiceException {
		
		
	}
	
	@Override
	public void rejeterRecevabilite(DOSSIER dossier, String motif)
			throws ServiceException {

		
	}

	@Override
	public void accepterConformite(DOSSIER dossier) throws ServiceException {
		
	} 
	
	@Override
	public void accepterConformite(Collection<DOSSIER> dossiers) throws ServiceException {
		for(DOSSIER dossier : dossiers)
			accepterConformite(dossier);
		
	}
	
	@Override
	public void rejeterConformite(Collection<DOSSIER> dossiers, String motif)
			throws ServiceException {
		
		
	}
	
	@Override
	public void rejeterConformite(DOSSIER dossier, String motif)
			throws ServiceException {
		
		
	}
	
	/* Fonctions techniques  */
	
	
	
	@Override
	public DOSSIER findSaisieByPersonneByNatureDeplacement(Personne personne, NatureDeplacement natureDeplacement) {
		return ((AbstractDossierDao<DOSSIER>)dao).readSaisieByPersonneByNatureDeplacement(personne, natureDeplacement);
	}
	
	@Override
	public Collection<DOSSIER> findByNatureDeplacementAndStatut(NatureDeplacement natureDeplacement, Statut statut) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByNatureDeplacementAndStatut(natureDeplacement, statut);
	}
	
	@Override
	public Collection<DOSSIER> findByNatureDeplacementsByStatut(Collection<NatureDeplacement> natureDeplacements, Statut statut) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByNatureDeplacementsByStatut(natureDeplacements, statut);
	}
	
	@Override
	public Collection<DOSSIER> findByNatureDeplacement(NatureDeplacement natureDeplacement) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByNatureDeplacement(natureDeplacement);
	}
	
	@Override
	public Collection<DOSSIER> findByStatut(Statut statut) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByStatut(statut);
	}
	
	@Override
	public Collection<DOSSIER> findByStatutId(String statutId) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByStatutId(statutId);
	}
	
	@Override
	public Collection<DOSSIER> findByAgentEtat(AgentEtat agentEtat) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByAgentEtat(agentEtat);
	}
	
	@Override
	public Collection<DOSSIER> findByAgentEtatAndAyantDroit(AgentEtat agentEtat) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByAgentEtatAndAyantDroit(agentEtat);
	}
	
	@Override
	public Collection<DOSSIER> findByDeplacement(Deplacement deplacement) {
		return ((AbstractDossierDao<DOSSIER>)dao).readByDeplacement(deplacement);
	}
	
	@Override
	public String findInstructions(DOSSIER dossier) {
		StringBuilder instructions = new StringBuilder();
		if(!((AbstractDossierDao<DOSSIER>)dao).exist(dossier.getNumero()) || dossier.getDernierTraitement()==null || dossier.getDernierTraitement().getStatut()==null)
			instructions.append("Veuillez enregistrer le dossier");
		else if(Code.STATUT_SAISIE.equals(dossier.getDernierTraitement().getStatut().getCode()))
			instructions.append("Veuillez soumettre le dossier");
		else if(Code.STATUT_SOUMIS.equals(dossier.getDernierTraitement().getStatut().getCode()))
			instructions.append("Votre dossier est en cours de traitement : vérification de la reçevabilité");
		else if(Code.STATUT_RECEVABLE.equals(dossier.getDernierTraitement().getStatut().getCode()))
			if(StringUtils.isEmpty(dossier.getCourrier().getNumero()))
				instructions.append("Veuillez déposer votre dossier au services de la solde et ensuite renseigner les informations de courrier");
			else
				instructions.append("Votre dossier est en cours de traitement : vérification de la conformité");
		else if(Code.STATUT_CONFORME.equals(dossier.getDernierTraitement().getStatut().getCode()))
			instructions.append("Votre dossier est en cours de traitement : liquidation");
		
		if(dossier.getDernierTraitement()!=null && dossier.getDernierTraitement().getValidationType()!=null && !ValidationType.ACCEPTER.equals(dossier.getDernierTraitement().getValidationType()))
			instructions.append("\r\n Motif "+dossier.getDernierTraitement().getValidationType()+" du dernier traitement : " +dossier.getDernierTraitement().getMotif());
		return instructions.toString();
	}

}
