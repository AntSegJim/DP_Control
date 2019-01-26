
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.MessageBox;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	private SponsorService		sponsorService;
	@Autowired
	private MessageBoxService	messageBoxService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateSponsor() {
		Sponsor s;
		s = this.sponsorService.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("hola");
		ua.setUsername("Antonio");
		ua.setAuthorities(s.getUserAccount().getAuthorities());

		s.setName("Antonio");
		s.setAddress("calle Arahal");
		s.setEmail("antonio@us.es");
		s.setPhone("654321123");
		s.setSurname("Segura");
		s.setIsBanned(0);
		s.setUserAccount(ua);

		Assert.isTrue(s.getUserAccount().getId() == ua.getId());
		Assert.isTrue(s.getUserAccount().getUsername().equals("Antonio"));
		Assert.isTrue(s.getUserAccount().getPassword().equals("hola"));

	}
	@Test
	public void testSaveSponsor() {
		Sponsor s, saved;
		Collection<Sponsor> ss;
		s = this.sponsorService.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("hola123");
		ua.setUsername("Antonio");
		ua.setAuthorities(s.getUserAccount().getAuthorities());

		s.setName("Antonio");
		s.setAddress("calle Arahal");
		s.setEmail("antonio@us.es");
		s.setPhone("654321123");
		s.setSurname("Segura");
		s.setIsBanned(0);

		s.setUserAccount(ua);

		Assert.isTrue(s.getUserAccount().getUsername().equals("Antonio"));
		Assert.isTrue(s.getUserAccount().getPassword().equals("hola123"));

		saved = this.sponsorService.save(s);

		final MessageBox trashBox2 = this.messageBoxService.getTrashBox(saved.getId());
		final MessageBox spamBox2 = this.messageBoxService.getSpamBox(saved.getId());
		final MessageBox inBox2 = this.messageBoxService.getInBox(saved.getId());
		final MessageBox outBox2 = this.messageBoxService.getOutBox(saved.getId());

		Assert.isTrue(trashBox2 != null && spamBox2 != null && inBox2 != null && outBox2 != null, "Hola");

		ss = this.sponsorService.findAll();
		Assert.isTrue(ss.contains(saved));
	}

}
