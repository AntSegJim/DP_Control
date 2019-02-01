
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.QuoletRepository;
import domain.Quolet;

@Component
@Transactional
public class StringToQuoletConverter implements Converter<String, Quolet> {

	@Autowired
	private QuoletRepository	quoletRepository;


	@Override
	public Quolet convert(final String text) {
		final Quolet result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.quoletRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
