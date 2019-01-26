
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

	@Query("select t.section from Tutorial t where t.id=?1")
	public List<Section> sectionsFromTutorial(int tutorialId);
}
