
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.CreditCardService;
import services.CreditCardTypeService;
import domain.CreditCard;

@Controller
@RequestMapping("/creditCard/customer,sponsor")
public class CreditCardCustomerSponsorController extends AbstractController {

	@Autowired
	private CreditCardService		creditCardS;
	@Autowired
	private ActorService			actorS;
	@Autowired
	private CreditCardTypeService	CRTService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView creditCards() {
		final ModelAndView result;
		Collection<CreditCard> creditCards;
		final UserAccount user = LoginService.getPrincipal();
		if (user.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER"))
			creditCards = this.creditCardS.getAllMyCreditCards(this.actorS.getActorLogged().getId());
		else
			creditCards = this.creditCardS.getAllMyCreditCards(this.actorS.getActorLogged().getId());
		result = new ModelAndView("creditCard/list");
		result.addObject("creditCards", creditCards);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView createCreditCard() {
		ModelAndView result;
		CreditCard newCR;
		final Collection<String> types = this.CRTService.getBrandName();

		newCR = this.creditCardS.create();
		Assert.notNull(newCR);
		result = new ModelAndView("creditCard/edit");
		result.addObject("creditCard", newCR);
		result.addObject("types", types);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			//this.creditCardS.save(creditCard);
			//result = new ModelAndView("redirect:list.do");
			result = new ModelAndView("creditCard/edit");
			result.addObject("creditCard", creditCard);

		} else {
			//result = new ModelAndView("creditCard/edit");
			this.creditCardS.save(creditCard);
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
}
