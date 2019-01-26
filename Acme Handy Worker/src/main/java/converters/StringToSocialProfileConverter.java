/*
 * StringToAuthorityConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import services.ProfileSocialNetworkService;
import domain.ProfileSocialNetwork;

@Component
@Transactional
public class StringToSocialProfileConverter implements Converter<String, ProfileSocialNetwork> {

	@Autowired
	private ProfileSocialNetworkService	profileService;


	@Override
	public ProfileSocialNetwork convert(final String text) {
		ProfileSocialNetwork result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.profileService.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
