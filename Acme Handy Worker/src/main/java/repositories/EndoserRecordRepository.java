
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.EndoserRecord;

@Repository
public interface EndoserRecordRepository extends JpaRepository<EndoserRecord, Integer> {

}
