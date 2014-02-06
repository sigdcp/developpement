package ci.gouv.budget.solde.sigdcp.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ci.gouv.budget.solde.sigdcp.model.MailMessage;
import ci.gouv.budget.solde.sigdcp.model.identification.Party;
import ci.gouv.budget.solde.sigdcp.service.utils.communication.MailService;

public class MailerServiceImpl implements MailService, Serializable {

	private static final long serialVersionUID = -8680313005464068114L;
	
	@Getter @AllArgsConstructor
	public enum MessageType{
		AVIS_SOUSCRIPTION_COMPTE_PERSONNE_ENREGISTREE("SIGDCP","avisVerrouillageCompteAccessMultiple_mail"),
		AVIS_SOUSCRIPTION_COMPTE_PERSONNE_ACCEPTEE("SIGDCP","avisVerrouillageCompteAccessMultiple_mail"),
		AVIS_SOUSCRIPTION_COMPTE_PERSONNE_REFUSEE("SIGDCP","avisVerrouillageCompteAccessMultiple_mail"),
		
		AVIS_COMPTE_UTILISATEUR_VERROUILLE_ACCES_MULTIPLE("SIGDCP","avisVerrouillageCompteAccessMultiple_mail"),
		AVIS_COMPTE_UTILISATEUR_DEVERROUILLE_ACCES_MULTIPLE("SIGDCP","avisVerrouillageCompteAccessMultiple_mail"),
		AVIS_COMPTE_UTILISATEUR_VERROUILLE_REINITIALISATION_PASSWORD("SIGDCP","avisVerrouillageCompteAccessMultiple_mail"),
		AVIS_COMPTE_UTILISATEUR_DEVERROUILLE_REINITIALISATION_PASSWORD("SIGDCP","avisVerrouillageCompteAccessMultiple_mail"),
		AVIS_COMPTE_UTILISATEUR_ETAT_SESSION("SIGDCP","avisVerrouillageCompteAccessMultiple_mail"),
		
		;
		private String subject;
		private String templateId;
	}
	
	@Resource(lookup = "mail/sigdcp")
    private Session session;

	@Override
	public void send(MailMessage message, String receiver) {
		send(message, new String[]{receiver});
	}
		
	public void send(MailMessage mailMessage,String[] receivers)  { 
		send(mailMessage, addresses(receivers));
	}
	
	public void send(MailMessage mailMessage,Collection<String> receivers) {
		send(mailMessage, addresses(receivers));
	}
	
	private InternetAddress[] addresses(String...theAddresses){
		Collection<InternetAddress> receiverAddresses = new LinkedList<>();
		for(String adrress : theAddresses)
			try {
				receiverAddresses.add(new InternetAddress(adrress));
			} catch (AddressException e) {
				e.printStackTrace();
			}
		return receiverAddresses.toArray(new InternetAddress[]{});
	}
	
	private InternetAddress[] addressesParty(Party...parties){
		Collection<String> receiverAddresses = new LinkedList<>();
		for(Party party : parties)
			receiverAddresses.add(party.getContact().getEmail());
		return addresses(receiverAddresses);
	}
	
	private InternetAddress[] addresses(Collection<String> receivers){
		return addresses(receivers.toArray(new String[]{}));
	}
	
	private InternetAddress[] addressesParty(Collection<Party> receivers){
		return addressesParty(receivers.toArray(new Party[]{}));
	}
	
	private void send(MailMessage mailMessage,InternetAddress[] receivers) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(session.getProperty("mail.from")));
			message.setRecipients(Message.RecipientType.TO, receivers);
			message.setSubject(mailMessage.getSubject());
			message.setSentDate(new Date());
			message.setText(mailMessage.getBody());
			Transport.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void send(MailMessage message, Party[] receivers) {
		send(message, addressesParty(receivers));
	}

	@Override
	public void send(MailMessage message, Party receiver) {
		send(message, new Party[]{receiver});
	}

	@Override
	public void sendParty(MailMessage message, Collection<Party> receivers) {
		send(message, addressesParty(receivers));
	}
    
    public void test(){
    	try {
			send(new MailMessage("Configuration Exchange 2010 SMTP Relay","http://www.youtube.com/watch?v=gj61RzL_WFc"),  new String[]{"komenanyc@yahoo.com"});
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	try {
			send(new MailMessage("Configuration Exchange 2010 SMTP Relay","http://www.youtube.com/watch?v=gj61RzL_WFc"),  new String[]{"christian.komenan@budget.gouv.ci"});
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }


    
}