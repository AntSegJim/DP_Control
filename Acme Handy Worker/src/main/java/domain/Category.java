
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String					name;
	private String					spanishName;
	private Category				parent;
	private Collection<Category>	soon;


	//Getters and Setters

	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@NotNull
	public String getSpanishName() {
		return this.spanishName;
	}
	public void setSpanishName(final String spanishName) {
		this.spanishName = spanishName;
	}

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	public Category getParent() {
		return this.parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}
	@Valid
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
	public Collection<Category> getSoon() {
		return this.soon;
	}

	public void setSoon(final Collection<Category> soon) {
		this.soon = soon;
	}

}
