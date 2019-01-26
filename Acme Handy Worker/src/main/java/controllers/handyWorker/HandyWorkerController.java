
package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import controllers.AbstractController;
import domain.HandyWorker;

@Controller
@RequestMapping("/handy-worker")
public class HandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.create();

		result = new ModelAndView("handyWorker/create");
		result.addObject("handyWorker", handyWorker);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final HandyWorker handyWorker, final BindingResult binding) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("redirect:action-2.do");
			} else {
				result = new ModelAndView("handyWorker/create");
				result.addObject("handyWorker", handyWorker);
			}
		} catch (final Exception e) {
			result = new ModelAndView("handyWorker/create");
			result.addObject("exception", e);
			handyWorker.getUserAccount().setPassword("");
			result.addObject("handyWorker", handyWorker);
		}

		return result;
	}

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("handyWorker/action-2");

		return result;
	}

}
