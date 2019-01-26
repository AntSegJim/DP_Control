
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
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Referee;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private WarrantyService		warrantyService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private CustomerService		customerService;


	@Test
	public void testCreateComplaint() {
		Complaint res;

		Referee referee;
		referee = this.refereeService.create();

		final UserAccount uA = new UserAccount();
		uA.setPassword("Antonio");
		uA.setUsername("Jesus");
		uA.setAuthorities(referee.getUserAccount().getAuthorities());

		referee.setAddress("Dirección prueba");
		referee.setEmail("jesuseli@gmail.com");
		referee.setMiddleName("prueba");
		referee.setName("Pablo");
		referee.setNumberSocialProfiles(1);
		referee.setPhone("654456653");
		referee.setPhoto("https://hangouts.google.com/");
		referee.setSurname("Perez");
		referee.setUserAccount(uA);

		final Customer customer = this.customerService.create();

		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("Antonio");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(customer.getUserAccount().getAuthorities());
		customer.setName("Antonio");
		customer.setAddress("calle Arahal");
		customer.setEmail("jesuseli@gmail.com");
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

		FixUpTask f, savedf;
		f = this.fixUpTaskService.create();
		f.setAddress("Calle Hola");
		f.setApplication(new HashSet<Application>());
		final Category c = this.categoryService.create();
		c.setName("Categoria3");
		c.setParent(c);
		f.setCategory(c);
		f.setCustomer(customer);
		f.setDescription("Description");
		f.setMaximunPrice(0.);
		f.setMoment(new Date());
		f.setPeriodTime(0);
		f.setTicker("123456-123qwe");
		f.setWarranty(w);
		savedf = this.fixUpTaskService.save(f);

		res = this.complaintService.create();
		res.setTicker("123456-123ABC");
		res.setMoment(new Date());
		res.setDescription("queja de prueba");
		res.setNumberAttachments(2);
		res.setReferee(referee);
		res.setFixUpTask(f);

		Assert.isTrue(res.getTicker().equals("123456-123ABC") && res.getMoment() == new Date() && res.getDescription() == "queja de prueba" && res.getNumberAttachments() == 2 && res.getFixUpTask() == f);
	}
	@Test
	public void saveComplaint() {
		super.authenticate("customer2");

		Complaint res, saved;
		final Collection<Complaint> complaints;

		Referee referee;
		referee = this.refereeService.create();

		final UserAccount uA = new UserAccount();
		uA.setPassword("Antonio");
		uA.setUsername("Jesus");
		uA.setAuthorities(referee.getUserAccount().getAuthorities());

		referee.setAddress("Dirección prueba");
		referee.setEmail("jesuseli@gmail.com");
		referee.setMiddleName("prueba");
		referee.setName("Pablo");
		referee.setNumberSocialProfiles(1);
		referee.setPhone("654456653");
		referee.setPhoto("https://hangouts.google.com/");
		referee.setSurname("Perez");
		referee.setUserAccount(uA);

		final Customer customer = this.customerService.create();

		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("Antonio");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(customer.getUserAccount().getAuthorities());
		customer.setName("Antonio");
		customer.setAddress("calle Arahal");
		customer.setEmail("jesuseli@gmail.com");
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
		f.setApplication(new HashSet<Application>());
		final Category c = this.categoryService.create();
		c.setName("Categoria3");
		c.setParent(c);
		f.setCategory(c);
		f.setCustomer(customer);
		f.setDescription("Description");
		f.setMaximunPrice(0.);
		f.setMoment(new Date());
		f.setPeriodTime(0);
		f.setTicker("123456-123qwe");
		f.setWarranty(w);

		res = this.complaintService.create();
		res.setTicker("123456-123ABC");
		res.setMoment(new Date());
		res.setDescription("queja de prueba");
		res.setNumberAttachments(2);
		res.setReferee(referee);
		res.setFixUpTask(f);

		saved = this.complaintService.save(res);
		complaints = this.complaintService.findAll();
		Assert.isTrue(complaints.contains(saved));

		super.authenticate(null);
	}

}
