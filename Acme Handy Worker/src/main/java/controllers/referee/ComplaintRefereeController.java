/*
 * AnnouncementCustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.referee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/referee")
public class ComplaintRefereeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;


	// Constructors -----------------------------------------------------------

	public ComplaintRefereeController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Complaint> complaints;

		complaints = this.complaintService.findAllNoReferee();

		result = new ModelAndView("complaint/List-Referee");
		result.addObject("requestURI", "complaint/referee/list.do");
		result.addObject("complaints", complaints);

		return result;
	}

	// Registration -----------------------------------------------------------

	@RequestMapping(value = "/self-assign", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int complaintId) {
		ModelAndView result;

		try {
			this.complaintService.selfAssignReferee(complaintId);
			result = this.list();
			result.addObject("message", "complaint.commit.ok");
		} catch (final Throwable oops) {
			result = this.list();
			result.addObject("message", "complaint.commit.error");
		}

		return result;
	}
	@RequestMapping(value = "/list2", method = RequestMethod.GET)
	public ModelAndView listSelfAssigne() {
		ModelAndView result;
		Collection<Complaint> complaints;

		complaints = this.complaintService.findAllByReferee();

		result = new ModelAndView("complaint/List-self-assign");
		result.addObject("requestURI", "complaint/referee/list2.do");
		result.addObject("complaints", complaints);

		return result;
	}

}
