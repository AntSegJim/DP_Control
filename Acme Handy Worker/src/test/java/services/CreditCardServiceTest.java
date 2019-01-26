
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
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	@Autowired
	private CreditCardService	CCService;
	@Autowired
	private ActorService		actorService;


	@Test
	public void testCreateCreditCard() {
		CreditCard creditCard;
		creditCard = this.CCService.create();

		creditCard.setBrandName("VISA");
		creditCard.setHolderName("raul");
		creditCard.setNumber(101);
		creditCard.setExpirationMonth(2);
		creditCard.setExpirationYear(2019);
		creditCard.setCW(201);
		creditCard.setActor(this.actorService.getActorLogged());
		Assert.isTrue(creditCard.getBrandName().equals("VISA") && creditCard.getHolderName().equals("raul") && creditCard.getNumber() == 101 && creditCard.getExpirationMonth() == 2 && creditCard.getExpirationYear() == 2019 && creditCard.getCW() == 201);
	}

	@Test
	public void testSaveCreditCard() {
		super.authenticate("customer2");
		CreditCard creditCard, saved;
		Collection<CreditCard> creditCards;
		creditCard = this.CCService.create();

		creditCard.setBrandName("VISA");
		creditCard.setHolderName("antonio");
		creditCard.setNumber(102);
		creditCard.setExpirationMonth(3);
		creditCard.setExpirationYear(2019);
		creditCard.setCW(202);
		creditCard.setActor(this.actorService.getActorLogged());
		saved = this.CCService.save(creditCard);
		creditCards = this.CCService.findAll();
		Assert.isTrue(creditCards.contains(saved));
		super.authenticate(null);
	}

}
