
package controllers;

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

import security.LoginService;
import security.UserAccount;
import services.ApplicationService;
import services.CreditCardService;
import services.CustomerService;
import services.CustomizableSystemService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/application/handyWorker,customer")
public class ApplicationHandyWorkerCustomerController extends AbstractController {

	public ApplicationHandyWorkerCustomerController() {
		super();
	}


	@Autowired
	private HandyWorkerService			HWService;
	@Autowired
	private CustomerService				customerService;
	@Autowired
	private ApplicationService			applicationS;
	@Autowired
	private CreditCardService			creditCardS;
	@Autowired
	private FixUpTaskService			fixUpTaskS;
	@Autowired
	private CustomizableSystemService	customizableSystem;


	@RequestMapping(value = "/applications", method = RequestMethod.GET)
	public ModelAndView applications() {
		final ModelAndView result;
		final Collection<Application> applications;
		final UserAccount user = LoginService.getPrincipal();
		if (user.getAuthorities().iterator().next().getAuthority().equals("HANDYWORKER")) {
			final HandyWorker h = this.HWService.handyWorkerUserAccount(user.getId());
			applications = h.getApplication();
		} else {
			final Customer c = this.customerService.customerByUserAccount(user.getId());
			applications = this.applicationS.findAllApplicationCustomer(c.getId());
		}

		result = new ModelAndView("application/applications");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/handyWorker,customer/applications.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final UserAccount user = LoginService.getPrincipal();
		final HandyWorker handyWorker = this.HWService.handyWorkerUserAccount(user.getId());
		ModelAndView result;
		Application application;

		application = this.applicationS.create();
		application.setHandyWorker(handyWorker);
		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationS.findOne(applicationId);
		Assert.notNull(application);

		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationS.save(application);
				result = new ModelAndView("redirect:applications.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}
		//		if (!binding.hasErrors()) {
		//			this.applicationS.save(application);
		//			result = new ModelAndView("redirect:applications.do");
		//		} else
		//			result = this.createEditModelAndView(application);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		try {
			application = this.applicationS.findOne(applicationId);
			Assert.notNull(application);
			result = new ModelAndView("application/show");
			result.addObject("application", application);
			result.addObject("IVA", this.customizableSystem.getIVA() * application.getPrice());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String message) {
		ModelAndView result;

		final Collection<FixUpTask> fixUpTasks;
		final Collection<FixUpTask> HandyFixUpTasks;
		final Collection<CreditCard> creditCards;
		final UserAccount user = LoginService.getPrincipal();
		if (user.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER"))
			fixUpTasks = null;
		//					final Actor customer = this.actorService.getActorByUserAccount(user.getId());
		//					creditCards = this.creditCardS.getAllMyCreditCards(customer.getId());
		else {

			fixUpTasks = this.fixUpTaskS.findAll();
			HandyFixUpTasks = this.fixUpTaskS.findAllfixUpTasksHandyWorkerId();
			fixUpTasks.removeAll(HandyFixUpTasks);
		}
		creditCards = null;
		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("creditCards", creditCards);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/loadCreditCard")
	public ModelAndView findByCertificationId(@RequestParam final Integer valorStatus) {
		ModelAndView result;

		Collection<CreditCard> creditCards;
		if (valorStatus == 0) {
			final UserAccount user = LoginService.getPrincipal();
			final Customer c = this.customerService.customerByUserAccount(user.getId());
			creditCards = this.creditCardS.getAllMyCreditCards(c.getId());
		} else
			creditCards = null;

		result = new ModelAndView("application/dropdown");
		result.addObject("creditCards", creditCards);

		return result;
	}

}
