package ci.gouv.budget.solde.sigdcp.controller.dossier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.primefaces.context.RequestContext;

import ci.gouv.budget.solde.sigdcp.model.dossier.PieceProduite;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceProduite;

public class BulletinLiquidationController implements Serializable {

	
	private Date[] dates;  
	
	private List<PieceProduite> piecesProduites = new ArrayList<PieceProduite>();  
	private LinkedList<TypePieceProduite> typepieceproduites = new LinkedList<TypePieceProduite>();
	
	public BulletinLiquidationController(){
		
		//populateRandomTypePieces(typepieceproduites);
				
        populateRandomPieces(piecesProduites, 9); 
        
        
	}
	
	private void populateRandomPieces(List<PieceProduite> list, int size) {  
        for(int i = 0 ; i < size ; i++)  
            list.add(new PieceProduite(getRandomNumero(), getRandomDate(), getRandomTypePieceProduite(typepieceproduites)));  
    }
	
	private void populateRandomTypePieces(List<TypePieceProduite> list) {  
        
        typepieceproduites.add(new TypePieceProduite("001", "Type pièce N° 1")) ;
    	typepieceproduites.add(new TypePieceProduite("002", "Type pièce N° 2")) ;
    	typepieceproduites.add(new TypePieceProduite("003", "Type pièce N° 3")) ;
    }
	
	

      
    public List<PieceProduite> getPiecesProduites() {  
        return piecesProduites;  
    }  
  
    private String getRandomNumero() {  
        return UUID.randomUUID().toString().substring(0, 8);  
    } 
          
    private Date getRandomDate() {  
        return dates[(int) (Math.random() * 10)];  
    } 
    
    private TypePieceProduite getRandomTypePieceProduite(List<TypePieceProduite> typepieceproduites) {  
        
    	int first = 0;
    	int last = typepieceproduites.size() - 1;        
    	
    	String result = "00" + (first + (int) Math.round(Math.random() * (last - first)));
        
    	Iterator<TypePieceProduite> it = typepieceproduites.iterator();
		while(it.hasNext()){
			TypePieceProduite num = it.next();
		    if(num.getCode().equals(result)){
		    	return num;
		    }
		}
        
		return null; 
        
    }  
      
    private int getRandomYear() {  
        return (int) (Math.random() * 50 + 1960);  
    }  
    
    public void selectCarFromDialog(PieceProduite pieceproduite) {  
        RequestContext.getCurrentInstance().closeDialog(pieceproduite);  
    }  
}
