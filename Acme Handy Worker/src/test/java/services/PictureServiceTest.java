
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Picture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PictureServiceTest extends AbstractTest {

	@Autowired
	private PictureService	PService;


	@Test
	public void testCreatePicture() {
		Picture picture;
		picture = this.PService.create();
		picture.setUrlPicture("http://google.com");
		Assert.isTrue(picture.getUrlPicture().equals("http://google.com"));
	}

	@Test
	public void testSavePicture() {
		Picture picture, saved;
		Collection<Picture> pictures;
		picture = this.PService.create();
		picture.setUrlPicture("http://amazom.com");

		saved = this.PService.save(picture);
		pictures = this.PService.finaAll();
		Assert.isTrue(pictures.contains(saved));
	}
	@Test
	public void testDeletePicture() {
		Picture picture, saved;
		Collection<Picture> pictures;
		picture = this.PService.create();
		picture.setUrlPicture("http://aliexpress.com");

		saved = this.PService.save(picture);
		this.PService.delete(saved);
		pictures = this.PService.finaAll();
		Assert.isTrue(!pictures.contains(saved));
	}

}
