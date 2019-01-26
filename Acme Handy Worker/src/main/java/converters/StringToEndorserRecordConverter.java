
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EndoserRecordRepository;
import domain.EndoserRecord;

@Component
@Transactional
public class StringToEndorserRecordConverter implements Converter<String, EndoserRecord> {

	@Autowired
	private EndoserRecordRepository	ERRepository;


	@Override
	public EndoserRecord convert(final String source) {

		EndoserRecord er;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				er = null;
			else {
				id = Integer.valueOf(source);
				er = this.ERRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return er;
	}
}
