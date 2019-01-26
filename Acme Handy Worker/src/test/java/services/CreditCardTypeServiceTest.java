
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
import domain.CreditCardType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CreditCardTypeServiceTest extends AbstractTest {

	@Autowired
	private CreditCardTypeService	creditCardTypeService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateCreditCardType() {
		CreditCardType c;
		c = this.creditCardTypeService.create();
		c.setBrandName("TARJETA");
		Assert.isTrue(c.getBrandName().equals("TARJETA"));
	}

	@Test
	public void testSaveCreditCardType() {
		CreditCardType c, saved;
		Collection<CreditCardType> cs;
		c = this.creditCardTypeService.create();
		c.setBrandName("TARJETA");

		saved = this.creditCardTypeService.save(c);
		cs = this.creditCardTypeService.findAll();
		Assert.isTrue(cs.contains(saved));
	}
	@Test
	public void testSaveCreditCardTypeMal() {
		CreditCardType c, saved = null;
		final Collection<CreditCardType> cs;
		c = this.creditCardTypeService.create();
		c.setBrandName("VISA");
		try {
			saved = this.creditCardTypeService.save(c);
		} catch (final Exception e) {
			cs = this.creditCardTypeService.findAll();
			Assert.isTrue(cs.contains(saved));
		}
	}

	@Test
	public void testDeleteCreditCardType() {
		CreditCardType c, saved;
		final Collection<CreditCardType> cs;
		c = this.creditCardTypeService.create();
		c.setBrandName("TARJETA");
		saved = this.creditCardTypeService.save(c);
		this.creditCardTypeService.delete(saved);
		cs = this.creditCardTypeService.findAll();
		Assert.isTrue(!cs.contains(saved));
	}
}
