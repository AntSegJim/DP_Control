
package controllers.handyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
@RequestMapping("/quolet/handy-worker")
public class QuoletHandyWorkerController extends AbstractController {

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
			quolets = this.quoletService.findAllQuoletsByFixPublished(fixUpTaskId);
			result.addObject("requestURI", "quolet/handy-worker/list.do");
			result.addObject("quolets", quolets);
			result.addObject("fixUpTaskId", fixUpTaskId);
		} else
			result.addObject("error", "Error");

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
		return result;
	}

}
