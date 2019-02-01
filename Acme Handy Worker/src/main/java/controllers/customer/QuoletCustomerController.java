
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import services.QuoletService;
import controllers.AbstractController;
import domain.FixUpTask;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/customer")
public class QuoletCustomerController extends AbstractController {

	@Autowired
	private QuoletService		quoletService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		final Collection<Quolet> quolets;

		final FixUpTask f = this.fixUpTaskService.findOne(fixUpTaskId);
		result = new ModelAndView("quolet/list");
		if (f != null) {
			quolets = this.quoletService.findAllQuoletsByFix(fixUpTaskId);
			result.addObject("requestURI", "quolet/customer/list.do");
			result.addObject("quolets", quolets);
			result.addObject("fixUpTaskId", fixUpTaskId);
			result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		} else
			result.addObject("error", "Error");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		final Quolet q = this.quoletService.create();
		q.setFixUpTask(this.fixUpTaskService.findOne(fixUpTaskId));
		result = new ModelAndView("quolet/create");
		result.addObject("quolet", q);
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final Quolet newQuolet, final BindingResult binding) {
		final ModelAndView result;

		if (!binding.hasErrors()) {
			this.quoletService.save(newQuolet);
			result = new ModelAndView("redirect:list.do?fixUpTaskId=" + newQuolet.getFixUpTask().getId());
		} else {
			result = new ModelAndView("quolet/create");
			result.addObject("quolet, newQuolet");
		}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int quoletId) {
		final ModelAndView result;
		Quolet q;

		q = this.quoletService.findOne(quoletId);
		Assert.notNull(quoletId);
		result = new ModelAndView("quolet/show");
		result.addObject("quolet", q);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int quoletId) {
		ModelAndView result;
		final Quolet w = this.quoletService.findOne(quoletId);
		try {
			this.quoletService.delete(w);
			result = new ModelAndView("redirect:list.do?fixUpTaskId=" + w.getFixUpTask().getId());
			return result;
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do?fixUpTaskId=" + w.getFixUpTask().getId());
			return result;
		}
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int quoletId) {
		ModelAndView result;
		Quolet q;
		try {
			q = this.quoletService.findOne(quoletId);
			Assert.notNull(q);
			result = new ModelAndView("quolet/edit");
			result.addObject("quolet", q);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Quolet newQuolet, final BindingResult binding) {
		final ModelAndView result;

		if (!binding.hasErrors()) {
			this.quoletService.save(newQuolet);
			result = new ModelAndView("redirect:list.do");
		} else {
			result = new ModelAndView("quolet/edit");
			result.addObject("quolet", newQuolet);
		}

		return result;

	}
}
