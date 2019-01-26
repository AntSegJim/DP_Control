
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationRecord extends DomainEntity {

	private String				titleDiploma;
	private Date				startDate;
	private Date				endDate;
	private String				institution;
	private String				link;
	private Collection<String>	comment;


	@NotBlank
	@NotNull
	public String getTitleDiploma() {
		return this.titleDiploma;
	}

	public void setTitleDiploma(final String titleDiploma) {
		this.titleDiploma = titleDiploma;
	}
	@Past
	@Temporal(TemporalType.DATE)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	//Opcional
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotBlank
	@NotNull
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@URL
	//Opcional
	public String getLink() {
		return this.link;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	//Opcional
	@ElementCollection
	public Collection<String> getComment() {
		return this.comment;
	}

	public void setComment(final Collection<String> comment) {
		this.comment = comment;
	}

}
