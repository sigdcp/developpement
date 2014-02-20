package ci.gouv.budget.solde.sigdcp.test.jasper;

import java.awt.GraphicsEnvironment;
import java.util.Arrays;

public class ReportTest {
	
	static final String PATH = System.getProperty("user.dir");
	
	public static void main(String[] args){
		 System.out.println(Arrays.asList(GraphicsEnvironment
                 .getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
		/*
		String rn;
		//rn ="blanktest.jrxml";
		rn = "feuilledeplacement.jrxml";
		InputStream inputStream = Report.class.getResourceAsStream(rn);

		List<FeuilleDeplacementEtat> dataBeanList = DonneesEtat.feuilledeplacement();
		System.out.println(dataBeanList);
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);
		
		JasperDesign jasperDesign;
		try {
			jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, PATH+"/reports/fd.pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}*/
		
		
	}
}