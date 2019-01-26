
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EducationRecord;

@Component
@Transactional
public class EducationRecordToStringConverter implements Converter<EducationRecord, String> {

	@Override
	public String convert(final EducationRecord source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());
		return result;
	}

}
