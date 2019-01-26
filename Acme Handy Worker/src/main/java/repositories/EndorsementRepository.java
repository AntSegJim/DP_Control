
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select e from Endorsement e where e.handyWorkerSender.id = ?1 or e.handyWorkerReceiver.id = ?1 or e.customerSender.id = ?1 or e.customerReceiver.id = ?1")
	public Collection<Endorsement> myEndorsements(int userAccountId);
}
