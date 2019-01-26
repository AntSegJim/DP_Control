/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.ReportService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	//DASHBOARD
	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private ReportService			reportService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView action1() {
		ModelAndView result;
		final Administrator administrator;

		administrator = this.administratorService.create();

		result = new ModelAndView("administrator/create");
		result.addObject("administrator", administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:action-2.do");
			} else {
				result = new ModelAndView("administrator/create");
				result.addObject("administrator", administrator);
			}
		} catch (final Exception e) {
			result = new ModelAndView("administrator/create");
			result.addObject("exception", e);
			result.addObject("administrator", administrator);
		}

		return result;
	}

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		final List<Object[]> fixUpTask = this.fixUpTaskService.maxMinAvgDevFixUpTask();
		final Double fixUpAvg = (Double) fixUpTask.get(0)[0];
		final Double fixUpMin = (Double) fixUpTask.get(0)[1];
		final Double fixUpMax = (Double) fixUpTask.get(0)[2];
		final Double fixUpDesv = (Double) fixUpTask.get(0)[3];

		final List<Object[]> fixUpTaskApp = this.fixUpTaskService.maxMinAvgDevFixUpTaskApp();
		final Integer fixUpAppMax = (Integer) fixUpTaskApp.get(0)[0];
		final Integer fixUpAppMin = (Integer) fixUpTaskApp.get(0)[1];
		final Double fixUpAppAvg = (Double) fixUpTaskApp.get(0)[2];
		final Double fixUpAppDesv = (Double) fixUpTaskApp.get(0)[3];

		final List<Object[]> fixUpTaskPrice = this.fixUpTaskService.maxMinAvgDesvFixUpPrice();
		final Double fixUpPriceMax = (Double) fixUpTaskPrice.get(0)[0];
		final Double fixUpPriceMin = (Double) fixUpTaskPrice.get(0)[1];
		final Double fixUpPriceAvg = (Double) fixUpTaskPrice.get(0)[2];
		final Double fixUpPriceDesv = (Double) fixUpTaskPrice.get(0)[3];

		final List<Object[]> applicationPriceOffered = this.applicationService.maxMavAvgDesvPriceOffered();
		final Double applicationPriceOfferedAvg = (Double) applicationPriceOffered.get(0)[0];
		final Double applicationPriceOfferedMin = (Double) applicationPriceOffered.get(0)[1];
		final Double applicationPriceOfferedMax = (Double) applicationPriceOffered.get(0)[2];
		final Double applicationPriceOfferedDesv = (Double) applicationPriceOffered.get(0)[3];

		final Double ratioPendingApp = this.applicationService.ratioPendingApp();
		final Double ratioAcceptedApp = this.applicationService.ratioAcceptedApp();
		final Double ratioRejectedApp = this.applicationService.ratioRejectedApp();
		final Double rationPendingAppStatus = this.applicationService.rationPendingAppStatus();

		final List<Object[]> fixUpTaskComplaint = this.fixUpTaskService.maxMinAvgDesvFixUpComplaint();
		final Double fixUpComplaintAvg = (Double) fixUpTaskComplaint.get(0)[0];
		final Double fixUpComplaintMin = (Double) fixUpTaskComplaint.get(0)[1];
		final Double fixUpComplaintMax = (Double) fixUpTaskComplaint.get(0)[2];
		final Double fixUpComplaintDesv = (Double) fixUpTaskComplaint.get(0)[3];

		final List<Object[]> reportNote = this.reportService.maxMinAvgDesv();
		final Double reportNoteAvg = (Double) reportNote.get(0)[0];
		final Double reportNoteMin = (Double) reportNote.get(0)[1];
		final Double reportNoteMax = (Double) reportNote.get(0)[2];
		final Double reportNoteDesv = (Double) reportNote.get(0)[3];

		final List<Object[]> customerFix = this.customerService.getCustomerFixUp();
		final Collection<String> handyWithMore10percent = this.handyWorkerService.handyWorkerMoreTentPercentApplicatonsAccepted3();

		result = new ModelAndView("administrator/dashboard");

		result.addObject("fixUpMax", fixUpMax);
		result.addObject("fixUpMin", fixUpMin);
		result.addObject("fixUpAvg", fixUpAvg);
		result.addObject("fixUpDesv", fixUpDesv);

		result.addObject("fixUpAppMax", fixUpAppMax);
		result.addObject("fixUpAppMin", fixUpAppMin);
		result.addObject("fixUpAppAvg", fixUpAppAvg);
		result.addObject("fixUpAppDesv", fixUpAppDesv);

		result.addObject("fixUpPriceMax", fixUpPriceMax);
		result.addObject("fixUpPriceMin", fixUpPriceMin);
		result.addObject("fixUpPriceAvg", fixUpPriceAvg);
		result.addObject("fixUpPriceDesv", fixUpPriceDesv);

		result.addObject("applicationPriceOfferedAvg", applicationPriceOfferedAvg);
		result.addObject("applicationPriceOfferedMin", applicationPriceOfferedMin);
		result.addObject("applicationPriceOfferedMax", applicationPriceOfferedMax);
		result.addObject("applicationPriceOfferedDesv", applicationPriceOfferedDesv);

		result.addObject("ratioPendingApp", ratioPendingApp);
		result.addObject("ratioAcceptedApp", ratioAcceptedApp);
		result.addObject("ratioRejectedApp", ratioRejectedApp);
		result.addObject("rationPendingAppStatus", rationPendingAppStatus);

		result.addObject("fixUpComplaintAvg", fixUpComplaintAvg);
		result.addObject("fixUpComplaintMin", fixUpComplaintMin);
		result.addObject("fixUpComplaintMax", fixUpComplaintMax);
		result.addObject("fixUpComplaintDesv", fixUpComplaintDesv);

		result.addObject("reportNoteAvg", reportNoteAvg);
		result.addObject("reportNoteMin", reportNoteMin);
		result.addObject("reportNoteMax", reportNoteMax);
		result.addObject("reportNoteDesv", reportNoteDesv);

		result.addObject("customerFix", customerFix);

		result.addObject("handyWorker10percent", handyWithMore10percent);
		return result;
	}
}
