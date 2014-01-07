package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
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
import ci.gouv.budget.solde.sigdcp.model.dossier.BordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.dossier.CauseDeces;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierObseques;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeBordereauTransmission;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.Categorie;

@ManagedBean @ViewScoped @Getter @Setter
public class BordereauPaieController implements Serializable {

	private static final long serialVersionUID = -1717986544723488894L;
	
	// D�claration de variables
	public int x,y,z, bordereauStatutValue;
	private Date datedebut, datefin;
	
	public long id;
	
	public boolean isEnabled, isBordereauButtonEnabled, isDossierButtonEnabled = true;
		
	public double montant, montantdossier = 0;
	
	private String date;	
	private String message;
	private String numero;
	private String numeroBordereau;
	private String statutBordereau="Non";	
	public TypeBordereauTransmission typeBordereau;
	private PieceProduite pieceProduite;
	
	
	
	// Propri�t�s de dossier
	private String numeroDossier;
	private Date dateDepot;
	private Integer numeroCourrier;
	private Date datePriseService;
	private Deplacement deplacement;
	private Categorie categorie;
	//private Beneficiaire beneficiaire;
	private LinkedList<PieceJustificative> pieceJustificative = new LinkedList<PieceJustificative>();
	
	private String nomAgentConstataire;
	private Date dateDeces;
	private boolean autopsie;
	private float coutTransport;
	
	private Localite lieuDeces;
	private CauseDeces causeDeces;
	
	
		
	public BordereauPaieController(){		
		tableauBordereauPaie();
	}
	
	private LinkedList<BordereauTransmission> btList = new LinkedList<BordereauTransmission>();
	private BordereauTransmission bt = new BordereauTransmission();
		
	public LinkedList<PieceProduite> pieceProduites = new LinkedList<PieceProduite>();
	
	//private LinkedList<Object[]> bordereauPaie = new LinkedList<Object[]>();
	private LinkedList<DossierObseques> dossiers = new LinkedList<DossierObseques>();
		
	private Dossier dossier = new Dossier();
	
	public LinkedList<BordereauTransmission> getBordereauPaie() {
		return btList;
	}
		
	public void setBordereauPaie(LinkedList<BordereauTransmission> bordereaux) {
		this.btList = bordereaux;
	}	
		
	public LinkedList<DossierObseques> getDossiers() {
		return dossiers;
	}

	public void setDossiers(LinkedList<DossierObseques> dossiers) {
		this.dossiers = dossiers;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNomAgentConstataire() {
		return nomAgentConstataire;
	}

	public void setNomAgentConstataire(String nomAgentConstataire) {
		this.nomAgentConstataire = nomAgentConstataire;
	}
	
	public Date getDateDeces() {
		return dateDeces;
	}

	public void setDateDeces(Date dateDeces) {
		this.dateDeces = dateDeces;
	}
	
	public boolean getAutopsie() {
		return autopsie;
	}

	public void setAutopsie(boolean autopsie) {
		this.autopsie = autopsie;
	}
	
	public float getCoutTransport() {
		return coutTransport;
	}

	public void setCoutTransport(float coutTransport) {
		this.coutTransport = coutTransport;
	}
	
	
	
	public Localite getLieuDeces() {
		return lieuDeces;
	}

	public void setLieuDeces(Localite lieuDeces) {
		this.lieuDeces = lieuDeces;
	}
	
	public CauseDeces getCauseDeces() {
		return causeDeces;
	}

	public void setCauseDeces(CauseDeces causeDeces) {
		this.causeDeces = causeDeces;
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
			
			//btList.add(new BordereauTransmission(id, btdateEts , pieceProduites, typeBordereau));
						
			}
	}

	public void tableauDossiers(long id){
		for(int i=1;i<5;i++){
			
			// G�n�ration du numero du dossier
			numero = String.valueOf(i);
			
			// G�n�rer une date al�atoire
			dateDepot = ConverStringToDate(date = randomDOB());
			datePriseService = ConverStringToDate(date = randomDOB()) ;	
			
			int lower = 0;
			int higher = 1000000;

			numeroCourrier = (int)(Math.random() * (higher-lower));						 
			
			/*dossiers.add(new DossierFO(numero, dateDepot, numeroCourrier, datePriseService, deplacement, categorie,
                                       beneficiaire, nomAgentConstataire, dateDeces, autopsie, coutTransport, 
                                       natureDeces, lieuDeces, causeDeces));*/
			
			
		}
	}
	
	public void afficherDossiers(String numBord) {
		dossiers.clear();
		tableauDossiers(id);	
		
		numeroBordereau = numBord;
	}
	
	public void afficherBordereaux() {
		if(numero.isEmpty()){
			String message = "Aucun num�ro de bordereau saisi";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,message,message); 			
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
		}else{
			btList.clear();
			tableauBordereauPaie();
			id = Long.parseLong(numero);
			//ok=true;
			Iterator<BordereauTransmission> it = btList.iterator();
			while(it.hasNext()){
				BordereauTransmission num = it.next();
			    if(!num.getId().equals(Long.parseLong(numero))){
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
		
		dossiers.clear();
		//id=Long.parseLong("");
		
		btList.clear();
		tableauBordereauPaie();
		numero = "";
		
			
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
	    return (lowerBound + (int) Math.round(Math.random()
	            * (upperBound - lowerBound)));
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
	
	
	  
}
