
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService	actorService;


	//
	//	@Test
	//	public void testSaveAdministrator() {
	//
	//		Actor a;
	//		final Actor saved;
	//		Collection<Actor> admins;
	//		a = this.actorService.create();
	//		final UserAccount uaAdmin = new UserAccount();
	//		uaAdmin.setAuthorities(a.getUserAccount().getAuthorities());
	//
	//		a.setSurname("Segura");
	//		a.setName("Antonio");
	//		a.setAddress("calle Arahal");
	//		a.setEmail("antArahal@hotmail.com");
	//		a.setPhone("654321231");
	//		uaAdmin.setUsername("adminCris");
	//		uaAdmin.setPassword("adminCris");
	//		a.setUserAccount(uaAdmin);
	//
	//		Assert.isTrue(a.getName().equals("Antonio"), "AdminService.create() -> nombre no coincide");
	//		Assert.isTrue(a.getSurname().equals("Segura"), "AdminService.create() -> apellido no coincide");
	//		Assert.isTrue(a.getAddress().equals("calle Arahal"), "AdminService.create() -> calle no coincide");
	//		Assert.isTrue(a.getEmail().equals("antArahal@hotmail.com"), "AdminService.create() -> email no coincide");
	//		Assert.isTrue(a.getPhone().equals("654321231"), "AdminService.create() -> telefono no coincide");
	//		Assert.isTrue(a.getUserAccount().getUsername().equals("adminCris"));
	//		Assert.isTrue(a.getUserAccount().getPassword().equals("adminCris"));
	//
	//		saved = this.actorService.save(a);
	//
	//		admins = this.actorService.findAll();
	//
	//		Assert.isTrue(admins.contains(saved));
	//
	//	}

	@Test
	public void testSaveAdministrator2() {

		final Actor a = this.actorService.getActorByEmail("antonioSegura@hotmail.com");
		final Actor saved;
		final Collection<Actor> actors;

		a.setName("Nombre guardado");
		a.setPhone("654854748");
		System.out.println(a.getName());
		System.out.println(a.getPhone());
		saved = this.actorService.save(a);
		System.out.println(saved.getName());

		actors = this.actorService.findAll();

		Assert.isTrue(actors.contains(saved));

	}
}
