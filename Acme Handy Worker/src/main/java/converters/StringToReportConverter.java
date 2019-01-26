
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ReportRepository;
import domain.Report;

@Component
@Transactional
public class StringToReportConverter implements Converter<String, Report> {

	@Autowired
	private ReportRepository	reportRepository;


	@Override
	public Report convert(final String source) {

		Report report;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				report = null;
			else {

				id = Integer.valueOf(source);
				report = this.reportRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return report;
	}

}
