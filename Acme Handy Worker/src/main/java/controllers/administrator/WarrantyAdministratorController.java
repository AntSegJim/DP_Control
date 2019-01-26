
package controllers.administrator;

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

import services.WarrantyService;
import controllers.AbstractController;
import domain.Warranty;

@Controller
@RequestMapping("/warranty/administrator")
public class WarrantyAdministratorController extends AbstractController {

	@Autowired
	private WarrantyService	warrantyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Warranty> warranties;

		warranties = this.warrantyService.findAll();

		result = new ModelAndView("warranty/list");
		result.addObject("requestURI", "warranty/administrator/list.do");
		result.addObject("warranties", warranties);
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int warrantyId) {
		final ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);
		Assert.notNull(warrantyId);
		result = new ModelAndView("warranty/show");
		result.addObject("warranty", warranty);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int warrantyId) {
		ModelAndView result;
		Warranty warranty;
		try {
			warranty = this.warrantyService.findOne(warrantyId);
			Assert.notNull(warranty);
			result = new ModelAndView("warranty/edit");
			result.addObject("warranty", warranty);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Warranty newWarranty, final BindingResult binding) {
		final ModelAndView result;

		if (!binding.hasErrors()) {
			this.warrantyService.save(newWarranty);
			result = new ModelAndView("redirect:list.do");
		} else {
			//result = new ModelAndView("redirect:edit.do?warrantyId=" + newWarranty.getId());
			result = new ModelAndView("warranty/edit");
			result.addObject("warranty", newWarranty);
		}

		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int warrantyId) {
		ModelAndView result;
		final Warranty w = this.warrantyService.findOne(warrantyId);
		try {
			this.warrantyService.delete(w);
			result = new ModelAndView("redirect:list.do");
			return result;
		} catch (final Exception e) {
			result = new ModelAndView("warranty/list");
			result.addObject("exception", e);
			result.addObject("warranties", this.warrantyService.findAll());
			return result;
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.create();
		result = new ModelAndView("warranty/create");
		result.addObject("warranty", warranty);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final Warranty newWarranty, final BindingResult binding, @RequestParam("laws") final String leyes, @RequestParam("terms") final String terminos) {
		final ModelAndView result;

		if (!binding.hasErrors()) {

			this.warrantyService.save(newWarranty);
			result = new ModelAndView("redirect:list.do");
		} else {
			result = new ModelAndView("warranty/create");
			result.addObject("warranty", newWarranty);
		}

		return result;

	}
}
