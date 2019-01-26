
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select UPPER(c.name) from Category c")
	public Collection<String> namesCategory();
	@Query("select c.soon from Category c where c.id=?1")
	public List<Category> categorySoonFromParent(int categoryId);
	@Query("select c from Category c where c.name = 'CATEGORY'")
	public Category rootCategory();
	@Query("select c from Category c where c.name=?1")
	public Category categoryByName(String name);

}
