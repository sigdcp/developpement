package ci.gouv.budget.solde.sigdcp.controller.remboursement;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

@ManagedBean @ViewScoped @Getter @Setter

public class tableauCarteController implements Serializable{
	
//	private String typed; 
	private Map<String,String> ministere = new HashMap<String, String>(); 
	private Map<String,String> direction = new HashMap<String, String>();
	private boolean visible =false;
	private String typeMin, typDir;  
	 private int indexe, totCarteMu, totCarteDr, totCarteGen; 
	 private Date d1,d2;
	 private static final long serialVersionUID = -2124199944340826386L;
	 
	
		  
	

    public tableauCarteController()
      {  
        ministere.put("Ministère du Budget", "MB");  
        ministere.put("Ministère de l'Economie et des Finances", "MF");  
        ministere.put("Ministère de la Santé", "MS");
        ministere.put("Tous les ministères", "TM");
        
        direction.put("Direction de la Solde", "DS");  
        direction.put("Direction des Traitements Informatiques", "DI");  
        direction.put("Direction du budget", "DB");
        direction.put("Toutes les directions", "TD");
      
        
         }  
   
      public void rechercher()
    {
		visible=true;

/*    		   System.out.println("total Mutations :" +totMU);
    		   System.out.println("total Affectations :" +totAF);
    		   System.out.println("total D�parts � la retraite :" +totDR);*/
    		
      }
  
  	
   public Date getD1() {
  		return d1;
  	}
  	public void setD1(Date d1) {
  		this.d1 = d1;
  	}
  	public Date getD2() {
  		return d2;
  	}
  	public void setD2(Date d2) {
  		this.d2 = d2;
  	}
  	
  	
  	public String validerRechercheTousMinistere(){
  		return "tableauCarteTousMinistereListe";
  	}
  	
  	public String validerRechercheMinistere(){
  		return "tableauCarteMinistereListe";
  	}
  	
  	public String validerRechercheDirection(){
  		return "tableauCarteDirectionListe";
  	}

}  
  
                      
