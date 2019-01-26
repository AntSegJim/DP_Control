
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {

	@Query("select p from Picture p where p.tutorial.handyWorker.id = ?1")
	public Collection<Picture> pictureByHandy(Integer id);

	@Query("select p from Picture p where p.tutorial.id = ?1")
	public Collection<Picture> pictureByTutorial(Integer id);

}
