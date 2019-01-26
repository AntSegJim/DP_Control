
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

@Component
@Transactional
public class StringToMiscellaneousRecordConverter implements Converter<String, MiscellaneousRecord> {

	@Autowired
	private MiscellaneousRecordRepository	MRRepository;


	@Override
	public MiscellaneousRecord convert(final String source) {

		MiscellaneousRecord mr;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				mr = null;
			else {
				id = Integer.valueOf(source);
				mr = this.MRRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return mr;
	}
}
