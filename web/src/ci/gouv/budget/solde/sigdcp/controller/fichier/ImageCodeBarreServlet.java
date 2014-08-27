package ci.gouv.budget.solde.sigdcp.controller.fichier;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.controller.Utils;
import ci.gouv.budget.solde.sigdcp.service.identification.CodeBarreService;

@WebServlet(name="authCodeBarreServlet",urlPatterns={"/_authcodebarre_/"})
public class ImageCodeBarreServlet extends HttpServlet {

	private static final long serialVersionUID = -7961765421121603659L;
	
	@Inject private CodeBarreService codeBarreService;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(StringUtils.isEmpty(request.getParameter("signature")))
			return;
		byte[] content = codeBarreService.encoder(request.getParameter("signature"));
        Utils.send(
    			getServletContext(),request, response, 
    			"code_barre" + System.currentTimeMillis() + ".png", 
    			content.length,
    			new ByteArrayInputStream(content));
    			
    }

}