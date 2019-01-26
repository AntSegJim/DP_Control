
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalRecord extends DomainEntity {

	private String	nameHandyWorker;
	private String	photo;
	private String	email;
	private String	phone;
	private String	linkedInProfile;


	@NotBlank
	@NotNull
	public String getNameHandyWorker() {
		return this.nameHandyWorker;
	}

	public void setNameHandyWorker(final String nameHandyWorker) {
		this.nameHandyWorker = nameHandyWorker;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	//	if email has following patterns, the value is well. 
	//	alias <identifier@domain>  -->  ^[A-z0-9]+\s*[A-z0-9\s]*\s\<[A-z0-9]+\@[A-z0-9]+\.[A-z0-9.]+\>
	//	identificador@  -->  ^[A-z0-9]+\@
	//	alias <identifier@>  -->  ^[A-z0-9]+\s*[A-z0-9\s]*\s\<[A-z0-9]+\@\>

	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	//"^(\+[1-9]{1}|\+[1-9]{1}+[0-9]{1}|\+[1-9]{1}+[0-9]{1}+[0-9]{1})$"
	@Pattern(regexp = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$ ")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@URL
	public String getLinkedInProfile() {
		return this.linkedInProfile;
	}

	public void setLinkedInProfile(final String linkedInProfile) {
		this.linkedInProfile = linkedInProfile;
	}

}
