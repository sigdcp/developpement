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
import ci.gouv.budget.solde.sigdcp.service.fichier.EtatService;

@WebServlet(name="blApercuEtatServlet",urlPatterns={"/_bletat_/"})
public class BulletinLiquidationApercuServlet extends HttpServlet {

	private static final long serialVersionUID = -7961765421121603659L;
	
	@Inject private EtatService etatService;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] content = etatService.buildBulletinLiquidation(Long.parseLong(request.getParameter("id")));
        Utils.send(getServletContext(),request, response, "bletat" + System.currentTimeMillis() + ".pdf", content.length,new ByteArrayInputStream(content));
    			
    }

}