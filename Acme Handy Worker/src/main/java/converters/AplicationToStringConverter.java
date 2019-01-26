
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Application;

@Component
@Transactional
public class AplicationToStringConverter implements Converter<Application, String> {

	@Override
	public String convert(final Application source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());
		return result;
	}

}
