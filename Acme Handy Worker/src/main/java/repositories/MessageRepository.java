
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select mb.messages From MessageBox mb where mb.id = ?1")
	public Collection<Message> getMessagesByBox(Integer id);

	@Query("select m From Message m where m.sender.id = ?1 OR m.receiver.id =?1")
	public Collection<Message> getMessagesByActor(Integer id);
}
