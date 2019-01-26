
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;
import domain.Customer;
import domain.Endorsement;

@Service
@Transactional
public class CustomerService {

	//------------------------Managed repository---------------------
	@Autowired
	private CustomerRepository	customerRepository;
	@Autowired
	private MessageBoxService	messageBoxService;
	@Autowired
	private ActorService		actorService;


	//------------------------Simple CRUD methods---------------------
	public Customer create() {
		final Customer c = new Customer();
		c.setAddress("");
		c.setEmail("");
		c.setEndorseCustomer(new HashSet<Endorsement>());
		c.setMiddleName("");
		c.setName("");
		c.setNumberSocialProfiles(0);
		c.setPhone("");
		c.setPhoto("");
		c.setReceiveEndorseFromCustomer(new HashSet<Endorsement>());
		c.setScore(0);
		c.setSurname("");
		c.setIsBanned(0);
		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.CUSTOMER);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		c.setUserAccount(user);

		return c;
	}
	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Customer findOne(final int customerId) {
		return this.customerRepository.findOne(customerId);
	}
	public Customer save(final Customer c) {

		//		final UserAccount userLoged = LoginService.getPrincipal();
		//		if (userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
		//			final Customer customer = this.customerRepository.findOne(c.getId());
		//
		//			Assert.isTrue(customer.getId() == (c.getId()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(customer.getId() == (c.getId()), "Un administrador no debe modificar estos datos");
		//
		//			Assert.isTrue(customer.getName().equals(c.getName()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(customer.getMiddleName().equals(c.getMiddleName()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(customer.getSurname().equals(c.getSurname()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(customer.getPhoto().equals(c.getPhoto()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(customer.getEmail().equals(c.getEmail()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(customer.getPhone().equals(c.getPhone()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(customer.getAddress().equals(c.getAddress()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(customer.getNumberSocialProfiles() == (c.getNumberSocialProfiles()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(customer.getUserAccount() == (c.getUserAccount()), "Un administrador no debe modificar estos datos");
		//
		//			Assert.isTrue(customer.getScore() == (c.getScore()), "Un administrador no debe modificar estos datos");
		//
		//		}

		Customer res = null;

		Assert.isTrue(c != null && c.getName() != null && c.getSurname() != null && c.getName() != "" && c.getSurname() != "" && c.getUserAccount() != null && c.getEmail() != null && c.getEmail() != "", "CustomerService.save -> Name or Surname invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(c.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(c.getEmail());
		Assert.isTrue(matcherEmail1.matches() == true || matcherEmail2.matches() == true, "CustomerService.save -> Correo no válido");

		final List<String> emails = this.actorService.getEmails();

		if (c.getId() == 0)
			Assert.isTrue(!emails.contains(c.getEmail()));
		else {
			final Customer a = this.customerRepository.findOne(c.getId());
			Assert.isTrue(a.getEmail().equals(c.getEmail()));
		}

		if (c.getPhone() != "" || c.getPhone() != null) {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(c.getPhone());
			Assert.isTrue(matcherTelefono.find() == true, "CustomerService.save -> Telefono no valido");
		}

		//NUEVO
		Assert.isTrue(c.getUserAccount().getUsername() != null && c.getUserAccount().getUsername() != "");
		Assert.isTrue(c.getUserAccount().getPassword() != null && c.getUserAccount().getPassword() != "");

		if (c.getId() == 0) {
			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(c.getUserAccount().getPassword(), null);
			final UserAccount user = c.getUserAccount();
			user.setPassword(hash);
		}
		Assert.isTrue(c.getIsBanned() == 0 || c.getIsBanned() == 1);

		if (c.getIsBanned() == 1) {
			final Collection<Authority> result = new ArrayList<Authority>();
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.CUSTOMER_BAN);
			result.add(authority);

			c.getUserAccount().setAuthorities(result);
		} else {
			final Collection<Authority> result = new ArrayList<Authority>();
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.CUSTOMER);
			result.add(authority);

			c.getUserAccount().setAuthorities(result);
		}

		res = this.customerRepository.save(c);

		if (c.getId() == 0)
			this.messageBoxService.createMessageBoxSystem(res);

		return res;
	}
	//------------------------Other business methods---------------------
	public Customer customerByUserAccount(final Integer id) {
		return this.customerRepository.customerUserAccount(id);
	}

	public Collection<Customer> getCustomerForWhomItIsWorked(final int handyWorkerId) {
		return this.customerRepository.getCustomerForWhomItIsWorked(handyWorkerId);
	}

	public List<Object[]> getCustomerFixUp() {
		return this.customerRepository.getCustomerFixUp();
	}
}
