
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CategoryRepository;
import domain.Category;

@Component
@Transactional
public class StringToCategoryConverter implements Converter<String, Category> {

	@Autowired
	private CategoryRepository	categoryRepository;


	@Override
	public Category convert(final String source) {

		Category category;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				category = null;
			else {
				id = Integer.valueOf(source);
				category = this.categoryRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return category;
	}

}
