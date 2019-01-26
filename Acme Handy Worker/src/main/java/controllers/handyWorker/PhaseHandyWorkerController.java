/*
 * AnnouncementCustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyWorker;

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

import services.ApplicationService;
import services.PhaseService;
import controllers.AbstractController;
import domain.Application;
import domain.Phase;

@Controller
@RequestMapping("/phase/handyWorker")
public class PhaseHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------

	public PhaseHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listIdComplaint(@RequestParam final int applicationId) {
		final ModelAndView result;

		Collection<Phase> phases;
		final Application application = this.applicationService.findOne(applicationId);

		phases = this.phaseService.findAllApplicationId(applicationId);

		result = new ModelAndView("phase/list");
		result.addObject("requestURI", "phase/handyWorker/list.do");
		result.addObject("phases", phases);
		result.addObject("application", application);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@Valid final int applicationId) {
		ModelAndView result;
		Phase phase;

		phase = this.phaseService.create();
		phase.setApplication(this.applicationService.findOne(applicationId));
		result = this.createEditModelAndView(phase);

		return result;
	}

	//	// Edition ----------------------------------------------------------------
	//
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phaseId) {
		ModelAndView result;
		Phase phase;

		phase = this.phaseService.findOne(phaseId);
		Assert.notNull(phase);

		result = this.createEditModelAndView(phase);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			this.phaseService.save(phase);
			final Integer idApplication = phase.getApplication().getId();
			result = new ModelAndView("redirect:list.do?applicationId=" + idApplication);
		} else
			result = this.createEditModelAndView(phase);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Phase phase, final BindingResult binding) {
		ModelAndView result;

		try {
			this.phaseService.delete(phase);
			final Integer idApplication = phase.getApplication().getId();
			result = new ModelAndView("redirect:list.do?applicationId=" + idApplication);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Phase phase) {
		ModelAndView result;

		result = this.createEditModelAndView(phase, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase, final String message) {
		ModelAndView result;

		final Application application;

		application = phase.getApplication();

		result = new ModelAndView("phase/edit");
		result.addObject("phase", phase);
		result.addObject("application", application);
		result.addObject("message", message);

		return result;
	}

}
