package ci.gouv.budget.solde.sigdcp.model.communication;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class NotificationMessage implements Serializable {

	private static final long serialVersionUID = 6163661021048066920L;

	private String subject;
	private String body;
	
	@Override
	public String toString() {
		return subject+"\n"+body;
	}
	
}
