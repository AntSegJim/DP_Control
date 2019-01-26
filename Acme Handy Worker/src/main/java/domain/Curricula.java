
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	private String							ticker;
	private HandyWorker						handyWorker;
	private Collection<EducationRecord>		educationsRecords;
	private Collection<MiscellaneousRecord>	miscellaneousRecords;
	private Collection<ProfessionalRecord>	professionalsRecords;
	private Collection<EndoserRecord>		endosersRecords;
	private PersonalRecord					personalRecord;


	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationRecord> getEducationsRecords() {
		return this.educationsRecords;
	}
	public void setEducationsRecords(final Collection<EducationRecord> ed_r) {
		this.educationsRecords = ed_r;
	}
	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}
	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> m_r) {
		this.miscellaneousRecords = m_r;
	}
	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalsRecords() {
		return this.professionalsRecords;
	}
	public void setProfessionalsRecords(final Collection<ProfessionalRecord> pr_r) {
		this.professionalsRecords = pr_r;
	}
	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EndoserRecord> getEndosersRecords() {
		return this.endosersRecords;
	}
	public void setEndosersRecords(final Collection<EndoserRecord> en_r) {
		this.endosersRecords = en_r;
	}
	@Valid
	@NotNull
	@OneToOne(optional = false)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}
	public void setPersonalRecord(final PersonalRecord per_r) {
		this.personalRecord = per_r;
	}

	@Pattern(regexp = "^[0-9]{6}\\-[A-z0-9]{6}$")
	@Column(unique = true)
	//@Pattern(regexp = "^[0-9]{6}$")
	@NotNull
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
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
