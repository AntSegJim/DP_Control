
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select n from Note n where n.report.id=?1 ")
	public Collection<Note> findAllNoteReportId(int i);

	@Query("select n from Note n join n.report r join r.complaint c join c.fixUpTask f where f.customer.id=?1")
	public Collection<Note> findAllNoteCustomerId(int i);

	@Query("select n from Note n join n.report r join r.complaint c join c.fixUpTask f join f.application a where a.handyWorker.id=?1")
	public Collection<Note> findAllNoteHandyWorkerId(int i);
}
