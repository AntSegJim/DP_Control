
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Endorsement;

@Component
@Transactional
public class EndorsementToStringConverter implements Converter<Endorsement, String> {

	@Override
	public String convert(final Endorsement endorse) {
		String result;

		if (endorse == null)
			result = null;
		else
			result = String.valueOf(endorse.getId());
		return result;
	}

}
