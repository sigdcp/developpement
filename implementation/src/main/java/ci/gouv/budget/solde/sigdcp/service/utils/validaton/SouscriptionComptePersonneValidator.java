package ci.gouv.budget.solde.sigdcp.service.utils.validaton;

import java.io.Serializable;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.identification.souscription.SouscriptionComptePersonne;
import ci.gouv.budget.solde.sigdcp.model.utils.validation.groups.Client;
import ci.gouv.budget.solde.sigdcp.service.utils.ServiceValidationUtils;

@Getter
public class SouscriptionComptePersonneValidator extends AbstractValidator<SouscriptionComptePersonne> implements Serializable {

	private static final long serialVersionUID = -261860698364195138L;
	
	@Inject private ServiceValidationUtils validationUtils;
	
	/*
	@AssertTrue(message="Matricule incorrect",groups=Client.class)
	private boolean matriculeFormatCorrect;
	
	@AssertTrue(message="Vous devez avoir plus de 18 ans",groups=Client.class)
	private boolean majeur;
	/*
	@Override
	public AbstractValidator<SouscriptionComptePersonne> init(SouscriptionComptePersonne souscriptionComptePersonne) {
		super.init(souscriptionComptePersonne);
		
		if(Code.TYPE_AGENT_ETAT_GENDARME.equals(object.getPersonneDemandeur().getType()))
			matriculeFormatCorrect = validationUtils.isMatrciuleGendarmeFormatCorrect(object.getPersonneDemandeur().getMatricule());
		matriculeFormatCorrect = validationUtils.isMatrciuleFonctionnaireFormatCorrect(object.getPersonneDemandeur().getMatricule());
		
		majeur = validationUtils.isMajeur(object.getPersonneDemandeur().getPersonne().getDateNaissance());
		
		return this;
	}
	*/
	
	@AssertTrue(message="Matricule incorrect",groups=Client.class)
	public boolean isMatriculeFormatCorrect(){
		return validationUtils.isMatriculeFormatCorrect(object.getPersonneDemandeur().getType(),object.getPersonneDemandeur().getMatricule());
	}
	
	@AssertTrue(message="Vous devez avoir plus de 18 ans",groups=Client.class)
	public boolean isMajeur(){
		return validationUtils.isMajeur(object.getPersonneDemandeur().getPersonne().getDateNaissance());
	}
	
	@AssertTrue(message="le type de la piece d'identite est obligatiore",groups=Client.class)
	public boolean isPieceIdentiteTypeCorrect(){
		return object.getPersonneDemandeur().getPersonne().getPieceIdentiteType()!=null;
	}
	
	@AssertTrue(message="numero de la piece d'identite est obligatiore",groups=Client.class)
	public boolean isPieceIdentiteNumeroCorrect(){
		return StringUtils.isNotEmpty(object.getPersonneDemandeur().getPersonne().getPieceIdentiteNumero());
	}
	
	
}
