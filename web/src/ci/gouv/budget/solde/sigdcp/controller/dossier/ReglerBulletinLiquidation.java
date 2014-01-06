package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeBordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;

import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;


@ManagedBean @ViewScoped @Getter @Setter
public class ReglerBulletinLiquidation {

	// D�claration de variables
		private int x,y,z, bordereauStatutValue, valeurCombo;
		
		private long id;
		
		private boolean isEnabled, isBordereauButtonEnabled, isDossierButtonEnabled = true;
			
		private double montant, montantdossier = 0;
		private Date dateEtablissement, datedebut, datefin;
		
		private String date;	
		private String message;
		private String numeroRecherche;
		private String numeroBordereau;
		private String statutBordereau="Non";	
		private TypeBordereauTransmission typeBordereau;
		private PieceProduite pieceProduite;
		
		
		private Categorie categorie;
		//private Beneficiaire beneficiaire;
		private LinkedList<PieceJustificative> pieceJustificative = new LinkedList<PieceJustificative>();
		
		// Informations sur le Bulletin de liquidation
		private String numero;
		private String dateBulletin;
		private TypePieceProduite typePieceProduite;
		
		
		private LinkedList<BordereauTransmission> btList = new LinkedList<BordereauTransmission>();
		private BordereauTransmission bt = new BordereauTransmission();
			
		private LinkedList<PieceProduite> ppList = new LinkedList<PieceProduite>();
		private PieceProduite pp = new PieceProduite();
		
		
		// Les Getters et les Setters
		public LinkedList<BordereauTransmission> getBordereauPaie() {
			return btList;
		}
			
		public void setBordereauPaie(LinkedList<BordereauTransmission> bordereaux) {
			this.btList = bordereaux;
		}	
		
		public String getNumeroRecherche() {
			return numeroRecherche;
		}

		public void setNumeroRecherche(String numero) {
			this.numeroRecherche = numero;
		}

		public Date getDateEtablissement() {
			return dateEtablissement;
		}

		public void setDateEtablissement(Date dateEts) {
			this.dateEtablissement = dateEts;
		}

		
		
		
		public ReglerBulletinLiquidation(){		
			tableauBordereauPaie();
		}
				
		public void tableauBordereauPaie(){
			for(int i = 1; i <= 5; i++){
				
				// Convertir int en long
				id = (long) (i);
				
				// G�n�rer une date al�atoire
				date = randomDOB();
				
				
				//double random = Math.random() * 1000000 + 1;
				
				// G�rer un num�ro incr�mental			
				/*x = i + new Random().nextInt();			
				y = x != y ? x : y;*/
				
				int lower = 0;
				int higher = 1000000;

				montant = (int)(Math.random() * (higher-lower));
				
				//bordereauPaie.add(new Object[]{"BP00"+(i+1),"Bordereau_"+(i+1), (new Date()), montant, statutBordereau});
				
				//btList.add(new BordereauTransmission(id, dateEtablissement , ppList, typeBordereau));
				
				
							
				}
		}
		
		public static String randomDOB() {

		    int yyyy = random(2009, 2013);
		    int mm = random(1, 12);
		    int dd = 0; // will set it later depending on year and month

		    switch(mm) {
		      case 2:
		        if (isLeapYear(yyyy)) {
		          dd = random(1, 29);
		        } else {
		          dd = random(1, 28);
		        }
		        break;

		      case 1:
		      case 3:
		      case 5:
		      case 7:
		      case 8:
		      case 10:
		      case 12:
		        dd = random(1, 31);
		        break;

		      default:
		        dd = random(1, 30);
		      break;
		    }

		    String year = Integer.toString(yyyy);
		    String month = Integer.toString(mm);
		    String day = Integer.toString(dd);

		    if (mm < 10) {
		        month = "0" + mm;
		    }

		    if (dd < 10) {
		        day = "0" + dd;
		    }

		    return day + '/' + month + '/' + year;
		  }
				
		public static int random(int lowerBound, int upperBound) {
		    return (lowerBound + (int) Math.round(Math.random() * (upperBound - lowerBound)));
		}

		public static boolean isLeapYear(int year) {
		    Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.YEAR, year);
		    int noOfDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

		    if (noOfDays > 365) {
		        return true;
		    }

		    return false;
		  }
		  		
		public static Date ConverStringToDate(String stringToConvert)
		{		
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
					return formatter.parse(stringToConvert);
				}
			catch (ParseException e) {
				
				e.printStackTrace();
				return null;
			}
		} 
		
		public void afficherBordereaux() {						
			if(numeroRecherche.isEmpty()){
				String message = "Aucun num�ro de bordereau saisi";
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,message,message); 			
		        FacesContext.getCurrentInstance().addMessage(null, msg); 
			}else{
				btList.clear();
				tableauBordereauPaie();
				id = Long.parseLong(numeroRecherche);
				//ok=true;
				Iterator<BordereauTransmission> it = btList.iterator();
				while(it.hasNext()){
					BordereauTransmission num = it.next();					
				    if(!num.getId().equals(Long.parseLong(numeroRecherche))){				    	
				    	it.remove();
				    }
				}
			}
						
			if(btList.size()==0){
				//ok=false;
			String message = "Aucune information correspondant au num�ro fourni";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message);  
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
	        //tableauMission();
			}
			
			
		}
		
		public void rafraichirEcran(){
			datedebut = new Date();
			datefin = new Date();
			
			ppList.clear();
			//id=Long.parseLong("");
			
			btList.clear();
			tableauBordereauPaie();
			numeroRecherche = "";
							
		}
		
		public void afficherBulletinLiquidation(long idbordereau) {
			ppList.clear();
			tableauBulletinLiquidation(id);				
			
			id = idbordereau;
		}
		
		public void tableauBulletinLiquidation(long id){
			for(int i=0;i<5;i++){
				
				montantdossier = montant / 5 ;
				
				numero = String.valueOf(i);
				
				// G�n�rer une date al�atoire
				dateBulletin = randomDOB();
				
				Date date = ConverStringToDate(dateBulletin);
				
				ppList.add(new PieceProduite(numero, date, typePieceProduite));
				
				
			}
		}
		
		public void showMessage() {  
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Paiement de bordereau", "Boredereau pay�.");  
	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);  
	    } 
		
		public void payerBordereau(String numBord) {		
			
			// Enregistrer paiement
			/* Consiste � mettre � jour le statut du bordereau, mettre � jour le statut des dossiers le constituant et � d�sactiver les boutons*/
			statutBordereau = "Oui";
			for(int i = 0; i < btList.size(); i++){
				if(btList.get(i).getId().equals(numBord)){
					//btList.get(i).setEstPaye("Oui");					
				}
			
			}
			
			// Afficher message de confirmation
			
			
			// Envoi mail de confirmation au b�n�ficiaire
			
		}
		
		public void chooseCar() { 
			
           // RequestContext.getCurrentInstance().openDialog("BulletinLiquidationVue");  
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Paiement de bordereau", "Boredereau pay�.");  
	          
	        RequestContext.getCurrentInstance().showMessageInDialog(message);  
        }  
  
        public void onCarChosen(SelectEvent event) {         	  
        	PieceProduite pp = (PieceProduite) event.getObject();  
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Car Selected", "Model:" + pp.getNumero());  
  
            FacesContext.getCurrentInstance().addMessage(null, message);  
        }  
}
