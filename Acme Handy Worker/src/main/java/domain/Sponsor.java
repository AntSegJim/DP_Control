
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	//	private Collection<Sponsorship>	sponsorship;
	//
	//
	//	//Getters and Setters
	//	@Valid
	//	@OneToMany
	//	public Collection<Sponsorship> getSponsorship() {
	//		return this.sponsorship;
	//	}
	//
	//	public void setSponsorship(final Collection<Sponsorship> sponsorship) {
	//		this.sponsorship = sponsorship;
	//	}

}
