
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.NoteRepository;
import domain.Note;

@Component
@Transactional
public class StringToNoteConverter implements Converter<String, Note> {

	@Autowired
	private NoteRepository	noteRepository;


	@Override
	public Note convert(final String source) {

		Note note;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				note = null;
			else {
				id = Integer.valueOf(source);
				note = this.noteRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return note;

	}

}
