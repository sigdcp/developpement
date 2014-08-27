package ci.gouv.budget.solde.sigdcp.service.dossier;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import ci.gouv.budget.solde.sigdcp.dao.dossier.DocumentDao;
import ci.gouv.budget.solde.sigdcp.dao.identification.CodeBarreServiceImpl;
import ci.gouv.budget.solde.sigdcp.model.Code;
import ci.gouv.budget.solde.sigdcp.model.dossier.Document;
import ci.gouv.budget.solde.sigdcp.model.dossier.Dossier;
import ci.gouv.budget.solde.sigdcp.model.dossier.PieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.dossier.TypePieceJustificative;
import ci.gouv.budget.solde.sigdcp.model.identification.AgentEtat;
import ci.gouv.budget.solde.sigdcp.model.signature.SignatureDocumentGenereDossier;

public class DocumentServiceImpl extends AbstractDocumentServiceImpl<Document> implements DocumentService , Serializable {

	private static final long serialVersionUID = -7601857525393731774L;
	
	//@Inject private CodeBarreService codeBarreService;
	@Inject private DossierService dossierService;
	
	
	private static final String SIGNATURE_SEPARATEUR = ";";
	
	@Inject
	public DocumentServiceImpl(DocumentDao dao) {
		super(dao);
	}

	@Override
	public String signature(Document document) {
		if(document instanceof PieceJustificative){
			PieceJustificative pieceJustificative = (PieceJustificative) document;
			dossierService.init(pieceJustificative.getDossier(), null);
			if(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT.equals(pieceJustificative.getModel().getTypePieceJustificative().getCode()) ||
					Code.TYPE_PIECE_BON_TRANSPORT.equals(pieceJustificative.getModel().getTypePieceJustificative().getCode())){
				return  StringUtils.join(new Object[]{
						pieceJustificative.getModel().getTypePieceJustificative().getCode(),
						pieceJustificative.getId(),
						pieceJustificative.getDateEtablissement().getTime(),
						pieceJustificative.getDossier().getId(),
						pieceJustificative.getDossier().getDateCreation().getTime(),
						authentificationInfos.getCompteUtilisateur().getUtilisateur().getId(),
						authentificationInfos.getCompteUtilisateur().getDateCreation().getTime()
				},SIGNATURE_SEPARATEUR);
			}
		}
		
		return null;
		
	}

	@Override
	public String decoderSignature(String signature) {
		String signatureEnClair = null;
		
		try {
			signatureEnClair =  signature; //new CodeBarreServiceImpl().decoder(codeBarre); //codeBarreService.decoder(codeBarre);
		} catch (Exception e) {
			if(!e.getMessage().equals("com.google.zxing.NotFoundException"))
				throw e;
		}
		
		return signatureEnClair;
	}

	@Override
	public Document findBySignature(String signature) {
		String[] parts = StringUtils.split(signature, SIGNATURE_SEPARATEUR);
		if(parts.length!=7)
			return null;
		Document document = ((DocumentDao)dao).read(Long.parseLong(parts[1]));
		if(document==null)
			serviceException("Code non valide");
		dossierService.init(((PieceJustificative)document).getDossier(), null);
		//if(signature(document).equals(signature))
		//	return document;
		return document;
	}

	@Override
	public InputStream genererCodeBarre(Document document) {
		return new ByteArrayInputStream(new CodeBarreServiceImpl().encoder(signature(document)));
	}

	@Override
	public SignatureDocumentGenereDossier formatterSignature(String signature) {
		String[] parts = StringUtils.split(decoderSignature(signature), SIGNATURE_SEPARATEUR);
		SignatureDocumentGenereDossier s = new SignatureDocumentGenereDossier();
		if(parts.length==7){
			if(Code.TYPE_PIECE_FEUILLE_DEPLACEMENT.equals(parts[0]) || Code.TYPE_PIECE_BON_TRANSPORT.equals(parts[0])){
				s.setDocumentType(genericDao.readByClass(TypePieceJustificative.class, parts[0]).getLibelle());
				PieceJustificative p = genericDao.readByClass(PieceJustificative.class,Long.class, parts[1]);
				if(p!=null)
					s.setDocumentNumero(p.getNumero());
				s.setDocumentDateCreation(new Date(Long.parseLong(parts[2])));
				
				Dossier dossier = genericDao.readByClass(Dossier.class,Long.class, parts[3]);
				if(dossier!=null){
					dossierService.init(dossier, null);
					s.setDossierNumero(dossier.getNumero());
				}
				s.setDossierDateCreation(new Date(Long.parseLong(parts[4])));
				
				AgentEtat agentEtat = genericDao.readByClass(AgentEtat.class,Long.class, parts[5]);
				if(agentEtat!=null){
					s.setRequerantMatricule(agentEtat.getMatricule());
					s.setRequerantNomPrenoms(agentEtat.getNomPrenoms());
				}
				
				s.setCompteUtilisateurDateCreation(new Date(Long.parseLong(parts[6])));
			}
		}
		return s;
	}

}
