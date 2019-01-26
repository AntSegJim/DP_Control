
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
import domain.Attachment;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Referee;
import domain.Report;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	@Autowired
	private ReportService		reportService;

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
	@Autowired
	private AttachmentService	attachmentService;


	@Test
	public void testCreateReport() {
		Report report;
		Attachment attachment;
		//Creando attachment
		Complaint res;

		Referee referee;
		referee = this.refereeService.create();

		final UserAccount uA = new UserAccount();
		uA.setPassword("hola");
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
		final Customer savedCustomer;
		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("hola");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(customer.getUserAccount().getAuthorities());
		customer.setName("Antonio");
		customer.setAddress("calle Arahal");
		customer.setEmail("jesuseli@gmail.com");
		customer.setPhone("654321123");
		customer.setSurname("surnaeCustomer");
		customer.setUserAccount(uaCustomer);
		customer.setScore(10);

		savedCustomer = this.customerService.save(customer);
		final Warranty warranty, savedWarranty;
		warranty = this.warrantyService.create();
		final Collection<String> laws = warranty.getLaws();
		laws.add("Ley1");
		final Collection<String> terms = warranty.getTerms();
		terms.add("Term1");
		warranty.setLaws(laws);
		warranty.setTerms(terms);
		warranty.setTitle("TituloWarranty");
		savedWarranty = this.warrantyService.save(warranty);

		FixUpTask f;
		f = this.fixUpTaskService.create();
		f.setAddress("Calle Hola");
		f.setApplication(new HashSet<Application>());
		final Category c = this.categoryService.create();
		c.setName("Categoria3");
		c.setParent(c);
		f.setCategory(c);
		f.setCustomer(savedCustomer);
		f.setDescription("Description");
		f.setMaximunPrice(0.);
		f.setMoment(Date.valueOf("2018-12-12"));
		f.setPeriodTime(0);
		f.setTicker("123456-123qwe");
		f.setWarranty(savedWarranty);

		res = this.complaintService.create();
		res.setTicker("123456-123ABC");
		res.setMoment(Date.valueOf("2018-12-12"));
		res.setDescription("queja de prueba");
		res.setNumberAttachments(2);
		res.setReferee(referee);
		res.setFixUpTask(f);
		attachment = this.attachmentService.create("https://github.com");
		final Collection<Attachment> attachments = new HashSet<>();
		attachments.add(attachment);

		report = this.reportService.create();
		report.setMoment(Date.valueOf("2018-12-12"));
		report.setDescription("Descrepción de prueba");
		report.setPublished(0);
		report.setAttachment(attachments);
		report.setComplaint(res);

		Assert.isTrue(report.getMoment() == Date.valueOf("2018-12-12") && report.getDescription() == "Descripcion de prueba" && report.getPublished() == 0 && report.getAttachment() == attachments && report.getComplaint() == res);

	}
	@Test
	public void TestSaveReport() {
		super.authenticate("referee1");

		final Report report, saved;
		Collection<Report> reports = new HashSet<>();
		Attachment attachment;
		//Creando attachment
		Complaint res;

		Referee referee;
		referee = this.refereeService.create();

		final UserAccount uA = new UserAccount();
		uA.setPassword("hola");
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
		final Customer savedCustomer;
		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("hola");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(customer.getUserAccount().getAuthorities());
		customer.setName("Antonio");
		customer.setAddress("calle Arahal");
		customer.setEmail("jesuseli@gmail.com");
		customer.setPhone("654321123");
		customer.setSurname("surnaeCustomer");
		customer.setUserAccount(uaCustomer);
		customer.setScore(10);

		savedCustomer = this.customerService.save(customer);
		final Warranty warranty, savedWarranty;
		warranty = this.warrantyService.create();
		final Collection<String> laws = warranty.getLaws();
		laws.add("Ley1");
		final Collection<String> terms = warranty.getTerms();
		terms.add("Term1");
		warranty.setLaws(laws);
		warranty.setTerms(terms);
		warranty.setTitle("TituloWarranty");
		savedWarranty = this.warrantyService.save(warranty);

		FixUpTask f;
		f = this.fixUpTaskService.create();
		f.setAddress("Calle Hola");
		f.setApplication(new HashSet<Application>());
		final Category c = this.categoryService.create();
		c.setName("Categoria3");
		c.setParent(c);
		f.setCategory(c);
		f.setCustomer(savedCustomer);
		f.setDescription("Description");
		f.setMaximunPrice(0.);
		f.setMoment(Date.valueOf("2018-12-12"));
		f.setPeriodTime(0);
		f.setTicker("123456-123qwe");
		f.setWarranty(savedWarranty);

		res = this.complaintService.create();
		res.setTicker("123456-123ABC");
		res.setMoment(Date.valueOf("2018-12-12"));
		res.setDescription("queja de prueba");
		res.setNumberAttachments(2);
		res.setReferee(referee);
		res.setFixUpTask(f);
		attachment = this.attachmentService.create("https://github.com");
		final Collection<Attachment> attachments = new HashSet<>();
		attachments.add(attachment);

		report = this.reportService.create();
		report.setMoment(Date.valueOf("2018-12-12"));
		report.setDescription("Descrepción de prueba");
		report.setPublished(0);
		report.setAttachment(attachments);
		report.setComplaint(res);

		saved = this.reportService.save(report);
		reports = this.reportService.findAll();
		Assert.isTrue(reports.contains(saved));

		super.authenticate(null);

	}
	@Test
	public void Testdelete() {
		super.authenticate("referee");

		final Report report, saved;
		Collection<Report> reports = new HashSet<>();
		Attachment attachment;
		//Creando attachment
		Complaint res;

		Referee referee;
		referee = this.refereeService.create();

		final UserAccount uA = new UserAccount();
		uA.setPassword("hola");
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
		final Customer savedCustomer;
		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("hola");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(customer.getUserAccount().getAuthorities());
		customer.setName("Antonio");
		customer.setAddress("calle Arahal");
		customer.setEmail("jesuseli@gmail.com");
		customer.setPhone("654321123");
		customer.setSurname("surnaeCustomer");
		customer.setUserAccount(uaCustomer);
		customer.setScore(10);

		savedCustomer = this.customerService.save(customer);
		final Warranty warranty, savedWarranty;
		warranty = this.warrantyService.create();
		final Collection<String> laws = warranty.getLaws();
		laws.add("Ley1");
		final Collection<String> terms = warranty.getTerms();
		terms.add("Term1");
		warranty.setLaws(laws);
		warranty.setTerms(terms);
		warranty.setTitle("TituloWarranty");
		savedWarranty = this.warrantyService.save(warranty);

		FixUpTask f;
		f = this.fixUpTaskService.create();
		f.setAddress("Calle Hola");
		f.setApplication(new HashSet<Application>());
		final Category c = this.categoryService.create();
		c.setName("Categoria3");
		c.setParent(c);
		f.setCategory(c);
		f.setCustomer(savedCustomer);
		f.setDescription("Description");
		f.setMaximunPrice(0.);
		f.setMoment(Date.valueOf("2018-12-12"));
		f.setPeriodTime(0);
		f.setTicker("123456-123qwe");
		f.setWarranty(savedWarranty);

		res = this.complaintService.create();
		res.setTicker("123456-123ABC");
		res.setMoment(Date.valueOf("2018-12-12"));
		res.setDescription("queja de prueba");
		res.setNumberAttachments(2);
		res.setReferee(referee);
		res.setFixUpTask(f);
		attachment = this.attachmentService.create("https://github.com");
		final Collection<Attachment> attachments = new HashSet<>();
		attachments.add(attachment);

		report = this.reportService.create();
		report.setMoment(Date.valueOf("2018-12-12"));
		report.setDescription("Descrepción de prueba");
		report.setPublished(0);
		report.setAttachment(attachments);
		report.setComplaint(res);

		saved = this.reportService.save(report);
		this.reportService.delete(saved);
		reports = this.reportService.findAll();
		super.authenticate(null);
		Assert.isTrue(!reports.contains(saved));

	}

}
