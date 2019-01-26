
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FixUpTaskRepository;
import domain.FixUpTask;

@Component
@Transactional
public class StringToFixUpTaskConverter implements Converter<String, FixUpTask> {

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;


	@Override
	public FixUpTask convert(final String source) {

		FixUpTask fixUpTask;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				fixUpTask = null;
			else {
				id = Integer.valueOf(source);
				fixUpTask = this.fixUpTaskRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return fixUpTask;

	}

}
