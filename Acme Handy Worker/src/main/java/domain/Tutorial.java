
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial extends DomainEntity {

	private String					title;
	private Date					moment;
	private String					summary;

	private Collection<Section>		section;
	private Collection<Sponsorship>	sponsorship;
	private HandyWorker				handyWorker;


	@NotBlank
	@NotNull
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Section> getSection() {
		return this.section;
	}

	public void setSection(final Collection<Section> section) {
		this.section = section;
	}
	@Valid
	@ManyToMany
	public Collection<Sponsorship> getSponsorship() {
		return this.sponsorship;
	}

	public void setSponsorship(final Collection<Sponsorship> sponsorship) {
		this.sponsorship = sponsorship;
	}

	@NotNull
	@ManyToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

}
