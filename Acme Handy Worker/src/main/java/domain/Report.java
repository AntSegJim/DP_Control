
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	private Date					moment;		// : Date {NotBlank}
	private String					description;	// : String {NotBlank}
	private int						published;		//: Integer {NotBlank}
	private Collection<Attachment>	attachment;
	private Complaint				complaint;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Range(min = 0, max = 1)
	public int getPublished() {
		return this.published;
	}

	public void setPublished(final int published) {
		this.published = published;
	}

	@Valid
	@ManyToMany
	public Collection<Attachment> getAttachment() {
		return this.attachment;
	}

	public void setAttachment(final Collection<Attachment> attachment) {
		this.attachment = attachment;
	}

	@Valid
	@ManyToOne(optional = false)
	public Complaint getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}

}
