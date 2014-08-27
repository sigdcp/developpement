package ci.gouv.budget.solde.sigdcp.controller.identification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.geographie.Localite;
import ci.gouv.budget.solde.sigdcp.model.identification.TypeAgentEtat;

@Named @ViewScoped
public class SouscriptionContractuelController extends AbstractSouscriptionComptePersonneController implements Serializable {

	private static final long serialVersionUID = 1588915965471299089L;
	
	@Getter @Inject @Named("pays") protected List<Localite> pays;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title += " des contractuels de l'Etat"; 
		demandeurDto.setShowTypePersonne(false);
		demandeurDto.setShowNationalite(true);
		demandeurDto.setExpatrie(true);
		demandeurDto.setPays(new ArrayList<>(pays));
		demandeurDto.getInfosSouscriptionComptePersonne().setType(genericService.findByClass(TypeAgentEtat.class, String.class,Code.TYPE_AGENT_ETAT_CONTRACTUEL));
		/*
		for(int i=0;i<demandeurDto.getPays().size();i++)
			if(demandeurDto.getPays().get(i).getCode().equals(Code.LOCALITE_COTE_DIVOIRE)){
				demandeurDto.getPays().remove(i);
				break;
			}
		*/
		
    }
	
}
