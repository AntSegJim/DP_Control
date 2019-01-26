
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private String		urlBanner;
	private String		linkTargetPage;
	private CreditCard	creditCard;
	private Sponsor		sponsor;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotBlank
	@NotNull
	@URL
	public String getUrlBanner() {
		return this.urlBanner;
	}

	@NotNull
	@NotBlank
	@URL
	public String getLinkTargetPage() {
		return this.linkTargetPage;
	}

	public void setUrlBanner(final String url) {
		this.urlBanner = url;
	}

	public void setLinkTargetPage(final String link) {
		this.linkTargetPage = link;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

}
