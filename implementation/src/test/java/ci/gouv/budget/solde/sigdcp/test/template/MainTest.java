package ci.gouv.budget.solde.sigdcp.test.template;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ci.gouv.budget.solde.sigdcp.service.resources.template.TemplateEngineServiceFreeMarkerImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class MainTest {

  public static void main(String[] args) throws Exception {
	  System.out.println(System.getProperty("user.dir")+"/src/main/resources");
    // 1. Configure FreeMarker
    //
    // You should do this ONLY ONCE, when your application starts,
    // then reuse the same Configuration object elsewhere.
    
    Configuration cfg = new Configuration();
    
    // Where do we load the templates from:
    cfg.setClassForTemplateLoading(TemplateEngineServiceFreeMarkerImpl.class, "freemaker");
    
    cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir"), "src/main/resources/template/freemaker"));
    
    // Some other recommended settings:
    cfg.setIncompatibleImprovements(new Version(2, 3, 20));
    cfg.setDefaultEncoding("UTF-8");
    cfg.setLocale(Locale.FRENCH);
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
    
    // 2. Proccess template(s)
    //
    // You will do this for several times in typical applications.
    
    // 2.1. Prepare the template input:
    
    Map<String, Object> input = new HashMap<String, Object>();
    
    //input.put("motifVerrouillage", "plusieurs accès à votre compte");
    input.put("nomPrenomsAgentEtat", "Tata pion");
    input.put("dateHeureVerrouillage", new Date().toString());
    input.put("adresseIP", "10.10.1.1");
    input.put("adresseGeographique", "France");
   
    
    // 2.2. Get the template

    Template template = cfg.getTemplate("avisVerrouillageCompteAccessMultiple_mail.html");
      
    // 2.3. Generate the output

    // Write output to the console
    Writer consoleWriter = new OutputStreamWriter(System.out);
    template.process(input, consoleWriter);

   

  }
} 