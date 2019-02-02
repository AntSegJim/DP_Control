
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select q from Quolet q where q.fixUpTask.id = ?1")
	public Collection<Quolet> getAllQuoletsByFix(int id);

	@Query("select q from Quolet q where q.fixUpTask.id = ?1 and q.draftMode = 0")
	public Collection<Quolet> getAllQuoletsByFixPublished(int id);

	@Query("select count(q)*1.0/(select count(c) from Customer c) from Quolet q where q.draftMode = 0")
	public Double a();

	@Query("select count(q)*1.0/(select count(q2) from Quolet q2) from Quolet q where q.draftMode = 0")
	public Double b();

	@Query("select count(q)*1.0/(select count(q2) from Quolet q2) from Quolet q where q.draftMode = 1")
	public Double c();
}
