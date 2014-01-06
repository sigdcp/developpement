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

public class tableauRembController implements Serializable{
	
//	private String typed; 
	private Map<String,String> type = new HashMap<String, String>(); 
	
	private boolean visible =false;
	private String typeDos;  
	
	 private Date d1,d2;
	 private static final long serialVersionUID = -2124199944340826386L;
	 
    public tableauRembController()
      {  
        type.put("Départ à la retraite", "DR"); 
        type.put("Mutation", "MU");  
        type.put("Affectation", "AF");  
        type.put("Transit de bagage", "TR");
        type.put("Mission Hors Côte d'Ivoire", "MHCI");  
        type.put("Transit de bagage", "TR");
        type.put("Frais d'obsèques", "FO");
        type.put("Tous", "TS");  
       
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
  	
  	
  	public String validerRecherche(){
  		return "rembListe";
  		
  	}
  
}  
  
                      
