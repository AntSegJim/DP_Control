
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AttachmentRepository;
import domain.Attachment;

@Component
@Transactional
public class StringToAttachmentConverter implements Converter<String, Attachment> {

	@Autowired
	private AttachmentRepository	attachmentRepository;


	@Override
	public Attachment convert(final String source) {

		Attachment attachment;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				attachment = null;
			else {
				id = Integer.valueOf(source);
				attachment = this.attachmentRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return attachment;

	}

}
