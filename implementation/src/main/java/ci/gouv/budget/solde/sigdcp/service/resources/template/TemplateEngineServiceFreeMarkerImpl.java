package ci.gouv.budget.solde.sigdcp.service.resources.template;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.service.utils.TemplateEngineService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@Log
@Singleton
public class TemplateEngineServiceFreeMarkerImpl implements TemplateEngineService {

	private Configuration configuration;

	@PostConstruct
	private void postConstruct() {
		// 1. Configure FreeMarker
		//
		// You should do this ONLY ONCE, when your application starts,
		// then reuse the same Configuration object elsewhere.
		configuration = new Configuration();

		// Where do we load the templates from:
		configuration.setClassForTemplateLoading(ci.gouv.budget.solde.sigdcp.model.template.Template.class, "freemarker");
		/*try {
			configuration.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir"), "src/main/resources/template/freemaker"));
		} catch (IOException e) {
			log.log(Level.SEVERE,e.toString(),e);
		}*/
		
		// Some other recommended settings:
		configuration.setIncompatibleImprovements(new Version(2, 3, 20));
		configuration.setDefaultEncoding("UTF-8");
		configuration.setLocale(Locale.FRENCH);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
	}

	@Override
	public String find(String templateId, Map<String, Object> parameters) {
		Template template = null;
		Writer contentWriter = new StringWriter();
		//System.out.println(parameters);
		//for(Entry<String, Object> entry : parameters.entrySet())
		//	template.
		try {
			template = configuration.getTemplate(templateId + ".html");
			template.setOutputEncoding("UTF-8");
			template.process(parameters, contentWriter);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return contentWriter.toString();
	}

}
