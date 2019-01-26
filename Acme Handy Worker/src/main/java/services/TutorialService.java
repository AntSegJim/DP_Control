
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.UserAccount;
import domain.Actor;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository	TRepo;
	@Autowired
	private SectionService		SService;
	@Autowired
	private ActorService		actorS;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	public Tutorial create() {
		final Tutorial tutorial = new Tutorial();
		//final Section section = this.SService.create();
		//Me creo una lista de con una seccion ya que un tutorial cuando se crea tiene que tener
		//al menos una section porque es una composicion, asi que no puedo crear una lista vacia como
		//en picture y en sponsorship
		//		final Collection<Section> sections = new HashSet<>();
		//		Collections.addAll(sections, section);

		tutorial.setTitle("");
		tutorial.setMoment(new Date());
		tutorial.setSummary("");
		tutorial.setSection(new HashSet<Section>());
		tutorial.setSponsorship(new HashSet<Sponsorship>());
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		tutorial.setHandyWorker(this.handyWorkerService.findOne(a.getId()));
		return tutorial;
	}

	//listing
	public Collection<Tutorial> findAll() {
		return this.TRepo.findAll();
	}
	public Tutorial findOne(final int tutorialId) {
		return this.TRepo.findOne(tutorialId);
	}

	//updating
	public Tutorial save(final Tutorial tutorial) {
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		//		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		//		tutorial.setHandyWorker(this.handyWorkerService.findOne(a.getId()));
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("HANDYWORKER"));
		Assert.isTrue(tutorial.getMoment() != null);
		Assert.isTrue(tutorial.getSection() != null);
		Assert.isTrue(tutorial.getHandyWorker() != null);
		Assert.isTrue(tutorial != null && tutorial.getTitle() != null && tutorial.getTitle() != "" && tutorial.getSummary() != "");
		//final HandyWorker h = this.handyWorkerService.handyWorkerUserAccount(user.getId());

		return this.TRepo.save(tutorial);
	}

	//deleting
	public void delete(final Tutorial tutorial) {
		//Borramos primero mas secciones del tutorial
		final List<Section> sectionsFromTutorial = this.SService.sectionsFromTutorial(tutorial.getId());
		Assert.isTrue(sectionsFromTutorial != null);
		this.TRepo.delete(tutorial);
	}

	public Collection<Tutorial> getTutorialsByHandyWorker(final int id) {
		return this.TRepo.tutorialByHandyWorker(id);
	}
}
