
package services;

import java.text.SimpleDateFormat;
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
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private WarrantyService		warrantyService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


	@Test
	public void createFixUpTask() throws java.text.ParseException {
		super.authenticate("customer2");
		final Customer customer = this.customerService.create();

		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("Antonio");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(customer.getUserAccount().getAuthorities());
		customer.setName("Antonio");
		customer.setAddress("calle Arahal");
		customer.setEmail("antonio@us.es");
		customer.setPhone("654321123");
		customer.setSurname("surnaeCustomer");
		customer.setUserAccount(uaCustomer);
		customer.setScore(10);

		Warranty w;
		w = this.warrantyService.create();
		w.setDraftMode(1);
		final Collection<String> laws = new HashSet<String>();
		laws.add("law1");
		w.setLaws(laws);
		w.setTitle("Warranty Title");
		final Collection<String> terms = new HashSet<String>();
		terms.add("term1");
		w.setTerms(terms);

		FixUpTask f;
		f = this.fixUpTaskService.create();
		f.setAddress("Calle Hola");

		Category c;
		c = this.categoryService.create();
		c.setName("Hijo");
		f.setCategory(c);
		f.setCustomer(customer);
		f.setDescription("Description");
		f.setMaximunPrice(3.);
		final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		final Date fecha = format.parse("2017/10/20 10:00");
		f.setMoment(fecha);
		f.setPeriodTime(0);
		f.setTicker("123456-123qwe");
		f.setWarranty(w);

		Assert.isTrue(f.getMoment() != null && f.getMoment().before(new Date()) && f.getAddress() == "Calle Hola" && f.getCategory() == c && f.getCustomer() == customer && f.getDescription() == "Description" && f.getMaximunPrice() == 3.
			&& f.getPeriodTime() == 0 && f.getTicker() == "123456-123qwe" && f.getWarranty() == w);
		Assert.isTrue(f.getCustomer().getName() == "Antonio");
		super.authenticate(null);
	}
	@Test
	public void savedFixUpTask() throws java.text.ParseException {
		super.authenticate("customer2");
		final Customer customer, savedCus;

		customer = this.customerService.create();

		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("Antonio");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(customer.getUserAccount().getAuthorities());
		customer.setName("Antonio");
		customer.setAddress("calle Arahal");
		customer.setEmail("antonio@us.es");
		customer.setPhone("654321123");
		customer.setSurname("surnaeCustomer");
		customer.setUserAccount(uaCustomer);
		customer.setScore(10);
		savedCus = this.customerService.save(customer);

		Warranty w, savedW;
		w = this.warrantyService.create();
		w.setDraftMode(1);
		final Collection<String> laws = new HashSet<String>();
		laws.add("law1");
		w.setLaws(laws);
		w.setTitle("Warranty Title");
		final Collection<String> terms = new HashSet<String>();
		terms.add("term1");
		w.setTerms(terms);
		savedW = this.warrantyService.save(w);

		FixUpTask f, savedF;
		Collection<FixUpTask> fs = new HashSet<>();
		f = this.fixUpTaskService.create();
		f.setAddress("Calle Hola");

		Category c, savedC;
		c = this.categoryService.create();
		c.setName("Hijo");
		savedC = this.categoryService.save(c);
		f.setCategory(savedC);
		f.setCustomer(this.customerService.findOne(5672));
		f.setDescription("Description");
		f.setMaximunPrice(3.);
		final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		final Date fecha = format.parse("2017/10/20 10:00");
		f.setMoment(fecha);
		f.setPeriodTime(0);
		f.setTicker("123456-123qwz");
		f.setWarranty(savedW);
		savedF = this.fixUpTaskService.save(f);
		fs = this.fixUpTaskService.findAll();
		Assert.isTrue(fs.contains(savedF));

		super.authenticate(null);
	}

	//	@Test
	//	public void maxMinAvgDev() {
	//		super.authenticate("customer2");
	//		final HandyWorker h = this.handyWorkerService.create();
	//		final UserAccount ua = new UserAccount();
	//		ua.setPassword("hola123");
	//		ua.setUsername("Antonio");
	//		ua.setAuthorities(h.getUserAccount().getAuthorities());
	//		h.setName("Antonio");
	//		h.setAddress("calle Arahal");
	//		h.setEmail("hola@us.es");
	//		h.setPhone("654321123");
	//		h.setSurname("surname");
	//		h.setUserAccount(ua);
	//		h.setScore(10);
	//		h.setMakeHandyWorker("makehandyworker");
	//		final HandyWorker savedHandyWorker = this.handyWorkerService.save(h);
	//
	//		Warranty w, savedW;
	//		w = this.warrantyService.create();
	//		w.setDraftMode(1);
	//		final Collection<String> laws = new HashSet<String>();
	//		laws.add("law1");
	//		w.setLaws(laws);
	//		w.setTitle("Warranty Title");
	//		final Collection<String> terms = new HashSet<String>();
	//		terms.add("term1");
	//		w.setTerms(terms);
	//		savedW = this.warrantyService.save(w);
	//
	//		Category c, savedC;
	//		c = this.categoryService.create();
	//		c.setName("Hijo");
	//		c.setParent(this.categoryService.findOne(5625));
	//		c.setSoon(new HashSet<Category>());
	//		savedC = this.categoryService.save(c);
	//
	//		final Customer customer = this.customerService.create();
	//		final Customer savedCustomer;
	//		final UserAccount uaCustomer = new UserAccount();
	//		uaCustomer.setPassword("Antonio");
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
	//		fix.setCategory(savedC);
	//		fix.setCustomer(savedCustomer);
	//		fix.setDescription("Descripcion en fixUpTask");
	//		fix.setMaximunPrice(1.0);
	//		fix.setMoment(new Date());
	//		fix.setPeriodTime(12);
	//		fix.setWarranty(savedW);
	//		fix.setTicker("123qweqwe2132");
	//		savedFix = this.fixUpTaskService.save(fix);
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
	//
	//		final Collection<Double> values = this.fixUpTaskService.maxMinAvgDevFixUpTask();
	//		Assert.isTrue(!values.isEmpty());
	//		super.authenticate(null);
	//	}

	@Test
	public void filterTest() {
		final Collection<FixUpTask> f = this.fixUpTaskService.filterFixUpTask("", "", "", new Date(), new Date(), 1., 10., "", "");
		final Collection<FixUpTask> f2 = this.fixUpTaskService.filterFixUpTask2("", "", "", new Date(0), new Date(), 1., 25., "", "", 3);
		System.out.println(f.size());
		System.out.println(f2.size());
	}
}
