
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select c.ticker from Curricula c")
	public Collection<String> tickerByCurricula();

	@Query("select c from Curricula c where c.handyWorker.id=?1")
	public Collection<Curricula> findAllHandyWorkerCurricula(Integer id);
}
