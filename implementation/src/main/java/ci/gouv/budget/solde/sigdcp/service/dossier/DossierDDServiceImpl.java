package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DossierDDDao;
import ci.gouv.budget.solde.sigdcp.dao.dossier.PieceJustificativeDao;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Deplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.DossierDD;
import ci.gouv.budget.solde.sigdcp.model.dossier.NatureDeplacement;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificativeAFournir;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypeDepense;
import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.utils.validaton.DossierDDValidator;

@Stateless
public class DossierDDServiceImpl extends AbstractDossierServiceImpl<DossierDD> implements DossierDDService,Serializable {
	
	private static final long serialVersionUID = -7765679080076677680L;
	
	@Inject private DossierDDValidator dossierDDValidator;
	@Inject private PieceJustificativeDao pieceJustificativeDao;
	
	@Inject
	public DossierDDServiceImpl(DossierDDDao dao) {
		super(dao);
	}
	
	
		
	@Override
	protected void validationSaisie(DossierDD dossier,Boolean soumission)throws ServiceException {
		//System.out.println("DossierDDServiceImpl.validationSaisie() : ");
		//System.out.println(ToStringBuilder.reflectionToString(dossier, ToStringStyle.MULTI_LINE_STYLE));
		dossierDDValidator.setSoumission(soumission);
		if(!dossierDDValidator.validate(dossier).isSucces())
			throw new ServiceException(dossierDDValidator.getMessagesAsString());
	}
	
	@Override
	protected void chargerPiecesJustificatives(DossierDD dossier) {
		super.chargerPiecesJustificatives(dossier);
		
		PieceJustificativeAFournir modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_MARIAGE,
				dossier.getDeplacement().getTypeDepense().getCode());
		if(modelPiece!=null)
			dossier.setMarie(pieceJustificativeDao.countByDossierByTypePieceJustificativeId(dossier, Code.TYPE_PIECE_EXTRAIT_MARIAGE)>0);
		modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_NAISSANCE,
				dossier.getDeplacement().getTypeDepense().getCode());
		if(modelPiece!=null)
			dossier.setNombreEnfant(pieceJustificativeDao.countByDossierByTypePieceJustificativeId(dossier, Code.TYPE_PIECE_EXTRAIT_NAISSANCE).intValue());
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void mettreAJourPiecesJustificatives(Collection<DossierDD> dossiers) {
		super.mettreAJourPiecesJustificatives(dossiers);
		DossierDD dossier = dossiers.iterator().next();
		PieceJustificativeAFournir modelPiece = null;
		int pieceJustificativeExistanteIndex;
		//Extrait de mariage
		
		modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_MARIAGE,
				dossier.getDeplacement().getTypeDepense().getCode());
		if(modelPiece!=null){
			pieceJustificativeExistanteIndex = index(dossier, modelPiece);
			if(Boolean.TRUE.equals(dossier.getMarie())){//ajouter un extrait de mariage si il n'en existe pas
				if(pieceJustificativeExistanteIndex==-1)
					dossier.getPieceJustificatives().add(new PieceJustificative(dossier, modelPiece));
			}else{//supprimer l'extrait de mariage si il en existe
				if(pieceJustificativeExistanteIndex!=-1)
					((List<PieceJustificative>)dossier.getPieceJustificatives()).remove(pieceJustificativeExistanteIndex);
			}
		}
		
		//Extrait de naissances des enfants
		modelPiece = pieceJustificativeAFournirDao.readByNatureDeplacementIdByTypePieceIdByTypeDepenseId(dossier.getDeplacement().getNature().getCode(), Code.TYPE_PIECE_EXTRAIT_NAISSANCE,
				dossier.getDeplacement().getTypeDepense().getCode());
		if(modelPiece!=null){
			if(dossier.getNombreEnfant()==null)
				dossier.setNombreEnfant(0);
			int n = dossier.getNombreEnfant() - count(dossier, modelPiece);
			if(n>0){// ajouter 
				for(int i=0;i<n;i++)
					dossier.getPieceJustificatives().add(new PieceJustificative(dossier, modelPiece));
			}else{//supprimer
				List<PieceJustificative> list = (List<PieceJustificative>) dossier.getPieceJustificatives();
				for(int j=0;n<0 && j<list.size();){
					if(list.get(j).getModel().equals(modelPiece)){
						((List<PieceJustificative>)dossier.getPieceJustificatives()).remove(j);
						n++;
					}else
						j++;
				}
			}
		}
	}
	

	@Override
	protected DossierDD createDossier(NatureDeplacement natureDeplacement) {
		
		DossierDD dossierDD=new DossierDD(new Deplacement(genericDao.readByClass(TypeDepense.class, String.class, Code.TYPE_DEPENSE_REMBOURSEMENT)));
		if(natureDeplacement!=null && natureDeplacement.getSceSolde()){
			dossierDD.getDeplacement().setAddUser(utilisateur());
			//dossierDD.setBeneficiaire(new AgentEtat());			
		}
		
		return dossierDD;
	}

	@Override
	protected void initSaisie(Dossier source, DossierDD destination) {
		super.initSaisie(source, destination);
		if(Code.NATURE_DEPLACEMENT_MUTATION.equals(destination.getDeplacement().getNature().getCode()))
			((DossierDD)destination).setServiceOrigine(source.getService());
		else
			destination.setService(null);
	}



}
