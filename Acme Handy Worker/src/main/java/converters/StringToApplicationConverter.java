
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ApplicationRepository;
import domain.Application;

@Component
@Transactional
public class StringToApplicationConverter implements Converter<String, Application> {

	@Autowired
	private ApplicationRepository	applicationRepository;


	@Override
	public Application convert(final String source) {

		Application app;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				app = null;
			else {
				id = Integer.valueOf(source);
				app = this.applicationRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return app;
	}

}
