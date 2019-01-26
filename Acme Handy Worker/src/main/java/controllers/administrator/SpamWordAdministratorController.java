
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SpamWordService;
import controllers.AbstractController;
import domain.SpamWord;

@Controller
@RequestMapping("/spam-word/administrator")
public class SpamWordAdministratorController extends AbstractController {

	@Autowired
	private SpamWordService	spamWordService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<SpamWord> spamWords;

		spamWords = this.spamWordService.findAll();
		final SpamWord newSpamWord = this.spamWordService.create();
		result = new ModelAndView("spamWord/list");
		result.addObject("requestURI", "spam-word/administrator/list.do");
		result.addObject("spamWords", spamWords);
		result.addObject("spamWord", newSpamWord);
		return result;

	}
	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SpamWord spamWord, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			this.spamWordService.save(spamWord);
			result = new ModelAndView("redirect:list.do");
		} else {
			final Collection<SpamWord> sws = this.spamWordService.findAll();
			result = new ModelAndView("spamWord/list");
			result.addObject("requestURI", "spam-word/administrator/list.do");
			result.addObject("spamWord", spamWord);
			result.addObject("spamWords", sws);
		}
		return result;
	}
}
