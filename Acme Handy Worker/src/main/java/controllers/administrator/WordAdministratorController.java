
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.WordService;
import controllers.AbstractController;
import domain.Word;

@Controller
@RequestMapping("/word/administrator")
public class WordAdministratorController extends AbstractController {

	@Autowired
	private WordService	wordService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Word> words;

		words = this.wordService.findAll();
		final Word newWord = this.wordService.create();
		result = new ModelAndView("word/list");
		result.addObject("requestURI", "word/administrator/list.do");
		result.addObject("words", words);
		result.addObject("newWord", newWord);
		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Word word, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.wordService.save(word);
				result = new ModelAndView("redirect:list.do");
			} else {
				final Collection<Word> words = this.wordService.findAll();
				result = new ModelAndView("word/list");
				result.addObject("requestURI", "word/administrator/list.do");
				result.addObject("words", words);
				result.addObject("newWord", word);
			}
		} catch (final Exception e) {
			final Collection<Word> words = this.wordService.findAll();
			result = new ModelAndView("word/list");
			result.addObject("requestURI", "word/administrator/list.do");
			result.addObject("words", words);
			result.addObject("newWord", word);
			result.addObject("exception", e);

		}
		return result;
	}

}
