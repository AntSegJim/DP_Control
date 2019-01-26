
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Attachment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AttachmentServiceTest extends AbstractTest {

	@Autowired
	private AttachmentService	attachmentService;


	@Test
	public void testCreateAttachment() {
		Attachment attachment;
		//Creando attachment

		attachment = this.attachmentService.create("https://github.com");

		Assert.isTrue(attachment.getLink() == "https://github.com");

	}

	@Test
	public void testSaveAttachment() {
		Attachment attachment, saved;
		Collection<Attachment> attachments;

		//Creando attachment

		attachment = this.attachmentService.create("https://github.com");

		saved = this.attachmentService.save(attachment);
		attachments = this.attachmentService.findAll();
		Assert.isTrue(attachments.contains(saved));

	}

	@Test
	public void testDeleteCategory() {
		final Attachment attachment, saved;
		Collection<Attachment> attachments;

		attachment = this.attachmentService.create("https://trello.com");

		saved = this.attachmentService.save(attachment);
		this.attachmentService.delete(saved);

		attachments = this.attachmentService.findAll();

		Assert.isTrue(!attachments.contains(saved));
	}

}
