
package controllers.administrator;

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

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService	categoryService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Category> categories;

		categories = this.categoryService.findAll();
		final String lang = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("language", lang);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Category category;
		final Collection<Category> categories;

		categories = this.categoryService.findAll();

		category = this.categoryService.create();

		result = new ModelAndView("category/create");
		result.addObject("category", category);
		result.addObject("categories", categories);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		final String lang = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("category/show");
		result.addObject("category", category);
		result.addObject("language", lang);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;
		final Collection<Category> categories;

		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);

		categories = this.categoryService.findAll();
		categories.remove(category);
		categories.removeAll(category.getSoon());

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("categories", categories);

		return result;
	}

	@RequestMapping(value = "/editByName", method = RequestMethod.GET)
	public ModelAndView edit2(@RequestParam final String nameCategory) {
		ModelAndView result;
		Category category;
		final Collection<Category> categories;

		category = this.categoryService.categoryByName(nameCategory);
		Assert.notNull(category);

		categories = this.categoryService.findAll();
		categories.remove(category);
		categories.removeAll(category.getSoon());

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("categories", categories);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");

			} else {
				result = new ModelAndView("category/edit");
				result.addObject("category", category);
				result.addObject("categories", this.categoryService.findAll());
			}
		} catch (final Exception e) {
			result = new ModelAndView("category/edit");
			result.addObject("category", category);
			result.addObject("categories", this.categoryService.findAll());
			result.addObject("exception", e);

		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Category category, final BindingResult binding) {
		ModelAndView result;

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
			result.addObject("categories", this.categoryService.findAll());
		} catch (final Exception e) {
			result = new ModelAndView("category/edit");
			result.addObject("category", category);
			final Collection<Category> categories = this.categoryService.findAll();
			result.addObject("categories", categories);
			result.addObject("exception", e);
		}
		return result;
	}

}
