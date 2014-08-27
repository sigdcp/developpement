package ci.gouv.budget.solde.sigdcp.service.fichier;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierTransitDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.GroupeDDDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.BulletinLiquidation;
import ci.gouv.budget.solde.sigdcp.model.dossier.CategorieDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Document;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierMission;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierTransit;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.identification.Sexe;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeDD;
import ci.gouv.budget.solde.sigdcp.model.indemnite.GroupeMission;
import ci.gouv.budget.solde.sigdcp.model.indemnite.IndemniteCalculee;
import ci.gouv.budget.solde.sigdcp.model.template.etat.AttestationdePriseenChargeEtat;
import ci.gouv.budget.solde.sigdcp.model.template.etat.BondeTransportEtat;
import ci.gouv.budget.solde.sigdcp.model.template.etat.BulletinLiquidationDDEtat;
import ci.gouv.budget.solde.sigdcp.model.template.etat.FeuilleDeplacementEtat;
import ci.gouv.budget.solde.sigdcp.model.template.etat.RemboursementEtat;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.dossier.BordereauTransmissionService;
import ci.gouv.budget.solde.sigdcp.service.dossier.DocumentService;
import ci.gouv.budget.solde.sigdcp.service.indemnite.GroupeMissionService;
import ci.gouv.budget.solde.sigdcp.service.indemnite.IndemniteOperandeService;
import ci.gouv.budget.solde.sigdcp.service.resources.report.Report;

@Stateless
public class EtatServiceJasperImpl implements EtatService {

	//private @Inject BulletinLiquidationDao bulletinLiquidationDao;
	@Inject private BordereauTransmissionService bordereauTransmissionService;
	@Inject private DossierDao dossierDao;
	@Inject private DossierTransitDao dossierTransitDao;
	@Inject private IndemniteOperandeService indemniteOperandeService;
	@Inject private PieceJustificativeDao pieceJustificativeDao;
	@Inject private GroupeDDDao groupeDDDao;
	@Inject private GroupeMissionService groupeMissionService;
	@Inject private DocumentService documentService;
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public <T> byte[] build(Class<T> aClass,InputStream templateInputStream,Collection<T> dataSource) {
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataSource);
		try {
			JasperDesign jasperDesign = JRXmlLoader.load(templateInputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);	
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			throw new ServiceException(e);
		}
	}
	
	private Collection<PieceJustificative> pieceJustificatives(Collection<PieceJustificative> pieceJustificatives,String type){
		Collection<PieceJustificative> collection = new ArrayList<>();
		for(PieceJustificative p : pieceJustificatives)
			if(type.equals(p.getModel().getTypePieceJustificative().getCode()))
				collection.add(p);
		return collection;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public byte[] build(Document document) {
		if(document instanceof PieceJustificative){
			PieceJustificative pieceJustificative = (PieceJustificative) document;
			if(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT.equals(pieceJustificative.getModel().getTypePieceJustificative().getCode()))
				if(pieceJustificative.getDossier() instanceof DossierDD)
					return feuilleDeplacementDD(pieceJustificative);
				else
					return feuilleDeplacementMhci(pieceJustificative);
			else if(Code.TYPE_PIECE_BON_TRANSPORT.equals(pieceJustificative.getModel().getTypePieceJustificative().getCode()))
				return bonTransportDD(pieceJustificative);
						
		}else if(document instanceof PieceProduite){
			PieceProduite pieceProduite = (PieceProduite) document;
			if(Code.TYPE_PIECE_PRODUITE_BL.equals(pieceProduite.getType().getCode())){
				Dossier dossier = dossierDao.readByBulletinLiquidation((BulletinLiquidation) pieceProduite);
				if(dossier instanceof DossierDD)
					return blDD((DossierDD) dossier, (BulletinLiquidation) document, "bulletinliquidationdd.jrxml");
				else
					;//return bl
				return null;
			}else if(Code.TYPE_PIECE_PRODUITE_APEC.equals(pieceProduite.getType().getCode())){
				DossierTransit dossier = dossierTransitDao.readByAttestationPriseEnCharge(pieceProduite);
				return apec(pieceProduite, dossier);
			}else if(Code.TYPE_PIECE_PRODUITE_DECISION_REMB.equals(pieceProduite.getType().getCode())){
				Dossier dossier = dossierDao.readByPieceProduite(pieceProduite);
				return decisionRemb(pieceProduite, dossier);
			}else if(Code.TYPE_PIECE_PRODUITE_BT.equals(pieceProduite.getType().getCode())){
				//DossierTransit dossier = dossierTransitDao.readByAttestationPriseEnCharge(pieceProduite);
				return bt((BordereauTransmission) pieceProduite);
			}
		}
		throw new ServiceException("Aucun Etat disponible pour <"+document.getId()+">");
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public byte[] buildBulletinLiquidation(Long numeroDossier) {
		return null;
	}
	
	
	
	private byte[] blDD(DossierDD dossier,BulletinLiquidation bulletinLiquidation,String fichierEtat){
		InputStream inputStream = Report.class.getResourceAsStream(fichierEtat);
		CategorieDeplacement categorieDeplacement = dossier.getDeplacement().getNature().getCategorie();
		List<BulletinLiquidationDDEtat> dataSource = new LinkedList<>();
		dataSource.add(new BulletinLiquidationDDEtat(pieceJustificativeDao.readAdministrativeByDossier(dossier), 
				pieceJustificativeDao.readByDossierByTypeId(dossier, Code.TYPE_PIECE_FEUILLE_DEPLACEMENT).iterator().next(), 
				bulletinLiquidation, null, null, indemniteOperandeService.nombreEnfant(dossier),indemniteOperandeService.distance(dossier),dossier.getGroupe().getLibelle(), 
				2,categorieDeplacement.getNombreJourIndemniteJournaliere(),"FORMULE IKP",indemniteCalculee(dossier, Code.INDEMNITE_KILOMETRIQUE_PERSONNE).getMontant().toString(), 
				"FORMULE AGENT BAGAGES", indemniteOperandeService.poidsAgent((GroupeDD) dossier.getGroupe())+"", 
				"FORMULE CONJOINT BAGAGES", indemniteOperandeService.poidsConjoint((GroupeDD) dossier.getGroupe())+"", 
				"FORMULE ENFANT BAGAGES", indemniteOperandeService.poidsEnfant((GroupeDD) dossier.getGroupe())+"", 
				
				"FORMULE AGENT MONTANT", indemniteOperandeService.montantAgent((GroupeDD) dossier.getGroupe())+"", 
				"FORMULE CONJOINT MONTANT", indemniteOperandeService.montantConjoint((GroupeDD) dossier.getGroupe())+"", 
				"FORMULE ENFANT MONTANT", indemniteOperandeService.montantEnfant((GroupeDD) dossier.getGroupe())+"", 
				
				dossier.getMontantIndemnisation().toString(), "TOTAL EN LETTRE"));
		
		return build(BulletinLiquidationDDEtat.class, inputStream, dataSource);
	}
	
	/*
	private byte[] blMhci(DossierMission dossier,BulletinLiquidation bulletinLiquidation,String fichierEtat){
		InputStream inputStream = Report.class.getResourceAsStream(fichierEtat);
		CategorieDeplacement categorieDeplacement = dossier.getDeplacement().getNature().getCategorie();
		dossierService.calculerMontantIndemnisation(dossier);
		
		List<BulletinLiquidation> dataSource = new LinkedList<>();
		dataSource.add(new BulletinLiquidationDDEtat(pj(dossier), pj(dossier), bulletinLiquidation, null, null, details.getNombreEnfant().intValue(), details.getDistance().intValue(), details.getGroupe().getLibelle(), 
				2,categorieDeplacement.getNombreJourIndemniteJournaliere(),details.getFormuleIkp(),details.getIndemniteKilometrique()+"", 
				details.getAgent().getFormuleBagages(), details.getAgent().getIndemniteBagages()+"", details.getConjoint().getFormuleBagages(), details.getConjoint().getIndemniteBagages()+"", 
				details.getEnfants().getFormuleBagages(), details.getEnfants().getIndemniteBagages()+"", details.getAgent().getFormuleJournaliere(), details.getAgent().getIndemniteJournaliere()+"",
				details.getConjoint().getFormuleJournaliere(), details.getConjoint().getIndemniteJournaliere()+"", details.getEnfants().getFormuleJournaliere(), details.getEnfants().getIndemniteJournaliere()+"",
				details.getTotal()+"", details.getTotalEnLettre()));
		return build(BulletinLiquidationDDEtat.class, inputStream, dataSource);
	}*/
	
	private BondeTransportEtat bonTransportEtat(BondeTransportEtat bonTransportEtat,String libdoc,String statutdoc){
		bonTransportEtat.setLibdoc(libdoc);
		bonTransportEtat.setStatutdoc(statutdoc);
		return bonTransportEtat;
	}
	
	private byte[] bonTransportDD(PieceJustificative pieceJustificative){
		InputStream inputStream = Report.class.getResourceAsStream("bondetransportdd.jrxml");
		List<BondeTransportEtat> dataSource = new LinkedList<>();
		PieceJustificative padmin = pieceJustificativeDao.readAdministrativeByDossier(pieceJustificative.getDossier());
		BondeTransportEtat bt = new BondeTransportEtat(pieceJustificative, "Original", null,padmin.getModel().getTypePieceJustificative().getLibelle(), padmin.getNumero(), padmin.getDateEtablissement()+"", 
				"Ministère", "Transporteur", "SDDCP", "RECPSERVICE", 
				new Date()+"", new Date()+"", new Date()+"", "Lieu Signature", "Budget général", "", "", "", "", "000410", new Date()+"", "332", "4101", "016289", "Lieu certif", 
				new Date()+"", "", "Materiel transporte", padmin.getDossier().getMontantIndemnisation()+"",documentService.genererCodeBarre(pieceJustificative));
		dataSource.add(bonTransportEtat(bt, "BON TRANSPORT", "ORIGINAL"));
		return build(BondeTransportEtat.class, inputStream, dataSource);
	}
	
	private byte[] feuilleDeplacementDD(PieceJustificative pieceJustificative){
		InputStream inputStream = Report.class.getResourceAsStream("feuilledeplacementdd.jrxml");
		List<FeuilleDeplacementEtat> dataSource = new LinkedList<>();
		Collection<PieceJustificative> pieceJustificatives = pieceJustificativeDao.readByDossier(pieceJustificative.getDossier());
		
		GroupeDD groupeDD = groupeDDDao.readByGrade(pieceJustificative.getDossier().getGrade());
		Boolean male = Sexe.MASCULIN.equals(pieceJustificative.getDossier().getBeneficiaire().getSexe());
		Boolean marie = !pieceJustificatives(pieceJustificatives, Code.TYPE_PIECE_EXTRAIT_MARIAGE).isEmpty();
		//PieceJustificative decision = pieceJustificatives(pieceJustificatives, Code.TYPE_PIECE_DECISION_AFFECTATION).iterator().next();
		PieceJustificative decision = pieceJustificativeDao.readAdministrativeByDossier(pieceJustificative.getDossier());
		int nbe = pieceJustificatives(pieceJustificatives, Code.TYPE_PIECE_EXTRAIT_NAISSANCE).size();
		
		StringBuilder accompaganteur = new StringBuilder();
		if(marie)
			accompaganteur.append("Epou"+(male?"x":"se"));
		if(nbe>0){
			accompaganteur.append(accompaganteur.length()==0?"":" et "+ (nbe+" enfant"+(nbe>1?"s":"")));
		}
		
		dataSource.add(new FeuilleDeplacementEtat(pieceJustificative, "Décision", decision.getDateEtablissement()+"", groupeDD.getLibelle(), pieceJustificative.getDossier().getBeneficiaire().getIndice()+"",
				accompaganteur.toString(),documentService.genererCodeBarre(pieceJustificative)));
		return build(FeuilleDeplacementEtat.class, inputStream, dataSource);
	}
	
	private byte[] feuilleDeplacementMhci(PieceJustificative pieceJustificative){
		InputStream inputStream = Report.class.getResourceAsStream("feuilledeplacementmhci.jrxml");
		List<FeuilleDeplacementEtat> dataSource = new LinkedList<>();
		DossierMission dossier = (DossierMission) pieceJustificative.getDossier();
		GroupeMission groupe = groupeMissionService.findByFonctionOrGrade(dossier.getFonction(), dossier.getGrade());
		
		StringBuilder accompaganteur = new StringBuilder();		
		dataSource.add(new FeuilleDeplacementEtat(pieceJustificative, "???", "???", groupe.getLibelle(), 
				pieceJustificative.getDossier().getBeneficiaire().getIndice()+"",accompaganteur.toString(),null));
		return build(FeuilleDeplacementEtat.class, inputStream, dataSource);
	}
	
	private byte[] apec(PieceProduite piece,DossierTransit dossier){
		InputStream inputStream = Report.class.getResourceAsStream("attestationprisecharge.jrxml");
		List<AttestationdePriseenChargeEtat> dataSource = new LinkedList<>();
		dataSource.add(new AttestationdePriseenChargeEtat(piece,dossier, "TRANSITAIRE", "CHAPITRE IMPUTATION", "ARTICLE IMPUTATION", "PARAGRAPHE IMPUTATION"));
		return build(AttestationdePriseenChargeEtat.class, inputStream, dataSource);
	}
	
	private byte[] decisionRemb(PieceProduite piece,Dossier dossier){
		InputStream inputStream = Report.class.getResourceAsStream("decisionremboursement3.jrxml");
		List<RemboursementEtat> dataSource = new LinkedList<>();
		dataSource.add( new RemboursementEtat(dossier.getDeplacement().getNature().getLibelle(), dossier.getBeneficiaire().getNomPrenoms(),
				"A Calculer", "qu'il paye", "Chapitre à determiner", "Libelle Chapitre à determiner", dossier.getDateCreation()+"", new ArrayList<>(pieceJustificativeDao.readByDossier(dossier))));
		return build(RemboursementEtat.class, inputStream, dataSource);
	}
	
	private byte[] bt(BordereauTransmission piece){
		InputStream inputStream = Report.class.getResourceAsStream("bordereautest.jrxml");
		bordereauTransmissionService.init(piece, null);
		return build(BordereauTransmission.class, inputStream, Arrays.asList(piece));
	}
	
	private IndemniteCalculee indemniteCalculee(Dossier dossier,String codeIndemnite){
		for(IndemniteCalculee indemniteCalculee : dossier.getIndemniteCalculees())
			if(indemniteCalculee.getId().getIndeminiteId().equals(codeIndemnite))
				return indemniteCalculee;
		return null;
	}

}
