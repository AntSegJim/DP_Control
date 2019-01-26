
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
import services.ActorService;
import services.MessageBoxService;
import services.MessageService;
import domain.Actor;
import domain.MessageBox;

@Controller
@RequestMapping("/messageBox/actor")
public class MessageBoxActorController {

	@Autowired
	private MessageBoxService	messageBoxServive;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;


	// Action-1 ---------------------------------------------------------------		

	//MUESTRA LAS CAJAS DE MENSAJE DE UN ACTOR
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView action1() {
		final ModelAndView result;
		final Collection<MessageBox> boxes;
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		boxes = this.messageBoxServive.findMessageBoxActor(a.getId());
		//
		result = new ModelAndView("messageBox/show");
		result.addObject("boxes", boxes);
		//
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MessageBox box;

		box = this.messageBoxServive.create();

		result = new ModelAndView("messageBox/create");
		result.addObject("messageBox", box);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageBoxId) {
		final ModelAndView result;
		MessageBox messageBox;

		messageBox = this.messageBoxServive.findOne(messageBoxId);
		Assert.notNull(messageBox);
		result = new ModelAndView("messageBox/edit");
		result.addObject("messageBox", messageBox);
		result.addObject("messages", this.messageService.getMessageByActor());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MessageBox messageBox, final BindingResult binding) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				this.messageBoxServive.save(messageBox);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("messageBox/edit");
				result.addObject("messageBox", messageBox);
				result.addObject("messages", this.messageService.getMessageByActor());
			}
		} catch (final Exception e) {
			result = new ModelAndView("messageBox/edit");
			result.addObject("exception", e);
			result.addObject("messageBox", messageBox);
			result.addObject("messages", this.messageService.getMessageByActor());
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MessageBox messageBox, final BindingResult binding) {
		ModelAndView result;

		try {
			this.messageBoxServive.delete(messageBox);
			result = new ModelAndView("redirect:list.do");

		} catch (final Exception e) {
			result = new ModelAndView("messageBox/edit");
			result.addObject("exception", e);
			result.addObject("messageBox", messageBox);
		}
		return result;
	}

}
