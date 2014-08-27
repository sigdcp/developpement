/**
*    Syst�me Int�gr� de Gestion des D�penses Communes de Personnel - SIGDCP
*
*    Mod�le M�tier
*
**/


package ci.gouv.budget.solde.sigdcp.model.dossier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;

import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;

@Getter @Setter 
@Entity
//@Table(name="BORDTRANS")
public class BordereauTransmission  extends PieceProduite  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private NatureDeplacement natureDeplacement;
	
	@Column(precision=15,scale=2,name="BT_MONTANT")
	private BigDecimal montant;
	
	@Transient private Collection<BulletinLiquidation> bulletinLiquidations = new ArrayList<>();
	@Transient private Date dateCreation;
	
	public BordereauTransmission() {
		super();
	}

	public BordereauTransmission(String numero, TypePieceProduite type, Date dateEtablissement) {
		super(numero, type,dateEtablissement);
	}
	
	public BordereauTransmission(BordereauTransmission bordereauTransmission) {
		this(bordereauTransmission.numero,bordereauTransmission.getType(),bordereauTransmission.getDateEtablissement());
		setId(bordereauTransmission.getId());
	}
		
	public static Collection<BordereauTransmission> etatCollection(){
		Collection<BordereauTransmission> collection = new ArrayList<>();
		collection.add(etatBT());
		return collection;
	}
	
	private static BordereauTransmission etatBT(){
		BordereauTransmission bt = new BordereauTransmission();
		bt.getBulletinLiquidations().add(etatBL(bt,"500500A","Tata","Paon",10000));
		bt.getBulletinLiquidations().add(etatBL(bt,"500500B","Toto","Poon",10000));
		bt.getBulletinLiquidations().add(etatBL(bt,"500500C","Tutu","Puon",10000));
		bt.setMontant(new BigDecimal(30000));
		return bt;
	}
	
	private static BulletinLiquidation etatBL(BordereauTransmission bt,String matricule,String nom,String prenoms,int montant){
		BulletinLiquidation bl = new BulletinLiquidation();
		bl.setBordereauTransmission(bt);
		bl.setMontant(new BigDecimal(montant));
		bl.setNumero(RandomStringUtils.randomAlphabetic(4));
		Dossier d = new Dossier();
		AgentEtat a = new AgentEtat();
		a.setMatricule(matricule);
		a.setNom(nom);
		a.setPrenoms(prenoms);
		d.setBeneficiaire(a);
		bl.setDossier(d);
		return bl;
	}
	
	public static void main(String[] args) {
		System.out.println();
	}
	
	
}