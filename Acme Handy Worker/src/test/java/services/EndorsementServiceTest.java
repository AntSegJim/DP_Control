
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

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	// ------------ Service under test ------------
	@Autowired
	private EndorsementService	endorsementService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private ActorService		actorService;


	//	---------------------- Test ----------------------
	@Test
	public void testCreateEndorsement() {
		super.authenticate("customer2");
		Endorsement e;
		e = this.endorsementService.create();
		final Collection<String> comments = new HashSet<String>();
		comments.add("Comentario1");
		e.setComments(comments);

		final Customer c, savedC;
		c = this.customerService.create();
		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("hola123");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(c.getUserAccount().getAuthorities());

		c.setSurname("Segura");
		c.setName("Antonio");
		c.setAddress("calle Arahal");
		c.setEmail("antonio@us.es");
		c.setPhone("654321123");
		c.setUserAccount(uaCustomer);
		c.setScore(10);
		savedC = this.customerService.save(c);

		HandyWorker h, savedH;
		h = this.handyWorkerService.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("hola123");
		ua.setUsername("Antonio");
		ua.setAuthorities(h.getUserAccount().getAuthorities());

		h.setName("Antonio");
		h.setAddress("calle Arahal");
		h.setEmail("antonio@us.es");
		h.setPhone("654321123");
		h.setSurname("Segura");
		h.setUserAccount(ua);

		savedH = this.handyWorkerService.save(h);
		e.setCustomerReceiver(savedC);
		e.setHandyWorkerSender(savedH);
		e.setMoment(new Date());
		Assert.isTrue(e.getComments().contains("Comentario1"));
		super.authenticate(null);
	}

	@Test
	public void testSaveEndorsement() {
		super.authenticate("customer2");
		Endorsement e;
		final Endorsement savedE;
		e = this.endorsementService.create();
		final Collection<String> comments = new HashSet<String>();
		comments.add("Comentario1");
		e.setComments(comments);

		final Customer c, savedC;
		c = this.customerService.create();
		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("hola123");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(c.getUserAccount().getAuthorities());

		c.setSurname("Segura");
		c.setName("Antonio");
		c.setAddress("calle Arahal");
		c.setEmail("antonio@us.es");
		c.setPhone("654321123");
		c.setUserAccount(uaCustomer);
		c.setScore(10);
		savedC = this.customerService.save(c);

		HandyWorker h, savedH;
		h = this.handyWorkerService.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("hola123");
		ua.setUsername("Antonio");
		ua.setAuthorities(h.getUserAccount().getAuthorities());

		h.setName("Antonio");
		h.setAddress("calle Arahal");
		h.setEmail("antonio@us.es");
		h.setPhone("654321123");
		h.setSurname("Segura");
		h.setUserAccount(ua);

		savedH = this.handyWorkerService.save(h);
		e.setCustomerReceiver(savedC);
		e.setHandyWorkerSender(savedH);
		e.setMoment(new Date());

		savedE = this.endorsementService.save(e);
		final int id = LoginService.getPrincipal().getId();
		final Collection<Endorsement> es = this.endorsementService.myEndorsements(this.actorService.getActorByUserAccount(id).getId());
		Assert.isTrue(es.contains(savedE));
		super.authenticate(null);
	}
	@Test
	public void testDeleteEndorsement() {
		super.authenticate("customer2");
		Endorsement e;
		final Endorsement savedE;
		e = this.endorsementService.create();
		final Collection<String> comments = new HashSet<String>();
		comments.add("Comentario1");
		e.setComments(comments);

		final Customer c, savedC;
		c = this.customerService.create();
		final UserAccount uaCustomer = new UserAccount();
		uaCustomer.setPassword("hola123");
		uaCustomer.setUsername("Antonio");
		uaCustomer.setAuthorities(c.getUserAccount().getAuthorities());

		c.setSurname("Segura");
		c.setName("Antonio");
		c.setAddress("calle Arahal");
		c.setEmail("antonio@us.es");
		c.setPhone("654321123");
		c.setUserAccount(uaCustomer);
		c.setScore(10);
		savedC = this.customerService.save(c);

		HandyWorker h, savedH;
		h = this.handyWorkerService.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("hola123");
		ua.setUsername("Antonio");
		ua.setAuthorities(h.getUserAccount().getAuthorities());

		h.setName("Antonio");
		h.setAddress("calle Arahal");
		h.setEmail("antonio@us.es");
		h.setPhone("654321123");
		h.setSurname("Segura");
		h.setUserAccount(ua);

		savedH = this.handyWorkerService.save(h);
		e.setCustomerReceiver(savedC);
		e.setHandyWorkerSender(savedH);
		e.setMoment(new Date());

		savedE = this.endorsementService.save(e);
		this.endorsementService.delete(savedE);
		final int id = LoginService.getPrincipal().getId();
		final Collection<Endorsement> es = this.endorsementService.myEndorsements(this.actorService.getActorByUserAccount(id).getId());
		Assert.isTrue(!es.contains(e));
		super.authenticate(null);
	}

	@Test
	public void testEndorsement() {
		super.authenticate("customer2");
		final int id = LoginService.getPrincipal().getId();
		final Collection<Endorsement> es = this.endorsementService.myEndorsements(this.actorService.getActorByUserAccount(id).getId());
		System.out.print(es);
		super.authenticate(null);
	}
}
