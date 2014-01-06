package ci.gouv.budget.solde.sigdcp.controller.remboursement;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

/*import org.apache.commons.lang3.RandomStringUtils;
import org.dgbf.solde.model.Requerant;
import org.dgbf.solde.model.dossier.Dossier;
import org.dgbf.solde.model.dossier.DossierDD;*/
@ManagedBean @ViewScoped @Getter @Setter

public class OuvrirDemandeController implements Serializable{
	
//	private String typed; 
	private Map<String,String> mois = new HashMap<String, String>(); 
	private boolean visible =false;
	private String moisDemande, anne, numDemande,
	   nomMinistere, nomDirection, nomDelegue, numBonCommande ;  
//	 private int indexe, totMU, totAF, totDR; 
	 private static final long serialVersionUID = -2124199944340826386L;
	 
//	private LinkedList<String[]> dossierDD = new LinkedList<String[]>();
		  
//	private LinkedList<String[]> selectedDossier;
//	private String typeDossier;

    public OuvrirDemandeController()
      {  
        mois.put("Janvier", "JR");  
        mois.put("Février", "FR");  
        mois.put("Mars", "MR");
        mois.put("Avril", "AV");
        mois.put("Mai", "MA");  
        mois.put("Juin", "JU");  
        mois.put("Juillet", "JL");  
        mois.put("Août", "AO");  
        mois.put("Septembre", "SP");
        mois.put("Octobre", "OT");
        mois.put("Novembre", "NV");  
        mois.put("Décembre", "DC");  
        anne = "2013";
        numDemande="001";
        nomMinistere="Ministère du budget";
        nomDirection="Direction Générale du Budget et des Finances";
        nomDelegue="kouadio konan";
        numBonCommande="00001";
         }  
   
     
         
     }

 
  
                      
