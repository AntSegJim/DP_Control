
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FinderRepository;
import domain.Finder;

@Component
@Transactional
public class StringToFinderConverter implements Converter<String, Finder> {

	@Autowired
	private FinderRepository	finderRepository;


	@Override
	public Finder convert(final String source) {

		Finder finder;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				finder = null;
			else {
				id = Integer.valueOf(source);
				finder = this.finderRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return finder;
	}

}
