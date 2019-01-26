
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageBoxServiceTest extends AbstractTest {

	@Autowired
	private MessageBoxService	messageBoxService;
	@Autowired
	private ActorService		actorService;


	@Test
	public void testCreateMessageBox() {
		super.authenticate("customer2");

		final MessageBox box;
		final Collection<Message> messages = new HashSet<>();
		box = this.messageBoxService.create();
		box.setName("Mensajes personales");
		box.setMessages(messages);

		Assert.isTrue(box.getName().equals("Mensajes personales"));
		Assert.isTrue(box.getName() != null);

		Assert.isTrue(box.getMessages().isEmpty());
		Assert.isTrue(box.getMessages() != null);

		Assert.isTrue(box.getActor() != null);
		super.authenticate(null);
	}

	@Test
	public void testSaveMessageBox() {
		super.authenticate("customer2");
		final Actor a = this.actorService.getActorByUserAccount(LoginService.getPrincipal().getId());
		final MessageBox box, saved;
		final Collection<MessageBox> boxes;
		final Collection<Message> messages = new HashSet<>();
		box = this.messageBoxService.create();
		box.setName("Mensajes personales");
		box.setMessages(messages);

		saved = this.messageBoxService.save(box);
		boxes = this.messageBoxService.findAll();
		super.authenticate(null);

		Assert.isTrue(boxes.contains(saved));
		Assert.isTrue(saved.getActor().equals(a));
	}
	@Test
	public void testDeleteMessageBox() {
		super.authenticate("customer2");
		final MessageBox box, saved;
		final Collection<MessageBox> boxes;
		final Collection<Message> messages = new HashSet<>();
		box = this.messageBoxService.create();
		box.setName("Mensajes personales");
		box.setMessages(messages);

		saved = this.messageBoxService.save(box);
		this.messageBoxService.delete(saved);
		boxes = this.messageBoxService.findAll();
		super.authenticate(null);
		Assert.isTrue(!boxes.contains(saved));
	}

	@Test
	public void testCreateSystemBox() {
		super.authenticate("referee1");
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		final MessageBox trashBox = this.messageBoxService.getTrashBox(a.getId());
		final MessageBox spamBox = this.messageBoxService.getSpamBox(a.getId());
		final MessageBox inBox = this.messageBoxService.getInBox(a.getId());
		final MessageBox outBox = this.messageBoxService.getOutBox(a.getId());

		Assert.isTrue(trashBox == null && spamBox == null && inBox == null && outBox == null);

		this.messageBoxService.createMessageBoxSystem(a);

		final MessageBox trashBox2 = this.messageBoxService.getTrashBox(a.getId());
		final MessageBox spamBox2 = this.messageBoxService.getSpamBox(a.getId());
		final MessageBox inBox2 = this.messageBoxService.getInBox(a.getId());
		final MessageBox outBox2 = this.messageBoxService.getOutBox(a.getId());

		Assert.isTrue(trashBox2 != null && spamBox2 != null && inBox2 != null && outBox2 != null);

		super.authenticate(null);

	}

}
