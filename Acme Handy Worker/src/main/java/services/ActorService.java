
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;
	@Autowired
	private SpamWordService	spamWordService;


	public Actor create() {
		final Actor admin = new Administrator();
		admin.setName("");
		admin.setMiddleName("");
		admin.setSurname("");
		admin.setPhoto("");
		admin.setEmail("");
		admin.setPhone("");
		admin.setAddress("");
		admin.setNumberSocialProfiles(0);
		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.ADMIN);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		admin.setUserAccount(user);
		return admin;
	}
	public Actor save(final Actor a) {
		Actor res = null;

		Assert.isTrue(a != null && a.getName() != null && a.getSurname() != null && a.getName() != "" && a.getSurname() != "" && a.getUserAccount() != null && a.getEmail() != null && a.getEmail() != "", "ActorService.save -> Name or Surname invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(a.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(a.getEmail());
		Assert.isTrue(matcherEmail1.matches() == true || matcherEmail2.matches() == true, "CustomerService.save -> Correo no válido");

		if (a.getPhone() != "" || a.getPhone() != null) {
			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
			final Pattern patternTelefono = Pattern.compile(regexTelefono);
			final Matcher matcherTelefono = patternTelefono.matcher(a.getPhone());
			Assert.isTrue(matcherTelefono.find() == true, "CustomerService.save -> Telefono no valido");
		}

		res = this.actorRepository.save(a);
		return res;
	}

	public Actor getActorByUserAccount(final Integer id) {
		return this.actorRepository.getActorByUserAccount(id);
	}

	public Actor getActorByEmail(final String email) {
		return this.actorRepository.getActorByEmail(email);
	}

	public Actor getActorLogged() {
		UserAccount userAccount;
		Actor actor;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		actor = this.actorRepository.getActorByUserAccount(userAccount.getId());
		Assert.notNull(actor);

		return actor;
	}

	public List<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int id) {
		return this.actorRepository.findOne(id);
	}

	public Actor getActorByUsername(final String username) {
		return this.actorRepository.getActoyByUsername(username);
	}

	public List<String> getEmails() {
		return this.actorRepository.getEmails();
	}

	//	//BAN ACTOR
	//

	//MESSAGE
	public Collection<Actor> getSuspiciousActorsByMessageWord(final String palabra) {
		return this.actorRepository.actorSuspicious(palabra);
	}

	public Collection<Actor> getSuspiciousActorsByMessage() {
		final Set<Actor> suspiciousActors = new HashSet<>();

		final List<String> spamWords = (List<String>) this.spamWordService.getNamesOfSpamWord();
		for (int i = 0; i < spamWords.size(); i++) {
			final Collection<Actor> a = this.getSuspiciousActorsByMessageWord(spamWords.get(i));
			suspiciousActors.addAll(a);
		}
		return suspiciousActors;
	}

	//FIXUP
	public Collection<Actor> getSuspiciousActorsByFixUp(final String palabra) {
		return this.actorRepository.actorSuspiciousFixUp(palabra);
	}

	public Collection<Actor> getSuspiciousActorsByFixUp() {
		final Set<Actor> suspiciousActors = new HashSet<>();

		final List<String> spamWords = (List<String>) this.spamWordService.getNamesOfSpamWord();
		for (int i = 0; i < spamWords.size(); i++) {
			final Collection<Actor> a = this.getSuspiciousActorsByFixUp(spamWords.get(i));
			suspiciousActors.addAll(a);
		}
		return suspiciousActors;
	}

	//COMPLAINT
	public Collection<Actor> getSuspiciousActorsByComplaint(final String palabra) {
		return this.actorRepository.actorSuspiciousComplaint(palabra);
	}

	public Collection<Actor> getSuspiciousActorsByComplaint() {
		final Set<Actor> suspiciousActors = new HashSet<>();

		final List<String> spamWords = (List<String>) this.spamWordService.getNamesOfSpamWord();
		for (int i = 0; i < spamWords.size(); i++) {
			final Collection<Actor> a = this.getSuspiciousActorsByComplaint(spamWords.get(i));
			suspiciousActors.addAll(a);
		}
		return suspiciousActors;
	}

	//REPORT
	public Collection<Actor> getSuspiciousActorsByReport(final String palabra) {
		return this.actorRepository.actorSuspicuiousReport(palabra);
	}

	public Collection<Actor> getSuspiciousActorsByReport() {
		final Set<Actor> suspiciousActors = new HashSet<>();

		final List<String> spamWords = (List<String>) this.spamWordService.getNamesOfSpamWord();
		for (int i = 0; i < spamWords.size(); i++) {
			final Collection<Actor> a = this.getSuspiciousActorsByReport(spamWords.get(i));
			suspiciousActors.addAll(a);
		}
		return suspiciousActors;
	}

	//UNION DE LISTAS
	public Collection<Actor> getSuspiciousActor() {
		final Set<Actor> suspiciousActors = new HashSet<>();
		suspiciousActors.addAll(this.getSuspiciousActorsByMessage());
		suspiciousActors.addAll(this.getSuspiciousActorsByFixUp());
		suspiciousActors.addAll(this.getSuspiciousActorsByComplaint());
		suspiciousActors.addAll(this.getSuspiciousActorsByReport());

		return suspiciousActors;

	}

}
