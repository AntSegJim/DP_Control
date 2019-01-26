
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

import services.SectionService;
import services.TutorialService;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section/handyWorker")
public class SectionHandyWorkerController extends AbstractController {

	@Autowired
	private TutorialService	tutorialService;
	@Autowired
	private SectionService	sectionService;


	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("section/action-2");

		return result;
	}

	@RequestMapping(value = "/showSections", method = RequestMethod.GET)
	public ModelAndView showSections(@RequestParam final int tutorialId) {
		final ModelAndView result;
		Tutorial t;
		Collection<Section> sections;

		t = this.tutorialService.findOne(tutorialId);
		sections = t.getSection();

		result = new ModelAndView("section/showSections");
		result.addObject("sections", sections);

		return result;
	}

	@RequestMapping(value = "/editSection", method = RequestMethod.GET)
	public ModelAndView editSection(@RequestParam final int sectionId) {
		ModelAndView result;
		Section section;

		section = this.sectionService.findOne(sectionId);
		Assert.notNull(section);
		result = new ModelAndView("section/editSection");
		result.addObject("section", section);
		return result;
	}

	@RequestMapping(value = "/editSection", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Section section, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = new ModelAndView("section/editSection");
		else
			try {
				this.sectionService.save(section);
				result = new ModelAndView("redirect:action-2.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("section/editSection");
			}
		return result;
	}
}
