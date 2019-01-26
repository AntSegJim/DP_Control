
package controllers.handyWorker;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CategoryService;
import services.CustomizableFinderService;
import services.FinderService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Category;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Warranty;

@Controller
@RequestMapping("/finder/handy-worker")
public class FinderHandyWorkerController extends AbstractController {

	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private FinderService				finderService;
	@Autowired
	private CategoryService				categoryService;
	@Autowired
	private WarrantyService				warrantyService;
	@Autowired
	private FixUpTaskService			fixUpTaskService;
	@Autowired
	private CustomizableFinderService	customizableFinderService;


	// Constructors -----------------------------------------------------------

	public FinderHandyWorkerController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	//MUESTRA EL FILTRO DEL FINDER
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		final ModelAndView result;
		final Integer id_user = LoginService.getPrincipal().getId();
		final HandyWorker handyWorker = this.handyWorkerService.handyWorkerUserAccount(id_user);
		final Finder finder = handyWorker.getFinder();

		result = new ModelAndView("finder/show");
		result.addObject("finder", finder);

		final Collection<Category> categories = this.categoryService.findAll();
		result.addObject("categories", categories);

		final Collection<Warranty> warranties = this.warrantyService.findAllNotDraftMode();
		result.addObject("warranties", warranties);

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "search")
	public ModelAndView actionSearch(@Valid final Finder newf, final BindingResult binding) {
		ModelAndView result;

		try {
			newf.setFixUpTask(this.buscarFixUpTask(newf));
			newf.setMoment(new Date());
			this.finderService.save(newf);
			result = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			result = new ModelAndView("finder/show");
			result.addObject("finder", newf);
			result.addObject("exception", e);
		}
		return result;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listFixUpTaskOfTheResults() {
		final ModelAndView result;
		result = new ModelAndView("finder/results");

		final Integer id_user = LoginService.getPrincipal().getId();
		final HandyWorker handyWorker = this.handyWorkerService.handyWorkerUserAccount(id_user);
		final Finder finder = handyWorker.getFinder();

		if ((new Date().getTime() - finder.getMoment().getTime()) / 3600000 > this.customizableFinderService.getValues().getTimeCache())
			try {
				finder.setFixUpTask(this.buscarFixUpTask(finder));
				finder.setMoment(new Date());
				this.finderService.save(finder);
			} catch (final Exception e) {
				result.addObject("exception", e);
			}

		result.addObject("requestURI", "finder/handy-worker/list.do");
		result.addObject("fixUpTasks", finder.getFixUpTask());
		return result;
	}

	private Collection<FixUpTask> buscarFixUpTask(final Finder finder) {
		String categoryName = "";
		if (finder.getCategory() != null)
			categoryName = finder.getCategory().getName();
		String warrantyTitle = "";
		if (finder.getWarranty() != null)
			warrantyTitle = finder.getWarranty().getTitle();

		Date fechaInicio = new Date(0);
		if (finder.getStartDate() != null)
			fechaInicio = finder.getStartDate();
		Date fechaFin = new Date();
		if (finder.getEndDate() != null)
			fechaFin = finder.getEndDate();

		Double precioMinimo = 0.0;
		if (finder.getLowPrice() != null)
			precioMinimo = finder.getLowPrice();
		Double precioMaximo = Double.MAX_VALUE;
		if (finder.getHighPrice() != null)
			precioMaximo = finder.getHighPrice();

		return this.fixUpTaskService.filterFixUpTask(finder.getTicker(), finder.getDescription(), finder.getAddress(), fechaInicio, fechaFin, precioMinimo, precioMaximo, categoryName, warrantyTitle);

	}
}
