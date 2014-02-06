package ci.gouv.budget.solde.sigdcp.service.resources.template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import lombok.extern.java.Log;
import ci.gouv.budget.solde.sigdcp.service.utils.TemplateEngineService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

@Log
@Singleton
public class TemplateEngineFreeMakerService implements TemplateEngineService {

	private static Configuration configuration;

	@PostConstruct
	private void postConstruct() {
		// 1. Configure FreeMarker
		//
		// You should do this ONLY ONCE, when your application starts,
		// then reuse the same Configuration object elsewhere.
		configuration = new Configuration();

		// Where do we load the templates from:
		configuration.setClassForTemplateLoading(TemplateEngineFreeMakerService.class, "freemaker");

		// Some other recommended settings:
		configuration.setIncompatibleImprovements(new Version(2, 3, 20));
		configuration.setDefaultEncoding("UTF-8");
		configuration.setLocale(Locale.US);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	@Override
	public String find(String templateId, Map<String, Object> parameters) {
		// 2. Proccess template(s)
		//
		// You will do this for several times in typical applications.

		// 2.1. Prepare the template input:

		Map<String, Object> input = new HashMap<String, Object>();

		input.put("title", "Vogella example");

		// 2.2. Get the template

		Template template = null;
		try {
			template = configuration.getTemplate(templateId + ".ftl");
		} catch (IOException e) {
			log.log(Level.SEVERE, e.toString(), e);
			return null;
		}

		Writer contentWriter = new StringWriter();
		try {
			template.process(input, contentWriter);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
			return null;
		}
		return contentWriter.toString();
	}

}
