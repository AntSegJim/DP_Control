
package services;

import java.sql.Date;
import java.util.Collection;
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
import domain.Application;
import domain.Category;
import domain.CreditCard;
import domain.Customer;
import domain.Endorsement;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private CustomerService		customerService;
	@Autowired
	private EndorsementService	endorsementService;
	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private WarrantyService		warrantyService;


	@Test
	public void testCreateApplication() {
		Application application;
		application = this.applicationService.create();

		Phase phase = this.phaseService.create();

		final Collection<Application> ca = new HashSet<>();
		ca.add(application);

		final Collection<String> comments = new HashSet<>();
		comments.add("comentario1");
		comments.add("comentario2");

		final CreditCard cc = this.creditCardService.create();
		cc.setBrandName("Visa");
		cc.setHolderName("jesus");
		cc.setNumber(12342123);
		cc.setExpirationMonth(3);
		cc.setExpirationYear(2019);
		cc.setCW(234321);

		final Category category = this.categoryService.create();
		category.setName("categoria de prueba");
		category.setParent(category);
		HandyWorker s;
		s = this.handyWorkerService.create();

		final UserAccount ua2 = new UserAccount();
		ua2.setPassword("hola");
		ua2.setUsername("Pablo");
		ua2.setAuthorities(s.getUserAccount().getAuthorities());

		s.setName("Pablo");
		s.setAddress("calle concordia");
		s.setEmail("jesus@us.es");
		s.setPhone("654321121");
		s.setUserAccount(ua2);
		s.setSurname("SurnameHandy");

		HandyWorker s1;
		s1 = this.handyWorkerService.create();

		final UserAccount ua3 = new UserAccount();
		ua3.setPassword("hola");
		ua3.setUsername("Javi");
		ua3.setAuthorities(s.getUserAccount().getAuthorities());

		s1.setName("Javi");
		s1.setAddress("calle Tarfia");
		s1.setEmail("Javi@us.es");
		s1.setPhone("654321120");
		s1.setUserAccount(ua3);
		s1.setSurname("SurnameHandy2");

		final Endorsement e = this.endorsementService.create();
		final Endorsement e2 = this.endorsementService.create();

		final Collection<Endorsement> ce = new HashSet<>();
		ce.add(e);
		final Collection<Endorsement> ce2 = new HashSet<>();
		ce.add(e2);

		final Customer c = this.customerService.create();
		final Customer c2 = this.customerService.create();

		//Endorsement
		e.setComments(comments);
		e.setCustomerReceiver(c);
		e.setCustomerSender(c2);
		e.setHandyWorkerReceiver(s);
		e.setHandyWorkerSender(s1);
		e.setMoment(Date.valueOf("2017-12-12"));

		final UserAccount ua = new UserAccount();
		ua.setPassword("hola");
		ua.setUsername("Jesus");
		ua.setAuthorities(c.getUserAccount().getAuthorities());
		//Customer c
		c.setAddress("Dirección prueba");
		c.setEmail("jesuselia@gmail.com");
		c.setEndorseCustomer(ce);
		c.setMiddleName("prueba");
		c.setName("Sergio");
		c.setNumberSocialProfiles(0);
		c.setPhone("654456654");
		c.setPhoto("https://hangouts.google.com/");
		c.setReceiveEndorseFromCustomer(ce2);
		c.setScore(8);
		c.setSurname("Perez");
		c.setUserAccount(ua);

		//c2
		final UserAccount uA = new UserAccount();
		uA.setPassword("hola");
		uA.setUsername("Jesus");
		uA.setAuthorities(c2.getUserAccount().getAuthorities());

		c2.setAddress("Dirección prueba");
		c2.setEmail("jesuseli@gmail.com");
		c2.setEndorseCustomer(ce2);
		c2.setMiddleName("prueba");
		c2.setName("Pablo");
		c2.setNumberSocialProfiles(0);
		c2.setPhone("654456653");
		c2.setPhoto("https://hangouts.google.com/");
		c2.setReceiveEndorseFromCustomer(ce);
		c2.setScore(8);
		c2.setSurname("Perez");
		c2.setUserAccount(uA);

		//Endorsement
		e2.setComments(comments);
		e2.setCustomerReceiver(c);
		e2.setCustomerSender(c2);
		e2.setHandyWorkerReceiver(s1);
		e2.setHandyWorkerSender(s);
		e2.setMoment(Date.valueOf("2018-12-12"));

		final Warranty ws = this.warrantyService.create();

		ws.setDraftMode(1);
		ws.setLaws(comments);
		ws.setTerms(comments);
		ws.setTitle("Titulo prueba");

		final FixUpTask f = this.fixUpTaskService.create();
		f.setAddress("Calle tarfia");
		f.setApplication(ca);
		f.setCategory(category);
		f.setCustomer(c);
		f.setDescription("Descripcion Prueba FixUpTask");
		f.setMaximunPrice(50.0);
		f.setMoment(Date.valueOf("2018-10-10"));
		f.setPeriodTime(10);
		f.setTicker("Cambiar a formato del Pattern");
		f.setWarranty(ws);

		//Application
		application.setComments(comments);
		application.setCreditCard(cc);
		application.setFixUpTask(f);
		application.setHandyWorker(s);
		application.setMoment(Date.valueOf("2018-10-10"));
		application.setPrice(3.9);
		application.setStatus(0);

		phase = this.phaseService.create();
		phase.setTitle("Primera");
		phase.setDescription("Descripcion");
		phase.setStartMoment(Date.valueOf("2018-11-11"));
		phase.setEndMoment(null);
		phase.setApplication(application);

		Assert.isTrue(application.getComments() == comments && application.getCreditCard() == cc && application.getFixUpTask() == f && application.getHandyWorker() == s && application.getMoment() == Date.valueOf("2018-10-10")
			&& application.getPrice() == 3.9 && application.getStatus() == 0);

	}
	@Test
	public void TestSaveApplication() {
		Application application, saved;
		final Collection<Application> applicationss;
		application = this.applicationService.create();

		Phase phase = this.phaseService.create();

		final Collection<Application> applications = new HashSet<>();

		final Collection<String> comments = new HashSet<>();
		comments.add("comentario1");
		comments.add("comentario2");

		final CreditCard cc = this.creditCardService.create();
		cc.setBrandName("Visa");
		cc.setHolderName("jesus");
		cc.setNumber(12342123);
		cc.setExpirationMonth(3);
		cc.setExpirationYear(2019);
		cc.setCW(234321);

		final Category category = this.categoryService.create();
		category.setName("categoria de prueba");
		category.setParent(category);
		HandyWorker s;
		s = this.handyWorkerService.create();

		final UserAccount ua2 = new UserAccount();
		ua2.setPassword("hola");
		ua2.setUsername("Pablo");
		ua2.setAuthorities(s.getUserAccount().getAuthorities());

		s.setName("Pablo");
		s.setAddress("calle concordia");
		s.setEmail("jesus@us.es");
		s.setPhone("654321121");
		s.setUserAccount(ua2);
		s.setSurname("SurnameHandy");

		HandyWorker s1;
		s1 = this.handyWorkerService.create();

		final UserAccount ua3 = new UserAccount();
		ua3.setPassword("hola");
		ua3.setUsername("Javi");
		ua3.setAuthorities(s.getUserAccount().getAuthorities());

		s1.setName("Javi");
		s1.setAddress("calle Tarfia");
		s1.setEmail("Javi@us.es");
		s1.setPhone("654321120");
		s1.setUserAccount(ua3);
		s1.setSurname("SurnameHandy2");

		final Endorsement e = this.endorsementService.create();
		final Endorsement e2 = this.endorsementService.create();

		final Collection<Endorsement> ce = new HashSet<>();
		ce.add(e);
		final Collection<Endorsement> ce2 = new HashSet<>();
		ce.add(e2);

		final Customer c = this.customerService.create();
		final Customer c2 = this.customerService.create();

		//Endorsement
		e.setComments(comments);
		e.setCustomerReceiver(c);
		e.setCustomerSender(c2);
		e.setHandyWorkerReceiver(s);
		e.setHandyWorkerSender(s1);
		e.setMoment(Date.valueOf("2017-12-12"));

		final UserAccount ua = new UserAccount();
		ua.setPassword("hola");
		ua.setUsername("Jesus");
		ua.setAuthorities(c.getUserAccount().getAuthorities());
		//Customer c
		c.setAddress("Dirección prueba");
		c.setEmail("jesuselia@gmail.com");
		c.setEndorseCustomer(ce);
		c.setMiddleName("prueba");
		c.setName("Sergio");
		c.setNumberSocialProfiles(0);
		c.setPhone("654456654");
		c.setPhoto("https://hangouts.google.com/");
		c.setReceiveEndorseFromCustomer(ce2);
		c.setScore(8);
		c.setSurname("Perez");
		c.setUserAccount(ua);

		//c2
		final UserAccount uA = new UserAccount();
		uA.setPassword("hola");
		uA.setUsername("Jesus");
		uA.setAuthorities(c2.getUserAccount().getAuthorities());

		c2.setAddress("Dirección prueba");
		c2.setEmail("jesuseli@gmail.com");
		c2.setEndorseCustomer(ce2);
		c2.setMiddleName("prueba");
		c2.setName("Pablo");
		c2.setNumberSocialProfiles(0);
		c2.setPhone("654456653");
		c2.setPhoto("https://hangouts.google.com/");
		c2.setReceiveEndorseFromCustomer(ce);
		c2.setScore(8);
		c2.setSurname("Perez");
		c2.setUserAccount(uA);

		//Endorsement
		e2.setComments(comments);
		e2.setCustomerReceiver(c);
		e2.setCustomerSender(c2);
		e2.setHandyWorkerReceiver(s1);
		e2.setHandyWorkerSender(s);
		e2.setMoment(Date.valueOf("2018-12-12"));

		final Warranty ws = this.warrantyService.create();

		ws.setDraftMode(1);
		ws.setLaws(comments);
		ws.setTerms(comments);
		ws.setTitle("Titulo prueba");

		final FixUpTask f = this.fixUpTaskService.create();
		f.setAddress("Calle tarfia");
		f.setApplication(applications);
		f.setCategory(category);
		f.setCustomer(c);
		f.setDescription("Descripcion Prueba FixUpTask");
		f.setMaximunPrice(50.0);
		f.setMoment(Date.valueOf("2018-10-10"));
		f.setPeriodTime(10);
		f.setTicker("Cambiar a formato del Pattern");
		f.setWarranty(ws);

		//Application
		application.setComments(comments);
		application.setCreditCard(cc);
		application.setFixUpTask(f);
		application.setHandyWorker(s);
		application.setMoment(Date.valueOf("2018-10-10"));
		application.setPrice(3.9);
		application.setStatus(0);

		phase = this.phaseService.create();
		phase.setTitle("Primera");
		phase.setDescription("Descripcion");
		phase.setStartMoment(Date.valueOf("2018-11-11"));
		phase.setEndMoment(null);
		phase.setApplication(application);

		saved = this.applicationService.save(application);
		applicationss = this.applicationService.findAll();
		Assert.isTrue(applicationss.contains(saved));
	}
}
