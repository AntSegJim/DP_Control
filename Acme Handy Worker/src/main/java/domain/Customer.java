
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	private int						score;
	private Collection<Endorsement>	endorseCustomer;
	private Collection<Endorsement>	receiveEndorseFromCustomer;


	//Getters and Setters
	public int getScore() {
		return this.score;
	}

	public void setScore(final int score) {
		this.score = score;
	}
	@Valid
	@OneToMany(mappedBy = "customerSender")
	public Collection<Endorsement> getEndorseCustomer() {
		return this.endorseCustomer;
	}

	public void setEndorseCustomer(final Collection<Endorsement> endorseCustomer) {
		this.endorseCustomer = endorseCustomer;
	}
	@Valid
	@OneToMany(mappedBy = "customerReceiver")
	public Collection<Endorsement> getReceiveEndorseFromCustomer() {
		return this.receiveEndorseFromCustomer;
	}

	public void setReceiveEndorseFromCustomer(final Collection<Endorsement> receiveEndorseFromCustomer) {
		this.receiveEndorseFromCustomer = receiveEndorseFromCustomer;
	}

}
