
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AttachmentRepository;
import domain.Attachment;

@Service
@Transactional
public class AttachmentService {

	@Autowired
	private AttachmentRepository	attachmentRepository;


	public Attachment create() {
		final Attachment res = new Attachment();
		res.setLink("");
		return res;
	}

	public Attachment create(final String link) {
		final Attachment attachment = new Attachment();
		attachment.setLink(link);

		return attachment;
	}
	//listing
	public Collection<Attachment> findAll() {
		return this.attachmentRepository.findAll();
	}

	public Attachment findOne(final int attachmentId) {
		return this.attachmentRepository.findOne(attachmentId);
	}

	//updating
	public Attachment save(final Attachment attachment) {
		Assert.isTrue(attachment != null && attachment.getLink() != null && attachment.getLink() != "");
		return this.attachmentRepository.save(attachment);
	}
	//deleting
	public void delete(final Attachment attachment) {
		this.attachmentRepository.delete(attachment);
	}

}
