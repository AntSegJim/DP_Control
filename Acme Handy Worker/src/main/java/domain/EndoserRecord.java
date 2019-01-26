
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EndoserRecord extends DomainEntity {

	private String				name;
	private String				email;
	private String				phoneNumber;
	private String				linkedln;
	private Collection<String>	comments;


	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
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
	@Pattern(regexp = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$ ")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@URL
	//Opcional
	public String getLinkedln() {
		return this.linkedln;
	}

	public void setLinkedln(final String linkedln) {
		this.linkedln = linkedln;
	}

	@ElementCollection
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

}
