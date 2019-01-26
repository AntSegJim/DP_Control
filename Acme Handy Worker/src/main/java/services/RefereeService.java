
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	@Autowired
	private RefereeRepository	refereeRepository;
	@Autowired
	private MessageBoxService	messageBoxService;
	@Autowired
	private ActorService		actorService;


	//Metodos CRUD

	public Referee create() {
		final Referee res = new Referee();

		res.setName("");
		res.setMiddleName("");
		res.setSurname("");
		res.setPhoto("");
		res.setEmail("");
		res.setPhone("");
		res.setAddress("");
		res.setNumberSocialProfiles(0);
		res.setIsBanned(0);
		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.REFEREE);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}

	//	public Referee create(final String name, final String middleName, final String surname, final String photo, final String email, final String phone, final String address, final Integer numberSocialProfiles) {
	//		final Referee referee = new Referee();
	//		referee.setName(name);
	//		referee.setMiddleName(middleName);
	//		referee.setSurname(surname);
	//		referee.setPhoto(photo);
	//		referee.setEmail(email);
	//		referee.setPhone(phone);
	//		referee.setAddress(address);
	//		referee.setNumberSocialProfiles(numberSocialProfiles);
	//
	//		return referee;
	//	}

	//listing
	public Collection<Referee> findAll() {
		return this.refereeRepository.findAll();
	}

	public Referee findOne(final int refereeId) {
		return this.refereeRepository.findOne(refereeId);
	}

	//updating
	public Referee save(final Referee r) {

		if (r.getId() == 0) {
			final UserAccount userLoged = LoginService.getPrincipal();
			Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		}

		final UserAccount userLoged = LoginService.getPrincipal();

		if (r.getId() != 0 && userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN"))
			if (userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
				final Referee referee = this.refereeRepository.findOne(r.getId());

				Assert.isTrue(referee.getId() == (r.getId()), "Un administrador no debe modificar estos datos");
				Assert.isTrue(referee.getId() == (r.getId()), "Un administrador no debe modificar estos datos");

				Assert.isTrue(referee.getName().equals(r.getName()), "Un administrador no debe modificar estos datos");
				Assert.isTrue(referee.getMiddleName().equals(r.getMiddleName()), "Un administrador no debe modificar estos datos");
				Assert.isTrue(referee.getSurname().equals(r.getSurname()), "Un administrador no debe modificar estos datos");
				Assert.isTrue(referee.getPhoto().equals(r.getPhoto()), "Un administrador no debe modificar estos datos");
				Assert.isTrue(referee.getEmail().equals(r.getEmail()), "Un administrador no debe modificar estos datos");
				Assert.isTrue(referee.getPhone().equals(r.getPhone()), "Un administrador no debe modificar estos datos");
				Assert.isTrue(referee.getAddress().equals(r.getAddress()), "Un administrador no debe modificar estos datos");
				Assert.isTrue(referee.getNumberSocialProfiles() == (r.getNumberSocialProfiles()), "Un administrador no debe modificar estos datos");
				Assert.isTrue(referee.getUserAccount() == (r.getUserAccount()), "Un administrador no debe modificar estos datos");
			}

		Referee res = null;

		Assert.isTrue(r != null && r.getName() != null && r.getSurname() != null && r.getName() != "" && r.getSurname() != "" && r.getUserAccount() != null && r.getEmail() != null && r.getEmail() != "", "RefereeService.save -> Name or Surname invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(r.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(r.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "CustomerService.save -> Correo inválido");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()));
		else {
			final Referee a = this.refereeRepository.findOne(r.getId());
			Assert.isTrue(a.getEmail().equals(r.getEmail()));
		}

		if (r.getPhone() != "" || r.getPhone() != null) {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(r.getPhone());
			Assert.isTrue(matcherTelefono.find() == true, "CustomerService.save -> Telefono no valido");
		}

		//NUEVO
		Assert.isTrue(r.getUserAccount().getUsername() != null && r.getUserAccount().getUsername() != "");
		Assert.isTrue(r.getUserAccount().getPassword() != null && r.getUserAccount().getPassword() != "");

		if (r.getId() == 0) {

			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(r.getUserAccount().getPassword(), null);
			final UserAccount user = r.getUserAccount();
			user.setPassword(hash);
		}

		Assert.isTrue(r.getIsBanned() == 0 || r.getIsBanned() == 1);

		if (r.getIsBanned() == 1) {
			final Collection<Authority> result = new ArrayList<Authority>();
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.REFEREE_BAN);
			result.add(authority);

			r.getUserAccount().setAuthorities(result);
		} else {
			final Collection<Authority> result = new ArrayList<Authority>();
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.REFEREE);
			result.add(authority);

			r.getUserAccount().setAuthorities(result);
		}

		res = this.refereeRepository.save(r);

		if (r.getId() == 0)
			this.messageBoxService.createMessageBoxSystem(res);

		return res;
	}

	//------------------------Other business methods---------------------
	public Referee refereeByUserAccount(final Integer userAccountId) {
		return this.refereeRepository.refereeUserAccount(userAccountId);
	}

}
