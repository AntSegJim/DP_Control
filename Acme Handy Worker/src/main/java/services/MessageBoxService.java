
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageBoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	@Autowired
	private MessageBoxRepository	messageRepositoryBox;
	@Autowired
	private ActorService			actorService;


	public MessageBox create() {
		final MessageBox box = new MessageBox();
		box.setName("");
		box.setMessages(new HashSet<Message>());

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		box.setActor(a);

		//		if (user.getAuthorities().contains(Authority.CUSTOMER))
		//			box.setActor(this.CService.create());
		//		else if (user.getAuthorities().contains(Authority.HANDYWORKER))
		//			box.setActor(this.HWService.create());
		//		else if (user.getAuthorities().contains(Authority.REFEREE))
		//			box.setActor(this.RService.create());
		//		else if (user.getAuthorities().contains(Authority.SPONSOR))
		//			box.setActor(this.SService.create());
		//		else
		//			box.setActor(this.AService.create());

		return box;
	}

	//Linstin
	public Collection<MessageBox> findAll() {
		return this.messageRepositoryBox.findAll();
	}
	public MessageBox findOne(final int messageBoxId) {
		return this.messageRepositoryBox.findOne(messageBoxId);
	}

	//Update
	public MessageBox save(final MessageBox box) {

		final MessageBox result;

		//Assert.isTrue(box.getActor().equals(a));

		Assert.isTrue(!(box.getName().replace(" ", "").toUpperCase().equals("INBOX")));
		Assert.isTrue(!(box.getName().replace(" ", "").toUpperCase().equals("OUTBOX")));
		Assert.isTrue(!(box.getName().replace(" ", "").toUpperCase().equals("TRASHBOX")));
		Assert.isTrue(!(box.getName().replace(" ", "").toUpperCase().equals("SPAMBOX")));
		Assert.isTrue(box != null && box.getName() != null && box.getName() != "" && box.getActor() != null);
		result = this.messageRepositoryBox.save(box);

		return result;
	}
	//Crear cajas del sistema

	public void createMessageBoxSystem(final Actor a) {
		final MessageBox mb1 = new MessageBox();
		mb1.setName("In box");
		mb1.setActor(a);
		mb1.setMessages(new HashSet<Message>());
		this.messageRepositoryBox.save(mb1);

		final MessageBox mb2 = new MessageBox();
		mb2.setName("Out box");
		mb2.setActor(a);
		mb2.setMessages(new HashSet<Message>());
		this.messageRepositoryBox.save(mb2);

		final MessageBox mb3 = new MessageBox();
		mb3.setName("Spam box");
		mb3.setActor(a);
		mb3.setMessages(new HashSet<Message>());
		this.messageRepositoryBox.save(mb3);

		final MessageBox mb4 = new MessageBox();
		mb4.setName("Trash box");
		mb4.setActor(a);
		mb4.setMessages(new HashSet<Message>());
		this.messageRepositoryBox.save(mb4);

	}

	//delete
	public void delete(final MessageBox messageBox) {
		Assert.isTrue(messageBox.getName().replace(" ", "").toUpperCase() != "INBOX");
		Assert.isTrue(messageBox.getName().replace(" ", "").toUpperCase() != "OUTBOX");
		Assert.isTrue(messageBox.getName().replace(" ", "").toUpperCase() != "TRASHBOX");
		Assert.isTrue(messageBox.getName().replace(" ", "").toUpperCase() != "SPAMBOX");
		this.messageRepositoryBox.delete(messageBox);
	}

	public List<MessageBox> findMessageBoxActor(final int actorId) {
		return this.messageRepositoryBox.boxesActor(actorId);
	}

	public MessageBox getTrashBox(final int actorId) {
		return this.messageRepositoryBox.getTrashBox(actorId);
	}

	public MessageBox getInBox(final int actorId) {
		return this.messageRepositoryBox.getInBox(actorId);
	}

	public MessageBox getOutBox(final int actorId) {
		return this.messageRepositoryBox.getOutBox(actorId);
	}

	public MessageBox getSpamBox(final int actorId) {
		return this.messageRepositoryBox.getSpamBox(actorId);
	}
}
