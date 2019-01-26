
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TutorialRepository;
import domain.Tutorial;

@Component
@Transactional
public class StringToTutorialConverter implements Converter<String, Tutorial> {

	@Autowired
	private TutorialRepository	tutorialRepository;


	@Override
	public Tutorial convert(final String source) {

		Tutorial tutorial;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				tutorial = null;
			else {
				id = Integer.valueOf(source);
				tutorial = this.tutorialRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return tutorial;
	}
}
