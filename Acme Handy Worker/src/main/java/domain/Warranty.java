
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	private String				title;
	private Collection<String>	terms;
	private Collection<String>	laws;
	private int					draftMode;


	//Getters and Setters
	@NotNull
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	@ElementCollection
	@NotNull
	public Collection<String> getTerms() {
		return this.terms;
	}

	public void setTerms(final Collection<String> terms) {
		this.terms = terms;
	}
	@ElementCollection
	@NotNull
	public Collection<String> getLaws() {
		return this.laws;
	}

	public void setLaws(final Collection<String> laws) {
		this.laws = laws;
	}
	@Range(min = 0, max = 1)
	public int getDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final int draftMode) {
		this.draftMode = draftMode;
	}

}
