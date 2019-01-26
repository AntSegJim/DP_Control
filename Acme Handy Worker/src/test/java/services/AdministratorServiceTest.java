
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
import domain.Administrator;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private MessageBoxService		messageBoxService;


	@Test
	public void testCreateAdministrator() {

		Administrator a;
		a = this.administratorService.create();
		final UserAccount uaAdmin = new UserAccount();
		uaAdmin.setAuthorities(a.getUserAccount().getAuthorities());

		a.setSurname("Segura");
		a.setName("Antonio");
		a.setAddress("calle Arahal");
		a.setEmail("antonio@us.es");
		a.setPhone("654321231");
		a.setIsBanned(0);
		uaAdmin.setUsername("adminCris");
		uaAdmin.setPassword("adminCris");
		a.setUserAccount(uaAdmin);

		Assert.isTrue(a.getName().equals("Antonio"), "AdminService.create() -> nombre no coincide");
		Assert.isTrue(a.getSurname().equals("Segura"), "AdminService.create() -> apellido no coincide");
		Assert.isTrue(a.getAddress().equals("calle Arahal"), "AdminService.create() -> calle no coincide");
		Assert.isTrue(a.getEmail().equals("antonio@us.es"), "AdminService.create() -> email no coincide");
		Assert.isTrue(a.getPhone().equals("654321231"), "AdminService.create() -> telefono no coincide");
		Assert.isTrue(a.getUserAccount().getUsername().equals("adminCris"));
		Assert.isTrue(a.getUserAccount().getPassword().equals("adminCris"));

	}

	@Test
	public void testSaveAdministrator() {

		super.authenticate("admin");
		Administrator a;
		final Administrator saved;
		Collection<Administrator> admins;
		a = this.administratorService.create();
		final UserAccount uaAdmin = new UserAccount();
		uaAdmin.setAuthorities(a.getUserAccount().getAuthorities());

		a.setSurname("Segura");
		a.setName("Antonio");
		a.setAddress("calle Arahal");
		a.setEmail("antArahal@hotmail.com");
		a.setPhone("654321231");
		a.setIsBanned(0);
		uaAdmin.setUsername("adminCris");
		uaAdmin.setPassword("adminCris");
		a.setUserAccount(uaAdmin);

		Assert.isTrue(a.getName().equals("Antonio"), "AdminService.create() -> nombre no coincide");
		Assert.isTrue(a.getSurname().equals("Segura"), "AdminService.create() -> apellido no coincide");
		Assert.isTrue(a.getAddress().equals("calle Arahal"), "AdminService.create() -> calle no coincide");
		Assert.isTrue(a.getEmail().equals("antArahal@hotmail.com"), "AdminService.create() -> email no coincide");
		Assert.isTrue(a.getPhone().equals("654321231"), "AdminService.create() -> telefono no coincide");
		Assert.isTrue(a.getUserAccount().getUsername().equals("adminCris"));
		Assert.isTrue(a.getUserAccount().getPassword().equals("adminCris"));

		saved = this.administratorService.save(a);
		final MessageBox trashBox2 = this.messageBoxService.getTrashBox(saved.getId());
		final MessageBox spamBox2 = this.messageBoxService.getSpamBox(saved.getId());
		final MessageBox inBox2 = this.messageBoxService.getInBox(saved.getId());
		final MessageBox outBox2 = this.messageBoxService.getOutBox(saved.getId());

		Assert.isTrue(trashBox2 != null && spamBox2 != null && inBox2 != null && outBox2 != null);

		admins = this.administratorService.findAll();

		Assert.isTrue(admins.contains(saved));
		super.authenticate(null);

	}

}
