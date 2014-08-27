package ci.gouv.budget.solde.sigdcp.controller.fichier;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ci.gouv.budget.solde.sigdcp.controller.Utils;
import ci.gouv.budget.solde.sigdcp.model.dossier.Document;
import ci.gouv.budget.solde.sigdcp.service.dossier.DocumentService;
import ci.gouv.budget.solde.sigdcp.service.fichier.EtatService;

@WebServlet(name="etatServlet",urlPatterns={"/_etat_/"})
public class EtatServlet extends HttpServlet {

	private static final long serialVersionUID = -7961765421121603659L;
	
	@Inject private DocumentService documentService;
	@Inject private EtatService etatService;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Document document = documentService.findById(Long.parseLong(request.getParameter("id")));
        
		byte[] content = etatService.build(document);
        Utils.send(
    			getServletContext(),request, response, 
    			"etat" + System.currentTimeMillis() + ".pdf", 
    			content.length,
    			new ByteArrayInputStream(content));
    			
    }

}