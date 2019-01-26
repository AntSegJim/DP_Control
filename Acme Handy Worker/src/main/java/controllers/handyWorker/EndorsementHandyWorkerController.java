
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

import security.LoginService;
import services.ActorService;
import services.CustomerService;
import services.EndorsementService;
import domain.Actor;
import domain.Endorsement;

@Controller
@RequestMapping("/endorsement/handy-worker")
public class EndorsementHandyWorkerController {

	@Autowired
	private EndorsementService	endorsementService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private CustomerService		customerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final int id_user = LoginService.getPrincipal().getId();
		final Actor actor = this.actorService.getActorByUserAccount(id_user);

		final Collection<Endorsement> endorsements = this.endorsementService.myEndorsements(actor.getId());

		result = new ModelAndView("endorsement/list");
		result.addObject("myEmail", actor.getEmail());
		result.addObject("endorsements", endorsements);
		result.addObject("requestURI", "endorsement/handy-worker/list.do");

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int endorsementId) {
		ModelAndView result;
		Endorsement endorsement;
		try {
			endorsement = this.endorsementService.findOne(endorsementId);
			Assert.notNull(endorsement);
			result = new ModelAndView("endorsement/show");
			result.addObject("endorsement", endorsement);
			final int id_user = LoginService.getPrincipal().getId();
			final Actor actor = this.actorService.getActorByUserAccount(id_user);
			result.addObject("myEmail", actor.getEmail());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int endorsementId) {
		ModelAndView result;
		try {
			final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
			Assert.notNull(endorsement);
			this.endorsementService.delete(endorsement);
			result = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do?");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final int id_user = LoginService.getPrincipal().getId();
		final Actor actor = this.actorService.getActorByUserAccount(id_user);
		final Endorsement endorsement = this.endorsementService.create();
		result = new ModelAndView("endorsement/create");
		result.addObject("endorsement", endorsement);
		result.addObject("customerReceivers", this.customerService.getCustomerForWhomItIsWorked(actor.getId()));
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorsementId) {
		ModelAndView result;
		Endorsement endorsement;
		try {
			endorsement = this.endorsementService.findOne(endorsementId);
			Assert.notNull(endorsement);
			result = new ModelAndView("endorsement/edit");
			result.addObject("endorsement", endorsement);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Endorsement newEndorsement, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors())
			try {
				this.endorsementService.save(newEndorsement);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = new ModelAndView("endorsement/edit");
				result.addObject("endorsement", newEndorsement);
				result.addObject("exception", e);
			}
		else {
			result = new ModelAndView("endorsement/edit");
			result.addObject("endorsement", newEndorsement);
		}

		return result;

	}
}
