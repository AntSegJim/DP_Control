
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PhaseRepository;
import domain.Phase;

@Component
@Transactional
public class StringToPhaseConverter implements Converter<String, Phase> {

	@Autowired
	private PhaseRepository	phaseRepository;


	@Override
	public Phase convert(final String source) {

		Phase phase;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				phase = null;
			else {
				id = Integer.valueOf(source);
				phase = this.phaseRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return phase;
	}

}
