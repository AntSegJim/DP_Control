
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EducationRecordRepository;
import domain.EducationRecord;

@Component
@Transactional
public class StringToEducationRecordConverter implements Converter<String, EducationRecord> {

	@Autowired
	private EducationRecordRepository	ERRepository;


	@Override
	public EducationRecord convert(final String source) {

		EducationRecord educationRecord;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				educationRecord = null;
			else {
				id = Integer.valueOf(source);
				educationRecord = this.ERRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return educationRecord;
	}

}
