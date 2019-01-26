
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
import domain.Application;
import domain.Attachment;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Note;
import domain.Referee;
import domain.Report;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest {

	@Autowired
	private NoteService			noteService;

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
	public void testCreateNote() {
		final Note note;

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
		f.setMoment(new Date());
		f.setPeriodTime(0);
		f.setTicker("123456-123qwe");
		f.setWarranty(savedWarranty);

		res = this.complaintService.create();
		res.setTicker("123456-123ABC");
		res.setMoment(new Date());
		res.setDescription("queja de prueba");
		res.setNumberAttachments(2);
		res.setReferee(referee);
		res.setFixUpTask(f);
		attachment = this.attachmentService.create("https://github.com");
		final Collection<Attachment> attachments = new HashSet<>();
		attachments.add(attachment);

		report = this.reportService.create();
		report.setMoment(new Date());
		report.setDescription("Descripción de prueba");
		report.setPublished(0);
		report.setAttachment(attachments);
		report.setComplaint(res);

		final Collection<String> comments = new HashSet<>();
		comments.add("Primer comentario prueba");
		comments.add("Segundo comentario prueba");

		note = this.noteService.create();
		note.setMoment(new Date());
		note.setComment("Comentario de prueba para nota");
		note.setOptionalComments(comments);
		note.setReport(report);

		Assert.isTrue(note.getMoment() == new Date() && note.getComment() == "comentario de prueba para nota" && note.getOptionalComments() == comments && note.getReport() == report);
	}

	@Test
	public void testSaveNote() {
		final Note note, saved;
		Collection<Note> notes = new HashSet<>();
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
		f.setMoment(new Date());
		f.setPeriodTime(0);
		f.setTicker("123456-123qwe");
		f.setWarranty(savedWarranty);

		res = this.complaintService.create();
		res.setTicker("123456-123ABC");
		res.setMoment(new Date());
		res.setDescription("queja de prueba");
		res.setNumberAttachments(2);
		res.setReferee(referee);
		res.setFixUpTask(f);
		attachment = this.attachmentService.create("https://github.com");
		final Collection<Attachment> attachments = new HashSet<>();
		attachments.add(attachment);

		report = this.reportService.create();
		report.setMoment(new Date());
		report.setDescription("Descripción de prueba");
		report.setPublished(0);
		report.setAttachment(attachments);
		report.setComplaint(res);

		final Collection<String> comments = new HashSet<>();
		comments.add("Primer comentario prueba");
		comments.add("Segundo comentario prueba");

		note = this.noteService.create();
		note.setMoment(new Date());
		note.setComment("Comentario de prueba para nota");
		note.setOptionalComments(comments);
		note.setReport(report);

		saved = this.noteService.save(note);
		notes = this.noteService.findAll();
		Assert.isTrue(notes.contains(saved));

	}

}
