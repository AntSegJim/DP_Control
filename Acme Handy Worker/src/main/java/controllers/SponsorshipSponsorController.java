
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.CreditCardService;
import services.SponsorService;
import services.SponsorshipService;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	@Autowired
	private SponsorService		sponsorService;
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Sponsorship> sponsorships;
		final int id_user = LoginService.getPrincipal().getId();
		final Sponsor sponsor = this.sponsorService.sponsorUserAccount(id_user);
		sponsorships = this.sponsorshipService.findAllMySponsorships(sponsor.getId());

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final int actorId = this.actorService.getActorLogged().getId();
		final Sponsorship sponsorship = this.sponsorshipService.create();

		result = new ModelAndView("sponsorship/create");
		result.addObject("sponsorship", sponsorship);
		result.addObject("creditCards", this.creditCardService.getAllMyCreditCards(actorId));
		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Sponsorship newSponsorship, final BindingResult binding) {
		final ModelAndView result;

		if (!binding.hasErrors()) {
			this.sponsorshipService.save(newSponsorship);
			result = new ModelAndView("redirect:list.do");
		} else {
			result = new ModelAndView("sponsorship/create");
			result.addObject("sponsorship", newSponsorship);
			final int actorId = this.actorService.getActorLogged().getId();
			result.addObject("creditCards", this.creditCardService.getAllMyCreditCards(actorId));
		}

		return result;

	}
}
