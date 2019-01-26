
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SponsorshipRepository;
import domain.Sponsorship;

@Component
@Transactional
public class StringToSponsorshipConverter implements Converter<String, Sponsorship> {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	@Override
	public Sponsorship convert(final String source) {

		Sponsorship sponsorship;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				sponsorship = null;
			else {
				id = Integer.valueOf(source);
				sponsorship = this.sponsorshipRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return sponsorship;
	}
}
