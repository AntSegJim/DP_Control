/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ComplaintService;
import services.CustomerService;
import domain.Actor;
import domain.Complaint;
import domain.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	@Autowired
	private ActorService		actorS;
	@Autowired
	private CustomerService		CService;
	@Autowired
	private ComplaintService	complaintS;
	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/complaints", method = RequestMethod.GET)
	public ModelAndView complaints() {
		ModelAndView result;
		final Collection<Complaint> complaints;
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		final Customer c = this.CService.customerByUserAccount(a.getId());
		complaints = this.complaintS.findAllCustomerComplaint(c.getId());

		result = new ModelAndView("customer/complaints");
		result.addObject("complaints", complaints);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Customer customer;

		customer = this.customerService.create();

		result = new ModelAndView("customer/create");
		result.addObject("customer", customer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Customer customer, final BindingResult binding) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				this.customerService.save(customer);
				result = new ModelAndView("redirect:action-2.do");
			} else {
				result = new ModelAndView("customer/create");
				result.addObject("customer", customer);
			}
		} catch (final Exception e) {
			result = new ModelAndView("customer/create");
			result.addObject("exception", e);
			customer.getUserAccount().setPassword("");
			result.addObject("customer", customer);
		}

		return result;
	}

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("customer/action-2");

		return result;
	}

}
