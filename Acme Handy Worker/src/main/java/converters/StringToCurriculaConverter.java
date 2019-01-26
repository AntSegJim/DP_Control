
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CurriculaRepository;
import domain.Curricula;

@Component
@Transactional
public class StringToCurriculaConverter implements Converter<String, Curricula> {

	@Autowired
	private CurriculaRepository	curriculaRepository;


	@Override
	public Curricula convert(final String source) {

		Curricula curricula;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				curricula = null;
			else {
				id = Integer.valueOf(source);
				curricula = this.curriculaRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return curricula;
	}

}
