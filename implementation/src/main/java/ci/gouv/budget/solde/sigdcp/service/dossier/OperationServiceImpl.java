package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.OperationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.TraitementDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Operation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.DefaultServiceImpl;
import ci.gouv.budget.solde.sigdcp.service.ServiceExceptionType;

@Stateless @Log
public class OperationServiceImpl extends DefaultServiceImpl<Operation,Long> implements OperationService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	//@Inject private OperationValidator validator;
	@Inject private DossierDao dossierDao;
	@Inject private DynamicEnumerationDao dynamicEnumerationDao;
	@Inject private TraitementDao traitementDao;
	
	@Inject
	public OperationServiceImpl(OperationDao dao) {
		super(dao);
	}

	@Override
	public void valider(String natureOperationCode,Collection<Dossier> dossiers, Personne effectueePar) {
		NatureOperation natureOperation = dynamicEnumerationDao.readByClass(NatureOperation.class, natureOperationCode);
		if(natureOperation==null)
			serviceException(ServiceExceptionType.OPERATION_INCONNUE);
		//validator.setTraitements(traitements);
		//validator.validate(natureOperation);
		//if(!validator.isSucces())
		//	serviceException(validator.getMessagesAsString());
		
		Operation operation = new Operation(new Date(), natureOperation, effectueePar);
		((OperationDao)dao).create(operation);
		//determiner le nouveau statut
		for(Dossier dossier : dossiers){
			dossier.getDernierTraitement().setDossier(dossier);
			dossier.getDernierTraitement().setOperation(operation);
			Statut statutCourant = dossierDao.read(dossier.getNumero()).getDernierTraitement().getStatut();
			switch(dossier.getDernierTraitement().getValidationType()){
			case ACCEPTER:			
				dossier.getDernierTraitement().setStatut(statutSuivant(natureOperation));
				break;
			case DIFFERER:
				dossier.getDernierTraitement().setStatut(statutCourant);
				break;
			case REJETER:
				if(Code.STATUT_SOUMIS.equals(statutCourant.getCode()))
					dossier.getDernierTraitement().setStatut(dynamicEnumerationDao.readByClass(Statut.class,Code.STATUT_SAISIE));
				if(Code.STATUT_RECEVABLE.equals(statutCourant.getCode()))
					dossier.getDernierTraitement().setStatut(dynamicEnumerationDao.readByClass(Statut.class,Code.STATUT_SAISIE));
				if(Code.STATUT_CONFORME.equals(statutCourant.getCode()))
					dossier.getDernierTraitement().setStatut(dynamicEnumerationDao.readByClass(Statut.class,Code.STATUT_SAISIE));
				break;
			}
			
			traitementDao.create(dossier.getDernierTraitement());
			dossierDao.update(dossier);
		}
	}
	
	private Statut statutSuivant(NatureOperation natureOperation){
		if(Code.NATURE_OPERATION_RECEVABILITE.equals(natureOperation.getCode()))
			return dynamicEnumerationDao.readByClass(Statut.class,Code.STATUT_RECEVABLE);
		if(Code.NATURE_OPERATION_CONFORMITE.equals(natureOperation.getCode()))
			return dynamicEnumerationDao.readByClass(Statut.class,Code.STATUT_CONFORME);
		log.severe("Aucun statut pour l'operation "+natureOperation);
		return null;
	}
	
}
