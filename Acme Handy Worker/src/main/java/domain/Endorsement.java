
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private Date				moment;
	private Collection<String>	comments;
	private HandyWorker			handyWorkerSender;
	private HandyWorker			handyWorkerReceiver;
	private Customer			customerSender;
	private Customer			customerReceiver;


	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	//Opcional
	@ElementCollection
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}
	@Valid
	@ManyToOne
	public HandyWorker getHandyWorkerSender() {
		return this.handyWorkerSender;
	}

	public void setHandyWorkerSender(final HandyWorker handyWorkerSender) {
		this.handyWorkerSender = handyWorkerSender;
	}

	@Valid
	@ManyToOne
	public HandyWorker getHandyWorkerReceiver() {
		return this.handyWorkerReceiver;
	}

	public void setHandyWorkerReceiver(final HandyWorker handyWorkerReceiver) {
		this.handyWorkerReceiver = handyWorkerReceiver;
	}
	@Valid
	@ManyToOne
	public Customer getCustomerSender() {
		return this.customerSender;
	}

	public void setCustomerSender(final Customer customerSender) {
		this.customerSender = customerSender;
	}

	@Valid
	@ManyToOne
	public Customer getCustomerReceiver() {
		return this.customerReceiver;
	}

	public void setCustomerReceiver(final Customer customerReceiver) {
		this.customerReceiver = customerReceiver;
	}

}
