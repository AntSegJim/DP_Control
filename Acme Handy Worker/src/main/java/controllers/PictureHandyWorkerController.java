
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
import services.ActorService;
import services.PictureService;
import services.TutorialService;
import domain.Actor;
import domain.Picture;
import domain.Tutorial;

@Controller
@RequestMapping("/picture/handyWorker")
public class PictureHandyWorkerController extends AbstractController {

	@Autowired
	private PictureService	pictureService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private TutorialService	tutorialService;


	//Cambiar
	//	@RequestMapping(value = "/showPicture", method = RequestMethod.GET)
	//	public ModelAndView tutorials() {
	//		final ModelAndView result;
	//		final Collection<Picture> pictures;
	//		final UserAccount user = LoginService.getPrincipal();
	//		final HandyWorker h = this.handyWorkerService.handyWorkerUserAccount(user.getId());
	//
	//		pictures = this.pictureService.picturesByHandy(h.getId());
	//
	//		result = new ModelAndView("picture/showPictures");
	//		result.addObject("pictures", pictures);
	//
	//		return result;
	//	}

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("picture/action-2");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView picturesByTutorial(@RequestParam final int idTutorial) {
		final ModelAndView result;
		final Collection<Picture> pictures;

		final Tutorial t = this.tutorialService.findOne(idTutorial);

		pictures = this.pictureService.picturesByTutorial(t.getId());

		result = new ModelAndView("picture/showPictures");
		result.addObject("pictures", pictures);

		return result;
	}

	@RequestMapping(value = "/createPicture", method = RequestMethod.GET)
	public ModelAndView createPicture() {
		ModelAndView result;
		Picture picture;
		Collection<Tutorial> tutorials;

		final Integer id = LoginService.getPrincipal().getId();
		final Actor a = this.actorService.getActorByUserAccount(id);

		tutorials = this.tutorialService.getTutorialsByHandyWorker(a.getId());

		picture = this.pictureService.create();
		result = new ModelAndView("picture/editPicture");
		result.addObject("picture", picture);
		result.addObject("tutorials", tutorials);

		return result;
	}

	@RequestMapping(value = "/editPicture", method = RequestMethod.GET)
	public ModelAndView editPicture(@RequestParam final int pictureId) {
		ModelAndView result;
		Picture picture;
		Collection<Tutorial> tutorials;

		final Integer id = LoginService.getPrincipal().getId();
		final Actor a = this.actorService.getActorByUserAccount(id);

		tutorials = this.tutorialService.getTutorialsByHandyWorker(a.getId());
		picture = this.pictureService.findOne(pictureId);
		Assert.notNull(picture);
		result = new ModelAndView("picture/editPicture");
		result.addObject("picture", picture);
		result.addObject("tutorials", tutorials);
		return result;
	}

	@RequestMapping(value = "/editPicture", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Picture picture, final BindingResult binding) {
		ModelAndView result;
		if (!binding.hasErrors()) {
			this.pictureService.save(picture);
			result = new ModelAndView("redirect:action-2.do");
		} else {
			Collection<Tutorial> tutorials;

			final Integer id = LoginService.getPrincipal().getId();
			final Actor a = this.actorService.getActorByUserAccount(id);

			tutorials = this.tutorialService.getTutorialsByHandyWorker(a.getId());

			result = new ModelAndView("picture/editPicture");
			result.addObject("picture", picture);
			result.addObject("tutorials", tutorials);
		}
		return result;
	}

	@RequestMapping(value = "/editPicture", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteTutorial(final Picture picture, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors()) {
			this.pictureService.delete(picture);
			result = new ModelAndView("redirect:action-2.do");
		} else {
			Collection<Tutorial> tutorials;

			final Integer id = LoginService.getPrincipal().getId();
			final Actor a = this.actorService.getActorByUserAccount(id);

			tutorials = this.tutorialService.getTutorialsByHandyWorker(a.getId());

			result = new ModelAndView("picture/editPicture");
			result.addObject("picture", picture);
			result.addObject("tutorials", tutorials);

		}

		return result;

	}
}
