
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
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private MessageBoxService	messageBoxService;


	@Test
	public void testCreateReferee() {

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

		Assert.isTrue(referee.getAddress() == "Dirección prueba" && referee.getEmail() == "jesuseli@gmail.com" && referee.getMiddleName() == "prueba" && referee.getName() == "Pablo" && referee.getNumberSocialProfiles() == 1
			&& referee.getPhone() == "654456653" && referee.getPhoto() == "https://hangouts.google.com/" && referee.getSurname() == "Perez" && referee.getUserAccount() == uA);

		Assert.isTrue(referee.getUserAccount().getUsername().equals("Jesus"));
		Assert.isTrue(referee.getUserAccount().getPassword().equals("Antonio"));

	}

	@Test
	public void testSaveReferee() {
		super.authenticate("admin");

		Referee referee, saved;
		final Collection<Referee> referees;
		referee = this.refereeService.create();

		final UserAccount uA = new UserAccount();
		uA.setPassword("Antonio");
		uA.setUsername("Jesus");
		uA.setAuthorities(referee.getUserAccount().getAuthorities());

		referee.setAddress("Dirección prueba");
		referee.setEmail("jesuseli@gmail.com");
		referee.setMiddleName("prueba");
		referee.setName("Pablo");
		referee.setNumberSocialProfiles(0);
		referee.setPhone("654456653");
		referee.setPhoto("https://hangouts.google.com/");
		referee.setSurname("Perez");
		referee.setUserAccount(uA);

		Assert.isTrue(referee.getUserAccount().getUsername().equals("Jesus"));
		Assert.isTrue(referee.getUserAccount().getPassword().equals("Antonio"));

		saved = this.refereeService.save(referee);

		final MessageBox trashBox2 = this.messageBoxService.getTrashBox(saved.getId());
		final MessageBox spamBox2 = this.messageBoxService.getSpamBox(saved.getId());
		final MessageBox inBox2 = this.messageBoxService.getInBox(saved.getId());
		final MessageBox outBox2 = this.messageBoxService.getOutBox(saved.getId());

		Assert.isTrue(trashBox2 != null && spamBox2 != null && inBox2 != null && outBox2 != null, "Hola");

		referees = this.refereeService.findAll();
		Assert.isTrue(referees.contains(saved));

		super.authenticate(null);

	}

}
