
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

import services.ActorService;
import services.AdministratorService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping("/suspiciousActor/administrator")
public class ActorAdministratorController {

	@Autowired
	private ActorService			actorService;

	//Servicios roles
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private SponsorService			sponsorService;
	@Autowired
	private RefereeService			refereeService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listActorSuspicuous() {
		ModelAndView result;
		Collection<Actor> actors;

		actors = this.actorService.getSuspiciousActor();

		result = new ModelAndView("actorsSuspicious/list");
		result.addObject("actors", actors);

		return result;
	}

	@RequestMapping(value = "/editAdministrator", method = RequestMethod.GET)
	public ModelAndView editAdminSuspicious(@RequestParam final int idAdmin) {
		final ModelAndView result;
		Administrator admin;

		admin = this.adminService.findOne(idAdmin);
		Assert.notNull(admin);

		result = new ModelAndView("actorsSuspicious/edit");
		result.addObject("actor", admin);
		result.addObject("action", "suspiciousActor/administrator/editAdministrator.do");

		return result;

	}

	@RequestMapping(value = "/editAdministrator", method = RequestMethod.POST, params = "save")
	public ModelAndView editAdminSuspicious(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.adminService.save(admin);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("actorsSuspicious/edit");
				result.addObject("actor", admin);
				result.addObject("action", "suspiciousActor/administrator/editAdministrator.do");
			}
		} catch (final Exception e) {
			result = new ModelAndView("actorsSuspicious/edit");
			result.addObject("actor", admin);
			result.addObject("action", "suspiciousActor/administrator/editAdministrator.do");
			result.addObject("exception", e);

		}
		return result;
	}

	@RequestMapping(value = "/editCustomer", method = RequestMethod.GET)
	public ModelAndView editCustomerSuspicious(@RequestParam final int idCustomer) {
		final ModelAndView result;
		Customer customer;

		customer = this.customerService.findOne(idCustomer);
		Assert.notNull(customer);

		result = new ModelAndView("actorsSuspicious/edit");
		result.addObject("actor", customer);
		result.addObject("action", "suspiciousActor/administrator/editCustomer.do");

		return result;

	}

	@RequestMapping(value = "/editCustomer", method = RequestMethod.POST, params = "save")
	public ModelAndView editCustomerSuspicious(@Valid final Customer customer, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.customerService.save(customer);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("actorsSuspicious/edit");
				result.addObject("actor", customer);
				result.addObject("action", "suspiciousActor/administrator/editCustomer.do");
			}
		} catch (final Exception e) {
			result = new ModelAndView("actorsSuspicious/edit");
			result.addObject("actor", customer);
			result.addObject("action", "suspiciousActor/administrator/editCustomer.do");
			result.addObject("exception", e);

		}
		return result;
	}

	@RequestMapping(value = "/editHandyWorker", method = RequestMethod.GET)
	public ModelAndView editHandyWorkerSuspicious(@RequestParam final int idHandyWorker) {
		final ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findOne(idHandyWorker);
		Assert.notNull(handyWorker);

		result = new ModelAndView("actorsSuspicious/edit");
		result.addObject("actor", handyWorker);
		result.addObject("action", "suspiciousActor/administrator/editHandyWorker.do");

		return result;

	}

	@RequestMapping(value = "/editHandyWorker", method = RequestMethod.POST, params = "save")
	public ModelAndView editHandyWorkerSuspicious(@Valid final HandyWorker handyWorker, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("actorsSuspicious/edit");
				result.addObject("actor", handyWorker);
				result.addObject("action", "suspiciousActor/administrator/editHandyWorker.do");
			}
		} catch (final Exception e) {
			result = new ModelAndView("actorsSuspicious/edit");
			result.addObject("actor", handyWorker);
			result.addObject("action", "suspiciousActor/administrator/editHandyWorker.do");
			result.addObject("exception", e);

		}
		return result;
	}

	@RequestMapping(value = "/editSponsor", method = RequestMethod.GET)
	public ModelAndView editSponsorSuspicious(@RequestParam final int idSponsor) {
		final ModelAndView result;
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(idSponsor);
		Assert.notNull(sponsor);

		result = new ModelAndView("actorsSuspicious/edit");
		result.addObject("actor", sponsor);
		result.addObject("action", "suspiciousActor/administrator/editSponsor.do");

		return result;

	}

	@RequestMapping(value = "/editSponsor", method = RequestMethod.POST, params = "save")
	public ModelAndView editAdminSuspicious(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("actorsSuspicious/edit");
				result.addObject("actor", sponsor);
				result.addObject("action", "suspiciousActor/administrator/editSponsor.do");
			}
		} catch (final Exception e) {
			result = new ModelAndView("actorsSuspicious/edit");
			result.addObject("actor", sponsor);
			result.addObject("action", "suspiciousActor/administrator/editSponsor.do");
			result.addObject("exception", e);
		}
		return result;
	}

	@RequestMapping(value = "/editReferee", method = RequestMethod.GET)
	public ModelAndView editRefereeSuspicious(@RequestParam final int idReferee) {
		final ModelAndView result;
		Referee referee;

		referee = this.refereeService.findOne(idReferee);
		Assert.notNull(referee);

		result = new ModelAndView("actorsSuspicious/edit");
		result.addObject("actor", referee);
		result.addObject("action", "suspiciousActor/administrator/editReferee.do");

		return result;

	}

	@RequestMapping(value = "/editReferee", method = RequestMethod.POST, params = "save")
	public ModelAndView editAdminSuspicious(@Valid final Referee referee, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.refereeService.save(referee);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("actorsSuspicious/edit");
				result.addObject("actor", referee);
				result.addObject("action", "suspiciousActor/administrator/editReferee.do");
			}
		} catch (final Exception e) {
			result = new ModelAndView("actorsSuspicious/edit");
			result.addObject("actor", referee);
			result.addObject("action", "suspiciousActor/administrator/editReferee.do");
			result.addObject("exception", e);
		}
		return result;
	}
}
