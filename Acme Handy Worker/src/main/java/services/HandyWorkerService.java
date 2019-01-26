
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

import repositories.HandyWorkerRepository;
import security.Authority;
import security.UserAccount;
import domain.Application;
import domain.Endorsement;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private FinderService			finderService;
	@Autowired
	private ActorService			actorService;


	public HandyWorker create() {
		final HandyWorker h = new HandyWorker();
		h.setAddress("");
		h.setEmail("");
		h.setEndorseHWorker(new HashSet<Endorsement>());
		h.setMiddleName("");
		h.setName("");
		h.setNumberSocialProfiles(0);
		h.setPhone("");
		h.setPhoto("");
		h.setReceiveEndorseFromHWorker(new HashSet<Endorsement>());
		h.setScore(0);
		h.setSurname("");
		h.setIsBanned(0);
		h.setFinder(this.finderService.save(this.finderService.create()));
		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.HANDYWORKER);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		h.setUserAccount(user);

		h.setMakeHandyWorker("changed-later");
		h.setApplication(new HashSet<Application>());
		return h;
	}

	public Collection<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}

	public HandyWorker findOne(final int handyWorkerId) {
		return this.handyWorkerRepository.findOne(handyWorkerId);
	}

	public HandyWorker save(final HandyWorker h) {

		//		final UserAccount userLoged = LoginService.getPrincipal();
		//		if (userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
		//			final HandyWorker handy = this.handyWorkerRepository.findOne(h.getId());
		//
		//			Assert.isTrue(handy.getId() == (h.getId()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(handy.getVersion() == (h.getVersion()), "Un administrador no debe modificar estos datos");
		//
		//			Assert.isTrue(handy.getName().equals(h.getName()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(handy.getMiddleName().equals(h.getMiddleName()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(handy.getSurname().equals(h.getSurname()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(handy.getPhoto().equals(h.getPhoto()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(handy.getEmail().equals(h.getEmail()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(handy.getPhone().equals(h.getPhone()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(handy.getAddress().equals(h.getAddress()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(handy.getNumberSocialProfiles() == (h.getNumberSocialProfiles()), "Un administrador no debe modificar estos datos");
		//			Assert.isTrue(handy.getUserAccount() == (h.getUserAccount()), "Un administrador no debe modificar estos datos");
		//
		//			//			Assert.isTrue(handy.getScore() == (h.getScore()), "Un administrador no debe modificar estos datos");
		//			//			Assert.isTrue(handy.getMakeHandyWorker() == (h.getMakeHandyWorker()), "Un administrador no debe modificar estos datos");
		//
		//		}

		HandyWorker res = null;

		Assert.isTrue(h.getName() != null && h.getSurname() != null && h.getName() != "" && h.getSurname() != "" && h.getUserAccount() != null && h.getEmail() != null && h.getEmail() != "", "HandyWorkerService.save -> Name or Surname invalid");

		if (h.getId() == 0)
			h.setMakeHandyWorker(h.getName() + " " + h.getMiddleName() + " " + h.getSurname());

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern pattern = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = pattern.matcher(h.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(h.getEmail());
		Assert.isTrue(matcherEmail1.matches() == true || matcherEmail2.matches() == true, "CustomerService.save -> Correo inválido");

		final List<String> emails = this.actorService.getEmails();

		if (h.getId() == 0)
			Assert.isTrue(!emails.contains(h.getEmail()));
		else {
			final HandyWorker a = this.handyWorkerRepository.findOne(h.getId());
			Assert.isTrue(a.getEmail().equals(h.getEmail()));
		}

		if (h.getPhone() != "" || h.getPhone() != null) {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(h.getPhone());
			Assert.isTrue(matcherTelefono.find() == true, "CustomerService.save -> Telefono inválido");
		}

		//NUEVO
		Assert.isTrue(h.getUserAccount().getUsername() != null && h.getUserAccount().getUsername() != "");
		Assert.isTrue(h.getUserAccount().getPassword() != null && h.getUserAccount().getPassword() != "");

		if (h.getId() == 0) {
			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(h.getUserAccount().getPassword(), null);
			final UserAccount user = h.getUserAccount();
			user.setPassword(hash);

		}
		Assert.isTrue(h.getIsBanned() == 0 || h.getIsBanned() == 1);

		if (h.getIsBanned() == 1) {
			final Collection<Authority> result = new ArrayList<Authority>();
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.HANDY_WORKER_BAN);
			result.add(authority);

			h.getUserAccount().setAuthorities(result);
		} else {
			final Collection<Authority> result = new ArrayList<Authority>();
			Authority authority;
			authority = new Authority();
			authority.setAuthority(Authority.HANDYWORKER);
			result.add(authority);

			h.getUserAccount().setAuthorities(result);
		}
		res = this.handyWorkerRepository.save(h);

		if (h.getId() == 0)
			this.messageBoxService.createMessageBoxSystem(res);
		return res;
	}

	public Collection<HandyWorker> handyWorkerMoreTentPercentApplicatonsAccepted() {
		return this.handyWorkerRepository.handyWorkerMoreTentPercentApplicatonsAccepted();
	}

	//	public Collection<HandyWorker> handyWorkerByUserAccount(final Integer userAccountId) {
	//		return this.handyWorkerByUserAccount(userAccountId);
	//	}

	//Añadido por jesus para un metodo en phaseService
	public HandyWorker handyWorkerUserAccount(final Integer id) {
		return this.handyWorkerRepository.handyWorkerUserAccount(id);
	}

	public static Double score(final Integer palabrasBuenas, final Integer palabrasMalas) {
		final Double x = (1.0 / ((double) palabrasBuenas + (double) palabrasMalas));
		final Double res = palabrasBuenas * x - palabrasMalas * x;
		return res;

	}

	public Collection<HandyWorker> getHandyWorkerInvolveInAnyOfHisFixUpTask(final int customerId) {
		return this.handyWorkerRepository.getHandyWorkerInvolveInAnyOfHisFixUpTask(customerId);
	}

	public Collection<String> handyWorkerMoreTentPercentApplicatonsAccepted3() {
		return this.handyWorkerRepository.handyWorkerMoreTentPercentApplicatonsAccepted3();
	}

}
