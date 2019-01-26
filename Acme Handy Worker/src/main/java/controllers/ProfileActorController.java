
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ProfileSocialNetworkService;
import domain.Actor;
import domain.ProfileSocialNetwork;

@Controller
@RequestMapping("/profileSocial/actor")
public class ProfileActorController {

	@Autowired
	private ProfileSocialNetworkService	profileService;

	@Autowired
	private ActorService				actorService;


	// Action-1 ---------------------------------------------------------------		

	//MUESTRA LAS CAJAS DE MENSAJE DE UN ACTOR
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView action1() {
		final ModelAndView result;
		final Collection<ProfileSocialNetwork> profiles;
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		profiles = this.profileService.getProfilesByActor(a.getId());
		//
		result = new ModelAndView("profiles/list");
		result.addObject("profiles", profiles);
		//
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		final ProfileSocialNetwork p = this.profileService.create();

		result = new ModelAndView("profile/create");
		result.addObject("newProfile", p);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idProfile) {

		ModelAndView result;

		final ProfileSocialNetwork p = this.profileService.findOne(idProfile);

		result = new ModelAndView("profile/show");
		result.addObject("newProfile", p);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final ProfileSocialNetwork profile, final BindingResult binding) {

		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				this.profileService.save(profile);
				this.profileService.UpdateProperty();
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("profile/show");
				result.addObject("newProfile", profile);
			}
		} catch (final Exception e) {
			result = new ModelAndView("profile/show");
			result.addObject("newProfile", profile);
			result.addObject("exception", e);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProfileSocialNetwork profile, final BindingResult binding) {

		ModelAndView result;

		try {
			this.profileService.delete(profile);
			this.profileService.UpdateProperty();
			result = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			result = new ModelAndView("profile/show");
			result.addObject("newProfile", profile);
			result.addObject("exception", e);
		}
		return result;
	}

}
