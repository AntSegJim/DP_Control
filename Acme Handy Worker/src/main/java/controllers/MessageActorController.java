
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

import services.MessageService;
import domain.Message;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController {

	@Autowired
	private MessageService	messageService;


	public MessageActorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMessage(@RequestParam final int messageBoxId) {
		final ModelAndView result;
		Collection<Message> messages;

		messages = this.messageService.getMessageByBox(messageBoxId);
		Assert.notNull(messages);

		result = new ModelAndView("messages/list");
		result.addObject("messages", messages);
		result.addObject("Uri", "message/actor/list.do?messageBoxId=" + messageBoxId);
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showMessage(@RequestParam final int messageId) {
		ModelAndView result;
		Message mensaje;

		mensaje = this.messageService.findOne(messageId);
		Assert.notNull(mensaje);

		result = new ModelAndView("mensaje/show");
		result.addObject("mensaje", mensaje);

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView createMessage() {
		final ModelAndView result;
		final Message mensaje;
		mensaje = this.messageService.create();

		result = new ModelAndView("mensaje/send");
		result.addObject("newMessage", mensaje);
		return result;

	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView sendMessage(@Valid final Message mensaje, final BindingResult binding) {
		final ModelAndView result;
		if (!binding.hasErrors()) {
			final Message saved = this.messageService.save(mensaje);
			this.messageService.sendMessage(saved);
			result = new ModelAndView("redirect:show.do?messageId=" + saved.getId());
		} else {
			result = new ModelAndView("mensaje/send");
			result.addObject("newMessage", mensaje);
		}
		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "broadcast")
	public ModelAndView sendMessageBroadcast(@Valid final Message mensaje, final BindingResult binding) {
		ModelAndView result;
		if (!binding.hasErrors()) {
			final Message saved = this.messageService.save(mensaje);
			this.messageService.sendBroadcastMessage(saved);
			result = new ModelAndView("redirect:show.do?messageId=" + saved.getId());
		} else {
			result = new ModelAndView("mensaje/send");
			result.addObject("newMessage", mensaje);
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteMessage(@RequestParam final Integer idMessage) {
		ModelAndView result;

		this.messageService.delete(this.messageService.findOne(idMessage));
		result = new ModelAndView("redirect:http://localhost:8080/Acme-Handy-Worker/messageBox/actor/list.do");

		return result;

	}

}
