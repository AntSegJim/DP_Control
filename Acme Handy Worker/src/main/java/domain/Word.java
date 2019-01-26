
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Word extends DomainEntity {

	private String	name;
	private int		value;


	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Range(min = 0, max = 1)
	public int getValue() {
		return this.value;
	}

	public void setValue(final int value) {
		this.value = value;
	}

}
