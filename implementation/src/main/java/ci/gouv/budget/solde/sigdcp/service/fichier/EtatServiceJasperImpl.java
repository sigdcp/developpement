package ci.gouv.budget.solde.sigdcp.service.fichier;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.template.etat.FeuilleDeplacementEtat;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.resources.report.Report;

public class EtatServiceJasperImpl implements EtatService {

	@Override
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
	
	@Override
	public byte[] build(PieceJustificative pieceJustificative) {
		if(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT.equals(pieceJustificative.getModel().getTypePieceJustificative().getCode())){
			InputStream inputStream = Report.class.getResourceAsStream("feuilledeplacement1.jrxml");
			List<FeuilleDeplacementEtat> dataSource = new LinkedList<>();
			dataSource.add(new FeuilleDeplacementEtat(pieceJustificative, "Décision", new Date().toString(), "Groupe 1", pieceJustificative.getDossier().getBeneficiaire().getIndice()+"","Femme et deux(02) enfants"));
			return build(FeuilleDeplacementEtat.class, inputStream, dataSource);
		}
		throw new ServiceException("Construction Etat <"+pieceJustificative.getModel().getTypePieceJustificative()+"> Pas encore implémenté");
	}

}
