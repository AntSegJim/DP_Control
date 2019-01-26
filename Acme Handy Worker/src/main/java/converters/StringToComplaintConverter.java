
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ComplaintRepository;
import domain.Complaint;

@Component
@Transactional
public class StringToComplaintConverter implements Converter<String, Complaint> {

	@Autowired
	private ComplaintRepository	complaintRepository;


	@Override
	public Complaint convert(final String source) {

		Complaint complaint;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				complaint = null;
			else {
				id = Integer.valueOf(source);
				complaint = this.complaintRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return complaint;
	}

}
