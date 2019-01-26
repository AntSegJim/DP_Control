
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PictureRepository;
import domain.Picture;

@Service
@Transactional
public class PictureService {

	@Autowired
	private PictureRepository	PRepo;
	@Autowired
	private TutorialService		TService;


	//Metodo create
	public Picture create() {
		final Picture pic = new Picture();
		pic.setUrlPicture("");
		pic.setTutorial(this.TService.create());
		return pic;
	}

	//Metodo findAll
	public Collection<Picture> finaAll() {
		return this.PRepo.findAll();
	}
	public Picture findOne(final int PictureId) {
		return this.PRepo.findOne(PictureId);
	}
	public Picture save(final Picture picture) {
		Assert.isTrue(picture.getTutorial() != null && picture != null && picture.getUrlPicture() != null && picture.getUrlPicture() != "");
		return this.PRepo.save(picture);
	}
	public void delete(final Picture picture) {
		this.PRepo.delete(picture);
	}
	public Collection<Picture> picturesByHandy(final Integer id) {
		return this.PRepo.pictureByHandy(id);
	}

	public Collection<Picture> picturesByTutorial(final Integer id) {
		return this.PRepo.pictureByTutorial(id);
	}

}
