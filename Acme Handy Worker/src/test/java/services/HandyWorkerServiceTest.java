
package services;

import java.util.Collection;
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
import domain.Category;
import domain.Finder;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	@Autowired
	private FinderService		finderService;
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private SponsorService		sponsorService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private WarrantyService		warrantyService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private MessageBoxService	messageBoxService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateHandyWorker() {
		HandyWorker s;
		s = this.handyWorkerService.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("hola123");
		ua.setUsername("Antonio");
		ua.setAuthorities(s.getUserAccount().getAuthorities());

		s.setName("Antonio");
		s.setAddress("calle Arahal");
		s.setEmail("antonio@gmail.es");
		s.setPhone("654321123");
		s.setUserAccount(ua);
		s.setSurname("SurnameHandy");
		s.setIsBanned(0);

		final Finder finder = this.finderService.create();
		finder.setAddress("hola");
		s.setFinder(finder);
		Assert.isTrue(s.getUserAccount().getId() == ua.getId());
		Assert.isTrue(s.getUserAccount().getUsername().equals("Antonio"));
		Assert.isTrue(s.getUserAccount().getPassword().equals("hola123"));
	}
	@Test
	public void testSaveHandyWorker() {
		HandyWorker h, saved;
		Collection<HandyWorker> hs;
		h = this.handyWorkerService.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("hola123");
		ua.setUsername("Antonio");
		ua.setAuthorities(h.getUserAccount().getAuthorities());

		h.setName("Antonio");
		h.setAddress("calle Arahal");
		h.setEmail("antonio@gmail.es");
		h.setPhone("654321123");
		h.setSurname("Segura");
		h.setUserAccount(ua);
		h.setMakeHandyWorker("handuWorkerMake");
		h.setScore(1);
		h.setIsBanned(0);
		final Finder finder = this.finderService.create();
		finder.setAddress("address");
		final Warranty warranty = this.warrantyService.create();
		warranty.setDraftMode(1);
		warranty.setLaws(new HashSet<String>());
		warranty.setTerms(new HashSet<String>());
		warranty.setTitle("Warranty1");
		final Category category = this.categoryService.create();
		category.setName("Categoria12");
		category.setParent(this.categoryService.rootCategory());
		category.setSoon(new HashSet<Category>());
		finder.setWarranty(this.warrantyService.save(warranty));
		finder.setCategory(this.categoryService.save(category));
		finder.setMoment(new Date());
		final Finder savedFinder = this.finderService.save(finder);
		h.setFinder(savedFinder);
		Assert.isTrue(h.getUserAccount().getUsername().equals("Antonio"));
		Assert.isTrue(h.getUserAccount().getPassword().equals("hola123"));

		saved = this.handyWorkerService.save(h);

		final MessageBox trashBox2 = this.messageBoxService.getTrashBox(saved.getId());
		final MessageBox spamBox2 = this.messageBoxService.getSpamBox(saved.getId());
		final MessageBox inBox2 = this.messageBoxService.getInBox(saved.getId());
		final MessageBox outBox2 = this.messageBoxService.getOutBox(saved.getId());

		Assert.isTrue(trashBox2 != null && spamBox2 != null && inBox2 != null && outBox2 != null, "Hola");

		hs = this.handyWorkerService.findAll();
		Assert.isTrue(hs.contains(saved));
	}
	@Test
	public void handyWorkerMoreTentPercentApplicatonsAccepted() {
		//		final Collection<HandyWorker> handyWorkers = this.handyWorkerService.handyWorkerMoreTentPercentApplicatonsAccepted();
		//		System.out.print(handyWorkers);
		//		super.authenticate("admin2");
		//		final HandyWorker h = this.handyWorkerService.create();
		//		final UserAccount ua = new UserAccount();
		//		ua.setPassword("hola123");
		//		ua.setUsername("Antonio");
		//		ua.setAuthorities(h.getUserAccount().getAuthorities());
		//		h.setName("Antonio");
		//		h.setAddress("calle Arahal");
		//		h.setEmail("antonio@gmail.es");
		//		h.setPhone("654321123");
		//		h.setSurname("surname");
		//		h.setUserAccount(ua);
		//		h.setScore(10);
		//		h.setMakeHandyWorker("makehandyworker");
		//		final HandyWorker savedHandyWorker = this.handyWorkerService.save(h);
		//
		//		Sponsor sponsor, saved;
		//		sponsor = this.sponsorService.create();
		//		final UserAccount uaSponsor = new UserAccount();
		//		uaSponsor.setPassword("hola123");
		//		uaSponsor.setUsername("Antonio");
		//		uaSponsor.setAuthorities(sponsor.getUserAccount().getAuthorities());
		//		sponsor.setName("Antonio");
		//		sponsor.setAddress("calle Arahal");
		//		sponsor.setEmail("antonio@gmail.es");
		//		sponsor.setPhone("654321123");
		//		sponsor.setSurname("Segura");
		//		sponsor.setUserAccount(ua);
		//		saved = this.sponsorService.save(sponsor);
		//
		//		CreditCard creditCard, savedCreditCard;
		//		creditCard = this.creditCardService.create();
		//		creditCard.setBrandName("Tarjeta1");
		//		creditCard.setCW(200);
		//		creditCard.setExpirationMonth(2);
		//		creditCard.setExpirationYear(1);
		//		creditCard.setNumber(12321321);
		//		creditCard.setHolderName("holdername");
		//		savedCreditCard = this.creditCardService.save(creditCard);
		//
		//		Sponsorship sponsorship;
		//		//final Sponsorship savedship;
		//		sponsorship = this.sponsorshipService.create();
		//		sponsorship.setCreditCard(savedCreditCard);
		//		sponsorship.setLinkTargetPage("hola.com");
		//		sponsorship.setSponsor(saved);
		//		sponsorship.setUrlBanner("hola.com");
		//		this.sponsorshipService.save(sponsorship);
		//
		//		final Warranty warranty, savedWarranty;
		//		warranty = this.warrantyService.create();
		//		final Collection<String> laws = warranty.getLaws();
		//		laws.add("Ley1");
		//		final Collection<String> terms = warranty.getTerms();
		//		terms.add("Term1");
		//		warranty.setLaws(laws);
		//		warranty.setTerms(terms);
		//		warranty.setTitle("TituloWarranty");
		//		savedWarranty = this.warrantyService.save(warranty);
		//
		//		Category category, savedCategory;
		//		category = this.categoryService.create();
		//		category.setName("CategoriaPrimera");
		//		category.setParent(category);
		//		savedCategory = this.categoryService.save(category);
		//
		//		final Customer customer = this.customerService.create();
		//		final Customer savedCustomer;
		//		final UserAccount uaCustomer = new UserAccount();
		//		uaCustomer.setPassword("hola123");
		//		uaCustomer.setUsername("Antonio");
		//		uaCustomer.setAuthorities(customer.getUserAccount().getAuthorities());
		//		customer.setName("Antonio");
		//		customer.setAddress("calle Arahal");
		//		customer.setEmail("antonio@gmail.es");
		//		customer.setPhone("654321123");
		//		customer.setSurname("surnaeCustomer");
		//		customer.setUserAccount(uaCustomer);
		//		customer.setScore(10);
		//		savedCustomer = this.customerService.save(customer);
		//
		//		FixUpTask fix;
		//		FixUpTask savedFix;
		//		fix = this.fixUpTaskService.create();
		//		final Collection<Application> appList = new HashSet<Application>();
		//		fix.setAddress("adressFix");
		//		fix.setApplication(appList);
		//		fix.setCategory(savedCategory);
		//		fix.setCustomer(savedCustomer);
		//		fix.setDescription("Descripcion en fixUpTask");
		//		fix.setMaximunPrice(1.0);
		//		fix.setMoment(new Date());
		//		fix.setPeriodTime(12);
		//		fix.setWarranty(savedWarranty);
		//		fix.setTicker("123qweqwe2132");
		//		savedFix = this.fixUpTaskService.save(fix);
		//
		//		final Application app, savedApp;
		//		app = this.applicationService.create();
		//		app.setCreditCard(savedCreditCard);
		//		app.setFixUpTask(savedFix);
		//		app.setStatus(1);
		//		app.setHandyWorker(savedHandyWorker);
		//		app.setMoment(new Date());
		//		app.setPrice(12.);
		//		savedApp = this.applicationService.save(app);
		//		savedFix = this.fixUpTaskService.save(fix);
		//		appList.add(savedApp);

		final Collection<HandyWorker> handyWorkers = this.handyWorkerService.handyWorkerMoreTentPercentApplicatonsAccepted();
		Assert.isTrue(handyWorkers.size() == 2, "HandyWorkerServiceTest.handyWorkerMoreTentPercentApplicatonsAccepted -> Los handy Workers no son iguales y deberian");
		super.authenticate(null);
	}

}
