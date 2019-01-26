
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RefereeRepository;
import domain.Referee;

@Component
@Transactional
public class StringToRefereeConverter implements Converter<String, Referee> {

	@Autowired
	private RefereeRepository	refereeRepository;


	@Override
	public Referee convert(final String source) {

		Referee referee;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				referee = null;
			else {
				id = Integer.valueOf(source);
				referee = this.refereeRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return referee;
	}

}
