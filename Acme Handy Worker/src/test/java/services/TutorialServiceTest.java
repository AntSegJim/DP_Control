
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	@Autowired
	private TutorialService		TService;
	@Autowired
	private SectionService		SSection;
	@Autowired
	private HandyWorkerService	handyWorkerS;


	@Test
	public void testCreateTutorial() {
		super.authenticate("handyWorker");
		Tutorial tutorial;
		Section section;
		//Creo una coleccion de secciones para el tutorial ya que debe de tener al menos una
		section = this.SSection.create();
		section.setNumber(1);
		section.setTitle("Hola");
		section.setPieceOfText("Primera seccion");
		final Collection<Section> sections = new HashSet<>();
		Collections.addAll(sections, section);
		//Creamos el handy worker
		final HandyWorker h;
		h = this.handyWorkerS.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("hola5667");
		ua.setUsername("Antonio");
		ua.setAuthorities(h.getUserAccount().getAuthorities());
		h.setName("Antonio");
		h.setAddress("calle Arahal");
		h.setEmail("antonio@us.es");
		h.setPhone("654321123");
		h.setUserAccount(ua);
		h.setSurname("SurnameHandy");

		//Creo el tutotial una vez creado la seccion
		tutorial = this.TService.create();
		tutorial.setTitle("Primer tutorial");
		tutorial.setMoment(new Date(02 / 04 / 2018));
		tutorial.setSummary("Descripcion");

		tutorial.setSection(sections);
		tutorial.setSponsorship(new HashSet<Sponsorship>());
		tutorial.setHandyWorker(h);

		Assert.isTrue(tutorial.getTitle().equals("Primer tutorial") && tutorial.getMoment().equals(new Date(02 / 04 / 2018)) && tutorial.getSummary().equals("Descripcion") && tutorial.getSection().equals(sections)
			&& tutorial.getSponsorship().equals(new HashSet<Sponsorship>()) && tutorial.getHandyWorker().equals(h));
		super.authenticate(null);
	}

	@Test
	public void testSaveTutorial() {
		super.authenticate("handyWorker");
		//Tutorial
		Tutorial tutorial, savedT;
		//Section
		Section section, savedS;
		//Lista de Tutorial
		final Collection<Tutorial> tutorials;
		//crear y guardar una seccion
		section = this.SSection.create();
		section.setNumber(2);
		section.setTitle("adios");
		section.setPieceOfText("segunda seccion");
		savedS = this.SSection.save(section);

		//crear y guardar un tutorial
		final Collection<Section> sectionsTutorial = new HashSet<>();
		Collections.addAll(sectionsTutorial, savedS);
		tutorial = this.TService.create();
		tutorial.setTitle("Primer tutorial");
		tutorial.setMoment(new Date(03 / 04 / 2018));
		tutorial.setSummary("Descripcion");

		tutorial.setSection(sectionsTutorial);
		tutorial.setSponsorship(new HashSet<Sponsorship>());
		savedT = this.TService.save(tutorial);
		//devulvo todos los tutoriales
		tutorials = this.TService.findAll();
		//Compruebo
		//Assert.isTrue(sections.contains(savedS));
		Assert.isTrue(tutorials.contains(savedT));
		super.authenticate(null);
	}

	@Test
	public void testDeleteTutorial() {
		super.authenticate("handyWorker");

		//Tutorial
		Tutorial tutorial, savedT;
		//Section
		Section section;
		//Lista de Tutorial
		final Collection<Tutorial> tutorials;
		//creamos una seccion
		section = this.SSection.create();
		section.setNumber(3);
		section.setTitle("bye");
		section.setPieceOfText("tercera seccion");

		//crear y guardar un tutorial
		final Collection<Section> sectionsTutorial = new HashSet<>();
		Collections.addAll(sectionsTutorial, section);
		tutorial = this.TService.create();
		tutorial.setTitle("segundo tutorial");
		tutorial.setMoment(new Date(01 / 04 / 2018));
		tutorial.setSummary("resumen del segundo tutorial");

		tutorial.setSection(sectionsTutorial);
		tutorial.setSponsorship(new HashSet<Sponsorship>());
		savedT = this.TService.save(tutorial);
		//Borro el tutorial
		this.TService.delete(savedT);
		//devulvo todos los tutoriales
		tutorials = this.TService.findAll();
		//Compruebo
		super.authenticate(null);

		Assert.isTrue(!tutorials.contains(savedT));

	}
}
