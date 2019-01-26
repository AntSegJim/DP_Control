
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Actor {

	private String					makeHandyWorker;
	private int						score;

	private Collection<Endorsement>	endorseHWorker;
	private Collection<Endorsement>	receiveEndorseFromHWorker;

	private Collection<Application>	application;
	private Finder					finder;


	@NotNull
	@NotBlank
	public String getMakeHandyWorker() {
		return this.makeHandyWorker;
	}

	public void setMakeHandyWorker(final String makeHandyWorker) {
		this.makeHandyWorker = makeHandyWorker;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(final int score) {
		this.score = score;
	}
	@Valid
	@OneToMany(mappedBy = "handyWorkerSender")
	public Collection<Endorsement> getEndorseHWorker() {
		return this.endorseHWorker;
	}

	public void setEndorseHWorker(final Collection<Endorsement> endorseHWorker) {
		this.endorseHWorker = endorseHWorker;
	}
	@Valid
	@OneToMany(mappedBy = "handyWorkerReceiver")
	public Collection<Endorsement> getReceiveEndorseFromHWorker() {
		return this.receiveEndorseFromHWorker;
	}

	public void setReceiveEndorseFromHWorker(final Collection<Endorsement> receiveEndorseFromHWorker) {
		this.receiveEndorseFromHWorker = receiveEndorseFromHWorker;
	}
	@Valid
	@OneToMany(mappedBy = "handyWorker")
	public Collection<Application> getApplication() {
		return this.application;
	}

	public void setApplication(final Collection<Application> application) {
		this.application = application;
	}

	@OneToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
