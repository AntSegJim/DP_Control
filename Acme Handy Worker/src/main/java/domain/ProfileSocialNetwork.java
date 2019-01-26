
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
public class ProfileSocialNetwork extends DomainEntity {

	private String	nickName;
	private String	nameSocialNetwork;
	private String	linkProfile;
	private Actor	actor;


	//Getters and Setters

	@NotBlank
	@NotNull
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(final String nickName) {
		this.nickName = nickName;
	}

	@NotBlank
	@NotNull
	public String getNameSocialNetwork() {
		return this.nameSocialNetwork;
	}

	public void setNameSocialNetwork(final String nameSocialNetwork) {
		this.nameSocialNetwork = nameSocialNetwork;
	}

	@NotBlank
	@URL
	@NotNull
	public String getLinkProfile() {
		return this.linkProfile;
	}

	public void setLinkProfile(final String linkProfile) {
		this.linkProfile = linkProfile;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
