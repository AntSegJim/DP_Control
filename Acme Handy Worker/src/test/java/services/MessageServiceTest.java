
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService		messageService;

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private ActorService		actorService;


	@Test
	public void testCreateMessage() {
		super.authenticate("customer2");
		final Message m = this.messageService.create();

		m.setSubject("Cristian");
		m.setBody("Este es un mensaje para el test");
		m.setPriority(0);
		m.setTag("tag");
		m.setReceiver(this.actorService.getActorByEmail("pablo@hotmail.com"));
		m.setEmailReceiver("cristian@hotmail.com");

		Assert.isTrue(m.getSubject().equals("Cristian") && m.getBody().equals("Este es un mensaje para el test") && m.getPriority() == 0 && m.getTag().equals("tag"));
		Assert.isTrue(m.getEmailReceiver().equals("cristian@hotmail.com"));
		super.authenticate(null);
	}
	@Test
	public void testSaveMessage() {
		super.authenticate("customer2");
		Message saved;
		Collection<Message> messages;

		Message m;
		m = this.messageService.create();
		m.setSubject("Cristian");
		m.setBody("Este es un mensaje para el test");
		m.setPriority(0);
		m.setTag("tag");
		m.setEmailReceiver("cristian@hotmail.com");

		saved = this.messageService.save(m);
		messages = this.messageService.findAll();
		super.authenticate(null);
		Assert.isTrue(messages.contains(saved));
	}

	@Test
	public void sendMessage() {
		super.authenticate("customer2");

		Message m, saved;
		m = this.messageService.create();
		m.setSubject("Cristian");
		m.setBody("Este es un mensaje para el test");
		m.setPriority(0);
		m.setTag("tag");
		m.setEmailReceiver("pablo@hotmail.com");
		saved = this.messageService.save(m);

		//OutBox del actor sender
		final MessageBox outBox = this.messageBoxService.getOutBox(saved.getSender().getId());

		this.messageService.sendMessage(saved);

		Assert.isTrue(outBox.getMessages().contains(saved));

		//Comprobacion actor receiver
		final MessageBox inBox = this.messageBoxService.getInBox(saved.getReceiver().getId());
		Assert.isTrue(inBox.getMessages().contains(saved));
		super.authenticate(null);

	}

	@Test
	public void sendMessageWithSpam() {
		super.authenticate("customer2");

		Message message, saved;
		message = this.messageService.create();
		message.setSubject("Pedro");
		message.setBody("Este sex un mensaje para el test");
		message.setPriority(1);
		message.setTag("tag2");
		message.setEmailReceiver("pablo@hotmail.com");

		saved = this.messageService.save(message);

		//OutBox del actor sender
		final MessageBox outBox = this.messageBoxService.getOutBox(saved.getSender().getId());
		this.messageService.sendMessage(saved);

		Assert.isTrue(outBox.getMessages().contains(saved));

		//Comprobacion actor receiver
		final MessageBox spamBox = this.messageBoxService.getSpamBox(saved.getReceiver().getId());
		Assert.isTrue(spamBox.getMessages().contains(saved));

		super.authenticate(null);

	}

	@Test
	public void sendMessageBroadcast() {
		super.authenticate("admin");

		Message message, saved;
		Collection<Message> messages;
		message = this.messageService.create();
		message.setSubject("Pedro");
		message.setBody("Este es un mensaje para el test");
		message.setPriority(1);
		message.setTag("tag2");
		message.setEmailReceiver("@");

		saved = this.messageService.save(message);

		messages = this.messageService.findAll();
		Assert.isTrue(messages.contains(saved));
		final MessageBox outBox = this.messageBoxService.getOutBox(saved.getSender().getId());
		this.messageService.sendBroadcastMessage(saved);

		Assert.isTrue(outBox.getMessages().contains(saved));

		final List<Actor> actors = this.actorService.findAll();
		actors.remove(saved.getSender());
		for (int i = 0; i < actors.size(); i++) {
			final MessageBox inBox = this.messageBoxService.getInBox(actors.get(i).getId());
			Assert.isTrue(inBox.getMessages().contains(saved));
		}

		super.authenticate(null);

	}

	@Test
	public void testDeleteMessage() {
		super.authenticate("customer2");

		Message m, saved;
		m = this.messageService.create();
		m.setSubject("Pedro");
		m.setBody("Este sex un mensaje para el test");
		m.setPriority(1);
		m.setTag("tag");
		m.setEmailReceiver("cristian@hotmail.com");

		saved = this.messageService.save(m);
		final MessageBox trashBox = this.messageBoxService.getTrashBox(saved.getSender().getId());
		this.messageService.delete(saved);

		//Mensaje no borrado, sintoma de que trashBox no contenia el mensaje
		Assert.notNull(saved);

		Assert.isTrue(trashBox.getMessages().contains(saved));
		super.authenticate(null);
	}

}
