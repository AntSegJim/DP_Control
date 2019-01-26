
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Component
@Transactional
public class StringToPersonalRecordConverter implements Converter<String, PersonalRecord> {

	@Autowired
	private PersonalRecordRepository	PRRepository;


	@Override
	public PersonalRecord convert(final String source) {

		PersonalRecord pr;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				pr = null;
			else {
				id = Integer.valueOf(source);
				pr = this.PRRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return pr;
	}
}
