
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
import domain.CustomizableFinder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomizableFinderServiceTest extends AbstractTest {

	@Autowired
	private CustomizableFinderService	customizableFinderService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateCustomizableFinder() {
		CustomizableFinder customizableFinder;
		customizableFinder = this.customizableFinderService.create();
		customizableFinder.setResultNumber(13);
		customizableFinder.setTimeCache(13);
		Assert.isTrue(customizableFinder.getResultNumber() >= 10 && customizableFinder.getResultNumber() <= 100);
		Assert.isTrue(customizableFinder.getTimeCache() >= 1 && customizableFinder.getTimeCache() <= 24);

	}

	@Test
	public void testSaveCustomizableFinder() {
		super.authenticate("admin1");
		CustomizableFinder customizableFinder, saved;
		final Collection<CustomizableFinder> customizableFinders;
		customizableFinder = this.customizableFinderService.create();
		customizableFinder.setResultNumber(13);
		customizableFinder.setTimeCache(13);

		saved = this.customizableFinderService.save(customizableFinder);
		customizableFinders = this.customizableFinderService.findAll();

		Assert.isTrue(customizableFinders.contains(saved));
		super.authenticate(null);
	}
}
