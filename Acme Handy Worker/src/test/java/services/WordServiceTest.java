
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
import domain.Word;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WordServiceTest extends AbstractTest {

	@Autowired
	private WordService	wordService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateWord() {
		Word word;
		word = this.wordService.create();
		word.setName("good");
		word.setValue(1);
		Assert.isTrue(word.getName().equals("good"));
		Assert.isTrue(word.getValue() == 1 || word.getValue() == 0);

	}

	@Test
	public void testSaveWord() {
		super.authenticate("admin");
		Word word, saved;
		Collection<Word> words;
		word = this.wordService.create();
		word.setName("good");
		word.setValue(1);

		saved = this.wordService.save(word);
		words = this.wordService.findAll();
		Assert.isTrue(words.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDeleteWord() {
		super.authenticate("admin");
		Word word, saved;
		final Collection<Word> words;
		word = this.wordService.create();
		word.setName("good");
		word.setValue(1);
		saved = this.wordService.save(word);
		this.wordService.delete(saved);
		words = this.wordService.findAll();
		Assert.isTrue(!words.contains(saved));
		super.authenticate(null);
	}

}
