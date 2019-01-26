
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
import domain.SpamWord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SpamWordServiceTest extends AbstractTest {

	// ------------ Service under test ------------
	@Autowired
	private SpamWordService	spamWordService;


	//---------------------- Test ----------------------
	@Test
	public void testCreateSpamWord() {
		SpamWord spamWord;
		spamWord = this.spamWordService.create();
		spamWord.setName("palabrota");
		Assert.isTrue(spamWord.getName().equals("palabrota"));
	}

	@Test
	public void testSaveSpamWord() {
		SpamWord spamWord, saved;
		Collection<SpamWord> spamWords;
		spamWord = this.spamWordService.create();
		spamWord.setName("insulto");

		saved = this.spamWordService.save(spamWord);
		spamWords = this.spamWordService.findAll();
		Assert.isTrue(spamWords.contains(saved));
	}

	@Test
	public void testDeleteSpamWord() {
		SpamWord spamWord, saved;
		final Collection<SpamWord> spamWords;
		spamWord = this.spamWordService.create();
		spamWord.setName("malhablado");
		saved = this.spamWordService.save(spamWord);
		this.spamWordService.delete(saved);
		spamWords = this.spamWordService.findAll();
		Assert.isTrue(!spamWords.contains(saved));
	}

}
