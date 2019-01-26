
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CustomizableFinder;

@Repository
public interface CustomizableFinderRepository extends JpaRepository<CustomizableFinder, Integer> {

	@Query("select cf from CustomizableFinder cf where cf.resultNumber between 0 and 100")
	public CustomizableFinder getCustomizableFinder();
}
