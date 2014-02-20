package ci.gouv.budget.solde.sigdcp.service.fichier;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
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
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
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
	public byte[] build(TypePieceJustificative typePieceJustificative) {
		if(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT.equals(typePieceJustificative.getCode())){
			InputStream inputStream = Report.class.getResourceAsStream("feuilledeplacement.jrxml");
			List<FeuilleDeplacementEtat> dataSource = null;//feuilledeplacement();
			return build(FeuilleDeplacementEtat.class, inputStream, dataSource);
		}
		throw new ServiceException("Construction Etat <"+typePieceJustificative+"> Pas encore implémenté");
	}
	
	/*
	public static List<FeuilleDeplacementEtat> feuilledeplacement (){
		
		List<FeuilleDeplacementEtat> l = new ArrayList<FeuilleDeplacementEtat>();
		l.add(new FeuilleDeplacementEtat("654614164M", "Abidjan", "Yamoussoukro", "Blissi Tebil", "10/01/2014", "09/02/2014", "SV1254", "08/01/2014", "H", "1035", "famille", "temporaire", "1845 Tonnes", "Chef de service", "A7"));
		return l;
	}
	*/

}
