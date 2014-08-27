/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.indemnite;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter @Setter 
@Embeddable
public class FraisMission implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(precision=10,scale=2)
	private BigDecimal indemnite;
	
	@Column(precision=10,scale=2)
	private BigDecimal transport;
	
	@Column(precision=10,scale=2)
	private BigDecimal divers;
	
	public BigDecimal getTotal(){
		BigDecimal r = new BigDecimal(0);
		r = add(r,indemnite);
		r = add(r,transport);
		r = add(r,divers);
		return r;
	}
	
	private BigDecimal add(BigDecimal a,BigDecimal b){
		if(b==null)
			return a;
		return a.add(b);
	}
	
}