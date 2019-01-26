
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RefereeService;
import domain.Referee;

@Controller
@RequestMapping("/referee/administrator")
public class RefereeAdministratorController {

	@Autowired
	private RefereeService	refereeService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView action1() {
		ModelAndView result;
		final Referee referee;

		referee = this.refereeService.create();

		result = new ModelAndView("referee/create");
		result.addObject("referee", referee);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Referee referee, final BindingResult binding) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				this.refereeService.save(referee);
				result = new ModelAndView("redirect:action-2.do");
			} else {
				result = new ModelAndView("referee/create");
				result.addObject("referee", referee);
			}
		} catch (final Exception e) {
			result = new ModelAndView("referee/create");
			result.addObject("exception", e);
			result.addObject("referee", referee);
		}

		return result;
	}
	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}

}
