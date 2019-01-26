
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository	SRepo;


	//Metodo create
	public Section create() {
		final Section s = new Section();
		s.setNumber(0);
		s.setTitle("");
		s.setPieceOfText("");
		return s;
	}

	//Metodo findAll
	public Collection<Section> findAll() {
		return this.SRepo.findAll();
	}
	public Section findOne(final int SectionId) {
		return this.SRepo.findOne(SectionId);
	}
	public Section save(final Section section) {
		Assert.isTrue(section != null && section.getTitle() != null && section.getTitle() != "");
		return this.SRepo.save(section);
	}
	public void delete(final Section s) {
		this.SRepo.delete(s);
	}

	//Lista con las secciones de un tutorial
	public List<Section> sectionsFromTutorial(final int tutorialId) {
		return this.SRepo.sectionsFromTutorial(tutorialId);
	}
}
