
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Word;

@Service
@Transactional
public class WordService {

	@Autowired
	private WordRepository	wordRepository;


	//Simple CRUD methods

	public Word create() {
		final Word word = new Word();
		word.setName("");
		word.setValue(0);
		return word;
	}

	public Collection<Word> findAll() {
		return this.wordRepository.findAll();
	}

	public Word findOne(final int wordId) {
		return this.wordRepository.findOne(wordId);
	}

	public Word save(final Word word) {
		final UserAccount user = LoginService.getPrincipal();
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"), "Comprobar que hay admin conectado");
		Assert.isTrue(word != null && word.getName() != null && word.getName() != "" && (word.getValue() == 0 || word.getValue() == 1));
		final Collection<String> words = this.words();
		Assert.isTrue(!words.contains(word.getName().toUpperCase()));
		return this.wordRepository.save(word);
	}

	public void delete(final Word word) {
		this.wordRepository.delete(word);
	}

	//Other bussines methods

	public Collection<Word> GoodWords() {
		return this.wordRepository.goodWords();
	}

	public Collection<Word> BadWords() {
		return this.wordRepository.badWords();
	}

	public Collection<String> words() {
		return this.wordRepository.words();
	}

}
