
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EndoserRecord;

@Component
@Transactional
public class EndorserRecordToStringConverter implements Converter<EndoserRecord, String> {

	@Override
	public String convert(final EndoserRecord source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());
		return result;
	}

}
