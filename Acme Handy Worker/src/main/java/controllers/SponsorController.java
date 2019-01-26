
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	@Autowired
	private SponsorService	sponsorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView action1() {
		ModelAndView result;
		final Sponsor sponsor;

		sponsor = this.sponsorService.create();

		result = new ModelAndView("sponsor/create");
		result.addObject("sponsor", sponsor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:action-2.do");
			} else {
				result = new ModelAndView("sponsor/create");
				result.addObject("sponsor", sponsor);
			}
		} catch (final Exception e) {
			result = new ModelAndView("sponsor/create");
			result.addObject("exception", e);
			result.addObject("sponsor", sponsor);
		}

		return result;
	}

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("sponsor/action-2");

		return result;
	}

}
