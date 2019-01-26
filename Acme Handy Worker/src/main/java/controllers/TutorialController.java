
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.HandyWorkerService;
import services.SectionService;
import services.SponsorshipService;
import services.TutorialService;
import domain.HandyWorker;
import domain.Picture;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private SectionService		sectionS;
	@Autowired
	private SponsorshipService	sponsorshipS;


	@RequestMapping(value = "/handyWorker/tutorials", method = RequestMethod.GET)
	public ModelAndView tutorials() {
		final ModelAndView result;
		final Collection<Tutorial> tutorials;
		final UserAccount user = LoginService.getPrincipal();
		final HandyWorker h = this.handyWorkerService.handyWorkerUserAccount(user.getId());

		tutorials = this.tutorialService.getTutorialsByHandyWorker(h.getId());

		result = new ModelAndView("tutorial/tutorials");
		result.addObject("tutorials", tutorials);

		return result;
	}

	@RequestMapping(value = "/handyWorker/showTutorial", method = RequestMethod.GET)
	public ModelAndView showTutorial(@RequestParam final int tutorialId) {
		final ModelAndView result;
		Tutorial tutorial;
		tutorial = this.tutorialService.findOne(tutorialId);

		result = new ModelAndView("tutorial/showTutorial");
		result.addObject("tutorial", tutorial);
		return result;
	}

	@RequestMapping(value = "/handyWorker/createTutorial", method = RequestMethod.GET)
	public ModelAndView createTutorial() {
		final ModelAndView result;
		final Tutorial tutorial;
		final Collection<Picture> pictures;
		final Collection<Section> sections;
		final Collection<Sponsorship> sponsorships;

		sections = this.sectionS.findAll();
		sponsorships = this.sponsorshipS.findAll();
		tutorial = this.tutorialService.create();

		result = new ModelAndView("tutorial/editTutorial");
		result.addObject("tutorial", tutorial);

		result.addObject("sections", sections);
		result.addObject("sponsorships", sponsorships);

		return result;
	}
	@RequestMapping(value = "/handyWorker/editTutorial", method = RequestMethod.GET)
	public ModelAndView editTutorial(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);
		result = new ModelAndView("tutorial/editTutorial");
		result.addObject("tutorial", tutorial);
		return result;
	}
	@RequestMapping(value = "/handyWorker/editTutorial", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;
		if (!binding.hasErrors()) {
			this.tutorialService.save(tutorial);
			result = new ModelAndView("redirect:tutorials.do");
		} else {
			result = new ModelAndView("tutorial/editTutorial");
			result.addObject("tutorial", tutorial);
		}
		return result;
	}

	@RequestMapping(value = "handyWorker/editTutotial", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteTutorial(final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;
		try {
			this.tutorialService.delete(tutorial);
			result = new ModelAndView("redirect:tutorials.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("tutorial/editTutorial");
			result.addObject("tutorial", tutorial);
		}
		return result;

	}

	//No logeado
	@RequestMapping(value = "/AllTutorials", method = RequestMethod.GET)
	public ModelAndView allTutorials() {
		final ModelAndView result;
		final Collection<Tutorial> tutorials;

		tutorials = this.tutorialService.findAll();

		result = new ModelAndView("tutorial/AllTutorials");
		result.addObject("tutorials", tutorials);

		return result;
	}

	@RequestMapping(value = "/showTutorialHW", method = RequestMethod.GET)
	public ModelAndView showTutorialHW(@RequestParam final int handyWorkerId) {
		final ModelAndView result;
		HandyWorker h;

		h = this.handyWorkerService.findOne(handyWorkerId);

		result = new ModelAndView("tutorial/showTutorialHW");
		result.addObject("h", h);

		return result;
	}

	@RequestMapping(value = "/showTutorialSectionHW", method = RequestMethod.GET)
	public ModelAndView showTutorialSectionHW(@RequestParam final int tutorialId) {
		final ModelAndView result;
		Tutorial t;
		Collection<Section> sections;

		t = this.tutorialService.findOne(tutorialId);
		sections = t.getSection();

		result = new ModelAndView("tutorial/showTutorialSectionHW");
		result.addObject("sections", sections);

		return result;
	}

	@RequestMapping(value = "/showTutorialSponsorshipHW", method = RequestMethod.GET)
	public ModelAndView showTutorialSponsorshipHW(@RequestParam final int tutorialId) {
		final ModelAndView result;
		Tutorial t;
		Collection<Sponsorship> sponsorships;

		t = this.tutorialService.findOne(tutorialId);
		sponsorships = t.getSponsorship();

		result = new ModelAndView("tutorial/showTutorialSponsorshipHW");
		result.addObject("sponsorships", sponsorships);

		return result;
	}
}
