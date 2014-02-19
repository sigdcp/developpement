package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.dao.DynamicEnumerationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DeplacementDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.OperationDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeAFournirDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.TraitementDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureOperation;
import ci.gouv.budget.solde.sigdcp.model.dossier.Operation;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.Statut;
import ci.gouv.budget.solde.sigdcp.model.dossier.Traitement;
import ci.gouv.budget.solde.sigdcp.model.identification.Personne;
import ci.gouv.budget.solde.sigdcp.service.utils.Action;


public class DaoCreateHelper implements Serializable {

	private static final long serialVersionUID = -8091730452402248411L;
	
	@Inject private DossierDao dossierDao;
	@Inject private PieceJustificativeDao pieceJustificativeDao;
	@Inject private DynamicEnumerationDao dynamicEnumerationDao;
	@Inject private OperationDao operationDao;
	@Inject private TraitementDao traitementDao;
	@Inject private PieceJustificativeAFournirDao pieceJustificativeAFournirDao;
	@Inject private DeplacementDao deplacementDao;
	
	@Setter
	private Date dateCourante;
	
	
	public void init(){
		dateCourante = new Date();
		/*
		dossierDaoCreate = new Action() {
			private static final long serialVersionUID = -3313129604734431548L;
			@Override
			protected Object __execute__(Object object){
				dossierDao.create((Dossier) object);
				return null;
			}
		};
		*/
	}
	
	public void createDeplacement(Deplacement deplacement){
		
	}
	

	
	public void soumettre(Operation operation,Dossier dossier,Personne personne){
		//traiter(operation, dossier, personne, Code.STATUT_SOUMIS);
	}
	/*
	private void traiter(Operation operation,Dossier dossier,Personne personne,String statutId){
		Traitement traitement = new Traitement(operation, null, dossier, dynamicEnumerationDao.readByClass(Statut.class, statutId));
		traitementDao.create(traitement);
		dossier.setDernierTraitement(traitement);
		dossierDao.update(dossier);
	}*/
	
	public Operation createOperation(String code,Personne personne){
		Operation operation = new Operation(dateCourante,dynamicEnumerationDao.readByClass(NatureOperation.class,code),personne);
		operationDao.create(operation);
		return operation;
	}
	

	
}
