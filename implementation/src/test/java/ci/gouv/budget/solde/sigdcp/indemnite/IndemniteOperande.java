package ci.gouv.budget.solde.sigdcp.indemnite;

public class IndemniteOperande {
	
	public int getA(){
		return 5;
	}
	
	public int getB(){
		return 10;
	}
	
	public int getC(){
		return 15;
	}
	
	public int plage(int valeur){
		return valeur<10?2:5;
	}
	
	public int forfait(boolean masculin){
		if(masculin)
			return 200;
		return 100;
	}
	

	
}
