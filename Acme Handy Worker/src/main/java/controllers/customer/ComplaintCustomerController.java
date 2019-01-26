
package controllers.customer;

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
import services.ComplaintService;
import services.CustomerService;
import services.FixUpTaskService;
import services.RefereeService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Referee;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	@Autowired
	private CustomerService		customerService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private RefereeService		refereeService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	public ComplaintCustomerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView complaints() {
		ModelAndView result;
		final Collection<Complaint> complaints;
		final UserAccount user = LoginService.getPrincipal();
		final Customer c = this.customerService.customerByUserAccount(user.getId());
		complaints = this.complaintService.findAllCustomerComplaint(c.getId());

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/customer/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Complaint complaint;

		complaint = this.complaintService.create();
		result = this.createEditModelAndView(complaint);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			this.complaintService.save(complaint);

			result = new ModelAndView("redirect:list.do");
		} else
			result = this.createEditModelAndView(complaint);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String message) {
		ModelAndView result;
		final Collection<Referee> referees;
		final Collection<FixUpTask> fixUpTasks;

		referees = this.refereeService.findAll();
		fixUpTasks = this.fixUpTaskService.findAllCustomer();

		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("referees", referees);
		result.addObject("message", message);

		return result;
	}
}
