
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipS;
	@Autowired
	private CreditCardService	creditCardS;
	@Autowired
	private SponsorService		sponsorS;


	@Test
	public void testCreateSponsorship() {
		super.authenticate("sponsor1");
		//Creamos la credit card
		CreditCard creditCard;
		creditCard = this.creditCardS.create();

		creditCard.setBrandName("VISA");
		creditCard.setHolderName("raul");
		creditCard.setNumber(101);
		creditCard.setExpirationMonth(2);
		creditCard.setExpirationYear(2019);
		creditCard.setCW(201);

		//Creamos el sponsor
		Sponsor s;
		s = this.sponsorS.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("holass");
		ua.setUsername("Antonio");
		ua.setAuthorities(s.getUserAccount().getAuthorities());

		s.setName("Antonio");
		s.setAddress("calle Arahal");
		s.setEmail("antonio@us.es");
		s.setPhone("654321123");
		s.setSurname("Segura");
		s.setUserAccount(ua);

		//Creamos el sponsorship
		Sponsorship sponsorship;
		sponsorship = this.sponsorshipS.create();
		sponsorship.setUrlBanner("htto://url.com");
		sponsorship.setLinkTargetPage("http://url2.com");
		sponsorship.setCreditCard(creditCard);
		sponsorship.setSponsor(s);

		Assert.isTrue(sponsorship.getUrlBanner().equals("htto://url.com"));
		Assert.isTrue(sponsorship.getLinkTargetPage().equals("http://url2.com"));
		Assert.isTrue(sponsorship.getCreditCard().equals(creditCard));
		Assert.isTrue(sponsorship.getSponsor().equals(s));
		super.authenticate(null);
	}

	@Test
	public void testSaveSponsorship() {
		super.authenticate("sponsor1");
		CreditCard creditCard;
		creditCard = this.creditCardS.create();

		creditCard.setBrandName("VISA");
		creditCard.setHolderName("raul");
		creditCard.setNumber(101);
		creditCard.setExpirationMonth(2);
		creditCard.setExpirationYear(2019);
		creditCard.setCW(201);
		//savedC = this.creditCardS.save(creditCard);

		Sponsor s;
		s = this.sponsorS.create();
		final UserAccount ua = new UserAccount();
		ua.setPassword("holass");
		ua.setUsername("Antonio");
		ua.setAuthorities(s.getUserAccount().getAuthorities());
		s.setName("Antonio");
		s.setAddress("calle Arahal");
		s.setEmail("antonio@us.es");
		s.setPhone("654321123");
		s.setSurname("Segura");
		s.setUserAccount(ua);
		//savedS = this.sponsorS.save(s);

		Sponsorship sponsorship, savedSS;
		Collection<Sponsorship> sponsorships = new HashSet<>();
		sponsorship = this.sponsorshipS.create();
		sponsorship.setUrlBanner("htto://url.com");
		sponsorship.setLinkTargetPage("http://url2.com");
		sponsorship.setCreditCard(creditCard);
		sponsorship.setSponsor(s);
		savedSS = this.sponsorshipS.save(sponsorship);
		sponsorships = this.sponsorshipS.findAll();
		Assert.isTrue(sponsorships.contains(savedSS));
		super.authenticate(null);
	}
}
