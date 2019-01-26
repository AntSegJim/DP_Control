
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
import domain.CustomizableSystem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomizableSystemServiceTest extends AbstractTest {

	@Autowired
	private CustomizableSystemService	customizableSystemService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateCustomizableSystem() {
		CustomizableSystem customizableSystem;
		customizableSystem = this.customizableSystemService.create();
		customizableSystem.setName("Acme Handy Worker");
		customizableSystem.setBanner("https://web.whatsapp.com/");
		customizableSystem.setMessageWelcomePage("Bienvenido a nuestra página");
		customizableSystem.setVATPercentage("21%");
		customizableSystem.setTelephoneCode("+34");

		Assert.isTrue(customizableSystem.getName() != null);
		Assert.isTrue(customizableSystem.getBanner() != null);
		Assert.isTrue(customizableSystem.getMessageWelcomePage() != null);
		Assert.isTrue(customizableSystem.getVATPercentage() != null);
		Assert.isTrue(customizableSystem.getTelephoneCode() != null);

	}

	@Test
	public void testSaveCustomizableSystem() {
		super.authenticate("admin");
		CustomizableSystem customizableSystem, saved;
		Collection<CustomizableSystem> customizableSystems;

		customizableSystem = this.customizableSystemService.create();
		customizableSystem.setName("Acme Handy Worker");
		customizableSystem.setBanner("https://web.whatsapp.com/");
		customizableSystem.setMessageWelcomePage("Bienvenido a nuestra página");
		customizableSystem.setVATPercentage("21%");
		customizableSystem.setTelephoneCode("+34");

		saved = this.customizableSystemService.save(customizableSystem);
		customizableSystems = this.customizableSystemService.findAll();
		Assert.isTrue(customizableSystems.contains(saved));
		super.authenticate(null);
	}

}
