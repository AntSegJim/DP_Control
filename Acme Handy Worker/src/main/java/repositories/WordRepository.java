
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {

	@Query("select w from Word w where w.value=1")
	Collection<Word> goodWords();

	@Query("select w from Word w where w.value=0")
	Collection<Word> badWords();

	@Query("select UPPER(w.name) from Word w")
	Collection<String> words();

}
