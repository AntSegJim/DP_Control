
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

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	@Autowired
	private SponsorRepository	sponsorRepository;
	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private ActorService		actorService;


	public Sponsor create() {
		final Sponsor s = new Sponsor();
		s.setAddress("");
		s.setEmail("");
		s.setMiddleName("");
		s.setName("");
		s.setNumberSocialProfiles(0);
		s.setPhone("");
		s.setPhoto("");
		s.setSurname("");
		s.setIsBanned(0);
		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.SPONSOR);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		s.setUserAccount(user);

		return s;
	}

	public Collection<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(final int sponsorId) {
		return this.sponsorRepository.findOne(sponsorId);
	}

	public Sponsor save(final Sponsor s) {

		//		final UserAccount userLoged = LoginService.getPrincipal();
		//		if (userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
		//			final Sponsor sponsor = this.sponsorRepository.findOne(s.getId());
		//
		//			Assert.isTrue(sponsor.getId() == (s.getId()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(sponsor.getVersion() == (s.getVersion()), "Un administrador no debe modificar estos datos");
		//
		//			Assert.isTrue(sponsor.getName().equals(s.getName()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(sponsor.getMiddleName().equals(s.getMiddleName()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(sponsor.getSurname().equals(s.getSurname()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(sponsor.getPhoto().equals(s.getPhoto()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(sponsor.getEmail().equals(s.getEmail()), "Un administrador no debe modificar estos datos");
		//			//			Assert.isTrue(sponsor.getPhone().equals(s.getPhone()), "Un administrador no debe modificar estos datos");
		//			//			Assert.isTrue(sponsor.getAddress().equals(s.getAddress()), "Un administrador no debe modificar estos datos");
		//			//			Assert.isTrue(sponsor.getNumberSocialProfiles() == (s.getNumberSocialProfiles()), "Un administrador no debe modificar estos datos");
		//
		//		}

		Sponsor res = null;
		Assert.isTrue(s.getName() != null && s.getSurname() != null && s.getName() != "" && s.getSurname() != "" && s.getUserAccount() != null && s.getEmail() != null && s.getEmail() != "", "SponsorService.save -> Name or Surname invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(s.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(s.getEmail());
		Assert.isTrue(matcherEmail1.matches() == true || matcherEmail2.matches() == true, "SponsorService.save -> Correo inválido");

		final List<String> emails = this.actorService.getEmails();

		if (s.getId() == 0)
			Assert.isTrue(!emails.contains(s.getEmail()));
		else {
			final Sponsor a = this.sponsorRepository.findOne(s.getId());
			Assert.isTrue(a.getEmail().equals(s.getEmail()));
		}

		if (s.getPhone() != "" || s.getPhone() != null) {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(s.getPhone());
			Assert.isTrue(matcherTelefono.find() == true, "SponsorService.save -> Teléfono inválido");
		}

		//NUEVO
		Assert.isTrue(s.getUserAccount().getUsername() != null && s.getUserAccount().getUsername() != "");
		Assert.isTrue(s.getUserAccount().getPassword() != null && s.getUserAccount().getPassword() != "");

		if (s.getId() == 0) {
			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(s.getUserAccount().getPassword(), null);
			final UserAccount user = s.getUserAccount();
			user.setPassword(hash);
		}
		Assert.isTrue(s.getIsBanned() == 0 || s.getIsBanned() == 1);

		if (s.getIsBanned() == 1) {
			final Collection<Authority> result = new ArrayList<Authority>();
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.SPONSOR_BAN);
			result.add(authority);

			s.getUserAccount().setAuthorities(result);
		} else {
			final Collection<Authority> result = new ArrayList<Authority>();
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.SPONSOR);
			result.add(authority);

			s.getUserAccount().setAuthorities(result);
		}

		res = this.sponsorRepository.save(s);

		if (s.getId() == 0)
			this.messageBoxService.createMessageBoxSystem(res);

		return res;
	}

	public Sponsor sponsorUserAccount(final int id) {
		return this.sponsorRepository.sponsorByUserAccount(id);
	}

}
