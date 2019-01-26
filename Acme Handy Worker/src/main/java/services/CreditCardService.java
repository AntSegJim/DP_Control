
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import security.UserAccount;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private CreditCardTypeService	CRTypeS;


	public Collection<CreditCard> findAll() {
		return this.creditCardRepository.findAll();
	}
	//Metodo create
	public CreditCard create() {
		final CreditCard cc = new CreditCard();
		cc.setBrandName("");
		cc.setHolderName("");
		cc.setNumber(0);
		cc.setExpirationMonth(0);
		cc.setExpirationYear(0);
		cc.setCW(0);
		cc.setActor(this.actorService.getActorLogged());
		return cc;
	}
	public CreditCard save(final CreditCard cc) {
		final Collection<String> brandNames = this.CRTypeS.getBrandName();
		final Collection<Integer> creditCardsNumbers = this.getAllNumbers();
		final UserAccount user = this.actorService.getActorLogged().getUserAccount();
		Assert.isTrue(brandNames.contains(cc.getBrandName()));
		Assert.isTrue(!creditCardsNumbers.contains(cc.getNumber()));
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER") || user.getAuthorities().iterator().next().getAuthority().equals("SPONSOR"));
		Assert.isTrue(cc != null && cc.getBrandName() != null && cc.getHolderName() != null && cc.getBrandName() != "" && cc.getHolderName() != "");
		return this.creditCardRepository.save(cc);

	}
	public Collection<CreditCard> getAllMyCreditCards(final int actorId) {
		return this.creditCardRepository.getAllMyCreditCards(actorId);
	}

	public Collection<Integer> getAllNumbers() {
		return this.creditCardRepository.getAllNumberCreditCards();
	}
}
