
package controllers.customer;

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

import services.CategoryService;
import services.CustomizableSystemService;
import services.FixUpTaskService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Category;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/fix-up-task/customer")
public class FixUpTaskCustomerController extends AbstractController {

	@Autowired
	private FixUpTaskService			fixUpTaskService;
	@Autowired
	private CategoryService				categoryService;
	@Autowired
	private WarrantyService				warrantyService;
	@Autowired
	private CustomizableSystemService	customizableSystem;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "exception", required = false, defaultValue = "-2") final Integer e) {
		final ModelAndView result;
		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskService.findAllCustomer();

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fix-up-task/customer/list.do");

		if (e == -1)
			result.addObject("exception", e);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final FixUpTask fixUpTask = this.fixUpTaskService.create();
		result = new ModelAndView("fixUpTask/create");
		result.addObject("fixUpTask", fixUpTask);

		final Collection<Category> categories = this.categoryService.findAll();
		result.addObject("categories", categories);

		final Collection<Warranty> warranties = this.warrantyService.findAllNotDraftMode();
		result.addObject("warranties", warranties);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;
		try {
			fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
			Assert.notNull(fixUpTask);
			result = new ModelAndView("fixUpTask/edit");
			result.addObject("fixUpTask", fixUpTask);
			final Collection<Category> categories = this.categoryService.findAll();
			result.addObject("categories", categories);

			final Collection<Warranty> warranties = this.warrantyService.findAllNotDraftMode();
			result.addObject("warranties", warranties);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView actionSearch(@Valid final FixUpTask newf, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			this.fixUpTaskService.save(newf);
			result = new ModelAndView("redirect:list.do");
		} else {
			result = new ModelAndView("fixUpTask/create");
			result.addObject("fixUpTask", newf);
			final Collection<Category> categories = this.categoryService.findAll();
			result.addObject("categories", categories);

			final Collection<Warranty> warranties = this.warrantyService.findAllNotDraftMode();
			result.addObject("warranties", warranties);
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;
		try {
			fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
			Assert.notNull(fixUpTask);
			result = new ModelAndView("fixUpTask/show");
			result.addObject("fixUpTask", fixUpTask);
			result.addObject("IVA", this.customizableSystem.getIVA() * fixUpTask.getMaximunPrice());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do?exception=-1");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		try {
			final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
			Assert.notNull(fixUpTask);
			this.fixUpTaskService.delete(fixUpTask);
			result = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do?exception=-1");
		}
		return result;
	}

}
